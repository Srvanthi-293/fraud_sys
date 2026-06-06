package com.fraudchecker.service;

import com.fraudchecker.model.FraudAnalysisResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for FraudAnalysisService.
 * Uses seeded H2 data — no manual input needed.
 *
 * T101 → SAFE   (0 pts)   — small amount, known payee, home city
 * T102 → FRAUD  (90 pts)  — high + new payee + odd hour + unusual location
 * T103 → REVIEW (50 pts)  — high amount + spending baseline
 * T104 → FRAUD  (90 pts)  — high + new payee + odd hour + unusual location
 */
@SpringBootTest
class FraudAnalysisServiceTest {

    @Autowired
    private FraudAnalysisService fraudAnalysisService;

    @Test
    @DisplayName("T101 — Rs.2,000 | Grocery | 1 PM | Chennai → SAFE (0 pts)")
    void t101_shouldBeSafe() {
        FraudAnalysisResponse response = fraudAnalysisService.analyzeTransaction(101L);

        assertEquals("SAFE", response.getStatus());
        assertEquals(0, response.getScore());
        assertTrue(response.getReasons().isEmpty());
        assertEquals("Sai", response.getCustomerName());
    }

    @Test
    @DisplayName("T102 — Rs.90,000 | Unknown | 3 AM | Mumbai → FRAUD (90+ pts)")
    void t102_shouldBeFraud() {
        FraudAnalysisResponse response = fraudAnalysisService.analyzeTransaction(102L);

        assertEquals("FRAUD", response.getStatus());
        assertTrue(response.getScore() >= 90);
        assertTrue(response.getReasons().contains("Amount exceeds Rs.50,000 threshold (+30)"));
        assertTrue(response.getReasons().contains("Transaction made to a new payee (+20)"));
        assertTrue(response.getReasons().contains("Transaction occurred during odd hours (+15)"));
        assertTrue(response.getReasons().contains("Transaction location differs from customer's home city (+25)"));
    }

    @Test
    @DisplayName("T103 — Rs.75,000 | Amazon | 2 PM | Bangalore → REVIEW")
    void t103_shouldBeReview() {
        FraudAnalysisResponse response = fraudAnalysisService.analyzeTransaction(103L);

        // High amount (30) + spending baseline (20) = 50 → REVIEW
        assertEquals("REVIEW", response.getStatus());
        assertTrue(response.getScore() >= 30);
        assertTrue(response.getReasons().contains("Amount exceeds Rs.50,000 threshold (+30)"));
    }

    @Test
    @DisplayName("T104 — Rs.65,000 | Unknown | 2 AM | Delhi → FRAUD")
    void t104_shouldBeFraud() {
        FraudAnalysisResponse response = fraudAnalysisService.analyzeTransaction(104L);

        assertEquals("FRAUD", response.getStatus());
        assertTrue(response.getScore() >= 61);
    }

    @Test
    @DisplayName("T105 — Rs.5,000 | Swiggy | Noon | Bangalore → SAFE")
    void t105_shouldBeSafe() {
        FraudAnalysisResponse response = fraudAnalysisService.analyzeTransaction(105L);

        assertEquals("SAFE", response.getStatus());
        assertEquals(0, response.getScore());
    }

    @Test
    @DisplayName("determineStatus — boundary checks")
    void determineStatus_boundaryChecks() {
        assertEquals("SAFE",   fraudAnalysisService.determineStatus(0));
        assertEquals("SAFE",   fraudAnalysisService.determineStatus(30));
        assertEquals("REVIEW", fraudAnalysisService.determineStatus(31));
        assertEquals("REVIEW", fraudAnalysisService.determineStatus(60));
        assertEquals("FRAUD",  fraudAnalysisService.determineStatus(61));
        assertEquals("FRAUD",  fraudAnalysisService.determineStatus(115));
    }

    @Test
    @DisplayName("explanationSummary — correct message per status")
    void explanationSummary_messages() {
        assertTrue(fraudAnalysisService.buildExplanationSummary("SAFE",   java.util.List.of()).contains("normal"));
        assertTrue(fraudAnalysisService.buildExplanationSummary("REVIEW", java.util.List.of()).contains("review"));
        assertTrue(fraudAnalysisService.buildExplanationSummary("FRAUD",  java.util.List.of()).contains("FRAUD"));
    }
}
