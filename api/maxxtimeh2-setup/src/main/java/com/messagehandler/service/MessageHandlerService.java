package com.messagehandler.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
//import org.openjfx.maxxplus.T;

import com.messagehandler.entity.LoginCredentials;
import com.messagehandler.entity.LoginDetails;
import com.messagehandler.entity.Project;
import com.messagehandler.entity.Projects;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

//import com.messagehandler.entity.MaxxBookings;
import com.messagehandler.entity.MaxxBooking;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.activityplanner.Validations;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.maxxplusapi.entity.BookingActivity;
import com.maxxplusapi.entity.User;
import com.maxxplusapi.service.BookingService;
import com.maxxplusapi.service.UserService;

// For ssl certificates
import javax.net.ssl.SSLContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

/**
 * This is the controller class for the User Interface
 * 
 * @author Charissa
 * 
 *
 */
@Controller

public class MessageHandlerService {
	/**
	 * This class provides services for accessing maxxsystem api
	 * 
	 */

	@Autowired
	BookingService bookingService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	private Environment env;
	
	@Value("${keystore.pw}")
	private String KEYSTOREPASSWORD;
	
	@Value("${keystore.cert}")
	private String KEYSTORECERT;
	
	private final String EP_LOGIN = "https://localhost:8042/maxx/external/api/login";
	private final String EP_FETCH_PROJECTS = "https://localhost:8042/maxx/api/projects/search";
	private final String EP_ADD_BOOKING = "";

	private int c_user_id;
	private String c_access_token;


	/**
	 * This function fetches credentials. Credentials are set upon installation of the system
	 * The table used for the credentials is called "User"
	 * @return userCred Type User
	 * */
	public User getCredentials() {
		User userCred = new User();
		if (userService.getUsers().size() > 0) {
			for(User u: userService.getUsers()) {
				userCred.setUsername(u.getUsername());
				userCred.setPassword(u.getPassword());
			}
		}
		
		return userCred;
	}
	
	/**
	 * Logs in to maxx system
     * @return credentials String[] which returns ([0] - userId:String, [1] - access_token, [2] - fullname:String )).  
	 */
	
    public String[] handleLogin(User user) {
    	String postUrl = EP_LOGIN;
    String name_last = "";
    String name_first = "";
    	try {
    	 	
        	LoginCredentials body = new LoginCredentials();
    		body.setLogin(user.getUsername().toString());
    		body.setCredentials(user.getPassword().toString());
    		
        	 final String password = ""+KEYSTOREPASSWORD+"";
             SSLContext sslContext = SSLContextBuilder
                     .create()
                     .loadTrustMaterial(ResourceUtils.getFile("classpath:"+KEYSTORECERT+""), password.toCharArray())
                     .build();

             CloseableHttpClient client = HttpClients.custom()
                     .setSSLContext(sslContext)
                     .build();
             
             HttpComponentsClientHttpRequestFactory requestFactory
                     = new HttpComponentsClientHttpRequestFactory();
             requestFactory.setHttpClient(client);
             
             
             RestTemplate restTemplate = new RestTemplate(requestFactory);
             
             HttpHeaders headers = new HttpHeaders();
    			headers.add("Content-Type", "application/json");
             
         	String json = new ObjectMapper().writeValueAsString(body);
    		
         	HttpEntity<String> entity = new HttpEntity<>(json, headers);
    		
            ResponseEntity<String> response = restTemplate.exchange(postUrl, HttpMethod.POST, entity, String.class);
          
            c_access_token = response.getHeaders().getFirst("Set-Cookie");
           
     	   	Gson gson = new Gson();

          	JSONObject responseObject = new JSONObject(response.getBody());
          	LoginDetails loginDetails = gson.fromJson(responseObject.toString(), LoginDetails.class);
          	c_user_id = loginDetails.getUserId();
          	name_last = loginDetails.getUserLastName();
          	name_first = loginDetails.getUserFirstName();
            
			} catch (Exception e) {
				throw new RuntimeException(e);
			} 
    	
    		String[] credentials = new String[3];
    		credentials[0] = String.valueOf(c_user_id);
    		credentials[1] = c_access_token;
    		credentials[2] = name_first+" "+name_last;
    		
    		return credentials;
    }
    
    /**
  	 * Add bookings which has notification cycle "N" or None  
  	 */
    public void handleAddBatchBooking() {
    		
    		String[] cred = handleLogin(getCredentials());
		c_user_id = Integer.parseInt(cred[0]);
		c_access_token = cred[1];
		
    		String userId =  "{\"userId\"}";
		String postUrl = "https://localhost:8042/maxx/api/users/1/bookings";
		
		JSONObject maxxBookings = new JSONObject();
		JSONArray maxxBookingArray = new JSONArray();
		JSONObject maxxBooking = new JSONObject();
		JSONObject jsonTarget = new JSONObject();
		 Validations v = new Validations();
		 
		 
		if (bookingService.getBookingByType("N").size() > 0) {
			for(com.maxxplusapi.entity.Booking bt: bookingService.getBookingByType("N")) {
				
				maxxBooking.put("type", bt.getName());
				maxxBooking.put("date", bt.getDate());
				maxxBooking.put("hours", bt.getHours());
				maxxBooking.put("remarks", bt.getRemark());
				maxxBooking.put("projectId", bt.getProjectId());
				maxxBooking.put("activityId", bt.getActivityId());
				
				maxxBookingArray.put(maxxBooking);		
				
				System.out.println("Adding schedule: '"+bt.getRemark().toString()+"' to maxx system");
			}
			maxxBookings.put("bookings",maxxBookingArray);
		
			jsonTarget.put("head", maxxBookings);
			
		  	try {
	    	 	
	    		
	        	 final String password = ""+KEYSTOREPASSWORD+"";
	             SSLContext sslContext = SSLContextBuilder
	                     .create()
	                     .loadTrustMaterial(ResourceUtils.getFile("classpath:"+KEYSTORECERT+""), password.toCharArray())
	                     .build();

	             CloseableHttpClient client = HttpClients.custom()
	                     .setSSLContext(sslContext)
	                     .build();
	             
	             HttpComponentsClientHttpRequestFactory requestFactory
	                     = new HttpComponentsClientHttpRequestFactory();
	             requestFactory.setHttpClient(client);
	             
	             
	             RestTemplate restTemplate = new RestTemplate(requestFactory);
	             
	             HttpHeaders headers = new HttpHeaders();
	    			headers.add("Cookie",c_access_token);
	    			headers.add("Content-Type", "application/json");
	                  	
	         	String json = jsonTarget.get("head").toString();
	         	
	        
	         	HttpEntity<String> entity = new HttpEntity<>(json, headers);
	           
	            ResponseEntity<String> response = restTemplate.exchange(postUrl, HttpMethod.POST, entity, String.class);
	            
	           	
	           
	            	 if(response.getStatusCodeValue() >= 200) {
	            		 	
	            		 System.out.println("SUCCESSFULLY ADDED!");
	         			
	            	  } else {
	            		 
	            		  System.out.println("WARNING: Cannot push the information to maxx system");
	            	  }
	            
				} catch (Exception e) {
					throw new RuntimeException(e);
				} 
		} else {
			
			
			System.out.println("WARNING: No schedule can be automatically pushed to maxx system.");
			
		}
			

  
	}
    /**
     * Add Booking
     * @param bookingNotif handles adding of a Booking
     */
    public void handleAddBooking(com.maxxplusapi.entity.Booking bookingNotif) {
		
		String[] cred = handleLogin(getCredentials());
		c_user_id = Integer.parseInt(cred[0]);
		c_access_token = cred[1];
	
		String userId =  "{\"userId\"}";
		String postUrl = "https://localhost:8042/maxx/api/users/1/bookings";
		
		JSONObject maxxBookings = new JSONObject();
		JSONArray maxxBookingArray = new JSONArray();
		JSONObject maxxBooking = new JSONObject();
		JSONObject jsonTarget = new JSONObject();
	
		//This for loop is just a delay
		for(com.maxxplusapi.entity.Booking bt: bookingService.getBookingByType(bookingNotif.getType())) {
			maxxBooking.put("type", bookingNotif.getName());
			maxxBooking.put("date", bookingNotif.getDate());
			maxxBooking.put("hours", bookingNotif.getHours());
			maxxBooking.put("remarks", bookingNotif.getRemark());
			maxxBooking.put("projectId", bookingNotif.getProjectId());
			maxxBooking.put("activityId", bookingNotif.getActivityId());			
		}
		maxxBookingArray.put(maxxBooking);		
		System.out.println("Adding schedule: '"+bookingNotif.getRemark().toString()+"' to maxx system");
		maxxBookings.put("bookings",maxxBookingArray);
	
		jsonTarget.put("head", maxxBookings);
		
		

    
	try {
	 	
		
    	 final String password = ""+KEYSTOREPASSWORD+"";
         SSLContext sslContext = SSLContextBuilder
                 .create()
                 .loadTrustMaterial(ResourceUtils.getFile("classpath:"+KEYSTORECERT+""), password.toCharArray())
                 .build();

         CloseableHttpClient client = HttpClients.custom()
                 .setSSLContext(sslContext)
                 .build();
         
         HttpComponentsClientHttpRequestFactory requestFactory
                 = new HttpComponentsClientHttpRequestFactory();
         requestFactory.setHttpClient(client);
         
         
         RestTemplate restTemplate = new RestTemplate(requestFactory);
         
         HttpHeaders headers = new HttpHeaders();
			headers.add("Cookie",c_access_token);
			headers.add("Content-Type", "application/json");
              	
     	String json = jsonTarget.get("head").toString();
     	
     	
     	
		HttpEntity<String> entity = new HttpEntity<>(json, headers);
       
        ResponseEntity<String> response = restTemplate.exchange(postUrl, HttpMethod.POST, entity, String.class);
        
     
		Validations v = new Validations();
		//v.showAlertBoxInfo("INFORMATION", "Adding schedule: '"+bookingNotif.getRemark().toString()+"' to maxx system");
        
	        	  if(response.getStatusCodeValue() >= 200) {
			 	        
					v.showAlertBox("INFORMATION", "'"+bookingNotif.getRemark().toString()+"' Erfolgreich zum maxx-System hinzugefügt!");
		   	 } else {
		   		 
			   	  System.out.println("Achtung: Die Informationen können nicht in das maxx-System übertragen werden");
			   	  Validations v2 = new Validations();
	       		  v2.showAlertBox("Achtung", "   NICHT erfolgreich!");
		   	  }

		} catch (Exception e) {
			Validations v3 = new Validations();
			v3.showAlertBox("Achtung",e.getMessage());
			
			throw new RuntimeException(e);
			  
		} 
}
    
    
    
    
    /**
 	 * Fetches projects from maxx system
 	 * @return projects type Projects
 	 */
	public Projects handleGetProjects() {
		
		String[] cred = handleLogin(getCredentials());
		c_user_id = Integer.parseInt(cred[0]);
		c_access_token = cred[1];
		
		
		
		String postUrl = EP_FETCH_PROJECTS;
	    	try {
	    	 	
	        	 final String password = ""+KEYSTOREPASSWORD+"";
	             SSLContext sslContext = SSLContextBuilder
	                     .create()
	                     .loadTrustMaterial(ResourceUtils.getFile("classpath:"+KEYSTORECERT+""), password.toCharArray())
	                     .build();
	
	             CloseableHttpClient client = HttpClients.custom()
	                     .setSSLContext(sslContext)
	                     .build();
	             
	             HttpComponentsClientHttpRequestFactory requestFactory
	                     = new HttpComponentsClientHttpRequestFactory();
	             requestFactory.setHttpClient(client);
	             
	             RestTemplate restTemplate = new RestTemplate(requestFactory);
	             
	             HttpHeaders headers = new HttpHeaders();
	    			headers.add("Cookie",c_access_token);
	    			headers.add("Content-Type", "application/json");
	    		
	    			HttpEntity<String> entity = new HttpEntity<>(headers);
	        
	            ResponseEntity<String> response = restTemplate.exchange(postUrl, HttpMethod.GET, entity, String.class);
	            
	         
		
	     	   	Gson gson = new Gson();
	
	          	JSONObject responseObject = new JSONObject(response.getBody());
	          	//System.out.println(responseObject);
	          	Projects projects = gson.fromJson(responseObject.toString(), Projects.class);
	          	Projects newProj = new Projects();
	            List<Project> ListOfProject = new ArrayList<Project>();
	          	// Iterate objects
	           	for(Project p: projects.getProjects()) {
	           		if(p.getContactPerson() != null ) {
		    				if (p.getContactPerson().equals(cred[2]) || p.getContactPerson().equals("")) {
		    					ListOfProject.add(p);
		    					
		    				}
	    				
	           		}
	   		    	}
	           	newProj.setProjects(ListOfProject);
	           	
	          	return newProj;
	
	            
				} catch (Exception e) {
					throw new RuntimeException(e);
				} 
	}
	
	public LocalDate parseDate(Date d) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
	            "MMM-dd-uuuu HH:mm:ss.SSS zzz(xx)", Locale.ROOT);
	    //String dd = "Nov-08-2019 07:00:28.190 UTC(+0000)";
		String dd = ""+d.toString()+" 07:00:28.190 UTC(+0000)";
		
	    LocalDate date = LocalDate.parse(dd, formatter);
	    
	    return date;
	}
	

	
	
	
	
	
	
}
