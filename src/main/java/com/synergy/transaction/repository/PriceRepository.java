package com.synergy.transaction.repository;

import com.synergy.transaction.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PriceRepository extends JpaRepository<Price, Long> {
    List<Price> findByRoomId(Long roomId);
}
