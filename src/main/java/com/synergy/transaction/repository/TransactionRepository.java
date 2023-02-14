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

//    @Transactional
//    @Modifying
//    @Query
//            (value = "UPDATE\n" +
//                    "  transaction\n" +
//                    "SET\n" +
//                    "  watched = true\n" +
//                    "FROM\n" +
//                    "  booking\n" +
//                    "WHERE\n" +
//                    "  booking.booking_id = transaction.booking_id\n" +
//                    "  AND booking.profile_id = :profileId", nativeQuery = true)
//    void getWatched(Long profileId);


    @Transactional
    @Modifying
    @Query
            (value = "UPDATE\n" +
                    "  transaction\n" +
                    "SET\n" +
                    "  watched_sk = true\n" +
                    "FROM\n" +
                    "  booking\n" +
                    "WHERE\n" +
                    "  booking.booking_id = transaction.booking_id\n" +
                    "  AND booking.profile_id = :profileId", nativeQuery = true)
    void getWatchedSeeker(Long profileId);

//    @Transactional
//    @Modifying
//    @Query
//            (value = "UPDATE\n" +
//                    "  transaction\n" +
//                    "SET\n" +
//                    "  watched_tn = true\n" +
//                    "FROM\n" +
//                    "  booking\n" +
//                    "WHERE\n" +
//                    "  booking.booking_id = transaction.booking_id\n" +
//                    "  AND booking.booking_id = :bookingId", nativeQuery = true)
//    void getWatchedTennant(Long bookingId);

    @Transactional
    @Modifying
    @Query
            (value = "UPDATE\n" +
                    "  transaction t\n" +
                    "SET\n" +
                    "  watched_tn = true\n" +
                    "FROM\n" +
                    "  kost k,\n" +
                    "  room r,\n" +
                    "  price p,\n" +
                    "  booking b\n" +
                    "WHERE\n" +
                    "  k.kost_id = r.kost_id\n" +
                    "  AND r.room_id = p.room_id\n" +
                    "  AND p.price_id = b.price_id\n" +
                    "  AND b.booking_id = t.booking_id\n" +
                    "  AND k.profile_id = :profileId", nativeQuery = true)
    void getWatchedTennant(Long profileId);



}
