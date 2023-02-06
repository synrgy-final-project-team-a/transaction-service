package com.synergy.transaction.controller;

import com.synergy.transaction.service.impl.TransactionServiceImpl;
import com.synergy.transaction.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/tennant/transaction")
public class GetTransactionAllTennant {


    @Autowired
    private TransactionServiceImpl transactionServiceImpl;

    @Autowired
    private Response res;


    @GetMapping(value = "/list/{profileId}")
    public ResponseEntity<Map<String, Object>> getTransactionByProfile(@PathVariable("profileId") Long profileId) {
        return res.resSuccess(transactionServiceImpl.getAllTransactionByIdTennant(profileId), "success", 200);
    }
}
