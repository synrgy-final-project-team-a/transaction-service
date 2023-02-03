package com.synergy.transaction.service;

import java.util.List;
import java.util.Map;

public interface TransactionService {
    List<Map<String, Object>> getAllTransactionByProfileId(Long profileId);

    boolean deleteTransaction(Long profileId, Long transactionId);
}
