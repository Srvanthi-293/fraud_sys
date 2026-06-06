package com.fraudchecker.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FraudAnalysisResponse {

    private Long transactionId;
    private String customerName;
    private int score;
    private String status;
    private List<String> reasons;
    private String explanationSummary;
}
