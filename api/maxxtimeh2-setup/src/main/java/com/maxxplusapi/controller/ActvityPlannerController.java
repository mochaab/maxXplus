package com.maxxplusapi.controller;




import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
//import java.time.Instant;
import java.time.LocalDate;
//import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
//import java.time.OffsetTime;
//import java.time.Period;
//import java.time.ZoneOffset;
//import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

//import javax.persistence.Column;

import javafx.collections.FXCollections;
import com.jfoenix.controls.JFXListView;
import com.maxxplusapi.ActivityPlannerApp;
import com.maxxplusapi.dao.BookingRepository;
import com.maxxplusapi.entity.Booking;
import com.maxxplusapi.entity.BookingActivity;
import com.maxxplusapi.service.BookingService;
import com.messagehandler.entity.Project;
import com.messagehandler.service.MessageHandlerService;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
//import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;


// Imports needed for resttemplate http requests
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.activityplanner.*;

//@Configuration


//@ComponentScan({"com.maxxplusapi","com.maxxplusapi.service","com.maxxplusapi.dao"})
//@ComponentScan(basePackageClasses = {com.maxxplusapi.service.BookingService.class, 
//		com.maxxplusapi.dao.BookingRepository.class,
//com.maxxplusapi.entity.Booking.class})

/**
 * This is the controller class for the User Interface
 * 
 * @author Charissa, Areeb, Cinzia
 * @version 1.5
 * 
 *
 */
@Controller
public class ActvityPlannerController {
    
	/**
	 * Creates an array of List type String for the drop down Combo boxe "Notification"
	 */
	List<String> listOfCycles = Arrays.asList("TÄGLICH","WÖCHENTLICH","MONATLICH","NONE");
	/**
	 * Creates an array of List type String for the drop down Combo boxe "Type"
	 */
	List<String> listOfTypes = Arrays.asList("FLEXTIME","LEAVE");
	
	/**
	 * Converts the Array List into an observable Array List to be used for Notification interval
	 */
    ObservableList<String>  intervalList = FXCollections.observableArrayList(listOfCycles);
    /**
     * Converts the Array List into an observable Array List to be used for Type of Schedules
     */
    ObservableList<String>  typeList = FXCollections.observableArrayList(listOfTypes);
    /*ObservableList<String>  projectTypeList = FXCollections.observableArrayList("ProjeKt Iot","Projekt Data & Analytics","Projekt SAP Intelligent Enterprise",
    		"Projekt Prozess & Betrieb"," Projekt Moderne Software Architekturen ");*/
    /**
     *  Represents a bookingService
     *  
     */
    @Autowired
	private BookingService bookingService;
    /**
     *  Represents a messagehandlerService
     */
    @Autowired
  	private MessageHandlerService messageHandlerService;
	
    /**
     * Represents a the Date picker input for Starting date
     */   
    @FXML
    private DatePicker beginDate;
    
    /**
     * Represent the text input for Starting time
     */
    @FXML
    private TextField beginTime;
    /**
     * Represents the button to clear of input fields
     */
    @FXML
    private Button clearButton;
    /**
     * Represents a the Date picker input for end date
     */
    @FXML
    private DatePicker endDate;
    /**
     * Represent the text input for End time
     */
    @FXML
    private TextField endTime;
    /**
     * Represents the text input for Reminder
     */
    @FXML
    private TextField reminderDesc;
    /**
     * Represent the button to Save data into the DB and the Table
     */
    @FXML
    private Button saveButton;
    /**
     * Represent the button to update data in the DB and the Table
     */
    @FXML
    private Button updateButton;
    /**
     * Represents the drop down for the Notification Interval
     */
    @FXML
    private ComboBox cycleComboBox;
    /**
     * Represents the drop down for the Projects
     */
    @FXML
    private ComboBox projectComboBox;
    /**
     * Represents a jfx lsit
     */
    @FXML
    private JFXListView<String> listofdata;
    /**
     * Represents a modify button 
     */
    @FXML
    private Button modifyButton;
    /**
     * Represents a resource bundle 
     */
    @FXML
    private ResourceBundle resources;
    /**
     * Represents a URL variable 
     */
    @FXML
    private URL url;
    /**
     * Represents a text input for Remarks
     */
    @FXML
    private TextField txtRemarks;
    /**
     * Represents the drop down for the Type of Schedule
     */
    @FXML
    private ComboBox comboType;
    /**
     * Represents the text input for Activity ID
     */
    @FXML
    private TextField txtActivityId;
    
    // Table View
    /**
     * Represents the table for Schedules containing columns with data
     */
    @FXML
    public TableView<BookingActivity> tableViewBooking;
    /**
     * Represents the column for Notification Reminder 
     */
    @FXML
    public TableColumn<BookingActivity, String> tableColName;
    /**
     * Represents the column for Calculated Hours
     */
    @FXML
    public TableColumn<BookingActivity, Double> tableColHours;
    /**
     * Represents the column for Notification Interval
     */
    @FXML
    public TableColumn<BookingActivity, String> tableColType;
    /**
     * Represents the column for Projects
     */
    @FXML
    public TableColumn<BookingActivity, String> tableColProject;
    /**
     * Represents the column for Start date
     */
    @FXML
    public TableColumn<BookingActivity, String> tableColDate;
    /**
     * Represents the column for Start Time
     */
    @FXML
    public TableColumn<BookingActivity, String> tableColSTime;
    /**
     * Represents the column for Start Time
     */
    @FXML
    public TableColumn<BookingActivity, String> tableColETime;
    /**
     * Represents the hidden column for Id for the Schedules in the DB
     * 
     */
    @FXML
    public TableColumn<BookingActivity, Integer> tableID;
    /**
     * Represents the column for Remarks
     */
    @FXML
    public TableColumn<BookingActivity, String> tableColRemarks;
    /**
     * Represents the column for Activity ID
     */
    @FXML
    public TableColumn<BookingActivity, Integer> tableColActivityId;
    /**
     * Represents an type integer variable for the ID
     */ 
    private int CID;
    /**
     * Represents a type BookingActivity variable for selecting the table row by ID
     */
    BookingActivity index;
    /**
     * Represents a Object of HashMap
     */
    HashMap<String,Integer> hash_map_projects = new HashMap<>();
    
    // Login to messageHandler
	//String[] cred = messageHandlerService.handleLogin(messageHandlerService.getCredentials());
	
    /**
     * This method is used to initialise  all the controls in this controller class which cannot be done from FXMl
     * @throws IOException if failed or interrupted I/O operations
     * @throws ParseException if parsing error
     */ 
    @FXML
    public void initialize() throws IOException, ParseException {
    		reminderDesc.setText("FLEXTIME"); //default type
    		tableID.setVisible(false);
     
        cycleComboBox.setItems(intervalList);
        cycleComboBox.getSelectionModel().select(0);
     
        comboType.setItems(typeList);
        comboType.getSelectionModel().select(0);
        
        projectComboBox.setItems(getComboProjects());
        projectComboBox.getSelectionModel().select(0);
        updateButton.setVisible(false);
        
        LoadData();
    }
    
    /**
     * This method sets the values of each column and individual items in table. Additionally this method is called from
     * intialize() to initialise the the values of the columns in the table
     * After every newly added or updated schedule this method is called so that the column values and the table can set the new or updated values
     * 
     * @throws IOException if failed or interrupted I/O operations
     * @throws ParseException if parsing error
     */
    public void LoadData() throws IOException, ParseException {
    	
        tableColName.setCellValueFactory(new PropertyValueFactory<>("Name"));
    		tableColRemarks.setCellValueFactory(new PropertyValueFactory<>("Remark"));
        tableColDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
        tableColSTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        tableColETime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        tableColHours.setCellValueFactory(new PropertyValueFactory<>("Hours"));
        tableColType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        tableID.setCellValueFactory(new PropertyValueFactory<>("Id"));
        tableColProject.setCellValueFactory(new PropertyValueFactory<>("Project"));
        tableColActivityId.setCellValueFactory(new PropertyValueFactory<>("ActivityId"));

        tableViewBooking.setItems(getTableBookings());
    }
    
    /**
     * This method is responsible for adding new schedules. The values of the input fields eg name, type, remarks, activityId(int), ProjectName are taken as String
     * by the use of get.Text()/get.Value
     * The inputs for beginTime and endTime are also taken as String then parsed as type LocalTime. 
     * Additionally we use class DateTimeFormatter to format Time as ("HH:mm") pattern.
     * To calculate the hours, we create two instances("from" and "to") of our "Parsed" LocalTimes("dt_time_start" and "dt_time_end") from the passed values of hour and minute and second
     * We use java class Duration to get the quantity of time from the two instances (from and to) in terms of seconds.
     * The we initialise a variable "hours" type float and get the seconds from the duration and convert it to hours by dividing by 3600.
     * Moreover, to display the Time as String we format the LocalTimes again and store it in String variable as "Stime" and "Etime"
     * For the Datepicker("beginDate") we take the value as type LocalDate then convert it to type Date by using java.sql.data which creates an instance of Date from an LocalDate object
     * However we format the Date object to pattern "YYYY-MM-DD" using DateFormat class and store it in String variable as "output"
     * Lastly, we create an Object of Booking class which sets all our inputed formatted values and then pass this object to createBooking method of class BookingService.
     * Limitation: We do not have implementation of Schedule type "LEAVE" which requires the Datepicker endDate which is not functional yet.
     * @param event occurs when the Speichern Button is pressed. 
     */
    @FXML
    void addData(MouseEvent event) {
    	Validations v = new Validations();
	 	
	   	if(v.checkIfNull((String)comboType.getValue(), comboType.getId()) || 
	   		v.checkIfNull((String)cycleComboBox.getValue(), cycleComboBox.getId()) ||
	   		v.checkIfNull(txtRemarks.getText(), txtRemarks.getId()) ||
	   		v.checkIfNull((String)projectComboBox.getValue(), projectComboBox.getId()) ||
	   		v.checkIfNull(txtActivityId.getText(), txtActivityId.getId()) ||
	   		v.checkIfNull(beginTime.getText(), beginTime.getId()) ||
	   		v.checkIfNull(endTime.getText(), endTime.getId()) 
	   			
	   	  ) {
	   		System.out.println("There is a null");
	   	} else {
	 
	  
		    	if (validate()) {
		    		try {

				    	    	// Data to be passed to api
				    	    	String name = (String)comboType.getValue(); //reminderDesc.getText() ;
				    	    	String type = (String)cycleComboBox.getValue();
				    	   
				    	    String remarks = txtRemarks.getText() ;  //text field for notification
				    	    	String projectName = (String)projectComboBox.getValue();
				    	    	int projectId = getComboProjectId(projectName); // Lookup table to be prepared
				    	    	int activityId = Integer.valueOf(txtActivityId.getText());
				    	    
				    	    	
				    	    	String beginTimeStr = beginTime.getText();
				    	   	String endTimeStr = endTime.getText();
				    	    	
				    	    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
				    	    	LocalTime dt_time_start = LocalTime.parse(beginTimeStr, formatter);
				    	    	LocalTime dt_time_end = LocalTime.parse(endTimeStr, formatter);
				    	
				    	    	LocalTime from = LocalTime.of(dt_time_start.getHour(), dt_time_start.getMinute(), dt_time_start.getSecond());
				    	    	LocalTime to = LocalTime.of(dt_time_end.getHour(), dt_time_end.getMinute(), dt_time_start.getSecond());
				    	    	
				    	    	String Stime = dt_time_start.format(DateTimeFormatter.ofPattern("HH:mm:ss")); // start time input HH:mm:ss"
				    	    	String Etime = dt_time_end.format(DateTimeFormatter.ofPattern("HH:mm:ss"));   // end time input  HH:mm:ss"
				    	    	
				    	    	Duration duration = Duration.between(from, to);
				    	    	//LocalTime.MIDNIGHT.plus(hours).format(DateTimeFormatter.ofPattern("HH:mm:ss"));
				    	    	
				    	    	float hours = (float) (duration.getSeconds()/60.0/60.0);
				    	    	DecimalFormat df1 = new DecimalFormat("0.##");
				    	    	String hours_str = df1.format(hours);
				    	    	
				    	    	LocalDate datepicker = beginDate.getValue();
				    	    	//Instant instant = Instant.from(datepicker.atStartOfDay(ZoneId.systemDefault()));
				    	    	//Date date_with_time = Date.from(instant);
						Date date = java.sql.Date.valueOf(datepicker);
				    	     	
				    	    	DateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd"); //should convert date to yyyy- MM- dd
				    	    	String output = outputFormatter.format(date);
			    			
		    				Booking body = new Booking();
						body.setName(name);
						body.setType(String.valueOf(type.charAt(0)));
						body.setDate(LocalDate.parse(output));
						body.setStartTime(LocalTime.parse(Stime));
						body.setEndTime(LocalTime.parse(Etime));
						body.setRemark(remarks);
						body.setHours(hours);
						body.setProjectId(projectId);
						body.setActivityId(activityId);
					
			    			
			    			bookingService.createBooking(body);
						
			    			LoadData(); // initialize
							
						Alert alert2 = new Alert(AlertType.INFORMATION);
						alert2.setTitle("Aktivität wurde erfolgreich erstellt.");
						alert2.setHeaderText(null);
						alert2.setContentText("Aktivität wurde erfolgreich erstellt für  " + output + " um " + Stime + " Uhr");
						alert2.showAndWait();
							
						clearFields();
			    	
					} catch (Exception e) {
						throw new RuntimeException(e);
					} 
		    	}
	   	}
    }
 
    /**
     * 
     * This method is responsible for deleting the a schedule. The selected row is removed from the Database by passing the 
     * selected row ID on to the deletebookingbyId method of class bookingService. Additionally, the corresponding row is also removed from the booking table.
     * @param event This event occurs when the Delete Button is pressed. 
     * @throws IOException if failed or interrupted I/O operations
     *
     * 
     */
    @FXML
    void DeleteData(MouseEvent event)  throws IOException {
    	
    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    	alert.setTitle("Löschung von Aktivität");
    	Optional<ButtonType> result = alert.showAndWait();
	    	if(!result.isPresent() || result.get() != ButtonType.OK) {
	    		Alert deletionAlert = new Alert(AlertType.INFORMATION);
    			deletionAlert.setTitle("Löschung von Aktivität");
		  	deletionAlert.setHeaderText(null);
		  	deletionAlert.setContentText("Cancelled");
		  	deletionAlert.showAndWait();
	    	} else {
	    		tableViewBooking.getItems().removeAll(tableViewBooking.getSelectionModel().
	  	      			  getSelectedItem());
		      	
		      			  index = tableViewBooking.getSelectionModel().getSelectedItem();
		      			  
		      			  try {	  
		  	    			  	Alert deletionAlert = new Alert(AlertType.INFORMATION);
		  	    			  
	  	  	    			  if(bookingService.getBookings().size() > 0) {
	  	  	    				  	deletionAlert.setTitle("Löschung von Aktivität");
	  	  		    			  	deletionAlert.setHeaderText(null);
	  	  		    			  	deletionAlert.setContentText(bookingService.deleteBookingById(CID));
	  	  		    			  	deletionAlert.showAndWait();
	  	  	    			  }  
		      			  }
		      			  catch (Exception e) {
		      				  throw new RuntimeException(e); 
		      			  }
	    		
	    	}
  
    	
    	//delete selected table row 
    		
    }
    /**
     * When a row is selected from the table, the Speichern Button's visibility is set to false and Update Button to true.
     * The functionality of this method is exactly the same as the addData except that the Booking object is passed on to the 
     * updateBooking method of BookingService Class.
     * @param event This event occurs when the Update Button is pressed. 
     */
    @FXML
    void Edit(MouseEvent event) {
    	
	    	String postUrl = "http://localhost:9191/updateBooking";
	
	    	
	    String name = (String)comboType.getValue(); //reminderDesc.getText() ;
	    	String remarks = txtRemarks.getText() ;  //text field for notification
	    	String type = (String) cycleComboBox.getValue();  // drop down selection for daily, monthly, weekly
	    	String projectName = (String)projectComboBox.getValue();
	    	int projectId = getComboProjectId(projectName); 
	    	int activityId = Integer.valueOf(txtActivityId.getText());
	    	
	    	
	    	String beginTimeStr = beginTime.getText();
	   	String endTimeStr = endTime.getText();
	    	
	    	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
	    	LocalTime dt_time_start = LocalTime.parse(beginTimeStr, formatter);
	    	LocalTime dt_time_end = LocalTime.parse(endTimeStr, formatter);
	
	    	LocalTime from = LocalTime.of(dt_time_start.getHour(), dt_time_start.getMinute(), dt_time_start.getSecond());
	    	LocalTime to = LocalTime.of(dt_time_end.getHour(), dt_time_end.getMinute(), dt_time_start.getSecond());
	    	
	
	    	
	    	String Stime = dt_time_start.format(DateTimeFormatter.ofPattern("HH:mm:ss")); // start time input HH:mm:ss"
	    	String Etime = dt_time_end.format(DateTimeFormatter.ofPattern("HH:mm:ss"));   // end time input  HH:mm:ss"
	    	
	    	Duration duration = Duration.between(from, to);
	    	
	    	float hours = (float) (duration.getSeconds()/60.0/60.0); // calculated hours 
	    	DecimalFormat df1 = new DecimalFormat("0.##");
	    	String hours_str = df1.format(hours);
	    	
	    	
	    	
	    	LocalDate datepicker = beginDate.getValue(); //LocalDate cannot be directly taken so we convert to Date
	    	//Instant instant = Instant.from(datepicker.atStartOfDay(ZoneId.systemDefault()));
	    	//Date date = Date.from(instant); // Date format right now Tue Feb 25 00:00:00 CST 2014
	   	Date date = java.sql.Date.valueOf(datepicker);
	    	
	    	DateFormat outputFormatter = new SimpleDateFormat("yyyy-MM-dd"); //should convert date to yyyy- MM- dd
	    	String output = outputFormatter.format(date); //  date picker input converted   Tue Feb 25 00:00:00 CST 2014 to 2014-02-25
    	
	    	
	    	Validations v = new Validations();
		 	
		   	if(v.checkIfNull((String)comboType.getValue(), comboType.getId()) || 
		   		v.checkIfNull((String)cycleComboBox.getValue(), cycleComboBox.getId()) ||
		   		v.checkIfNull(txtRemarks.getText(), txtRemarks.getId()) ||
		   		v.checkIfNull((String)projectComboBox.getValue(), projectComboBox.getId()) ||
		   		v.checkIfNull(txtActivityId.getText(), txtActivityId.getId()) ||
		   		v.checkIfNull(beginTime.getText(), beginTime.getId()) ||
		   		v.checkIfNull(endTime.getText(), endTime.getId()) 
		   			
		   	  ) {
		   		System.out.println("There is a null");
		   	} else {
	    	
	    	if (validate()) {
	    	
			    	try {
					
					
					Booking body = new Booking();
					body.setId(CID);
					body.setName(name);
					body.setType(String.valueOf(type.charAt(0)));
					body.setDate(LocalDate.parse(output));
					body.setStartTime(LocalTime.parse(Stime));
					body.setEndTime(LocalTime.parse(Etime));
					body.setRemark(remarks);
					body.setHours(hours);
					body.setProjectId(projectId);
					body.setActivityId(activityId);
					
					bookingService.updateBooking(body);
					
					
					LoadData(); // initialize
					
					Alert alert2 = new Alert(AlertType.INFORMATION);
					alert2.setTitle("Aktivität wurde erfolgreich aktualisiert.");
					alert2.setHeaderText(null);
					alert2.setContentText("Aktivität wurde erfolgreich aktualisiert für " + output + " um " + Stime + " Uhr" );
					alert2.showAndWait();
					
					saveButton.setVisible(true);
					clearFields();
					
			        
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
	    	
	    }
		  	}
    	
    }
    
    
    //getting the selected list to database
    
    /**
     * When ever a row is selected, it is selected by its corresponding ID in the database. The "index" variable stores the selected item(row) by index of the table "tabelViewBooking". Subsequently, the input fields are 
     * set corresponding to the column values of the selected row.
     * Additionally the "Speichern" button is set to act as a update Button because saveButton visibility is set to false and updateButton is set to true
     * @param event This event occurs when a row is click in the table "tableViewBooking" 
     */
    @FXML
    void getSelected(MouseEvent event) {
    	 	updateButton.setVisible(true);
    	 	saveButton.setVisible(false);
    	 	
	    	index= tableViewBooking.getSelectionModel().getSelectedItem();
	    	CID = tableID.getCellData(index);
	    	beginTime.setText(tableColSTime.getCellData(index).toString());
	    	endTime.setText(tableColETime.getCellData(index).toString());
	     beginDate.setValue(LocalDate.parse(tableColDate.getCellData(index).toString()));
	    	beginDate.setValue(LocalDate.parse(tableColDate.getCellData(index).toString()));
	    	endDate.setValue(LocalDate.parse(tableColDate.getCellData(index).toString()));
	    	txtRemarks.setText(tableColRemarks.getCellData(index));
	    	reminderDesc.setText(tableColName.getCellData(index).toString());
	    	cycleComboBox.setValue(tableColType.getCellData(index).toString());
	    	projectComboBox.setValue(tableColProject.getCellData(index).toString());
	    	txtActivityId.setText(tableColActivityId.getCellData(index).toString());
	    	
	    
 
    }
    
    /**
     *  This method is responsible for the clear button. All the input fields are declared null
     * @param event This event occurs when the clear button is clicked. 
     */
    @FXML
    void clearFields(MouseEvent event) {
		beginTime.setText(null);
		endTime.setText(null);
		beginDate.getEditor().clear();
		endDate.getEditor().clear();
		cycleComboBox.getSelectionModel().select(0);
		
		reminderDesc.setText("FLEXTIME");
		saveButton.setVisible(true);
		updateButton.setVisible(false);
		txtRemarks.setText("");;
		txtActivityId.clear();
		
	}

    /**
     * This method is used to clear all the fields after an event
     */  
   public void clearFields() {
		beginTime.setText(null);
		endTime.setText(null);
		beginDate.getEditor().clear();
		endDate.getEditor().clear();
		cycleComboBox.getSelectionModel().select(1);
		reminderDesc.setText("FLEXTIME");
		saveButton.setVisible(true);
		updateButton.setVisible(false);
		txtRemarks.clear();
		txtActivityId.clear();

		
		
	}

    
   /**
    *  Gets all the values after they are added to the Database t be displayed on to the table
    * @return Returns all the values from booking Activity in a Observable Array list to be added to the Table
    * @throws IOException if failed or interrupted I/O operations
    */ 
    public ObservableList<BookingActivity> getTableBookings() throws IOException {
    	ObservableList<BookingActivity> bookingTable = FXCollections.observableArrayList();

    		
		    	for(Booking b: bookingService.getBookings()) {
		    	   
		    /* bookingTable.add(new BookingActivity(b.getName(),b.getType(),
		    		 b.getDate(), b.getHours(), b.getName(), 
		    		 b.getProjectId(),b.getActivityId(),b.getProject()));*/
		    		    		
		    	 	bookingTable.add(new BookingActivity(b.getId(),
		    	 			b.getName(),
		    	 			getTypeName(b.getType()),
		    	 			b.getDate().toString(),
		    	 			b.getStartTime().toString(), 
		    	 			b.getEndTime().toString(), 
		    	 			b.getHours(), b.getRemark(),
		    	 			getComboProjectName(b.getProjectId()),
		    	 			b.getProjectId(), b.getActivityId()));
		    	}

		    	return bookingTable;    	
    }
    
    

    /**
     * Validate if any input field is left empty and if the time duration is positive
     * @return true if all fields are full and the calculated hours is positive float value
     */
    private boolean validate() {
    	
	    	String WARN_TITLE_NULL = "Gültige Werte eingeben";
	    	String WARN_TITLE_TIME = "Gültige Zeitdauer eingeben.";
	    	
	    	String WARN_DESC_NULL = "Felder sollen nicht leer sein";
	    	String WARN_DESC_TIME = "Beginnzeit soll kleiner als Endzeit sein";
    	
	    	Boolean status = false;
    		String beginTimeStr = beginTime.getText();
	    String endTimeStr = endTime.getText();
	
		String str_stime = beginTime.getText() == null ? "" : beginTime.toString();
		String str_etime = endTime.getText() == null ? "" : endTime.toString();
		String str_beg_date = beginDate == null ? "" : beginDate.toString();
		
		Validations v = new Validations();
	
		
		if (v.isTextNull(reminderDesc,txtRemarks, beginTime, endTime,beginDate,txtActivityId)) {
		
			
		 v.showAlertBox(WARN_TITLE_NULL,WARN_DESC_NULL+" "+v.getNullFields(reminderDesc,txtRemarks, beginTime, endTime,beginDate,txtActivityId));
		
		return false;
			
	} else {
			if(v.computeHours(beginTimeStr, endTimeStr) <= 0){
 				v.showAlertBox(WARN_TITLE_TIME,WARN_DESC_TIME);
   				return false;
	    	} else {
		    		return true;
		    	}
			
	}
	   		
}
    
		
    
//    private boolean FieldisEmpty() {
// 	   
// 	   if (beginTime.getText().isEmpty() | endTime.getText().isEmpty() | beginDate.getEditor().getText().isEmpty()
// 			  | reminderDesc.getText().isEmpty() | txtActivityId.getText().isEmpty() ) {
// 		   
// 			Alert alert = new Alert(AlertType.WARNING);
// 			alert.setTitle("Field is Empty");
// 			alert.setHeaderText(null);
// 			alert.setContentText("Please Enter Into The Fields" );
// 			alert.showAndWait();
// 			return false;
// 		   
// 	   }
// 	   
// 	   return true;
// 	   
//    }
    
    /**
     * switched to secondary
     * @throws IOException if failed or interrupted I/O operations
     */
    @FXML
    private void switchToSecondary() throws IOException {
       // ActivityPlannerApp.setRoot("/activityplanner/secondary");
    }
    /**
     * Gets name type
     * @param s String to be passed
     * @return The name type as String
     */
    private String getTypeName(String s) {
    		
    		String typeName = "";
    		
    		for(String t: listOfCycles) {
 
    			if (s.equals(String.valueOf(t.charAt(0)))){
    				typeName = t;
    			}

    		}
    		return typeName;
   }
    /**
     * This method is responsible to fill the Project combo box
     * @return The project as a observable Array list to be accessed from the combo box
     */
    private ObservableList<String>  getComboProjects() {
    		//hash_map can be used, but for convenience and deadline purposes, I used List<String>
    	
    		List<String> listOfTypes = new ArrayList<String>();
    		
        
    		for(Project p : messageHandlerService.handleGetProjects().getProjects()) {
    					listOfTypes.add(p.getProjectName());
		
    		}
    		
    		ObservableList<String>  projectList = FXCollections.observableArrayList(listOfTypes);
    		
    		return projectList;
    		
    }
    /**
     * This method is responsible to get Project Id's from the messageHandlerService class
     * @param projectName the Project name to get the corresponding ProjectId
     * @return the Project's id corresponding to the Project name
     */
    private int getComboProjectId(String projectName) {
    		// hash map is better used for more efficiency
    		int projectId = 0;
	    	for(Project p : messageHandlerService.handleGetProjects().getProjects()) {
				if(p.getProjectName().equals(projectName)) {
					projectId = p.getId();
					
				}
	    	}
	    	return projectId;
    }
    /**
     * This method is responsible to get Project names from the messageHandlerService class
     * @param projectId the ProjectId to get the corresponding Project name
     * @return the Project's name corresponding to the Id
     */
    private String getComboProjectName(int projectId) {
		// hash map is better used for more efficiency
		String projectName = "";
	    	for(Project p : messageHandlerService.handleGetProjects().getProjects()) {
				if(p.getId() == projectId) {
					projectName = p.getProjectName();
					
				}
	    	}
    	return projectName;
}
}
