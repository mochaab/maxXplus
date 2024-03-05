package com.maxxplusapi;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.maxxplusapi.entity.Booking;
import com.maxxplusapi.service.BookingService;
import com.messagehandler.MessagehandlerApplication;
import com.messagehandler.service.MessageHandlerService;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
//@Configuration
//@EnableScheduling

@SpringBootApplication
@ComponentScan(basePackages = {"com.maxxplusapi","com.messagehandler","com.scheduler"})
public class PushNotificationApp extends Application {

	
	
	ConfigurableApplicationContext context;
	
	private static Scene scene;
	
	@Autowired
	private BookingService bookingService;
	
	 @Override
	 public void init() {
	   
	    	context = new SpringApplicationBuilder(PushNotificationApp.class).profiles("notifications").run();
	}

	@Override
	public void start(Stage stage) throws IOException {
		// Parent root = FXMLLoader.load(getClass().getResource("Scene2.fxml"));
		//scene = new Scene(loadFXML("/primary"), 500, 300);
		scene = new Scene(loadFXML("/primary"), 500, 210);
		stage.setScene(scene);
		stage.show();
	}

	  public void setRoot(String fxml) throws IOException {
		  scene.setRoot(loadFXML(fxml)); }
		 
		  private Parent loadFXML(String fxml) throws IOException {
		   
		    	//ApplicationContext applicationContext = SpringApplication.run(ActivityPlannerApp.class, fxml);
		    	//BookingService service = applicationContext.getBean(BookingService.class);
			  FXMLLoader fxmlLoader = new FXMLLoader(PushNotificationApp.class.getResource(fxml + ".fxml"));
			  	/*System.out.println(fxml);
		        FXMLLoader fxmlLoader = new FXMLLoader(PushNotificationApp.class.getResource(fxml + ".fxml"));
		       // fxmlLoader.setControllerFactory();
		        fxmlLoader.setControllerFactory(clazz -> context.getBean(clazz));*/
			  fxmlLoader.setControllerFactory(clazz -> context.getBean(clazz));
			  
			// This code fetches from h2 database and pass to notificationWindow
				// Certain conditions needs to be added here
				
							// Fetch all data from h2 database
							// taglich == machine clock
				// e.g when will the window display after the h2 data has been fetched?
				ApplicationContext applicationContext = SpringApplication.run(PushNotificationApp.class, fxml);
				BookingService service = applicationContext.getBean(BookingService.class);
			   
				int count = 11;//service.getBookings().size();
				for (Booking b: service.getBookings()) {
					count = b.getId();
				}
				
				
				String h2_name = service.getBookingById(count).getName().toString();
				String h2_date = service.getBookingById(count).getDate().toString();
				String h2_type = service.getBookingById(count).getType().toString();
				String h2_starttime = service.getBookingById(count).getDate().toString();
				String h2_endtime = service.getBookingById(count).getDate().toString();
				String h2_project = service.getBookingById(count).getRemark();
				String h2_hours = String.valueOf(service.getBookingById(count).getHours());
				// Date needs to be agreed if it will be string or date datatype from here
				
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
				
				
			    //String reminder = "Haben Sie "+interval+ " von " + h2_starttime + " bis " + h2_endtime + " fur " + h2_project + " abgehalten?";
				//String reminder = "Haben Sie "+h2_hours+ " Stunden lang  an dem '" + "Project" + "' gearbeitet?";
					
				String reminder = "Erinnerung an datum "+ h2_date+": Haben Sie " +interval+" von "+h2_starttime+ " bis " +h2_endtime+" Uhr an dem '" + h2_project + "' gearbeitet?";
			    System.out.println(reminder);
				
				//System.out.println(bookingService.getBookingById(4));
				  fxmlLoader.getNamespace()
		           .put("labelText", reminder);
				   
		        
		        return fxmlLoader.load();

     }
		  

	//@Scheduled(fixedDelay = 5000) -- error on main, Only no-arg methods may be annotated with @Scheduled
	public static void main(String[] args) {
		String reminder = "Did you had daily scrum at 9:15?";
		 System.out.println(
			      "Fixed delay task - " + System.currentTimeMillis() / 1000);
		// Read h2 database
		// Iterate on the data
		// Display it by interval
		launch();
	}
	
	
	public void scheduleFixedDelayTask() {
	    System.out.println(
	      "Fixed delay task - " + System.currentTimeMillis() / 1000);
	}

}