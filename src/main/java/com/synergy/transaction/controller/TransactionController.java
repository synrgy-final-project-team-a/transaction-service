package com.synergy.transaction.controller;

import com.synergy.transaction.dto.PostBookingDto;
import com.synergy.transaction.dto.UploadProofOfPayment;
import com.synergy.transaction.service.impl.TransactionServiceImpl;
import com.synergy.transaction.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/v1/transactions")
@CrossOrigin("*")
public class TransactionController {

    @Autowired
    private TransactionServiceImpl transactionServiceImpl;

    @Autowired
    private Response res;



    @GetMapping(value = "/{bookingId}")
    public ResponseEntity<Map<String, Object>> getByBookingId(
            @PathVariable("bookingId") Long profileId
    ) {
        try {
            return transactionServiceImpl.getTransactionHistoryByIdBooking(profileId);
        } catch (Exception e) {
            return res.internalServerError(e.getMessage());
        }
    }

}
