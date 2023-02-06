package com.synergy.transaction.repository;

import com.synergy.transaction.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
@Query(nativeQuery = true, value = "SELECT \n" +
        "k.kost_name,\n" +
        "b.booking_id,\n" +
        "b.booking_code,\n" +
        "k.address,\n" +
        "k.city,\n" +
        "k.province,\n" +
        "p.price,\n" +
        "t.status\n" +
        "FROM booking b \n" +
        "JOIN transaction t on t.booking_id = b.booking_id \n" +
        "JOIN room r on b.room_id  = r.room_id\n" +
        "JOIN price p on p.room_id = r.room_id\n" +
        "JOIN kost k on k.kost_id = r.kost_id \n" +
        "WHERE k.profile_id = :profile_id\n" +
        "AND t.deleted_at is null")
public Page<Map<String, Object>> getAllTransactionByIdTennant(@Param(value = "profile_id") Long profileId, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT \n" +
            "k.kost_name,\n" +
            "b.booking_id,\n" +
            "b.booking_code,\n" +
            "k.address,\n" +
            "k.city,\n" +
            "k.province,\n" +
            "p.price,\n" +
            "t.status\n" +
            "FROM booking b \n" +
            "JOIN transaction t on t.booking_id = b.booking_id \n" +
            "JOIN room r on b.room_id  = r.room_id\n" +
            "JOIN price p on p.room_id = r.room_id\n" +
            "JOIN kost k on k.kost_id = r.kost_id \n" +
            "WHERE b.profile_id = :profile_id\n" +
            "AND t.deleted_at is null")
    public Page<Map<String, Object>> getAllTransactionByIdSeeker(@Param(value = "profile_id") Long profileId, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT \n" +
            "k.kost_name,\n" +
            "b.booking_id,\n" +
            "b.booking_code,\n" +
            "k.address,\n" +
            "k.city,\n" +
            "k.province,\n" +
            "p.price,\n" +
            "t.status\n" +
            "FROM booking b \n" +
            "JOIN transaction t on t.booking_id = b.booking_id \n" +
            "JOIN room r on b.room_id  = r.room_id\n" +
            "JOIN price p on p.room_id = r.room_id\n" +
            "JOIN kost k on k.kost_id = r.kost_id \n")
    public Page<Map<String, Object>> getAllTransactionByAdmin( Pageable pageable);
}
