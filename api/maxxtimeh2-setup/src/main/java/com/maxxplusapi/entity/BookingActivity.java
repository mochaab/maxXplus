package com.maxxplusapi.entity;

import java.time.LocalDate;
import java.util.Date;

// import javax.persistence.Column;
/**
 * This class is used to define the data structure of a Schedule, containing methods which can be called from the ActivityplannerController
 * @author Charissa
 * @version 1.0
 * 
 *
 */
public class BookingActivity {
	
	/**
	 * Represents the id of BookingActivity
	 */
	private int id;
	/**
	 * Represents the type of schedule
	 */
	private String name;
	/**
	 * Represents the type of notification interval
	 */
	private String type;
	/**
	 * Represents the Start Date of schedule
	 */
	private String  date;
	/**
	 * Represents the start time of schedule
	 */
	private String startTime;
	/**
	 * Represents the end time of schedule
	 */
	private String endTime;
	/**
	 * Represents the hours taken to complete one schedule
	 */
	private double hours;
	/**
	 * Represents the  remarks for scheduling
	 */
	private String remark;
	/**
	 * Represents the Id of the project
	 */
	private int projectId;
	/**
	 * Represents the Id of the Activity
	 */
	private int activityId;
	/**
	 * Represents a project
	 */
	private String project;

	public BookingActivity(int id,String name, String type, String  date,  String startTime, String endTime,  double hours, String remark, String project, int projectId, int activityId) {
		this.name = name;
		
		this.id = id;
		this.name = name;
		this.type = type;
		this.date = date;
		this.startTime = startTime;
		this.endTime = endTime;
		this.hours = hours;
		this.remark = remark;
		this.projectId = projectId;
		this.activityId = activityId;
		this.project = project;
		
	
	}
	/**
	 * 
	 * @return The type of Notification Interval
	 */
	public String getType() {
		return type;
	}
	/**
	 * 
	 * @param type The type of Notification interval to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * 
	 * @return The Start Date of schedule
	 */
	public String  getDate() {
		return date;
	}
	/**
	 * 
	 * @param date The date to set it
	 */
	public void setDate(String  date) {
		this.date = date;
	}
	/**
	 * 
	 * @return The Start time of the schedule
	 */
	public String getStartTime() {
		return startTime;
	}
	/**
	 * 
	 * @param startTime The start time to set 
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	/**
	 * 
	 * @return The end time of the schedule
	 */
	public String getEndTime() {
		return endTime;
	}
	/**
	 * 
	 * @param endTime The end time to set 
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * 
	 * @return The calculated hours taken to complete a schedule
	 */
	public double getHours() {
		return hours;
	}
	/**
	 * 
	 * @param hours The hours to set
	 */
	public void setHours(double hours) {
		this.hours = hours;
	}
	/**
	 * 
	 * @return The notification remark of schedule
	 */
	public String getRemark() {
		return remark;
	}
	/**
	 * 
	 * @param remark The Remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	/**
	 * 
	 * @return The project Id
	 */
	public int getProjectId() {
		return projectId;
	}
	/**
	 * 
	 * @param projectId The project Id to set 
	 * 
	 */
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	/**
	 * 
	 * @return The Activity ID
	 */
	public int getActivityId() {
		return activityId;
	}
	/**
	 * 
	 * @param activityId The Activity ID to set
	 */
	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}
	
	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}


	

	/**
	 * 
	 * @return The ID of the schedule
	 */
	public int getId() {
		return id;
	}

	/**
	 * 
	 * @param id The Id to set 
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 * @return The Type of schedule
	 */
	public String getName() {
		return name;
	}

	/**
	 * 
	 * @param name The type of Schedule to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @return A string representation of a object of the class
	 * 
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		
		builder.append("Id: ").append(id).append(", ")
			   .append("Name").append(name).append(", ")
			   .append("Type").append(name).append(", ")
			   .append("Date").append(name).append(", ")
			   .append("startTime").append(name).append(", ")
			   .append("endTime").append(name).append(", ")
			   .append("Hours").append(name).append(", ")
			   .append("Remark").append(name).append(", ")
			   .append("ProjectId").append(name).append(", ")
			   .append("ActivityId").append(name).append(", ");
		return builder.toString();
	}
}
