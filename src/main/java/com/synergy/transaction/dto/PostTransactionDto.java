package com.synergy.transaction.dto;

import java.time.LocalDateTime;

public class PostTransactionDto {

    private String paymentMethod;

    private LocalDateTime checkIn;

    private LocalDateTime deadlinePayment;

    private String proofOfPayment;
}
