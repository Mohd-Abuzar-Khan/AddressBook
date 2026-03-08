#  Address Book System

A full-stack **Spring Boot REST API** application for managing contacts across multiple address books, backed by **MySQL**, with **JSON File IO**, **Multi-threading**, and **REST Assured** testing — built following all 25 Use Cases.

---

## 📁 Project Structure

```
AddressBook/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/com/addressbook/
    │   │   ├── AddressBookApplication.java
    │   │   ├── controller/
    │   │   │   ├── ContactController.java
    │   │   │   └── FileIOController.java
    │   │   ├── model/
    │   │   │   └── Contact.java
    │   │   ├── dto/
    │   │   │   └── ContactDTO.java
    │   │   ├── repository/
    │   │   │   └── ContactRepository.java
    │   │   ├── service/
    │   │   │   ├── IAddressBookService.java
    │   │   │   ├── AddressBookService.java
    │   │   │   └── FileIOService.java
    │   │   └── exception/
    │   │       ├── ContactNotFoundException.java
    │   │       ├── DuplicateContactException.java
    │   │       └── GlobalExceptionHandler.java
    │   └── resources/
    │       └── application.properties
    └── test/java/com/addressbook/
        ├── AddressBookServiceTest.java
        └── AddressBookRestAssuredTest.java
```

---

##  Tech Stack

| Technology | Purpose |
|------------|---------|
| **Java 21** | Core language |
| **Spring Boot 3.2** | REST API framework |
| **Spring Data JPA** | Database ORM |
| **MySQL** | Persistent storage |
| **GSON** | JSON File IO |
| **Lombok** | Reduce boilerplate |
| **JUnit 5** | Unit testing |
| **REST Assured** | Integration testing |
| **Maven** | Build & dependency management |

---

## Getting Started

### Prerequisites
- Java 21
- Maven 3.8+
- MySQL 8.0+
- IntelliJ IDEA (recommended)

### 1. Clone the Repository
```bash
git clone https://github.com/your-username/AddressBook.git
cd AddressBook
```

### 2. Create MySQL Database
```sql
CREATE DATABASE addressbook_db;
```

### 3. Configure `application.properties`
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/addressbook_db?useSSL=false&allowPublicKeyRetrieval=true
spring.datasource.username=root
spring.datasource.password=your_password
spring.jpa.hibernate.ddl-auto=update
server.port=8080
file.json.path=addressbook.json
```

### 4. Run the Application
```bash
mvn spring-boot:run
```

### 5. Run Tests
```bash
# Run all tests
mvn test

# Run only service tests
mvn test -Dtest=AddressBookServiceTest

# Run only REST Assured tests
mvn test -Dtest=AddressBookRestAssuredTest
```

---

## 📌 Use Case Summary

### Section 1 — Streams API (UC1–UC12)

| UC | Feature | Endpoint |
|----|---------|----------|
| UC1 | Create Contact model | — |
| UC2 | Add contact to Address Book | `POST /addressbook/contact` |
| UC3 | Edit existing contact by name | `PUT /addressbook/contact/{id}` |
| UC4 | Delete contact by name | `DELETE /addressbook/contact/{id}` |
| UC5 | Add multiple contacts | `POST /addressbook/contacts/bulk` |
| UC6 | Multiple address books | `GET /addressbook/contacts/book/{name}` |
| UC7 | No duplicate contacts | Handled in service layer |
| UC8 | Search by city or state | `GET /addressbook/contacts/city/{city}` |
| UC9 | View persons by city or state | `GET /addressbook/contacts/group/city` |
| UC10 | Count contacts by city or state | `GET /addressbook/contacts/count/city` |
| UC11 | Sort contacts by name | `GET /addressbook/contacts/sort/name` |
| UC12 | Sort by city, state or zip | `GET /addressbook/contacts/sort/city` |

### Section 2 — IO Streams (UC13–UC15)

| UC | Feature | Endpoint |
|----|---------|----------|
| UC13 | Read/Write contacts to text file | — |
| UC14 | Read/Write contacts as CSV | — |
| UC15 | Read/Write contacts as JSON | `POST /addressbook/file/json/write` |

### Section 3 — JDBC / JPA (UC16–UC20)

| UC | Feature | Endpoint |
|----|---------|----------|
| UC16 | Retrieve all contacts from DB | `GET /addressbook/contacts` |
| UC17 | Update contact, sync with DB | `PUT /addressbook/contact/{id}` |
| UC18 | Retrieve contacts by date range | `GET /addressbook/contacts/date` |
| UC19 | Count contacts in DB by city/state | `GET /addressbook/contacts/count/city` |
| UC20 | Add contact to DB with transaction | `POST /addressbook/contact` |

### Section 4 — Threads (UC21)

| UC | Feature | Endpoint |
|----|---------|----------|
| UC21 | Add multiple contacts using threads | `POST /addressbook/contacts/bulk` |

### Section 5 — REST Assured (UC22–UC25)

| UC | Feature | Endpoint |
|----|---------|----------|
| UC22 | Read entries from JSON Server | `GET /addressbook/contacts` |
| UC23 | Add entries and sync memory | `POST /addressbook/contact` |
| UC24 | Update entry and sync memory | `PUT /addressbook/contact/{id}` |
| UC25 | Delete entry and sync memory | `DELETE /addressbook/contact/{id}` |

---

## API Reference

### Contact Endpoints

#### Add Contact — UC2 / UC20
```http
POST /addressbook/contact
Content-Type: application/json

{
    "firstName":       "Alice",
    "lastName":        "Smith",
    "address":         "123 Elm Street",
    "city":            "Mumbai",
    "state":           "MH",
    "zip":             "400001",
    "phone":           "9999999999",
    "email":           "alice@mail.com",
    "addressBookName": "Friends"
}
```
**Response `200`:**
```json
{
    "id":              1,
    "firstName":       "Alice",
    "lastName":        "Smith",
    "city":            "Mumbai",
    "addressBookName": "Friends",
    "dateAdded":       "2024-01-01"
}
```

---

####  Get All Contacts — UC16
```http
GET /addressbook/contacts
```

---

#### Get Contact by ID — UC16
```http
GET /addressbook/contact/{id}
```

---

#### Update Contact — UC17
```http
PUT /addressbook/contact/{id}
Content-Type: application/json

{
    "firstName": "Alice",
    "lastName":  "Smith",
    "city":      "Pune",
    "phone":     "8888888888",
    "addressBookName": "Friends"
}
```

---

####  Delete Contact — UC4
```http
DELETE /addressbook/contact/{id}
```
**Response `200`:**
```json
{ "message": "Contact with id 1 deleted successfully" }
```

---

####  Add Multiple Contacts (Threaded) — UC21
```http
POST /addressbook/contacts/bulk
Content-Type: application/json

[
    { "firstName": "Bob",   "lastName": "Jones", ... },
    { "firstName": "Carol", "lastName": "White", ... }
]
```

---

### Search & Filter Endpoints

####  Search by City — UC8
```http
GET /addressbook/contacts/city/{city}
```

#### Search by State — UC8
```http
GET /addressbook/contacts/state/{state}
```

#### Group by City — UC9
```http
GET /addressbook/contacts/group/city
```
**Response:**
```json
{
    "Mumbai": [ { "firstName": "Alice", ... } ],
    "Pune":   [ { "firstName": "Bob",   ... } ]
}
```

#### Group by State — UC9
```http
GET /addressbook/contacts/group/state
```

#### Count by City — UC10 / UC19
```http
GET /addressbook/contacts/count/city
```
**Response:**
```json
{
    "Mumbai": 3,
    "Pune":   2
}
```

#### Count by State — UC10 / UC19
```http
GET /addressbook/contacts/count/state
```

---

### Sort Endpoints — UC11 / UC12

####  Sort by Name
```http
GET /addressbook/contacts/sort/name
```

####  Sort by City
```http
GET /addressbook/contacts/sort/city
```

####  Sort by State
```http
GET /addressbook/contacts/sort/state
```

#### Sort by Zip
```http
GET /addressbook/contacts/sort/zip
```

---

### Address Book & Date Endpoints

#### Get by Address Book — UC6
```http
GET /addressbook/contacts/book/{bookName}
```

#### Get by Date Range — UC18
```http
GET /addressbook/contacts/date?start=2024-01-01&end=2024-12-31
```

---

### File IO Endpoints — Section 2

####  Write to JSON File — UC15
```http
POST /addressbook/file/json/write
```

#### Read from JSON File — UC15
```http
GET /addressbook/file/json/read
```

#### Sync JSON File to DB — UC22
```http
POST /addressbook/file/json/sync
```

---

##  Error Handling

All errors return a structured JSON response:

```json
{
    "status":    404,
    "error":     "NOT_FOUND",
    "message":   "No contact found with id: 99",
    "timestamp": "2024-01-01T10:00:00"
}
```

| HTTP Status | Error | Cause |
|-------------|-------|-------|
| `400` | `VALIDATION_FAILED` | Invalid field values |
| `404` | `NOT_FOUND` | Contact ID does not exist |
| `409` | `CONFLICT` | Duplicate contact in same address book |
| `500` | `INTERNAL_ERROR` | Unexpected server error |

### Validation Rules

| Field | Rule |
|-------|------|
| `firstName` | Must not be blank |
| `lastName` | Must not be blank |
| `phone` | Must be exactly 10 digits |
| `email` | Must be a valid email format |
| `addressBookName` | Must not be blank |

---

## 🧪 Testing

### Service Layer Tests — `AddressBookServiceTest.java`

| Test | UC | What it Verifies |
|------|----|-----------------|
| `testAddContact` | UC2 | Contact saves with correct fields |
| `testAddDuplicateContact` | UC7 | Throws `DuplicateContactException` |
| `testGetAllContacts` | UC16 | Returns non-empty list |
| `testGetContactById` | UC16 | Returns correct contact |
| `testUpdateContact` | UC17 | City updated correctly |
| `testSearchByCity` | UC8 | Finds contacts by city |
| `testSearchByState` | UC8 | Finds contacts by state |
| `testCountByCity` | UC10 | Returns city count map |
| `testGroupByCity` | UC9 | Returns grouped map |
| `testGetSortedByName` | UC11 | Alphabetical order verified |
| `testGetContactsAddedBetween` | UC18 | Date range filter works |
| `testAddMultipleContacts` | UC21 | All contacts saved via threads |
| `testGetContactsByAddressBook` | UC6 | Only correct book contacts returned |
| `testDeleteContact` | UC4 | Contact removed from DB |
| `testDeleteNonExistent` | UC4 | Throws `ContactNotFoundException` |

### REST Assured Tests — `AddressBookRestAssuredTest.java`

| Test | UC | What it Verifies |
|------|----|-----------------|
| `testGetAllContacts` | UC22 | `GET` returns 200 + list |
| `testAddContact` | UC23 | `POST` returns 200 + correct body |
| `testAddedContactAppearsInGetAll` | UC23 | Memory synced after add |
| `testUpdateContact` | UC24 | `PUT` updates city and phone |
| `testUpdatedContactReflectedInGetById` | UC24 | Memory synced after update |
| `testDeleteContact` | UC25 | `DELETE` returns 200 |
| `testDeletedContactReturns404` | UC25 | Memory synced after delete |
| `testInvalidData` | — | Returns `400 VALIDATION_FAILED` |
| `testDuplicateContact` | UC7 | Returns `409 CONFLICT` |

---

## Design Decisions

### UC7 — Duplicate Check
Duplicate is defined as same `firstName + lastName + addressBookName`.
Enforced at both DB level (unique constraint) and service layer.

### UC21 — Multi-threading
Multiple contacts are inserted using `CompletableFuture.supplyAsync()`
so each insert runs on a separate thread — non-blocking.

### UC24 — Open/Close Principle
`IAddressBookService` interface ensures the service is **open for extension**
(new data sources like CSV, JSON, DB) but **closed for modification**.

### Section 6 — Non-blocking IO
All DB and file operations use `@Transactional` + `CompletableFuture`
to ensure the main thread is never blocked during IO.

---

##  Database Schema

```sql
CREATE TABLE contacts (
    id                BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name        VARCHAR(255) NOT NULL,
    last_name         VARCHAR(255) NOT NULL,
    address           VARCHAR(255),
    city              VARCHAR(255),
    state             VARCHAR(255),
    zip               VARCHAR(255),
    phone             VARCHAR(10),
    email             VARCHAR(255),
    date_added        DATE,
    address_book_name VARCHAR(255) NOT NULL,
    UNIQUE KEY uk_name_addressbook (first_name, last_name, address_book_name)
);
```

---

##  Git Branch Strategy

Each use case is developed on its own branch and merged to `main`:

```
main
├── uc1-create-contact
├── uc2-add-contact
├── uc3-edit-contact
├── uc4-delete-contact
├── uc5-multiple-contacts
├── uc6-multiple-address-books
├── uc7-duplicate-check
├── uc8-search-by-city-state
├── uc9-view-by-city-state
├── uc10-count-by-city-state
├── uc11-sort-by-name
├── uc12-sort-by-city-state-zip
├── uc13-file-io-text
├── uc14-file-io-csv
├── uc15-file-io-json
├── uc16-jdbc-retrieve
├── uc17-jdbc-update
├── uc18-jdbc-date-range
├── uc19-jdbc-count
├── uc20-jdbc-add
├── uc21-threads-bulk-add
├── uc22-rest-read
├── uc23-rest-add
├── uc24-rest-update
└── uc25-rest-delete
``
