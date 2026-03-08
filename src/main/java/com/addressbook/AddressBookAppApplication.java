package com.addressbook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class AddressBookAppApplication {
	public static void main(String[] args) {
       log.info("Welcome to Address Book Program");
		SpringApplication.run(AddressBookAppApplication.class, args);
	}

}
