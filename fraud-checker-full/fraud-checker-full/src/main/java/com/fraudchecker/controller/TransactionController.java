package com.fraudchecker.controller;

import com.fraudchecker.model.Transaction;
import com.fraudchecker.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    /** GET /api/customers/{id}/transactions — all transactions for a customer */
    @GetMapping("/customers/{id}/transactions")
    public List<Transaction> getTransactionsByCustomer(@PathVariable Long id) {
        return transactionService.getTransactionsByCustomer(id);
    }

    /** GET /api/transactions/{id} — a single transaction by ID */
    @GetMapping("/transactions/{id}")
    public ResponseEntity<Transaction> getTransactionById(@PathVariable Long id) {
        return ResponseEntity.ok(transactionService.getTransactionById(id));
    }
}
