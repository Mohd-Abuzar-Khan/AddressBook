package com.addressbook;

import com.addressbook.model.Contact;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ContactModelTest {

    private Contact contact;

    @BeforeEach
    void setUp() {
        contact = Contact.builder()
                .firstName("Alice")
                .lastName("Smith")
                .address("123 Elm Street")
                .city("Mumbai")
                .state("MH")
                .zip("400001")
                .phone("9999999999")
                .email("alice@mail.com")
                .addressBookName("Friends")
                .build();
    }

    // ── Test UC 1 : Contact object is created successfully ────────────────────
    @Test
    @Order(1)
    @DisplayName("UC1 - Contact object should be created successfully")
    void testContactCreation_ShouldNotBeNull() {
        assertNotNull(contact,
            "Contact object should not be null");
    }

    // ── Test UC 1 : All fields together ────────────
    @Test
    @Order(15)
    @DisplayName("UC1 - All fields should be set correctly together")
    void testAllContactFields_ShouldMatchTogether() {
        assertAll("All contact fields",
            () -> assertEquals("Alice",          contact.getFirstName()),
            () -> assertEquals("Smith",          contact.getLastName()),
            () -> assertEquals("123 Elm Street", contact.getAddress()),
            () -> assertEquals("Mumbai",         contact.getCity()),
            () -> assertEquals("MH",             contact.getState()),
            () -> assertEquals("400001",         contact.getZip()),
            () -> assertEquals("9999999999",     contact.getPhone()),
            () -> assertEquals("alice@mail.com", contact.getEmail()),
            () -> assertEquals("Friends",        contact.getAddressBookName())
        );
    }
}