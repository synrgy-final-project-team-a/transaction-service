package com.synergy.transaction.service;

import com.synergy.transaction.dto.PostBookingDto;

import java.util.Map;

public interface TransactionService {
    Map<String, Object> bookKost(Long profileId, Long roomId, PostBookingDto postBookingDto);
}
