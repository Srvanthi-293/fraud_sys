package com.fraudchecker.config;

import com.fraudchecker.model.Customer;
import com.fraudchecker.model.Transaction;
import com.fraudchecker.repository.CustomerRepository;
import com.fraudchecker.repository.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


@Component
public class DataLoader implements CommandLineRunner {

    private final CustomerRepository customerRepository;
    private final TransactionRepository transactionRepository;

    public DataLoader(CustomerRepository customerRepository,
                      TransactionRepository transactionRepository) {
        this.customerRepository = customerRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void run(String... args) {

        // ── Customers ──────────────────────────────────────────────────────
        customerRepository.save(new Customer(1L, "Sai",   "Chennai",   25000));
        customerRepository.save(new Customer(2L, "Rahul", "Bangalore", 18000));
        customerRepository.save(new Customer(3L, "Priya", "Hyderabad", 22000));


        transactionRepository.save(new Transaction(
                101L, 1L, 2000, "Grocery Store", false, 13, "Chennai",
                LocalDateTime.of(2025, 6, 5, 13, 0)));

        transactionRepository.save(new Transaction(
                102L, 1L, 90000, "Unknown Payee", true, 3, "Mumbai",
                LocalDateTime.of(2025, 6, 5, 3, 0)));


        transactionRepository.save(new Transaction(
                103L, 2L, 75000, "Amazon", false, 14, "Bangalore",
                LocalDateTime.of(2025, 6, 5, 14, 0)));


        transactionRepository.save(new Transaction(
                104L, 3L, 65000, "Unknown Payee", true, 2, "Delhi",
                LocalDateTime.of(2025, 6, 5, 2, 0)));


        transactionRepository.save(new Transaction(
                105L, 2L, 5000, "Swiggy", false, 12, "Bangalore",
                LocalDateTime.of(2025, 6, 5, 12, 0)));

        transactionRepository.save(new Transaction(
                106L, 3L, 8000, "Flipkart", false, 2, "Hyderabad",
                LocalDateTime.of(2025, 6, 5, 2, 30)));
    }
}
