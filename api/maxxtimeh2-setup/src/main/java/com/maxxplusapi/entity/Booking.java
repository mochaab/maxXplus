package com.maxxplusapi.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * This class represents a Booking
 * @author Charissa, Nasim
 * @version 1.0
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Booking {
	/**
	 * Represents an id of Booking
	 */
	@Id
	@GeneratedValue
	private int id;
	
	
	@Column(name= "name")
	private String name;
	/**
	 * Represents type of Booking:
	 * T = Taeglich - Daily notification
	 * W = Wochentlich - Weekly notification
	 * M = Monatlich - Monthly notification
	 * N = None - No notification but the data sent to maxx system daily
	 * 
	 */
	@Column(name="type")
	private String type;
	/**
	 * Represents start date Booking
	 */
	@Column(name="date")
	private LocalDate date;
	/**
	 * Represents calculated hours of Booking
	 */
	@Column(name="hours")
	private double hours;
	/**
	 * Represents a remark of Booking
	 */
	@Column(name="remark")
	private String remark;
	/**
	 * Represents project id of Booking
	 */
	@Column(name="projectId")
	private int projectId;
	/**
	 * Represents activity id of Booking
	 */
	@Column(name="activityId")
	private int activityId;
	/**
	 * Represents the project Booking
	 */
	@Column(name="project")
	private String project;
	/**
	 * Represents the start time of Booking
	 */
	@Column(name="starttime")
	private LocalTime startTime;
	/**
	 * Represents the end time of Booking
	 */
	@Column(name="endtime")
	private LocalTime endTime;
	
	 /**
     *  Get startTime of Bookinh
     *  @return startTime StartTime of Booking
     *  
     */
	public LocalTime getStartTime() {
		return startTime;
	}
	/**
	 * Set startTime of booing
	 * @param startTime StartTime of booking
	 */

	public void setStartTime(LocalTime stime) {
		this.startTime = stime;
	}

	/**
	 * Get end time of booking
	 * @return endTime 
	 */
	public LocalTime getEndTime() {
		return endTime;
	}
	/**
	 * Set endTime of booking
	 * @param endTime end time to set
	 */

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}


	/**
	 * Get Id of booking
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	
	/**
	 * Set Id of booking
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
		
	/**
	 * Get Name of booking
	 * @return the id
	 */
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	/**
	 * Set Name of booking
	 * @param name the name of project to set
	 */
	public void setName(String name2) {
		// TODO Auto-generated method stub
		this.name = name2;
		
	}
	
	/**
	 * Get type of booking
	 * @return type (T,W,M,N)
	 */
	public String getType() {
		return type;
	}

	
	/**
	 * Set type of booking
	 * @param type the account type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Get date of booiing
	 * @return date Date of the booking
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * Set date of booking
	 * @param localDate the date to set
	 */
	public void setDate(LocalDate localDate) {
		this.date = localDate;
	}

	/**
	 * Get hours per booking
	 * @return hours
	 */
	public double getHours() {
		return hours;
	}

	/**
	 * Set computed hours of booking
	 * @param hours the hours to set to set
	 */
	public void setHours(double hours) {
		this.hours = hours;
	}

	/**
	 * Get remark of booking
	 * @return remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * Set remark of booking
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	/**
	 * Get Project Id of booking
	 * @return projectId
	 */
	public int getProjectId() {
		return projectId;
	}

	/**
	 * Set Project Id of booking
	 * @param projectId project id to set
	 */
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}

	/**
	 * Get activity id of booking
	 * @return activityId
	 */
	public int getActivityId() {
		return activityId;
	}

	/**
	 * Set activit id of booking
	 * @param activityId The activity Id to set
	 */
	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}
	
	
	/**
	 * Get a project
	 * @return project
	 */
	public String getProject() {
		return project;
	}

	/**
	 * Set a project
	 * @param project project to set
	 */
	public void setProject(String project) {
		this.project = project;
	}

}
