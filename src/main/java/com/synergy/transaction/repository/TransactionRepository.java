package com.synergy.transaction.repository;

import com.synergy.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByProfileId(Long profileId);

    Optional<Transaction> findByTransactionIdAndProfileId(Long id, Long profileId);

    @Query(value = "SELECT * FROM transaction t WHERE t.transaction_id = :transactionId", nativeQuery = true)
    Transaction getTransactionByIdTennant(Long transactionId);
}
