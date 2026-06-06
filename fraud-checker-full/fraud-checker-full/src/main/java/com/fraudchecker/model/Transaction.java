package com.fraudchecker.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaction {

    @Id
    private Long id;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private double amount;

    @Column(nullable = false)
    private String payee;

    /** true if this is the first-ever payment to this payee */
    @Column(nullable = false)
    private boolean newPayee;

    /** Hour of the day when the transaction occurred (0–23) */
    @Column(nullable = false)
    private int transactionHour;

    @Column(nullable = false)
    private String location;

    private LocalDateTime timestamp;
}
