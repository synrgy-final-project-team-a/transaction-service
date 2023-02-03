package com.synergy.transaction.repository;

import com.synergy.transaction.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
