package com.addressbook;

import com.addressbook.model.Contact;
import java.util.*;
import java.util.stream.Collectors;

/**
 * UC10: Count Contacts by City or State
 * Uses Java Streams with groupingBy and counting
 * UC11: Sort Contacts by Name
 * UC12: Sort by City, State, or Zip
 */
public class AddressBookStatistics {
    private AddressBookSystem addressBookSystem;

    public AddressBookStatistics(AddressBookSystem addressBookSystem) {
        this.addressBookSystem = addressBookSystem;
    }

    /**
     * UC10: Count contacts by city using Streams
     */
    public Map<String, Long> countByCity() {
        List<Contact> allContacts = getAllContacts();
        return allContacts.stream()
            .filter(c -> c.getCity() != null && !c.getCity().isEmpty())
            .collect(Collectors.groupingBy(
                Contact::getCity,
                Collectors.counting()
            ));
    }

    /**
     * UC10: Count contacts by state using Streams
     */
    public Map<String, Long> countByState() {
        List<Contact> allContacts = getAllContacts();
        return allContacts.stream()
            .filter(c -> c.getState() != null && !c.getState().isEmpty())
            .collect(Collectors.groupingBy(
                Contact::getState,
                Collectors.counting()
            ));
    }

    /**
     * UC11: Sort contacts alphabetically by name
     */
    public List<Contact> sortByName() {
        List<Contact> allContacts = getAllContacts();
        return allContacts.stream()
            .sorted(Comparator.comparing(Contact::getFirstName)
                             .thenComparing(Contact::getLastName))
            .collect(Collectors.toList());
    }

    /**
     * UC12: Sort contacts by city
     */
    public List<Contact> sortByCity() {
        List<Contact> allContacts = getAllContacts();
        return allContacts.stream()
            .sorted(Comparator.comparing(Contact::getCity, Comparator.nullsLast(String::compareToIgnoreCase)))
            .collect(Collectors.toList());
    }

    /**
     * UC12: Sort contacts by state
     */
    public List<Contact> sortByState() {
        List<Contact> allContacts = getAllContacts();
        return allContacts.stream()
            .sorted(Comparator.comparing(Contact::getState, Comparator.nullsLast(String::compareToIgnoreCase)))
            .collect(Collectors.toList());
    }

    /**
     * UC12: Sort contacts by zip
     */
    public List<Contact> sortByZip() {
        List<Contact> allContacts = getAllContacts();
        return allContacts.stream()
            .sorted(Comparator.comparing(Contact::getZip, Comparator.nullsLast(String::compareToIgnoreCase)))
            .collect(Collectors.toList());
    }

    /**
     * Get all contacts from all address books
     */
    private List<Contact> getAllContacts() {
        List<Contact> allContacts = new ArrayList<>();
        for (AddressBook book : addressBookSystem.getAllAddressBooks().values()) {
            allContacts.addAll(book.getContacts());
        }
        return allContacts;
    }
}
