package com.addressbook.threading;

import com.addressbook.AddressBook;
import com.addressbook.model.Contact;
import java.util.List;
import java.util.concurrent.*;

/**
 * UC21: Add Multiple Contacts Using Threads
 * Uses ExecutorService for concurrent contact insertions
 */
public class ThreadedContactService {
    private AddressBook addressBook;
    private ExecutorService executorService;

    public ThreadedContactService(AddressBook addressBook) {
        this.addressBook = addressBook;
        this.executorService = Executors.newFixedThreadPool(10);
    }

    /**
     * UC21: Add multiple contacts using threads
     * Each contact insertion runs on a separate thread
     */
    public List<Future<Boolean>> addMultipleContacts(List<Contact> contacts) {
        List<Future<Boolean>> futures = new java.util.ArrayList<>();
        
        for (Contact contact : contacts) {
            Future<Boolean> future = executorService.submit(() -> {
                try {
                    return addressBook.addContact(contact);
                } catch (Exception e) {
                    System.err.println("Error adding contact: " + e.getMessage());
                    return false;
                }
            });
            futures.add(future);
        }
        
        return futures;
    }

    /**
     * UC21: Add multiple contacts and wait for all to complete
     */
    public boolean addMultipleContactsAndWait(List<Contact> contacts) throws InterruptedException, ExecutionException {
        List<Future<Boolean>> futures = addMultipleContacts(contacts);
        
        boolean allSuccess = true;
        for (Future<Boolean> future : futures) {
            try {
                if (!future.get()) {
                    allSuccess = false;
                }
            } catch (ExecutionException e) {
                allSuccess = false;
                e.printStackTrace();
            }
        }
        
        return allSuccess;
    }

    /**
     * Shutdown the executor service
     */
    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
