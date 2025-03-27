package com.drone.delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DroneManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(DroneManagementApplication.class, args);
	}

}
