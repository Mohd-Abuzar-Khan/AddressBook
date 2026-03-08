package com.addressbook;

import com.addressbook.model.Contact;
import java.util.*;
import java.util.stream.Collectors;

/**
 * UC9: View Persons by City or State
 * Maintains dictionaries mapping city/state to lists of persons
 */
public class AddressBookView {
    private AddressBookSystem addressBookSystem;

    public AddressBookView(AddressBookSystem addressBookSystem) {
        this.addressBookSystem = addressBookSystem;
    }

    /**
     * UC9: Get dictionary mapping city to list of persons
     */
    public Map<String, List<Contact>> getPersonsByCity() {
        Map<String, List<Contact>> cityMap = new HashMap<>();
        
        for (AddressBook book : addressBookSystem.getAllAddressBooks().values()) {
            for (Contact contact : book.getContacts()) {
                if (contact.getCity() != null && !contact.getCity().isEmpty()) {
                    cityMap.computeIfAbsent(contact.getCity(), k -> new ArrayList<>()).add(contact);
                }
            }
        }
        
        return cityMap;
    }

    /**
     * UC9: Get dictionary mapping state to list of persons
     */
    public Map<String, List<Contact>> getPersonsByState() {
        Map<String, List<Contact>> stateMap = new HashMap<>();
        
        for (AddressBook book : addressBookSystem.getAllAddressBooks().values()) {
            for (Contact contact : book.getContacts()) {
                if (contact.getState() != null && !contact.getState().isEmpty()) {
                    stateMap.computeIfAbsent(contact.getState(), k -> new ArrayList<>()).add(contact);
                }
            }
        }
        
        return stateMap;
    }

    /**
     * UC9: Display all persons in a given city
     */
    public void displayPersonsByCity(String city) {
        Map<String, List<Contact>> cityMap = getPersonsByCity();
        List<Contact> contacts = cityMap.getOrDefault(city, new ArrayList<>());
        
        if (contacts.isEmpty()) {
            System.out.println("No contacts found in city: " + city);
        } else {
            System.out.println("Contacts in " + city + ":");
            contacts.forEach(System.out::println);
        }
    }

    /**
     * UC9: Display all persons in a given state
     */
    public void displayPersonsByState(String state) {
        Map<String, List<Contact>> stateMap = getPersonsByState();
        List<Contact> contacts = stateMap.getOrDefault(state, new ArrayList<>());
        
        if (contacts.isEmpty()) {
            System.out.println("No contacts found in state: " + state);
        } else {
            System.out.println("Contacts in " + state + ":");
            contacts.forEach(System.out::println);
        }
    }
}
