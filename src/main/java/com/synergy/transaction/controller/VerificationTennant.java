package com.synergy.transaction.controller;


import com.synergy.transaction.service.impl.TransactionServiceImpl;
import com.synergy.transaction.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/tennant/transaction")
@CrossOrigin(origins = "*")
public class VerificationTennant {

    @Autowired
    private TransactionServiceImpl transactionServiceImpl;

    @Autowired
    private Response res;


    @PostMapping(value = "/user/{transactionId}")
    public ResponseEntity<Map<String, Object>> approveTransaction(@PathVariable("transactionId") Long transactionId) {
        boolean approvedTransaction = transactionServiceImpl.approveTransaction(transactionId);

        if (!approvedTransaction) {
            return res.clientError("transaction doesn't exist");
        }
        return res.resSuccess(1, "success", 201);    }

    @DeleteMapping(value = "/user/{transactionId}")
    public ResponseEntity<Map<String, Object>> rejectTransaction(@PathVariable("transactionId") Long transactionId) {
        boolean rejectedTransaction = transactionServiceImpl.rejectTransaction(transactionId);

        if (!rejectedTransaction) {
            return res.clientError("transaction doesn't exist");
        }
        return res.resSuccess(1, "success", 200);    }

}
