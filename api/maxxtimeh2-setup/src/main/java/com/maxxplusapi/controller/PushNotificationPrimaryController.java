package com.maxxplusapi.controller;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.annotation.PostConstruct;
import javax.net.ssl.SSLContext;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import com.activityplanner.Validations;
import com.maxxplusapi.ActivityPlannerApp;
import com.maxxplusapi.entity.Booking;
import com.maxxplusapi.service.BookingService;
import com.messagehandler.service.MessageHandlerService;

import javafx.application.Platform;
//import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


/**
 * Controller class for pop up window notification
 * 
 * @author Charissa, Sheddy
 * 
 *
 */

//@Controller
//@ComponentScan({"com.maxxplusapi","com.messagehandler","com.notifications","com.scheduler"})
/**
 *  
 *  This is the controller class for the Notification popup
 * @author Charrisa, Ijiehon
 * @version 1.5
 *
 */
@Configuration
@ComponentScan({"com.maxxplusapi","com.messagehandler","com.scheduler"})
public class PushNotificationPrimaryController  {

    //public void initialize(URL url, ResourceBundle rb) {
    //}
	/**
	 * Represents the no button
	 */
	@FXML
    private Button neinButton;
	/**
	 * Represents a scenePane
	 */
    @FXML
    private AnchorPane scenePane;
    /**
     * Represents the yes button
     */
    @FXML
    private Button jaButton;
    /**
     * Represents modify button
     */
    @FXML
    private Button ändernButton;
    /**
     * Repesents the area where notification text is displayed
     */
    @FXML 
    private TextArea txtAreaNotification;
    /**
     * Represents a stage
     */
    Stage stage;
    /**
     * Represents a bookingService variable
     */
    @Autowired
    BookingService bookingService;
    /**
     * Represents a messageHandlerService variable
     */
    @Autowired
    MessageHandlerService messageHandlerService;
    /**
     * Represents a ConfigurableApplicationContext variable
     */
    ConfigurableApplicationContext context;
   /**
    * Represent the name of booking as String 
    */
	private String h2_name = "";
	/**
	 * Represent the type of booking as String 
	 */
	private String h2_type = "";
	/**
	 * Represent the starttime of booking 
	 */
	private LocalTime h2_starttime = null;
	/**
	 * Represent the endtime of booking 
	 */
	private LocalTime h2_endtime = null;
	/**
	 * Represent the Date of booking 
	 */
	private LocalDate h2_date = null;
	/**
	 * Represent the remark of booking 
	 */
	private String remarks ="";
	/**
	 * Represent the project Id of booking  
	 */
	private int h2_projectId = 0;
	/**
	 * Represent the activity Id of booking 
	 */
	private int h2_activityId = 0;
	/**
	 * Represent the hours of booking 
	 */
	private Double h2_hours = 0.0;
	/**
	 * Represent the Id of booking
	 */
	private int h2_id = 0;
    
 /**
  * Sets values fetch from database and displays as String in the Notification popup
  * @param bookingToShow The bookingToshow will get all values from Booking class and store it in the defined variables
  */
    public void initData(Booking bookingToShow) {
		System.out.println("INSIDE INIT DATA OF PUSH NOTIFICATIONS");
		System.out.println(bookingToShow.getName());
		
		h2_id = bookingToShow.getId();
		h2_name = bookingToShow.getName().toString();
		h2_type = bookingToShow.getType().toString();
		h2_starttime = bookingToShow.getStartTime();
		h2_endtime = bookingToShow.getEndTime();
		h2_date = bookingToShow.getDate();
		remarks =bookingToShow.getRemark();
		h2_projectId = bookingToShow.getProjectId();
		h2_activityId = bookingToShow.getActivityId();
		h2_hours = bookingToShow.getHours();
		
		//Identify which interval
			String interval = "";
			switch(h2_type) {
			  case "T":
			    // daily reminder
				  interval = "täglich";
			    break;
			  case "W":
			    // weekly reminder
				  interval = "wochentlich";
			    break;
			  case "M":
				// monthly reminder
				  interval = "monatlich";
				    break;
			  default:
			    // no reminder. These data will be fetched and directly pushed to maxx api
				  interval = "none";
			}
		
    		String reminder = "Erinnerung an datum "+ String.valueOf(h2_date)+": Haben Sie " +interval+" von "+h2_starttime+ " bis " +h2_endtime+" Uhr an dem '" + remarks + "' gearbeitet?";
    		
    		System.out.println(reminder);
    		txtAreaNotification.setText(reminder); 
    }
    /**
     * Used to initialise the stage
     */
    @FXML
    public void initialize() {
   
    		
    		int count = 0;
     	
	
    }
    
   
    
/**
 * Opens a confirmation popup when Nein button button is clicked. If clicked ok the window will be closed
 * @param event event occurs when Nein button is clicked
 */
    public void alertNein(ActionEvent event) {
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    	alert.setTitle("Abmelden");
    	alert.setContentText("Fenster wird geschlossen");

	    	if(alert.showAndWait().get() == ButtonType.OK) {
	    		closeProgram(event);
	    	}
    }
    /**
     * Notification popup stage is closed when method is called in alertNein(event) method
     * @param event Window is closed
     */
    public void closeProgram(ActionEvent event) {
    	 stage = (Stage) scenePane.getScene().getWindow();
    	 stage.close();
    }
/**
 * Forwards the fetched values from the Database that are stored in defined variables to the Maxx Api
 * using the functionality of the messageHandlerService class
 * @param event event occurs when ja button is clicked
 */
    @FXML
    public void alertJa(ActionEvent event) {

    		
    	//	try {
      		int count = 0;
	     
			
      			Booking booking = new Booking();
	     
			    	booking.setId(h2_id);
			    	booking.setStartTime(h2_starttime);
			    	booking.setEndTime(h2_endtime);
			   // 	beginDate.setValue(tableColDate.getCellData(index));
			    booking.setDate(h2_date);
			    booking.setRemark(remarks);
			    booking.setName(h2_name);
			    booking.setType(h2_type);
			    booking.setProjectId(h2_projectId);
			    booking.setActivityId(h2_activityId);
			    booking.setHours(h2_hours);
			    
			    System.out.println("INSIDE ALERT JA");
			    System.out.println(booking.getId());
			    
			    messageHandlerService.handleAddBooking(booking);
			    
		
				closeProgram(event);
		}
      




    /**
     * Loads the Secondary Controller and sets the fetched values in the secondary controller's input fiels
     * @param event event occurs when Ändern button is clicked
     * @throws IOException IOException if failed or interrupted I/O operations
     * @throws ParseException ParseException if parsing error 
     */
    @FXML
    public void popupÄndern(ActionEvent event) throws IOException, ParseException{		    


    	
    	
    			// runLater
			Platform.runLater(()->{
				
				
				Group root;
			    try {
			    
			    	context = new SpringApplicationBuilder(PushNotificationsSecondaryController.class).profiles("activity-planner").run();
				     FXMLLoader loader = new FXMLLoader(
				    		    getClass().getResource(
				    		      "/secondary.fxml"
				    		    )
				    		  );
				     
				    loader.setControllerFactory(clazz -> context.getBean(clazz));
				     
				     Stage stage = new Stage(StageStyle.DECORATED);
				     stage.setScene(
				       new Scene(loader.load())
				     );
				     
				     
				     	int count = 0;
				
		     	
				     	Booking booking = new Booking();
				     
						    	booking.setId(h2_id);
						    	booking.setStartTime(h2_starttime);
						    	booking.setEndTime(h2_endtime);
						    booking.setDate(h2_date);
						    booking.setRemark(remarks);
						    booking.setName(h2_name);
						    booking.setType(h2_type);
						    booking.setProjectId(h2_projectId);
						    booking.setActivityId(h2_activityId);
		    
				    	  PushNotificationsSecondaryController scontroller = loader.getController();
				    	  scontroller.initData(booking);
				    	  stage.show(); 
	
			        
			        
			    } catch (IOException e) {
			        e.printStackTrace();
			        
			    } catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}  
			
			});

		   



   }



//    @FXML
//   private void receiveData(MouseEvent event) {
//      // Step 1
//      Node node = (Node) event.getSource();
//      Stage stage = (Stage) node.getScene().getWindow();
//      // Step 2
//      Booking u = (Booking) stage.getUserData();
//      // Step 3
//      String name = u.getName();
//    
//    } 


}


