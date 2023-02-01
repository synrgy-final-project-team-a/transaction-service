package com.synergy.transaction.entity;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@Entity
@Table(name = "room")
@SQLDelete(sql = "UPDATE room SET deleted_at = now() WHERE room_id=?")
@Where(clause = "deleted_at is null")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id", nullable = false)
    private Long id;

    @Column(name = "room_name", nullable = false)
    private String roomName;

    @Column(name = "inside_room_photo",  columnDefinition = "TEXT")
    private String insideRoomPhoto;

    @Column(name = "other_room_photo",  columnDefinition = "TEXT")
    private String otherRoomPhoto;

    @Column(name = "quantity_room")
    private Integer quantityRoom;


    @Column(name = "available_room")
    private Integer availableRoom;

    @Column(name = "size_room")
    private String sizeRoom;

    @Column(name = "facility_id")
    private Long facilityId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "kost_id")
    private Kost kost;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

}