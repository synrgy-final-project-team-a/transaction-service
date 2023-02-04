package com.synergy.transaction.service.impl;

import com.synergy.transaction.entity.Transaction;
import com.synergy.transaction.repository.TransactionRepository;
import com.synergy.transaction.service.TransactionService;
import com.synergy.transaction.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    public Response res;

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
    public Map getByIdTennant(Long transactionId) {
        try {
            Transaction checkingData = transactionRepository.getTransactionByIdTennant(transactionId);
            if (checkingData == null) {
                return (Map) res.notFoundError("Transaction cannot be found!");
            }
            return (Map) res.resSuccess(checkingData,"success",200);
        } catch (Exception e) {
            return (Map) res.clientError("Error get by id: " + e.getMessage());
        }
    }
}
