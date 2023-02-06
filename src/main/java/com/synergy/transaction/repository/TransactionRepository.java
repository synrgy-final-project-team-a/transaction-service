package com.synergy.transaction.repository;

import com.synergy.transaction.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
//    List<Transaction> findByProfileId(Long profileId);
//
//    Optional<Transaction> findByTransactionIdAndProfileId(Long id, Long profileId);

//    @Query("SELECT t FROM Transaction t WHERE t.seeker.id = :seekerId")
//    Page<Transaction> findAllBySeekerId(@Param("seekerId") Long seekerId, Pageable pageable);

}
