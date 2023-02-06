package com.synergy.transaction.service;

import com.synergy.transaction.dto.PostBookingDto;
import com.synergy.transaction.dto.UploadProofOfPayment;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.Map;

public interface TransactionService {
    ResponseEntity<Map<String, Object>> bookKost(Long profileId, Long roomId, PostBookingDto postBookingDto);

    ResponseEntity<Map<String, Object>> uploadProofOfPayment(
            Long transactionId, UploadProofOfPayment uploadSpoofOfPayment) throws IOException;
}
