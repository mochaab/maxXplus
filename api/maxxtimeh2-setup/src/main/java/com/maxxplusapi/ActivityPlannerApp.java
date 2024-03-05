package com.maxxplusapi;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
// import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import com.maxxplusapi.service.BookingService;
import com.messagehandler.MessagehandlerApplication;


/**
 * JavaFX App
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.maxxplusapi","com.messagehandler","com.scheduler"})
/**
 * This is the controller class for the User Interface
 * 
 * @author Charissa, Areeb, Cinzia
 * 
 *
 */
public class ActivityPlannerApp extends Application {
	
	/*
	 * Login
	 * */
	

	
	ConfigurableApplicationContext context;

    private static Scene scene;
    @FXML
    
    @Override
    public void init() {
   
    	context = new SpringApplicationBuilder(ActivityPlannerApp.class).profiles("activity-planner").run();
    }
    
    @Override
    public void start(Stage stage) throws IOException {
    	
		
		  scene = new Scene(loadFXML("/activityplanner/primary"), 917, 683); 
    
		  stage.setScene(scene);
		  stage.setTitle("Willkommen bei Maxxplus"); 
		  stage.show();

}
	
	  public void setRoot(String fxml) throws IOException {
	  scene.setRoot(loadFXML(fxml)); }
	 
	  private Parent loadFXML(String fxml) throws IOException {
	    	System.out.println("F X M L ");
	    	
	    	//ApplicationContext applicationContext = SpringApplication.run(ActivityPlannerApp.class, fxml);
	    	//BookingService service = applicationContext.getBean(BookingService.class);
			
	    	System.out.println(fxml);
	        FXMLLoader fxmlLoader = new FXMLLoader(ActivityPlannerApp.class.getResource(fxml + ".fxml"));
	       // fxmlLoader.setControllerFactory();
	        fxmlLoader.setControllerFactory(clazz -> context.getBean(clazz));
	        
	        return fxmlLoader.load();
	    }
	  
	  public static void main(String[] args) {
		  //Initialise applicationframework of springboot
		 
	        
		  launch();
    }
	 
}