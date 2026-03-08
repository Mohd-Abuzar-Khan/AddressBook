package com.addressbook.rest;

import com.addressbook.AddressBook;
import com.addressbook.model.Contact;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

/**
 * UC22-25: REST Assured tests for JSON Server operations
 * 
 * Note: Requires JSON Server to be running locally
 * Install: npm install -g json-server
 * Run: json-server --watch db.json --port 3000
 */
public class AddressBookRestAssuredTest {
    
    private static final String JSON_SERVER_BASE_URL = "http://localhost:3000";
    private AddressBook addressBook;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = JSON_SERVER_BASE_URL;
        addressBook = new AddressBook("TestBook");
    }

    /**
     * UC22: Read contacts from JSON Server
     */
    @Test
    void testReadContactsFromJsonServer() {
        Response response = given()
            .contentType(ContentType.JSON)
            .when()
            .get("/contacts")
            .then()
            .statusCode(200)
            .extract()
            .response();

        // Parse JSON response and load into address book
        Contact[] contacts = response.as(Contact[].class);
        
        for (Contact contact : contacts) {
            addressBook.addContact(contact);
        }
        
        assert addressBook.getContactCount() > 0 : "Contacts should be loaded from JSON Server";
    }

    /**
     * UC23: Add multiple contacts to JSON Server
     */
    @Test
    void testAddMultipleContactsToJsonServer() {
        Contact contact1 = Contact.builder()
            .firstName("John")
            .lastName("Doe")
            .city("Mumbai")
            .state("MH")
            .phone("1234567890")
            .email("john@example.com")
            .addressBookName("Friends")
            .build();

        Contact contact2 = Contact.builder()
            .firstName("Jane")
            .lastName("Smith")
            .city("Pune")
            .state("MH")
            .phone("0987654321")
            .email("jane@example.com")
            .addressBookName("Friends")
            .build();

        // POST contact1
        Response response1 = given()
            .contentType(ContentType.JSON)
            .body(contact1)
            .when()
            .post("/contacts")
            .then()
            .statusCode(201)
            .body("firstName", equalTo("John"))
            .extract()
            .response();

        Contact addedContact1 = response1.as(Contact.class);
        addressBook.addContact(addedContact1);

        // POST contact2
        Response response2 = given()
            .contentType(ContentType.JSON)
            .body(contact2)
            .when()
            .post("/contacts")
            .then()
            .statusCode(201)
            .body("firstName", equalTo("Jane"))
            .extract()
            .response();

        Contact addedContact2 = response2.as(Contact.class);
        addressBook.addContact(addedContact2);

        // Verify contacts are in memory
        assert addressBook.getContactCount() >= 2 : "Contacts should be added to memory";
    }

    /**
     * UC24: Update a contact on JSON Server
     */
    @Test
    void testUpdateContactOnJsonServer() {
        // First, add a contact
        Contact contact = Contact.builder()
            .firstName("Bob")
            .lastName("Wilson")
            .city("Delhi")
            .state("DL")
            .phone("1111111111")
            .email("bob@example.com")
            .addressBookName("Work")
            .build();

        Response createResponse = given()
            .contentType(ContentType.JSON)
            .body(contact)
            .when()
            .post("/contacts")
            .then()
            .statusCode(201)
            .extract()
            .response();

        Contact createdContact = createResponse.as(Contact.class);
        Long contactId = createdContact.getId();

        // Update the contact
        Contact updatedContact = Contact.builder()
            .id(contactId)
            .firstName("Bob")
            .lastName("Wilson")
            .city("Bangalore")
            .state("KA")
            .phone("2222222222")
            .email("bob.updated@example.com")
            .addressBookName("Work")
            .build();

        Response updateResponse = given()
            .contentType(ContentType.JSON)
            .body(updatedContact)
            .when()
            .put("/contacts/" + contactId)
            .then()
            .statusCode(200)
            .body("city", equalTo("Bangalore"))
            .body("phone", equalTo("2222222222"))
            .extract()
            .response();

        Contact updated = updateResponse.as(Contact.class);
        addressBook.addContact(updated);

        // Verify update in memory
        assert addressBook.getContactCount() > 0 : "Updated contact should be in memory";
    }

    /**
     * UC25: Delete a contact from JSON Server
     */
    @Test
    void testDeleteContactFromJsonServer() {
        // First, add a contact
        Contact contact = Contact.builder()
            .firstName("Alice")
            .lastName("Brown")
            .city("Chennai")
            .state("TN")
            .phone("3333333333")
            .email("alice@example.com")
            .addressBookName("Family")
            .build();

        Response createResponse = given()
            .contentType(ContentType.JSON)
            .body(contact)
            .when()
            .post("/contacts")
            .then()
            .statusCode(201)
            .extract()
            .response();

        Contact createdContact = createResponse.as(Contact.class);
        Long contactId = createdContact.getId();
        addressBook.addContact(createdContact);

        // Delete the contact
        given()
            .when()
            .delete("/contacts/" + contactId)
            .then()
            .statusCode(200);

        // Verify contact no longer exists on server
        given()
            .when()
            .get("/contacts/" + contactId)
            .then()
            .statusCode(404);

        // Remove from memory (sync)
        addressBook.deleteContact(createdContact.getFirstName(), createdContact.getLastName());
    }
}
