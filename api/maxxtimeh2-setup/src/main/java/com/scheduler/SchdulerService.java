package com.scheduler;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import com.activityplanner.Validations;
import com.maxxplusapi.PushNotificationApp;
import com.maxxplusapi.controller.PushNotificationPrimaryController;
import com.maxxplusapi.controller.PushNotificationsSecondaryController;
import com.maxxplusapi.entity.Booking;
import com.maxxplusapi.service.BookingService;
import com.messagehandler.service.MessageHandlerService;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import  com.sun.javafx.application.LauncherImpl;

@EnableAsync
@Controller
public class SchdulerService {

	/**
	 * This class triggers the notification service on a schedule: daily, weekly or monthly
	 * This program should be a microservice, a jar file and be put in the machine's startup upon installation
	 * */
	@Autowired
	BookingService bookingService;
	
	@Autowired
	MessageHandlerService messageHandlerService;
	
	ConfigurableApplicationContext context;
    
	private int i = 0;
	
	
	
	@Scheduled(fixedRate = 7000)
	public void sample() {
		System.out.println("Waiting for notification");
	}
	
	/**
	 * Execute notification services daily
	 * */
	@Scheduled(cron = "0/59 * * * * *")
	//@Scheduled(cron = "@daily")
	//@Scheduled(cron = "0 59 18 * * *")
	public void runDailyInterval() throws IOException, ParseException {
			i++;
	
			System.out.println(i);
			String notificationInterval = "N";
		
			if (i == 1) {
				Platform.setImplicitExit(false);
				Platform.startup(()->{
					Group root;
				    try {
				    //	System.out.println("BEFORE CONTEXT 1");
				    	context = new SpringApplicationBuilder(PushNotificationPrimaryController.class).profiles("scheduler").run();
					     FXMLLoader loader = new FXMLLoader(
					    		    getClass().getResource(
					    		      "/primary.fxml"
					    		    )
					    		  );
					     
					    loader.setControllerFactory(clazz -> context.getBean(clazz));
				   
				        Stage stage = new Stage();
				        root = new Group();
				        Scene scene = new Scene(root);

				        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/primary.fxml"));
				        Node node = loader.load();
				      
				        // Controller stuff
				        root.getChildren().add(node);
				        stage.setScene(scene);
				        //stage.setOnHidden(evt -> Platform.exit()); // make sure to completely shut down JavaFX when closing the window
				        PushNotificationPrimaryController scontroller = loader.getController();
				    	  	        
				        // Here we can enable machine time and compare to end time
				        // but the error of instantiatiing or initialising Platform or JFX should be fixed
				        if (bookingService.getBookingByType(notificationInterval).size() > 0) {
				        		System.out.println("inside booking type");
							for(com.maxxplusapi.entity.Booking bt: bookingService.getBookingByType(notificationInterval)) {
									
									Booking bookingToShow = new Booking();
									bookingToShow.setName(bt.getName());
									bookingToShow.setType(bt.getType());
									bookingToShow.setDate(bt.getDate());
									bookingToShow.setStartTime(bt.getStartTime());
									bookingToShow.setEndTime(bt.getEndTime());
									bookingToShow.setRemark(bt.getRemark());
									bookingToShow.setHours(bt.getHours());
									bookingToShow.setProjectId(bt.getProjectId());
									bookingToShow.setActivityId(bt.getActivityId());
									bookingToShow.setId(bt.getId());
									
									scontroller.initData(bookingToShow);
							}
							  
				        }
				        		       
				        stage.show();
				        
				    } catch (IOException e) {
				        e.printStackTrace();
				    }  
				  
				 	//messageHandlerService.handleAddBatchBooking();
				});  
			} else {
				Platform.setImplicitExit(false);
				Platform.runLater(()->{
					Group root;
				    try {
				    	System.out.println("BEFORE CONTEXT > 1");
				    	context = new SpringApplicationBuilder(PushNotificationPrimaryController.class).profiles("scheduler").run();
					     FXMLLoader loader = new FXMLLoader(
					    		    getClass().getResource(
					    		      "/primary.fxml"
					    		    )
					    		  );
					    loader.setControllerFactory(clazz -> context.getBean(clazz));
				        
					    Stage stage = new Stage();
				        root = new Group();
				        Scene scene = new Scene(root);

				       // FXMLLoader loader = new FXMLLoader(getClass().getResource("/primary.fxml"));
				        Node node = loader.load();
				      
				        // Controller stuff
				        root.getChildren().add(node);
				        stage.setScene(scene);
				        PushNotificationPrimaryController scontroller = loader.getController();
				    	  	        
				        // Here we can enable machine time and compare to end time
				        // but the error of instantiatiing or initialising Platform or JFX should be fixed
				        if (bookingService.getBookingByType(notificationInterval).size() > 0) {
				        		System.out.println("inside booking type");
							for(com.maxxplusapi.entity.Booking bt: bookingService.getBookingByType(notificationInterval)) {
									

									Booking bookingToShow = new Booking();
									bookingToShow.setName(bt.getName());
									bookingToShow.setType(bt.getType());
									bookingToShow.setDate(bt.getDate());
									bookingToShow.setStartTime(bt.getStartTime());
									bookingToShow.setEndTime(bt.getEndTime());
									bookingToShow.setRemark(bt.getRemark());
									bookingToShow.setHours(bt.getHours());
									bookingToShow.setProjectId(bt.getProjectId());
									bookingToShow.setActivityId(bt.getActivityId());
									bookingToShow.setId(bt.getId());
									
									scontroller.initData(bookingToShow);
							}
							  
				        }
				        		       
				        stage.show();
				        
				    } catch (IOException e) {
				        e.printStackTrace();
				    }  
				  
				 	
				});  
			} 
			
	}
	
	
	/**
	 * This code automatically sends the data tagged as "N" daily without notification.
	 * */
	@Scheduled(cron = "0 30 15 * * *")
	public void runAutomaticDaily() {
		messageHandlerService.handleAddBatchBooking();
	}
	
	/**
	 * Execute notification services weekly
	 * */
	
	//@Scheduled(cron = "@weekly")
	/*@Scheduled(cron = "0/10 * * * * *")
	public void runWeeklyInterval() {
		System.out.println("Weekly Interval");
		String notificationInterval = "W";
		Platform.startup(()->{
			Group root;
		    try {
		    
		        Stage stage = new Stage();
		        root = new Group();
		        Scene scene = new Scene(root);

		        FXMLLoader loader = new FXMLLoader(getClass().getResource("/primary.fxml"));
		        Node node = loader.load();
		      
		        
		        // Controller stuff
		        root.getChildren().add(node);
		        stage.setScene(scene);
		        stage.setOnHidden(evt -> Platform.exit()); // make sure to completely shut down JavaFX when closing the window
		        PushNotificationPrimaryController scontroller = loader.getController();
		    	  	        
		        System.out.println(bookingService.getBookingByType(notificationInterval).size());
		        if (bookingService.getBookingByType(notificationInterval).size() > 0) {
		        		System.out.println("inside booking type");
					for(com.maxxplusapi.entity.Booking bt: bookingService.getBookingByType(notificationInterval)) {
						
							Booking bookingToShow = new Booking();
							bookingToShow.setName(bt.getName());
							bookingToShow.setType(bt.getType());
							bookingToShow.setDate(bt.getDate());
							bookingToShow.setStartTime(bt.getStartTime());
							bookingToShow.setEndTime(bt.getEndTime());
							bookingToShow.setRemark(bt.getRemark());
							bookingToShow.setHours(bt.getHours());
							bookingToShow.setProjectId(bt.getProjectId());
							bookingToShow.setActivityId(bt.getActivityId());
							bookingToShow.setId(bt.getId());
							
							scontroller.initData(bookingToShow);
					}
					  
		        }
		        		       
		        stage.show();
		        
		    } catch (IOException e) {
		        e.printStackTrace();
		    }  
			
		 	//messageHandlerService.handleAddBatchBooking();
		});
	} */
	
	
	/**
	 * Execute notification services monthly
	 * */
	@Scheduled(cron = "@monthly")
	public void runMonthlyInterval() {
		String notificationInterval = "M";
		Platform.startup(()->{
			Group root;
		    try {
		    
		        Stage stage = new Stage();
		        root = new Group();
		        Scene scene = new Scene(root);

		        FXMLLoader loader = new FXMLLoader(getClass().getResource("/primary.fxml"));
		        Node node = loader.load();
		      
		        
		        // Controller stuff
		        root.getChildren().add(node);
		        stage.setScene(scene);
		        stage.setOnHidden(evt -> Platform.exit()); // make sure to completely shut down JavaFX when closing the window
		        PushNotificationPrimaryController scontroller = loader.getController();
		    	  	        
		        System.out.println(bookingService.getBookingByType(notificationInterval).size());
		        if (bookingService.getBookingByType(notificationInterval).size() > 0) {
		        		System.out.println("inside booking type");
					for(com.maxxplusapi.entity.Booking bt: bookingService.getBookingByType(notificationInterval)) {
						
							Booking bookingToShow = new Booking();
							bookingToShow.setName(bt.getName());
							bookingToShow.setType(bt.getType());
							bookingToShow.setDate(bt.getDate());
							bookingToShow.setStartTime(bt.getStartTime());
							bookingToShow.setEndTime(bt.getEndTime());
							bookingToShow.setRemark(bt.getRemark());
							bookingToShow.setHours(bt.getHours());
							bookingToShow.setProjectId(bt.getProjectId());
							bookingToShow.setActivityId(bt.getActivityId());
							bookingToShow.setId(bt.getId());
							
							scontroller.initData(bookingToShow);
					}
					  
		        }
		        		       
		        stage.show();
		        
		    } catch (IOException e) {
		        e.printStackTrace();
		    }  
			
		 	//messageHandlerService.handleAddBatchBooking();
		});
	}
}
