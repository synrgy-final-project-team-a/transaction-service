package com.synergy.transaction.repository;

import com.synergy.transaction.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;



public interface TransactionRepository extends JpaRepository<Transaction, Long> {


    @Query(value = "SELECT * FROM transaction t WHERE t.transaction_id = :transactionId AND t.deleted_at is null", nativeQuery = true)
    Transaction findByTransactionId(Long transactionId);


//    Optional<Transaction> findByTransactionIdAndProfileId(Long id, Long profileId);

}
