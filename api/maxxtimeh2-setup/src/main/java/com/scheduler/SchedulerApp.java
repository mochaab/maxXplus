package com.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {"com.maxxplusapi","com.messagehandler","com.notifications","com.scheduler"})
@EnableScheduling
public class SchedulerApp {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(SchedulerApp.class, args);
	}
}
