package com.maxxplusapi.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maxxplusapi.dao.BookingRepository;
import com.maxxplusapi.entity.Booking;
import com.maxxplusapi.service.BookingService;


/**
 * This is the controller class Booking
 * 
 * 
 *
 */
@CrossOrigin
@RestController
public class BookingController {
	
	 /**
     * This class is deprecated. Originally our API was a microservice so this class was used.
     * However, we everntually changed the structure to an integrated one so instead of accessing the endpoints offered by this class, we directly accessed the service classes.
     */
	

	@Autowired
	private BookingService bookingService;
	

	@PostMapping("/addBooking")
	public Booking addBooking(@RequestBody Booking booking) {
		return bookingService.createBooking(booking);
	}
	
	@PostMapping("/addBookings")
	public List<Booking> addBooking(@RequestBody List<Booking> bookings) {
		return bookingService.createBooking(bookings);
	}
	
	@GetMapping("/booking/{id}")
	public Booking getBookingvById(@PathVariable int id) {
		return bookingService.getBookingById(id);
	}
	
	@GetMapping("/bookings")
	public List<Booking> getAllBookings() {
		return bookingService.getBookings();
	}
	
	@PutMapping("/updateBooking")
	public Booking updateBooking(@RequestBody Booking booking) {
		return bookingService.updateBooking(booking);
	}
	
	@DeleteMapping("/booking/{id}")
	public String deleteBooking(@PathVariable int id) {
		return bookingService.deleteBookingById(id);
		
	}
	
	@GetMapping("/booking/name")
	public ResponseEntity<List<Booking>> getBookingByName(@RequestParam String name) {
		return new ResponseEntity<List<Booking>>(bookingService.getBookingByName(name), HttpStatus.OK);
	}
	
	@GetMapping("/booking/type")
	public ResponseEntity<List<Booking>> getBookingByType(@RequestParam String type) {
		return new ResponseEntity<List<Booking>>(bookingService.getBookingByType(type), HttpStatus.OK);
	}
	
	@GetMapping("/booking/date")
	public ResponseEntity<List<Booking>> getBookingByDate(@RequestParam Date date) {
		return new ResponseEntity<List<Booking>>(bookingService.getBookingByDate(date), HttpStatus.OK);
	}
	
	@GetMapping("/booking/hours")
	public ResponseEntity<List<Booking>> getBookingByHours(@RequestParam double hours) {
		return new ResponseEntity<List<Booking>>(bookingService.getBookingByHours(hours), HttpStatus.OK);
	}
	
	@GetMapping("/booking/remark")
	public ResponseEntity<List<Booking>> getBookingByRemark(@RequestParam String remark) {
		return new ResponseEntity<List<Booking>>(bookingService.getBookingByRemark(remark), HttpStatus.OK);
	}
	
	@GetMapping("/booking/projectId")
	public ResponseEntity<List<Booking>> getBookingByProjectId(@RequestParam int projectId) {
		return new ResponseEntity<List<Booking>>(bookingService.getBookingByProjectId(projectId), HttpStatus.OK);
	}
	
	@GetMapping("/booking/activityId")
	public ResponseEntity<List<Booking>> getBookingByActivityId(@RequestParam int activityId) {
		return new ResponseEntity<List<Booking>>(bookingService.getBookingByActivityId(activityId), HttpStatus.OK);
	}
	
	
	
/*	@GetMapping("/booking/name")
	public ResponseEntity<List<Booking>> findByName() {
		try {
			List<Booking> bookings = bookingService.getBookingByName(name);

			if (bookings.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(bookings, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}*/
}
