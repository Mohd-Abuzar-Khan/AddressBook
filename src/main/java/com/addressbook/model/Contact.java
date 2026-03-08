package com.addressbook.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.Objects;

@Entity
@Table(name = "contacts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Contact {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	// UC1 : Core contacts fields
	
	@NotBlank(message = "First name is required")
	@Column(name = "first_name", nullable = false)
	private String firstName;
	
	@NotBlank(message = "Last name is required")
	@Column(name = "last_name", nullable = false)
	private String lastName;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "city")
	private String city;
	
	@Column(name = "state")
	private String state;
	
	@Column(name = "zip")
	private String zip;
	
	@Pattern(regexp = "^[0-9]{10}$", message = "Phone must be 10 Digits")
	@Column(name = "phone")
	private String phone;
	
	@Email(message = "Email format is invalid")
	@Column(name = "email")
	private String email;

    @Column(name = "address_book_name")
    private String addressBookName;
	
	
	
	// UC7: Override equals() to compare by firstName, lastName, and addressBookName
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null || getClass() != obj.getClass()) return false;
		Contact contact = (Contact) obj;
		return Objects.equals(firstName, contact.firstName) &&
			   Objects.equals(lastName, contact.lastName) &&
			   Objects.equals(addressBookName, contact.addressBookName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(firstName, lastName, addressBookName);
	}
	
	@Override
    public String toString() {
        return "%s %s | %s | %s, %s %s | %s | %s"
            .formatted(firstName, lastName, address,
                       city, state, zip, phone, email);
    }

}
