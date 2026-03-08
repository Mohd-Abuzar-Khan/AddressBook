package com.addressbook.io;

import com.addressbook.AddressBook;
import com.addressbook.model.Contact;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.lang.reflect.Type;
import java.util.List;

/**
 * UC15: Read/Write Address Book as JSON
 * Uses GSON library for JSON serialization/deserialization
 */
public class JsonIOService {
    private static final String JSON_FILE_PATH = "addressbook.json";
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /**
     * UC15: Write address book to JSON file using GSON
     */
    public static void writeToJson(AddressBook addressBook) throws IOException {
        try (FileWriter writer = new FileWriter(JSON_FILE_PATH)) {
            gson.toJson(addressBook.getContacts(), writer);
        }
    }

    /**
     * UC15: Read address book from JSON file using GSON
     */
    public static AddressBook readFromJson() throws IOException {
        AddressBook addressBook = new AddressBook();
        
        try (FileReader reader = new FileReader(JSON_FILE_PATH)) {
            Type contactListType = new TypeToken<List<Contact>>(){}.getType();
            List<Contact> contacts = gson.fromJson(reader, contactListType);
            
            if (contacts != null) {
                for (Contact contact : contacts) {
                    addressBook.addContact(contact);
                }
            }
        }
        
        return addressBook;
    }
}
