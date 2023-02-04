package com.synergy.transaction.controller;

import com.synergy.transaction.service.impl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/tennant")
public class TransactionTennantController {
    @Autowired
    private TransactionServiceImpl transactionServiceImpl;

    @GetMapping(value = "/transaction/get/{transactionId}")
    public ResponseEntity<Map> getById(@PathVariable("transactionId") Long transactionId) {
        return new ResponseEntity<Map>(transactionServiceImpl.getByIdTennant(transactionId), HttpStatus.OK);
    }
}
