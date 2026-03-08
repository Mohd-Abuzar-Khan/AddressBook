package com.addressbook.io;

import com.addressbook.AddressBook;
import com.addressbook.model.Contact;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * UC13: Read/Write Address Book Using File IO
 * Uses Java's built-in File IO (FileWriter, BufferedReader, FileReader)
 */
public class FileIOService {
    private static final String FILE_PATH = "addressbook.txt";

    /**
     * UC13: Write address book to a plain text file
     */
    public static void writeToFile(AddressBook addressBook) throws IOException {
        try (FileWriter writer = new FileWriter(FILE_PATH)) {
            for (Contact contact : addressBook.getContacts()) {
                writer.write(formatContact(contact) + "\n");
            }
        }
    }

    /**
     * UC13: Read address book from a plain text file
     */
    public static AddressBook readFromFile() throws IOException {
        AddressBook addressBook = new AddressBook();
        List<Contact> contacts = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Contact contact = parseContact(line);
                if (contact != null) {
                    contacts.add(contact);
                }
            }
        }
        
        for (Contact contact : contacts) {
            addressBook.addContact(contact);
        }
        
        return addressBook;
    }

    /**
     * Format contact as a line in the file
     */
    private static String formatContact(Contact contact) {
        return String.format("%s|%s|%s|%s|%s|%s|%s|%s|%s",
            contact.getFirstName() != null ? contact.getFirstName() : "",
            contact.getLastName() != null ? contact.getLastName() : "",
            contact.getAddress() != null ? contact.getAddress() : "",
            contact.getCity() != null ? contact.getCity() : "",
            contact.getState() != null ? contact.getState() : "",
            contact.getZip() != null ? contact.getZip() : "",
            contact.getPhone() != null ? contact.getPhone() : "",
            contact.getEmail() != null ? contact.getEmail() : "",
            contact.getAddressBookName() != null ? contact.getAddressBookName() : ""
        );
    }

    /**
     * Parse a line from the file into a Contact object
     */
    private static Contact parseContact(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }
        
        String[] parts = line.split("\\|");
        if (parts.length < 9) {
            return null;
        }
        
        return Contact.builder()
            .firstName(parts[0])
            .lastName(parts[1])
            .address(parts[2])
            .city(parts[3])
            .state(parts[4])
            .zip(parts[5])
            .phone(parts[6])
            .email(parts[7])
            .addressBookName(parts[8])
            .build();
    }
}
