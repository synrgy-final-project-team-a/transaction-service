package com.synergy.transaction.repository;

import com.synergy.transaction.entity.Price;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceRepository extends JpaRepository<Price, Long> {

}
