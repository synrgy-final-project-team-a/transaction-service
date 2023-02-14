package com.synergy.transaction.controller;

import com.synergy.transaction.repository.BookingRepository;
import com.synergy.transaction.service.impl.TransactionServiceImpl;
import com.synergy.transaction.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admin/transactions")
@CrossOrigin("*")
public class TransactionControllerAdmin {

    @Autowired
    private TransactionServiceImpl transactionServiceImpl;

    @Autowired
    private Response res;

    @Autowired
    private BookingRepository bookingRepository;
    
    // @Autowired
    // private TransactionRepository transactionRepository;

    @GetMapping("/list")
    public ResponseEntity<Map<String, Object>> getTransactionList(
            @RequestParam() Integer page,
            @RequestParam() Integer size) {

        Pageable pagination = PageRequest.of(page, size);
        Page<Map<String, Object>> transactionsListForAdmin = bookingRepository.getAllTransactionByAdmin(pagination);

        return res.resSuccess(transactionsListForAdmin, "success", 200);
    }

    @GetMapping(value = "/{bookingId}")
    public ResponseEntity<Map<String, Object>> getByBookingId(
            @PathVariable("bookingId") Long bookingId
    ) {
        try {
            // transactionRepository.getWatchedTennant(bookingId);
            return transactionServiceImpl.getTransactionHistoryByIdBookingAdmin(bookingId);
        } catch (Exception e) {
            return res.internalServerError(e.getMessage());
        }
    }
}
