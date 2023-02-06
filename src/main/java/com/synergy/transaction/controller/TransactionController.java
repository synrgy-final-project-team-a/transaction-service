package com.synergy.transaction.controller;

import com.synergy.transaction.dto.PostBookingDto;
import com.synergy.transaction.service.impl.TransactionServiceImpl;
import com.synergy.transaction.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/transactions")
public class TransactionController {

    @Autowired
    private TransactionServiceImpl transactionServiceImpl;

    @Autowired
    private Response res;

    @PostMapping(value = "/user/{profileId}/room/{roomId}")
    public org.springframework.http.ResponseEntity<java.util.Map<String, Object>> createBookingRoom(
            @PathVariable("profileId") Long profileId,
            @PathVariable("roomId") Long roomId,
            @ModelAttribute PostBookingDto postBookingDto
    ) {
        try {
            Map<String, Object> bookKost = transactionServiceImpl.bookKost(profileId, roomId, postBookingDto);
            return res.resSuccess(bookKost.get("data"), "success", 200);
        } catch (Exception e) {
            return res.internalServerError(e.getMessage());
        }
    }


}
