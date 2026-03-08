package com.addressbook;

import com.addressbook.model.Contact;
import java.util.*;

/**
 * UC6: Multiple Address Books
 * Manages multiple AddressBook objects using HashMap
 */
public class AddressBookSystem {
    private Map<String, AddressBook> addressBooks;

    public AddressBookSystem() {
        this.addressBooks = new HashMap<>();
    }

    /**
     * UC6: Create a new address book by name
     */
    public void createAddressBook(String name) {
        if (!addressBooks.containsKey(name)) {
            addressBooks.put(name, new AddressBook(name));
            System.out.println("Address book '" + name + "' created successfully!");
        } else {
            System.out.println("Address book '" + name + "' already exists!");
        }
    }

    /**
     * UC6: Get an address book by name
     */
    public AddressBook getAddressBook(String name) {
        return addressBooks.get(name);
    }

    /**
     * UC6: Add a contact to a specific address book
     */
    public boolean addContactToBook(String bookName, Contact contact) {
        AddressBook book = addressBooks.get(bookName);
        if (book != null) {
            book.addContact(contact);
            return true;
        }
        return false;
    }

    /**
     * UC8: Search across all address books by city
     */
    public List<Contact> searchByCityAcrossAllBooks(String city) {
        List<Contact> results = new ArrayList<>();
        for (AddressBook book : addressBooks.values()) {
            results.addAll(book.searchByCity(city));
        }
        return results;
    }

    /**
     * UC8: Search across all address books by state
     */
    public List<Contact> searchByStateAcrossAllBooks(String state) {
        List<Contact> results = new ArrayList<>();
        for (AddressBook book : addressBooks.values()) {
            results.addAll(book.searchByState(state));
        }
        return results;
    }

    /**
     * Get all address book names
     */
    public Set<String> getAllAddressBookNames() {
        return new HashSet<>(addressBooks.keySet());
    }

    /**
     * Get all address books
     */
    public Map<String, AddressBook> getAllAddressBooks() {
        return new HashMap<>(addressBooks);
    }
}
