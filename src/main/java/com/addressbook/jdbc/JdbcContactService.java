package com.addressbook.jdbc;

import com.addressbook.model.Contact;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * UC16-20: JDBC-based contact operations
 * Uses JDBC directly instead of JPA for database operations
 */
@Service
public class JdbcContactService {
    
    @Value("${spring.datasource.url}")
    private String dbUrl;
    
    @Value("${spring.datasource.username}")
    private String dbUsername;
    
    @Value("${spring.datasource.password}")
    private String dbPassword;

    /**
     * UC16: Retrieve all contacts from database using JDBC
     */
    public List<Contact> retrieveAllContacts() throws SQLException {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM contacts";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                contacts.add(mapResultSetToContact(rs));
            }
        }
        
        return contacts;
    }

    /**
     * UC17: Update contact in DB and sync with memory
     */
    public Contact updateContact(Long id, Contact updatedContact) throws SQLException {
        String sql = "UPDATE contacts SET first_name=?, last_name=?, address=?, city=?, " +
                     "state=?, zip=?, phone=?, email=?, address_book_name=? WHERE id=?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, updatedContact.getFirstName());
            pstmt.setString(2, updatedContact.getLastName());
            pstmt.setString(3, updatedContact.getAddress());
            pstmt.setString(4, updatedContact.getCity());
            pstmt.setString(5, updatedContact.getState());
            pstmt.setString(6, updatedContact.getZip());
            pstmt.setString(7, updatedContact.getPhone());
            pstmt.setString(8, updatedContact.getEmail());
            pstmt.setString(9, updatedContact.getAddressBookName());
            pstmt.setLong(10, id);
            
            pstmt.executeUpdate();
        }
        
        // Re-fetch from DB to verify sync
        return getContactById(id);
    }

    /**
     * UC18: Retrieve contacts added in a date range
     */
    public List<Contact> retrieveContactsByDateRange(LocalDate startDate, LocalDate endDate) throws SQLException {
        List<Contact> contacts = new ArrayList<>();
        String sql = "SELECT * FROM contacts WHERE date_added BETWEEN ? AND ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setDate(1, Date.valueOf(startDate));
            pstmt.setDate(2, Date.valueOf(endDate));
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    contacts.add(mapResultSetToContact(rs));
                }
            }
        }
        
        return contacts;
    }

    /**
     * UC19: Count contacts by city from DB using SQL aggregate functions
     */
    public java.util.Map<String, Long> countByCity() throws SQLException {
        java.util.Map<String, Long> cityCount = new java.util.HashMap<>();
        String sql = "SELECT city, COUNT(*) as count FROM contacts GROUP BY city";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                cityCount.put(rs.getString("city"), rs.getLong("count"));
            }
        }
        
        return cityCount;
    }

    /**
     * UC19: Count contacts by state from DB using SQL aggregate functions
     */
    public java.util.Map<String, Long> countByState() throws SQLException {
        java.util.Map<String, Long> stateCount = new java.util.HashMap<>();
        String sql = "SELECT state, COUNT(*) as count FROM contacts GROUP BY state";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                stateCount.put(rs.getString("state"), rs.getLong("count"));
            }
        }
        
        return stateCount;
    }

    /**
     * UC20: Add new contact to DB with transaction
     */
    public Contact addContactWithTransaction(Contact contact) throws SQLException {
        String sql = "INSERT INTO contacts (first_name, last_name, address, city, state, zip, phone, email, address_book_name, date_added) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false); // Start transaction
            
            try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                pstmt.setString(1, contact.getFirstName());
                pstmt.setString(2, contact.getLastName());
                pstmt.setString(3, contact.getAddress());
                pstmt.setString(4, contact.getCity());
                pstmt.setString(5, contact.getState());
                pstmt.setString(6, contact.getZip());
                pstmt.setString(7, contact.getPhone());
                pstmt.setString(8, contact.getEmail());
                pstmt.setString(9, contact.getAddressBookName());
                pstmt.setDate(10, Date.valueOf(contact.getDateAdded() != null ? contact.getDateAdded() : LocalDate.now()));
                
                pstmt.executeUpdate();
                
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        contact.setId(generatedKeys.getLong(1));
                    }
                }
                
                conn.commit(); // Commit transaction
            }
        } catch (SQLException e) {
            if (conn != null) {
                conn.rollback(); // Rollback on error
            }
            throw e;
        } finally {
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
        
        return contact;
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    }

    private Contact mapResultSetToContact(ResultSet rs) throws SQLException {
        return Contact.builder()
            .id(rs.getLong("id"))
            .firstName(rs.getString("first_name"))
            .lastName(rs.getString("last_name"))
            .address(rs.getString("address"))
            .city(rs.getString("city"))
            .state(rs.getString("state"))
            .zip(rs.getString("zip"))
            .phone(rs.getString("phone"))
            .email(rs.getString("email"))
            .addressBookName(rs.getString("address_book_name"))
            .dateAdded(rs.getDate("date_added") != null ? rs.getDate("date_added").toLocalDate() : null)
            .build();
    }

    private Contact getContactById(Long id) throws SQLException {
        String sql = "SELECT * FROM contacts WHERE id=?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setLong(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToContact(rs);
                }
            }
        }
        
        return null;
    }
}
