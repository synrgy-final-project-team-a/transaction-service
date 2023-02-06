package com.synergy.transaction.controller;

import com.synergy.transaction.repository.BookingRepository;
import com.synergy.transaction.service.impl.TransactionServiceImpl;
import com.synergy.transaction.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/tennant/transactions")
public class TransactionControllerTennant {
    @Autowired
    private TransactionServiceImpl transactionServiceImpl;

    @Autowired
    private Response res;

    @Autowired
    private BookingRepository bookingRepository;

    @GetMapping("/list/{profileId}")
    public ResponseEntity<Map> getTransactionListByIdSeeker(
            @PathVariable Long profileId,
            @RequestParam(required = true) Integer page,
            @RequestParam(required = true) Integer size) {
        Pageable show_data = PageRequest.of(page, size);
        Page<Map<String, Object>> list = null;
        list = bookingRepository.getAllTransactionByIdTennant(profileId, show_data);

        return new ResponseEntity<>(res.success(list, "success get list kost!", 200), HttpStatus.OK);
    }
}
