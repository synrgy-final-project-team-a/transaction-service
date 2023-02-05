package com.synergy.transaction.service.impl;

import com.synergy.transaction.entity.Transaction;
import com.synergy.transaction.entity.enumeration.EStatus;
import com.synergy.transaction.repository.TransactionRepository;
import com.synergy.transaction.service.TransactionService;
import com.synergy.transaction.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.*;

@Service
public class TransactionServiceImpl implements TransactionService {
    private static Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);


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
    public Boolean approveTransaction(Long transactionId) {
        try {
            Transaction checkingData = transactionRepository.findByTransactionId(transactionId);
            if (checkingData == null) {
                return false;
            }
            checkingData.setStatus(EStatus.APPROVED.name());
            checkingData.setDeadlinePayment(null);
            checkingData.setInvoiceCode(checkingData.getCreatedAt().getYear() + "/" + checkingData.getCreatedAt().getMonthValue() + "/" + checkingData.getCreatedAt().getDayOfMonth() + "/" + checkingData.getTransactionId());
            Transaction done = transactionRepository.save(checkingData);
            return true;

        } catch (Exception e) {
            logger.error("Error approve transaction, {} " + e);
        }
        return false;
    }

    @Override
    public Boolean rejectTransaction(Long transactionId) {
        try {
            Transaction checkingData = transactionRepository.findByTransactionId(transactionId);
            if (checkingData == null) {
                return false;
            }
            checkingData.setStatus(EStatus.CANCELLED.name());
            checkingData.setDeadlinePayment(null);
            Transaction done = transactionRepository.save(checkingData);
            return true;

        } catch (Exception e) {
            logger.error("Error approve transaction, {} " + e);
        }
        return false;
    }


    @Override
    public Boolean softDeleteTransaction(Long transactionId) {
        try {
            Transaction checkingData = transactionRepository.findByTransactionId(transactionId);
            if (checkingData == null) {
                return false;
            }
            checkingData.setStatus(EStatus.CANCELLED.name());
            checkingData.setDeadlinePayment(null);
            checkingData.setDeletedAt(new Timestamp(System.currentTimeMillis()).toLocalDateTime());
            Transaction done = transactionRepository.save(checkingData);
            return true;

        } catch (Exception e) {
            logger.error("Error approve transaction, {} " + e);
        }
        return false;
    }

    @Override
    public Map<String, List<Map<String, Object>>> getAllTransactionByIdTennant(Long profileId) {
        List<Map<String, Object>> data = transactionRepository.findAllTransactionTennant(profileId);
        Map<String, List<Map<String, Object>>> resp = new HashMap<>();
        List<Map<String, Object>> transaction = new ArrayList<>();


        for (Map<String, Object> response : data) {
            Map<String, Object> itemTransaction = new HashMap<>();

            //Add field room
            itemTransaction.put("price", response.get("price"));
            itemTransaction.put("booking_id", response.get("booking_id"));
            itemTransaction.put("status", response.get("status"));
            itemTransaction.put("kost_name", response.get("kost_name"));
            itemTransaction.put("address", response.get("address"));

            transaction.add(itemTransaction);
        }

        resp.put("transaction", transaction);
        return resp;
    }

    @Override
    public Map<String, List<Map<String, Object>>> getAllTransactionByIdAdmin() {
        List<Map<String, Object>> data = transactionRepository.findAllTransactionAdmin();
        Map<String, List<Map<String, Object>>> resp = new HashMap<>();
        List<Map<String, Object>> transaction = new ArrayList<>();


        for (Map<String, Object> response : data) {
            Map<String, Object> itemTransaction = new HashMap<>();

            //Add field room
            itemTransaction.put("price", response.get("price"));
            itemTransaction.put("booking_id", response.get("booking_id"));
            itemTransaction.put("status", response.get("status"));
            itemTransaction.put("kost_name", response.get("kost_name"));
            itemTransaction.put("address", response.get("address"));

            transaction.add(itemTransaction);
        }

        resp.put("transaction", transaction);
        return resp;
    }
}
