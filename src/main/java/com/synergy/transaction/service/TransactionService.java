package com.synergy.transaction.service;

import com.synergy.transaction.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface TransactionService {
    List<Map<String, Object>> getAllTransactionByProfileId(Long profileId);

    boolean deleteTransaction(Long profileId, Long transactionId);

    Page<Transaction> getAllTransactionHistoryByIdSeekerWithPagination(Long seekerId, Pageable pageable);


}
