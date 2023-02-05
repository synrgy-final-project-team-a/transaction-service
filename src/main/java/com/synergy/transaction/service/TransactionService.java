package com.synergy.transaction.service;

import java.util.List;
import java.util.Map;

public interface TransactionService {
    List<Map<String, Object>> getAllTransactionByProfileId(Long profileId);

    boolean deleteTransaction(Long profileId, Long transactionId);

    Boolean approveTransaction(Long transactionId);

    Boolean rejectTransaction(Long transactionId);

    Boolean softDeleteTransaction(Long transactionId);
    Map<String, List<Map<String, Object>>> getAllTransactionByIdTennant(Long profileId);

    Map<String, List<Map<String, Object>>> getAllTransactionByIdAdmin();
}
