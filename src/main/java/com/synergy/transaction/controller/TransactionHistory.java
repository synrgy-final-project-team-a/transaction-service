package com.synergy.transaction.controller;

import com.synergy.transaction.service.impl.TransactionServiceImpl;
import com.synergy.transaction.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/v1/transactions")
public class TransactionHistory {

    @Autowired
    private TransactionServiceImpl transactionServiceImpl;

    @Autowired
    private Response res;


    @GetMapping(value = "/user/{profileId}")
    public ResponseEntity<Map<String, Object>> getTransactionByProfile(@PathVariable("profileId") Long profileId) {
        return res.resSuccess(transactionServiceImpl.getAllTransactionByProfileId(profileId), "success", 200);
    }

    @DeleteMapping(value = "/user/{profileId}/transaction/{transactionId}")
    public ResponseEntity<Map<String, Object>> deleteTransactionById(@PathVariable("profileId") Long profileId, @PathVariable("transactionId") Long transactionId) {
        if (!transactionServiceImpl.deleteTransaction(profileId, transactionId)) {
            return res.clientError("transaction doesn't exist");
        }
        return res.resSuccess(1, "success", 200);
    }

}
