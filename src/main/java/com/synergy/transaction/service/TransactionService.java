package com.synergy.transaction.service;

import com.synergy.transaction.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import com.synergy.transaction.dto.PostBookingDto;
import com.synergy.transaction.dto.UploadProofOfPayment;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Map;

public interface TransactionService {
    ResponseEntity<Map<String, Object>> bookKost(Long profileId, Long roomId, PostBookingDto postBookingDto);

    boolean deleteTransaction(Long profileId, Long transactionId);

    Page<Transaction> getAllTransactionHistoryByIdSeekerWithPagination(Long seekerId, Pageable pageable);


    ResponseEntity<Map<String, Object>> uploadProofOfPayment(
            Long transactionId, UploadProofOfPayment uploadSpoofOfPayment) throws IOException;
}
