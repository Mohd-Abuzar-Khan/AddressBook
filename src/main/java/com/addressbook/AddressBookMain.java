package com.addressbook;

import com.addressbook.model.Contact;
import java.util.List;
import java.util.Scanner;

/**
 * UC2: Main class for console-based Address Book operations
 * Uses Scanner to take input from console
 */
public class AddressBookMain {
    private static Scanner scanner = new Scanner(System.in);
    private static AddressBook addressBook = new AddressBook("Default");

    public static void main(String[] args) {
        System.out.println("Welcome to Address Book Program");
        
        boolean running = true;
        while (running) {
            System.out.println("\n=== Address Book Menu ===");
            System.out.println("1. Add Contact (UC2)");
            System.out.println("2. Edit Contact (UC3)");
            System.out.println("3. Delete Contact (UC4)");
            System.out.println("4. View All Contacts");
            System.out.println("5. Search by City (UC8)");
            System.out.println("6. Search by State (UC8)");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    addContact();
                    break;
                case 2:
                    editContact();
                    break;
                case 3:
                    deleteContact();
                    break;
                case 4:
                    viewAllContacts();
                    break;
                case 5:
                    searchByCity();
                    break;
                case 6:
                    searchByState();
                    break;
                case 0:
                    running = false;
                    System.out.println("Thank you for using Address Book!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        
        scanner.close();
    }

    /**
     * UC2: Add a contact to address book via console input
     */
    private static void addContact() {
        System.out.println("\n--- Add New Contact ---");
        
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        
        System.out.print("Address: ");
        String address = scanner.nextLine();
        
        System.out.print("City: ");
        String city = scanner.nextLine();
        
        System.out.print("State: ");
        String state = scanner.nextLine();
        
        System.out.print("Zip: ");
        String zip = scanner.nextLine();
        
        System.out.print("Phone (10 digits): ");
        String phone = scanner.nextLine();
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        Contact contact = Contact.builder()
            .firstName(firstName)
            .lastName(lastName)
            .address(address)
            .city(city)
            .state(state)
            .zip(zip)
            .phone(phone)
            .email(email)
            .build();
        
        addressBook.addContact(contact);
        System.out.println("Contact added successfully!");
    }

    /**
     * UC3: Edit an existing contact
     */
    private static void editContact() {
        System.out.println("\n--- Edit Contact ---");
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        
        Contact existingContact = addressBook.searchContact(firstName, lastName);
        if (existingContact == null) {
            System.out.println("Contact not found!");
            return;
        }
        
        System.out.println("Current details:");
        System.out.println(existingContact);
        System.out.println("\nEnter new details (press Enter to keep current value):");
        
        System.out.print("Address [" + existingContact.getAddress() + "]: ");
        String address = scanner.nextLine();
        if (address.isEmpty()) address = existingContact.getAddress();
        
        System.out.print("City [" + existingContact.getCity() + "]: ");
        String city = scanner.nextLine();
        if (city.isEmpty()) city = existingContact.getCity();
        
        System.out.print("State [" + existingContact.getState() + "]: ");
        String state = scanner.nextLine();
        if (state.isEmpty()) state = existingContact.getState();
        
        System.out.print("Zip [" + existingContact.getZip() + "]: ");
        String zip = scanner.nextLine();
        if (zip.isEmpty()) zip = existingContact.getZip();
        
        System.out.print("Phone [" + existingContact.getPhone() + "]: ");
        String phone = scanner.nextLine();
        if (phone.isEmpty()) phone = existingContact.getPhone();
        
        System.out.print("Email [" + existingContact.getEmail() + "]: ");
        String email = scanner.nextLine();
        if (email.isEmpty()) email = existingContact.getEmail();
        
        Contact updatedContact = Contact.builder()
            .firstName(existingContact.getFirstName())
            .lastName(existingContact.getLastName())
            .address(address)
            .city(city)
            .state(state)
            .zip(zip)
            .phone(phone)
            .email(email)
            .build();
        
        if (addressBook.editContact(firstName, lastName, updatedContact)) {
            System.out.println("Contact updated successfully!");
        } else {
            System.out.println("Failed to update contact!");
        }
    }

    /**
     * UC4: Delete a contact
     */
    private static void deleteContact() {
        System.out.println("\n--- Delete Contact ---");
        System.out.print("Enter First Name: ");
        String firstName = scanner.nextLine();
        
        System.out.print("Enter Last Name: ");
        String lastName = scanner.nextLine();
        
        if (addressBook.deleteContact(firstName, lastName)) {
            System.out.println("Contact deleted successfully!");
        } else {
            System.out.println("Contact not found!");
        }
    }

    private static void viewAllContacts() {
        System.out.println("\n--- All Contacts ---");
        List<Contact> contacts = addressBook.getContacts();
        if (contacts.isEmpty()) {
            System.out.println("No contacts found.");
        } else {
            contacts.forEach(System.out::println);
        }
    }

    /**
     * UC8: Search by city
     */
    private static void searchByCity() {
        System.out.println("\n--- Search by City ---");
        System.out.print("Enter City: ");
        String city = scanner.nextLine();
        
        List<Contact> contacts = addressBook.searchByCity(city);
        if (contacts.isEmpty()) {
            System.out.println("No contacts found in " + city);
        } else {
            System.out.println("Contacts in " + city + ":");
            contacts.forEach(System.out::println);
        }
    }

    /**
     * UC8: Search by state
     */
    private static void searchByState() {
        System.out.println("\n--- Search by State ---");
        System.out.print("Enter State: ");
        String state = scanner.nextLine();
        
        List<Contact> contacts = addressBook.searchByState(state);
        if (contacts.isEmpty()) {
            System.out.println("No contacts found in " + state);
        } else {
            System.out.println("Contacts in " + state + ":");
            contacts.forEach(System.out::println);
        }
    }
}
