package com.synergy.transaction.dto;
import com.synergy.transaction.entity.Profile;
import com.synergy.transaction.entity.Room;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionDto {

    private String bookingCode;

    private Profile profile;

    private Room room;

    private String paymentMethod;

    private LocalDateTime checkIn;

    private LocalDateTime checkOut;

    private LocalDateTime deadlinePayment;


    private String invoiceCode;

    private LocalDateTime deletedAt;
}
