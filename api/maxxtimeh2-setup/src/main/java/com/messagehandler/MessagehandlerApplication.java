package com.messagehandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONObject;
//import org.openjfx.maxxplus.T;

import com.messagehandler.entity.MaxxBooking;
import com.messagehandler.service.MessageHandlerService;
import com.messagehandler.entity.LoginCredentials;

import javafx.application.Application;
import javafx.stage.Stage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.maxxplusapi.ActivityPlannerApp;
import com.maxxplusapi.entity.User;
import com.maxxplusapi.service.UserService;

// For ssl certificates
import javax.net.ssl.SSLContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
@ComponentScan(basePackages = {"com.maxxplusapi","com.messagehandler","com.notifications","com.scheduler"})
public class MessagehandlerApplication {

	
	ConfigurableApplicationContext context;
	
    @Autowired
    private MessageHandlerService messageHandlerService;
    
    

    
	public static void main(String[] args) throws IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException, CertificateException {
		
		System.out.println(args);
		for(String a: args) {
			System.out.println(a);
		}
		//SpringApplication.run(MessagehandlerApplication.class, args);
		
		
	
		ApplicationContext applicationContextMessageHandler = SpringApplication.run(MessagehandlerApplication.class, args);
		
		MessageHandlerService serviceMessageHandler = applicationContextMessageHandler.getBean(MessageHandlerService.class);
		UserService serviceUser = applicationContextMessageHandler.getBean(UserService.class);
		
	
	//	User userCredentials = serviceUser.getUserByID(1);
//		serviceMessageHandler.handleLogin(userCredentials);
		
		//serviceMessageHandler.handleGetProjects();
		//serviceMessageHandler.handleAddBatchBooking();
		
	
	
		System.out.println("Hello World this is done");
	}


    
    

    
    
    

}
