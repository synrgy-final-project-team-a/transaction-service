package com.synergy.transaction.service;

import com.synergy.transaction.dto.PostBookingDto;
import com.synergy.transaction.dto.UploadProofOfPayment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public interface TransactionService {
    ResponseEntity<Map<String, Object>> bookKost(Long profileId, Long roomId, PostBookingDto postBookingDto);

    Boolean approveTransaction(Long transactionId);

    Boolean rejectTransaction(Long transactionId);

    Boolean cancelTransaction(Long transactionId);

    boolean confirmTransaction(Long transactionId);

    ResponseEntity<Map<String, Object>> uploadProofOfPayment(
            Long transactionId, UploadProofOfPayment uploadSpoofOfPayment) throws IOException;

    ResponseEntity<Map<String, Object>> getTransactionHistoryByIdBooking(Long bookingId);

    ResponseEntity<Map<String, Object>> getTransactionHistoryByIdBookingAdmin(Long bookingId);

    Page<Map<String, Object>> getSeekerTransactions(Long profileId, Pageable pageable);

    Page<Map<String, Object>> getTenantTransactions(Long profileId, Pageable pageable);

    byte[] downloadInvoice(Long transactionId) throws IOException, SQLException;
}
