package com.maxxplusapi.dao;

import java.util.Date;

/**
 * 
 * 
 * @author Charissa
 * 
 *
 */

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maxxplusapi.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

	 /**
     * Repository class encapsulates the fetching of data from the database
     */

	/**
	 * Find record by name.
     * @param name Name of the booking
     */
	List<Booking> findByName(String name);

	/**
	 * Find record by type.
     * @param type Type of booking: Either T for Taeglich, W for Wochentlich and M for Monatlich
     */
	List<Booking> findByType(String type);

	/**
	 * Find record by type.
     * @param date Date of booking
     */
	List<Booking> findByDate(Date date);

	/**
	 * Find record by Hours.
     * @param hours double
     */
	List<Booking> findByHours(double hours);

	/**
	 * Find record by Remark.
     * @param remark Specific name of activity. E.g Daily Scrum
     */
	List<Booking> findByRemark(String remark);

	/**
	 * Find record by projectId.
     * @param projectId int
     */
	List<Booking> findByProjectId(int projectId);
	
	/**
	 * Find record by activityId.
     * @param activityId Activity Id assigned to user
     */
	List<Booking> findByActivityId(int activityId);
	//List<Booking> findByOrderByTypeAsc(String type);
	
	/**
	 * Fetches all booking according to sorted endTime.
     */
	List<Booking> findAllByOrderByEndTimeAsc();
}
