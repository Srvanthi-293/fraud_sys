package com.fraudchecker.service;

import com.fraudchecker.model.Customer;
import com.fraudchecker.model.FraudAnalysisResponse;
import com.fraudchecker.model.Transaction;
import com.fraudchecker.model.TransactionFact;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FraudAnalysisService {

    private final KieContainer kieContainer;
    private final TransactionService transactionService;
    private final CustomerService customerService;

    public FraudAnalysisService(KieContainer kieContainer,
                                 TransactionService transactionService,
                                 CustomerService customerService) {
        this.kieContainer = kieContainer;
        this.transactionService = transactionService;
        this.customerService = customerService;
    }

    /**
     * Main entry point — fetches transaction + customer from DB,
     * runs Drools rules, and returns a full FraudAnalysisResponse.
     */
    public FraudAnalysisResponse analyzeTransaction(Long transactionId) {

        // 1. Fetch data from database
        Transaction transaction = transactionService.getTransactionById(transactionId);
        Customer customer = customerService.getCustomerById(transaction.getCustomerId());

        // 2. Derive location mismatch (case-insensitive comparison)
        boolean locationMismatch = !transaction.getLocation()
                .equalsIgnoreCase(customer.getHomeCity());

        // 3. Build the Drools fact
        TransactionFact fact = new TransactionFact(
                transaction.getAmount(),
                transaction.isNewPayee(),
                transaction.getTransactionHour(),
                locationMismatch,
                customer.getAverageTransactionAmount()
        );

        // 4. Open a KieSession, insert fact, fire all rules, then dispose
        KieSession session = kieContainer.newKieSession("FraudSession");
        try {
            session.insert(fact);
            session.fireAllRules();
        } finally {
            session.dispose();
        }

        // 5. Calculate final status and build response
        int score = fact.getScore();
        String status = determineStatus(score);
        String summary = buildExplanationSummary(status, fact.getReasons());

        return new FraudAnalysisResponse(
                transaction.getId(),
                customer.getName(),
                score,
                status,
                fact.getReasons(),
                summary
        );
    }

    /** 0–30 → SAFE | 31–60 → REVIEW | 61+ → FRAUD */
    String determineStatus(int score) {
        if (score <= 30) return "SAFE";
        if (score <= 60) return "REVIEW";
        return "FRAUD";
    }

    /** Human-readable explanation based on status and triggered rules */
    String buildExplanationSummary(String status, List<String> reasons) {
        return switch (status) {
            case "FRAUD"   -> "Transaction classified as FRAUD because multiple high-risk fraud indicators were triggered.";
            case "REVIEW"  -> "Transaction requires manual review. Some fraud indicators were detected.";
            default        -> "No fraud rules were triggered. Transaction appears normal.";
        };
    }
}
