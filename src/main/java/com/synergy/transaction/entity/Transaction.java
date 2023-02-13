package com.synergy.transaction.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "transaction")
@Where(clause = "deleted_at IS NULL")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long transactionId;

    @Column(name = "invoice_code", unique = true)
    private String invoiceCode;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", nullable = false)
    private Booking booking;

    @Column(name = "payment_method")
    private String paymentMethod;


    @Column(name = "check_in", nullable = false)
    private LocalDateTime checkIn;

    @Column(name = "check_out", nullable = false)
    private LocalDateTime checkOut;

    @Column(name = "deadline_payment")
    private LocalDateTime deadlinePayment;

    @Column(name = "proof_of_payment", columnDefinition = "TEXT")
    private String proofOfPayment;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "watched")
    private Boolean watched;

    @Column(name = "created_at", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT now()")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false, columnDefinition = "TIMESTAMP DEFAULT now()")
    @CreationTimestamp
    private LocalDateTime updatedAt;
}
