package com.synergy.transaction.dto;

import com.synergy.transaction.entity.Booking;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetTransactionDto {

    private String invoiceCode;

    private Booking booking;

    private String paymentMethod;

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

    private LocalDateTime deadlinePayment;

    private String proofOfPayment;
}
