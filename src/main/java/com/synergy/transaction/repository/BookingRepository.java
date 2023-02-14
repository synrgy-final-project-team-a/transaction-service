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
            "t.transaction_id,\n" +
            "t.watched_tn,\n" +
            "k.kost_name,\n" +
            "b.booking_id,\n" +
            "b.booking_code,\n" +
            "k.address,\n" +
            "k.city,\n" +
            "k.province,\n" +
            "p.price,\n" +
            "t.status,\n" +
            "t.deadline_payment\n" +
            "FROM booking b \n" +
            "JOIN transaction t on t.booking_id = b.booking_id \n" +
            "JOIN price p on p.price_id = b.price_id\n" +
            "JOIN room r on p.room_id  = r.room_id\n" +
            "JOIN kost k on k.kost_id = r.kost_id \n" +
            "WHERE k.profile_id = :profile_id\n" +
            "AND t.deleted_at is null\n" +
            "ORDER BY t.updated_at DESC"
    )
    Page<Map<String, Object>> getAllTransactionByIdTenant(@Param(value = "profile_id") Long profileId, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT \n" +
            "t.transaction_id,\n" +
            "t.status,\n" +
            "t.deadline_payment,\n" +
            "t.watched_sk,\n" +
            "t.watched_tn\n" +
            "FROM booking b \n" +
            "JOIN transaction t on t.booking_id = b.booking_id \n" +
            "JOIN price p on p.price_id = b.price_id\n" +
            "JOIN room r on p.room_id  = r.room_id\n" +
            "JOIN kost k on k.kost_id = r.kost_id \n" +
            "WHERE k.profile_id = :profile_id\n" +
            "AND t.deleted_at is null\n" +
            "ORDER BY t.updated_at DESC"
    )
    Page<Map<String, Object>> getListTransactionIdOfTenant(@Param(value = "profile_id") Long profileId, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT \n" +
            "t.transaction_id,\n" +
            "t.watched_sk,\n" +
            "k.kost_name,\n" +
            "b.booking_id,\n" +
            "b.booking_code,\n" +
            "k.address,\n" +
            "k.city,\n" +
            "k.province,\n" +
            "p.price,\n" +
            "t.status,\n" +
            "t.deadline_payment\n" +
            "FROM booking b \n" +
            "JOIN transaction t on t.booking_id = b.booking_id \n" +
            "JOIN price p on p.price_id = b.price_id\n" +
            "JOIN room r on p.room_id  = r.room_id\n" +
            "JOIN kost k on k.kost_id = r.kost_id \n" +
            "WHERE b.profile_id = :profile_id\n" +
            "AND t.deleted_at is null\n" +
            "ORDER BY t.updated_at DESC"
    )
    Page<Map<String, Object>> getAllTransactionByIdSeeker(@Param(value = "profile_id") Long profileId, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT \n" +
            "t.transaction_id,\n" +
            "t.status,\n" +
            "t.deadline_payment\n" +
            "FROM booking b \n" +
            "JOIN transaction t on t.booking_id = b.booking_id \n" +
            "JOIN price p on p.price_id = b.price_id\n" +
            "JOIN room r on r.room_id  = p.room_id\n" +
            "JOIN kost k on k.kost_id = r.kost_id \n" +
            "WHERE b.profile_id = :profile_id\n" +
            "AND t.deleted_at is null\n" +
            "ORDER BY t.updated_at DESC"
    )
    Page<Map<String, Object>> getListTransactionIdOfSeeker(@Param(value = "profile_id") Long profileId, Pageable pageable);

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
            "JOIN price p on p.price_id = b.price_id\n" +
            "JOIN room r on p.room_id  = r.room_id\n" +
            "JOIN kost k on k.kost_id = r.kost_id \n")
    Page<Map<String, Object>> getAllTransactionByAdmin(Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT \n" +
            "k.kost_name,\n" +
            "b.booking_id,\n" +
            "b.booking_code,\n" +
            "b.name,\n" +
            "t.payment_method,\n" +
            "b.phone_number,\n" +
            "k.address,\n" +
            "r.room_name,\n" +
            "k.city,\n" +
            "k.province,\n" +
            "p.price,\n" +
            "t.status,\n" +
            "p.duration_type,\n" +
            "t.updated_at,\n" +
            "pro.id,\n" +
            "pro.bank_name \n" +
            "FROM booking b \n" +
            "JOIN transaction t on t.booking_id = b.booking_id \n" +
            "JOIN price p on p.price_id = b.price_id\n" +
            "JOIN room r on p.room_id  = r.room_id\n" +
            "JOIN kost k on k.kost_id = r.kost_id \n" +
            "JOIN profile pro on pro.id = k.profile_id \n" +
            "WHERE b.booking_id = :booking_id\n" +
            "AND t.deleted_at is null")
    List<Map<String, Object>> getTransactionById(@Param(value = "booking_id") Long bookingId);

    @Query(nativeQuery = true, value = "SELECT \n" +
            "k.kost_name,\n" +
            "b.booking_id,\n" +
            "b.booking_code,\n" +
            "b.name,\n" +
            "b.phone_number,\n" +
            "k.address,\n" +
            "r.room_name,\n" +
            "k.city,\n" +
            "k.province,\n" +
            "p.price,\n" +
            "t.status,\n" +
            "t.payment_method,\n" +
            "p.duration_type,\n" +
            "t.updated_at,\n" +
            "pro.id,\n" +
            "pro.bank_name \n" +
            "FROM booking b \n" +
            "JOIN transaction t on t.booking_id = b.booking_id \n" +
            "JOIN price p on p.price_id = b.price_id\n" +
            "JOIN room r on p.room_id  = r.room_id\n" +
            "JOIN kost k on k.kost_id = r.kost_id \n" +
            "JOIN profile pro on pro.id = k.profile_id \n" +
            "WHERE b.booking_id = :booking_id \n" +
            "AND t.deleted_at is null \n")
    List<Map<String, Object>> getTransactionByIdAdmin(@Param(value = "booking_id") Long bookingId);
}
