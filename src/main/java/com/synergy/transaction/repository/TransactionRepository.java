package com.synergy.transaction.repository;

import com.synergy.transaction.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByTransactionId(Long transactionId);

    @Transactional
    @Modifying
    @Query
            (value = "UPDATE\n" +
                    "  transaction\n" +
                    "SET\n" +
                    "  watched_sk = true\n" +
                    "WHERE\n" +
                    "  AND booking_id = :bookingId", nativeQuery = true)
    void getWatchedSeeker(Long bookingId);

    @Transactional
    @Modifying
    @Query
            (value = "UPDATE\n" +
                    "  transaction\n" +
                    "SET\n" +
                    "  watched_tn = true\n" +
                    "WHERE\n" +
                    "  AND booking_id = :bookingId", nativeQuery = true)
    void getWatchedTennant(Long bookingId);



}
