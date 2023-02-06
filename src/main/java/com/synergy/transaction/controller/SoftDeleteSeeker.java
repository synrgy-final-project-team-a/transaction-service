package com.synergy.transaction.controller;

import com.synergy.transaction.service.impl.TransactionServiceImpl;
import com.synergy.transaction.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/seeker/transaction")
@CrossOrigin(origins = "*")
public class SoftDeleteSeeker {

    @Autowired
    private TransactionServiceImpl transactionServiceImpl;

    @Autowired
    private Response res;

    @DeleteMapping(value = "/user/{transactionId}")
    public ResponseEntity<Map<String, Object>> deleteTransactionBySeeker(@PathVariable("transactionId") Long transactionId) {
        boolean deleteTransaction = transactionServiceImpl.softDeleteTransaction(transactionId);

        if (!deleteTransaction) {
            return res.clientError("transaction doesn't exist");
        }
        return res.resSuccess(1, "success", 200);
    }

}
