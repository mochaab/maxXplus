package com.messagehandler.entity;

import java.util.Date;

import javax.persistence.Entity;

// import javax.persistence.Column;

//@Entity
public class MaxxBooking {
	//private int id;
	private String type;
	private Date date;
	private double hours;
	private String remarks;
	private int projectId;
	private int activityId;
	

	public MaxxBooking() {
		
	}
	
	public MaxxBooking(String type, Date date, double hours, String remarks, int projectId, int activityId) {
		this.type = type;
		this.date = date;
		this.hours = hours;
		this.remarks = remarks;
		this.projectId = projectId;
		this.activityId = activityId;
	
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getHours() {
		return hours;
	}

	public void setHours(double hours) {
		this.hours = hours;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public int getProjectId() {
		return projectId;
	}

	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	public int getActivityId() {
		return activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}
	

/*	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	} */


	
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		
		builder
			   .append("Type:").append(type).append(", ")
			   .append("Date:").append(date).append(", ")
			   .append("Hours:").append(hours).append(", ")
			   .append("Remarks:").append(remarks).append(", ")
			   .append("ProjectId:").append(projectId).append(", ")
			   .append("ActivityId:").append(activityId).append(", ");
		return builder.toString();
	}
}
