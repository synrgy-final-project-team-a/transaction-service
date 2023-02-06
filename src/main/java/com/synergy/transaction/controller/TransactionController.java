package com.synergy.transaction.controller;

import com.synergy.transaction.dto.PostBookingDto;
import com.synergy.transaction.service.impl.TransactionServiceImpl;
import com.synergy.transaction.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/v1/transactions")
public class TransactionController {

    @Autowired
    private TransactionServiceImpl transactionServiceImpl;

    @Autowired
    private Response res;

    @PostMapping(value = "/user/{profileId}/room/{roomId}")
    public ResponseEntity<Map<String, Object>> createBookingRoom(
            @PathVariable("profileId") Long profileId,
            @PathVariable("roomId") Long roomId,
            @ModelAttribute @Valid PostBookingDto postBookingDto
    ) {
        try {
            System.out.println(postBookingDto.getPrice_id());
            return transactionServiceImpl.bookKost(profileId, roomId, postBookingDto);
        } catch (Exception e) {
            return res.internalServerError(e.getMessage());
        }
    }


}
