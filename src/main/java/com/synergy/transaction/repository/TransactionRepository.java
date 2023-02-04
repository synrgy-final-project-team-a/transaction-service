package com.synergy.transaction.repository;

import com.synergy.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByProfileId(Long profileId);

    Optional<Transaction> findByTransactionIdAndProfileId(Long id, Long profileId);

    @Query(value = "SELECT * FROM Transaction t WHERE t.transaction_id = :id", nativeQuery = true)
    Transaction getTransactionDataSeeker(Long transaction_id);
}
