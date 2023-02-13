package com.synergy.transaction.repository;

import com.synergy.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByTransactionId(Long transactionId);

    @Transactional
    @Modifying
    @Query
            (value = "INSERT t.watched = false FROM transaction t JOIN booking b ON b.booking_id = t.booking_id WHERE b.profile_id = :profileId", nativeQuery = true)
    Boolean getWatched(Long profileId);

}
