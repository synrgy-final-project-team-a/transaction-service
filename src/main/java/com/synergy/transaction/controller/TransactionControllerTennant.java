package com.synergy.transaction.controller;

import com.synergy.transaction.repository.TransactionRepository;
import com.synergy.transaction.service.impl.TransactionServiceImpl;
import com.synergy.transaction.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/tennant/transactions")
@CrossOrigin("*")
public class TransactionControllerTennant {

    private final Logger logger = LoggerFactory.getLogger(TransactionControllerTennant.class);

    @Autowired
    private TransactionServiceImpl transactionServiceImpl;

    @Autowired
    private Response res;
    @Autowired
    private TransactionRepository transactionRepository;

    @GetMapping("/list/{profileId}")
    public ResponseEntity<Map<String, Object>> getTransactionListByIdTennant(
            @PathVariable Long profileId,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {

        try {
            Pageable pagination = PageRequest.of(page, size);
            Page<Map<String, Object>> tenantTransactions = transactionServiceImpl.getTenantTransactions(profileId, pagination);
            return res.resSuccess(tenantTransactions, "success", 200);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return res.internalServerError(e.getMessage());
        }
    }

    @PostMapping(value = "/confirm/{transactionId}")
    public ResponseEntity<Map<String, Object>> confirmTransaction(@PathVariable("transactionId") Long transactionid) {
        try {
            boolean isConfirmed = transactionServiceImpl.confirmTransaction(transactionid);

            if (!isConfirmed) {
                return res.clientError("transaction doesn't exist");
            }

            return res.resSuccess(1, "success", 201);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return res.internalServerError(e.getMessage());
        }
    }

    @PostMapping(value = "/approve/{transactionId}")
    public ResponseEntity<Map<String, Object>> approveTransaction(@PathVariable("transactionId") Long transactionId) {
        try {
            boolean approvedTransaction = transactionServiceImpl.approveTransaction(transactionId);

            if (!approvedTransaction) {
                return res.clientError("transaction doesn't exist");
            }

            return res.resSuccess(1, "success", 201);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return res.internalServerError(e.getMessage());
        }
    }

    @DeleteMapping(value = "/reject/{transactionId}")
    public ResponseEntity<Map<String, Object>> rejectTransaction(@PathVariable("transactionId") Long transactionId) {
        try {
            boolean rejectedTransaction = transactionServiceImpl.rejectTransaction(transactionId);

            if (!rejectedTransaction) {
                return res.clientError("transaction doesn't exist");
            }
            return res.resSuccess(1, "success", 200);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return res.internalServerError(e.getMessage());
        }
    }

}
