package com.schoolmanagement.schoolmanagementwebsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SchoolmanagementwebsiteApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolmanagementwebsiteApplication.class, args);
		System.out.println("ZYNTaks Education System Run Successfully");
	}

}
