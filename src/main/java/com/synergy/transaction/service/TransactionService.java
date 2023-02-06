package com.synergy.transaction.service;

import com.synergy.transaction.dto.PostBookingDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface TransactionService {
    ResponseEntity<Map<String, Object>> bookKost(Long profileId, Long roomId, PostBookingDto postBookingDto);
}
