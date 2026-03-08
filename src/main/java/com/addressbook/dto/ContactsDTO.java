package com.addressbook.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContactsDTO {
	
	@NotBlank(message = "First Name is Required")
	private String firstName;
	
	@NotBlank(message = "Last Name is Required")
	private String lastName;
	
	private String address;
	private String zip;
	private String city;
	private String state;
	
	@Pattern(regexp = "^[0-9]{10}$", message = "Phone Number must be 10 Digits")
	private String phone;
	
	@Email(message = "Enter a valid Email")
	private String email;
	
	private String addressBookName;
}
