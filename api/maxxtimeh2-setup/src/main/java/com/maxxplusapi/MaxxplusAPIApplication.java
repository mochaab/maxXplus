package com.maxxplusapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.maxxplusapi","com.messagehandler","com.scheduler"})
//@SpringBootApplication(scanBasePackageClasses = {com.maxxplusapi.dao.BookingRepository.class, 
//												com.maxxplusapi.service.BookingService.class,
//												com.maxxplusapi.entity.Booking.class})
public class MaxxplusAPIApplication {

	public static void main(String[] args) {
		SpringApplication.run(MaxxplusAPIApplication.class, args);
	}

}
