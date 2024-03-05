package com.messagehandler.entity;

import java.util.Date;

import javax.persistence.Entity;

// import javax.persistence.Column;

//@Entity
public class Project {
	private int id;
	private String projectNumber;
	private String projectName;
	private String abbreviation;
	private Date startDate;
	private Date endDate;
	private String customerAbbreviation;
	private String contactPerson;
	
	
	
	
	
	
	public int getId() {
		return id;
	}






	public void setId(int id) {
		this.id = id;
	}






	public String getProjectNumber() {
		return projectNumber;
	}






	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}






	public String getProjectName() {
		return projectName;
	}






	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}






	public String getAbbreviation() {
		return abbreviation;
	}






	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}






	public Date getStartDate() {
		return startDate;
	}






	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}






	public Date getEndDate() {
		return endDate;
	}






	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}






	public String getCustomerAbbreviation() {
		return customerAbbreviation;
	}






	public void setCustomerAbbreviation(String customerAbbreviation) {
		this.customerAbbreviation = customerAbbreviation;
	}






	public String getContactPerson() {
		return contactPerson;
	}






	public void setContactPerson(String contactPerson) {
		this.contactPerson = contactPerson;
	}






	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		
		
		builder.append("Id: ").append(id).append(", ")
			   .append("projectNumber:").append(projectNumber).append(", ")
			   .append("projectName:").append(projectName).append(", ")
			   .append("abbreviation:").append(abbreviation).append(", ")
			   .append("startDate:").append(startDate).append(", ")
			   .append("endDate:").append(endDate).append(", ")
			   .append("customerAbbreviation:").append(customerAbbreviation).append(", ")
			   .append("contactPerson:").append(contactPerson).append(", ");
		return builder.toString();
	}
}
