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
//    List<Transaction> findByProfileId(Long profileId);
//
//    Optional<Transaction> findByTransactionIdAndProfileId(Long id, Long profileId);

//    @Query("SELECT t FROM Transaction t WHERE t.seeker.id = :seekerId")
//    Page<Transaction> findAllBySeekerId(@Param("seekerId") Long seekerId, Pageable pageable);

    @Query(value = "SELECT * FROM transaction t WHERE t.profile_id = :profileId AND t.kost_id = :kostId", nativeQuery = true)
    List<Transaction> findByKostId(Long kostId);


    @Query(value = "SELECT * FROM transaction t WHERE t.transaction_id = :transactionId AND t.deleted_at is null", nativeQuery = true)
    Transaction findByTransactionId(Long transactionId);


    Optional<Transaction> findByTransactionIdAndProfileId(Long id, Long profileId);

    @Query(value = "SELECT \n" +
            "k.kost_name,\n" +
            "t.booking_code,\n" +
            "k.address,\n" +
            "p.price,\n" +
            "t.status,\n" +
            "k.profile_id\n" +
            "FROM transaction t \n" +
            "JOIN room r on t.room_id = r.room_id\n" +
            "JOIN price p on p.room_id = r.room_id\n" +
            "JOIN kost k on k.kost_id = r.kost_id \n" +
            "WHERE k.profile_id = :profileId and t.deleted_at is null", nativeQuery = true)
    List<Map<String, Object>> findAllTransactionTennant(@Param(value = "profileId") Long profileId);

    @Query(value = "SELECT \n" +
            "k.kost_name,\n" +
            "t.booking_code,\n" +
            "k.address,\n" +
            "p.price,\n" +
            "t.status,\n" +
            "k.profile_id\n" +
            "FROM transaction t \n" +
            "JOIN room r on t.room_id = r.room_id\n" +
            "JOIN price p on p.room_id = r.room_id\n" +
            "JOIN kost k on k.kost_id = r.kost_id \n", nativeQuery = true)
    List<Map<String, Object>> findAllTransactionAdmin();

}
