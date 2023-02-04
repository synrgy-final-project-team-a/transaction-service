package com.synergy.transaction.service.impl;

import com.synergy.transaction.entity.Transaction;
import com.synergy.transaction.repository.TransactionRepository;
import com.synergy.transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;


    @Override
    public List<Map<String, Object>> getAllTransactionByProfileId(Long profileId) {
        List<Transaction> transactions = transactionRepository.findByProfileId(profileId);
        List<Map<String, Object>> transactionHistory = new ArrayList<>();

        for (Transaction transaction : transactions) {
            Map<String, Object> transactionInformation = new HashMap<>();

            transactionInformation.put("transaction_id", transaction.getTransactionId());
            transactionInformation.put("kost_name", transaction.getRoom().getKost().getKostName());
            transactionInformation.put("room_name", transaction.getRoom().getRoomName());
            transactionInformation.put("booking_code", transaction.getBookingCode());
            transactionInformation.put("check_in", transaction.getCheckIn());
            transactionInformation.put("check_out", transaction.getCheckOut());
            transactionInformation.put("deadline_payment", transaction.getDeadlinePayment());
            transactionInformation.put("invoice_code", transaction.getInvoiceCode());

            transactionHistory.add(transactionInformation);

        }
        return transactionHistory;
    }

    @Override
    public boolean deleteTransaction(Long profileId, Long transactionId) {
        Optional<Transaction> transaction = transactionRepository.findByTransactionIdAndProfileId(transactionId, profileId);

        if (transaction.isPresent()) {
            transactionRepository.deleteById(transactionId);
            return true;
        }
        return false;
    }

     @Override
    public Page<Transaction> getAllTransactionHistoryByIdSeekerWithPagination(Long seekerId, Pageable pageable) {
        return transactionRepository.findAllBySeekerId(seekerId, pageable);
    }



}
