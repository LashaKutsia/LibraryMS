package com.example.LibraryMS;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.example.LibraryMS.entity")
public class LibraryMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LibraryMsApplication.class, args);
	}

}
