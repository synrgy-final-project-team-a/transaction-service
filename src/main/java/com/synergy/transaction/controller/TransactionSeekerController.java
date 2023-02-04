package com.synergy.transaction.controller;

import com.synergy.transaction.service.TransactionService;
import com.synergy.transaction.service.impl.TransactionServiceImpl;
import com.synergy.transaction.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
@RestController
public class TransactionSeekerController {
    @Autowired
    private Response res;
    @Autowired
    TransactionService transactionService;

    @GetMapping(value = {"/Transaction/get/{transaction_id}"})
    public ResponseEntity<Map<String, Object>> getTransactionById(@PathVariable(value = "transaction_id") Long transaction_id) {
        return res.resSuccess(transactionService.getTransactionByIdSeeker(transaction_id), "success", 200);
    }
}
