package com.activityplanner;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;


/**
 * This class contains validations functions
 *
 */
public class Validations {
	
	String WARN_TITLE_NULL = "Enter valid values";
	String WARN_TITLE_TIME = "Enter valid time duration.";
	
	String WARN_DESC_NULL = "Fields should not be null:";
	String WARN_DESC_TIME = "StartTime should be less than EndTime";
	
	/**
	 * This function checks if one of the fields contains null
	 *
	 */
	public boolean isTextNull(TextField name, TextField remarks, TextField stime, TextField etime, DatePicker date, TextField txtActivityId){		
		return name.getText() == null || remarks.getText() == null || stime.getText() == null ||etime.getText() == null || date.getValue() == null||txtActivityId.getText() == null;
	}
	
	/**
	 * This function checks if one of the fields contains empty string
	 *
	 */

	public String getNullFields(TextField name, TextField remarks, TextField stime, TextField etime, DatePicker date, TextField txtActivityId){
		String nullValues = "";
		
		
		
		if (name.getText() == null || name.getText().equals("") ) {
			nullValues = "Name,"+ nullValues;
		}
		if (remarks.getText() == null || remarks.getText().equals("") ) {
			nullValues ="Remarks,"+ nullValues;
			
		}
		if (stime.getText() == null || remarks.getText().equals("")) {
			nullValues = "StartTime,"+ nullValues;
		}
		if (etime.getText() == null || remarks.getText().equals("")) {
			nullValues = "EndTime,"+ nullValues;
		}
		if (date.getValue() == null) {
			nullValues = "Date,"+ nullValues;
		} 
		
		
		  if (txtActivityId.getText() == null) { 
			  nullValues = "ActivityId,"+ nullValues; 
			  
		}
		 		
		return nullValues;
	  }	
	
		/*
		 * public boolean isTextNull(TextField txtActivityId) { return
		 * txtActivityId.getText() == null; }
		 * 
		 * public String getNullFields (TextField txtActivityId) { String nullValues =
		 * "";
		 * 
		 * if (txtActivityId.getText() == null) {
		 * 
		 * nullValues = "ActivityId,"+ nullValues;
		 * 
		 * } return nullValues; }
		 */
	
	/**
	 * This function shows alertbox warning
	 *
	 */
	public void showAlertBox(String title, String desc) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(desc);
		alert.showAndWait();
	}
	
	/**
	 * This function shows alertbox information
	 *
	 */
	public void showAlertBoxInfo(String title, String desc) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(null);
		alert.setContentText(desc);
		alert.showAndWait();
	}
	
	/**
	 * This function shows computes hours
	 *
	 */
	public double computeHours(String stime, String etime) {
		String beginTimeStr = stime;
	    String endTimeStr = etime;
	
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
		LocalTime dt_time_start = LocalTime.parse(beginTimeStr, formatter);
		LocalTime dt_time_end = LocalTime.parse(endTimeStr, formatter);
	
		LocalTime from = LocalTime.of(dt_time_start.getHour(), dt_time_start.getMinute(), dt_time_start.getSecond());
	
		LocalTime to = LocalTime.of(dt_time_end.getHour(), dt_time_end.getMinute(), dt_time_start.getSecond());
		Duration duration = Duration.between(from, to);
		//LocalTime.MIDNIGHT.plus(hours).format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		
		float hours = (float) (duration.getSeconds()/60.0/60.0);
		DecimalFormat df1 = new DecimalFormat("0.##");
		String hours_str = df1.format(hours);
		
		return hours;
	}
	
	/**
	 * Check if fields are null
	 * */
	public boolean checkIfNull(String s, String txtName) {
		if(s.equals("")) {
			
			showAlertBox("Warning", "Invalid value: "+txtName+" should not be empty.");
			return true;
		}
		System.out.println(" NOT");
		return false;
	}

}
