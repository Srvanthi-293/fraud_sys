package com.fraudchecker.controller;

import com.fraudchecker.model.FraudAnalysisResponse;
import com.fraudchecker.service.FraudAnalysisService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fraud")
public class FraudController {

    private final FraudAnalysisService fraudAnalysisService;

    public FraudController(FraudAnalysisService fraudAnalysisService) {
        this.fraudAnalysisService = fraudAnalysisService;
    }

    /**
     * GET /api/fraud/analyze/{transactionId}
     *
     * Fetches the transaction and customer from H2,
     * runs the Drools rule engine, and returns a full fraud analysis.
     */
    @GetMapping("/analyze/{transactionId}")
    public ResponseEntity<FraudAnalysisResponse> analyze(@PathVariable Long transactionId) {
        return ResponseEntity.ok(fraudAnalysisService.analyzeTransaction(transactionId));
    }
}
