package com.addressbook;

import com.addressbook.model.Contact;
import java.util.ArrayList;
import java.util.List;

/**
 * UC2: AddressBook class that holds contacts
 * UC5: Refactored to hold a List<Contact> instead of a single contact
 */
public class AddressBook {
    private List<Contact> contacts;
    private String name;

    public AddressBook(String name) {
        this.name = name;
        this.contacts = new ArrayList<>();
    }

    public AddressBook() {
        this("Default");
        this.contacts = new ArrayList<>();
    }

    /**
     * UC2: Add a contact to the address book
     */
    public void addContact(Contact contact) {
        if (contact != null) {
            contact.setAddressBookName(this.name);
            this.contacts.add(contact);
        }
    }

    /**
     * UC3: Edit an existing contact by name
     */
    public boolean editContact(String firstName, String lastName, Contact updatedContact) {
        for (int i = 0; i < contacts.size(); i++) {
            Contact contact = contacts.get(i);
            if (contact.getFirstName().equalsIgnoreCase(firstName) && 
                contact.getLastName().equalsIgnoreCase(lastName)) {
                updatedContact.setId(contact.getId());
                updatedContact.setAddressBookName(this.name);
                contacts.set(i, updatedContact);
                return true;
            }
        }
        return false;
    }

    /**
     * UC4: Delete a contact by name
     */
    public boolean deleteContact(String firstName, String lastName) {
        return contacts.removeIf(contact -> 
            contact.getFirstName().equalsIgnoreCase(firstName) && 
            contact.getLastName().equalsIgnoreCase(lastName)
        );
    }

    /**
     * UC3: Search for a contact by name
     */
    public Contact searchContact(String firstName, String lastName) {
        return contacts.stream()
            .filter(contact -> contact.getFirstName().equalsIgnoreCase(firstName) && 
                             contact.getLastName().equalsIgnoreCase(lastName))
            .findFirst()
            .orElse(null);
    }

    /**
     * UC8: Search contacts by city
     */
    public List<Contact> searchByCity(String city) {
        return contacts.stream()
            .filter(contact -> contact.getCity() != null && 
                             contact.getCity().equalsIgnoreCase(city))
            .toList();
    }

    /**
     * UC8: Search contacts by state
     */
    public List<Contact> searchByState(String state) {
        return contacts.stream()
            .filter(contact -> contact.getState() != null && 
                             contact.getState().equalsIgnoreCase(state))
            .toList();
    }

    public List<Contact> getContacts() {
        return new ArrayList<>(contacts);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getContactCount() {
        return contacts.size();
    }
}
