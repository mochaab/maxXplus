package com.maxxplusapi.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maxxplusapi.dao.BookingRepository;
import com.maxxplusapi.entity.Booking;


/**
 * This class is a service class for booking, it contains the business logic
 * 
 * @author Charissa, Nasim
 * 
 *
 */

@Service
//@EnableSpringConfigured
public class BookingService {
	
	/**
	 * This class contains the business logic related with Booking operations
	 * */

	@Autowired
	private BookingRepository bookingRepository;
	
	/**
	 * Create a booking
	 * It returns status bookingRepository.save(booking)
	 * @param booking A Booking object
	 */
	public Booking createBooking(Booking booking) {
		return bookingRepository.save(booking);
		
	}
	
	/**
	 * Create multiple bookings
	 * It returns status bookingRepository.saveAll(booking)
	 * @param bookings A list of bookings
	 */
	public List<Booking> createBooking(List<Booking> bookings) {
		return bookingRepository.saveAll(bookings);
		
	}
	
	/**
	 * Get booking by Id
	 * It returns status bookingRepository.findById(id).orElse(null)
	 * @param id int
	*/
	public Booking getBookingById(int id) {
		return bookingRepository.findById(id).orElse(null);
	}

	/**
	 * Get Booking by name
	 * It returns status of bookingRepository.findByName(name);
	 * @param name String
	*/
	public List<Booking> getBookingByName(String name) {
		return bookingRepository.findByName(name);
	}
	
	/**
	 * Get booking by type
	 * It returns status of bookingRepository.findByType(type)
	 * @param name String
	*/
	public List<Booking> getBookingByType(String type) {
		return bookingRepository.findByType(type);
	}
	
	/**
	 * Get booking by date
	 * It returns status of bookingRepository.findByDate(date)
	 * @param date Date
	 *
	*/
	public List<Booking> getBookingByDate(Date date) {
		return bookingRepository.findByDate(date);
	}
	/**
	 * Get booking by hours
	 * It returns status of bookingRepository.findByHours(hours)
	 * @param hours double
	 *
	*/

	public List<Booking> getBookingByHours(double hours) {
		return bookingRepository.findByHours(hours);
	}
	
	/**
	 * Get booking by remarks
	 * It returns status of bookingRepository.findByRemark(remark)
	 * @param remarks String
	 *
	*/
	public List<Booking> getBookingByRemark(String remark) {
		return bookingRepository.findByRemark(remark);
	}
	

	/**
	 * Get booking by project id
	 * It returns status of bookingRepository.findByProjectId(projectId)
	 * @param project_id int
	*/
	public List<Booking> getBookingByProjectId(int projectId) {
		return bookingRepository.findByProjectId(projectId);
	}
	
	/**
	 * Get booking by activity id
	 * It returns status of bookingRepository.findByActivityId(activityId)
	 * @param activity_id int
	*/
	public List<Booking> getBookingByActivityId(int activityId) {
		return bookingRepository.findByActivityId(activityId);
	}

	//public List<Booking> getBookingByOrderByTypeAsc(String type){return bookingRepository.findByOrderByTypeAsc(type);}

	/**
	 * Get bookings according to sorted EndTime
	 * It returns status of bookingRepository.findAllByOrderByEndTimeAsc()
	 * @param activity_id int
	*/
	public List<Booking> getAllByOrderByEndTimeAsc(){return bookingRepository.findAllByOrderByEndTimeAsc();}

	/**
	 * Get all bookings
	 * returns the status of bookingRepository.findAll()
	 */
	
	public List<Booking> getBookings() {
		
		return bookingRepository.findAll();
	}
	

	/**
	 * Update booking
	 * @param booking Booking
	*/
	public Booking updateBooking(Booking booking) {
	
		
		Booking oldBooking=null;
		Optional<Booking> optionalbooking=bookingRepository.findById(booking.getId());
		if(optionalbooking.isPresent()) {
			oldBooking=optionalbooking.get();
			oldBooking.setName(booking.getName());
			oldBooking.setType(booking.getType());
			oldBooking.setDate(booking.getDate());
			oldBooking.setStartTime(booking.getStartTime());
			oldBooking.setEndTime(booking.getEndTime());
			oldBooking.setRemark(booking.getRemark());
			oldBooking.setHours(booking.getHours());
			oldBooking.setProjectId(booking.getProjectId());
			oldBooking.setActivityId(booking.getActivityId());
			bookingRepository.save(oldBooking);
		}else {
			return new Booking();
		}
		return oldBooking;
	}
	
	/**
	 * Delete booking
	 * @param id int
	*/
	public String deleteBookingById(int id) {
		bookingRepository.deleteById(id);
		return "Aktivität " +id+" wurde gelöscht";
	}


	
	
	
}
