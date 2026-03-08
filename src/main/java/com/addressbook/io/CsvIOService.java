package com.addressbook.io;

import com.addressbook.AddressBook;
import com.addressbook.model.Contact;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import java.io.*;
import java.util.List;

/**
 * UC14: Read/Write Address Book as CSV
 * Uses OpenCSV library for CSV operations
 */
public class CsvIOService {
    private static final String CSV_FILE_PATH = "addressbook.csv";

    /**
     * UC14: Write address book to CSV file using OpenCSV
     */
    public static void writeToCsv(AddressBook addressBook) throws Exception {
        try (Writer writer = new FileWriter(CSV_FILE_PATH)) {
            StatefulBeanToCsv<Contact> beanToCsv = new StatefulBeanToCsvBuilder<Contact>(writer)
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .build();
            
            // Write header
            writer.write("First Name,Last Name,Address,City,State,Zip,Phone,Email,Address Book Name\n");
            
            // Write contacts
            beanToCsv.write(addressBook.getContacts());
        }
    }

    /**
     * UC14: Read address book from CSV file using OpenCSV
     */
    public static AddressBook readFromCsv() throws Exception {
        AddressBook addressBook = new AddressBook();
        
        try (Reader reader = new FileReader(CSV_FILE_PATH)) {
            CsvToBean<Contact> csvToBean = new CsvToBeanBuilder<Contact>(reader)
                .withType(Contact.class)
                .withIgnoreLeadingWhiteSpace(true)
                .build();
            
            List<Contact> contacts = csvToBean.parse();
            
            for (Contact contact : contacts) {
                addressBook.addContact(contact);
            }
        }
        
        return addressBook;
    }
}
