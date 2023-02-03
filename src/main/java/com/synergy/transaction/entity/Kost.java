package com.synergy.transaction.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@SQLDelete(sql = "UPDATE kost SET deleted_at = now() WHERE kost_id=?")
@Table(name = "kost")
@Where(clause = "deleted_at is null")
public class Kost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "kost_id")
    private Long id;

    @Column(name = "kost_name", nullable = false)
    private String kostName;


    @Column(name = "description", nullable = false)
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    @Column(name = "pic")
    private String pic;

    @Column(name = "pic_phone_number")
    private String picPhoneNumber;

    @Column(name = "additional_notes")
    @Type(type = "org.hibernate.type.TextType")
    private String additionalNotes;

    @Column(name = "front_building_photo", columnDefinition = "TEXT")
    private String frontBuildingPhoto;
    @Column(name = "front_farbuilding_photo",  columnDefinition = "TEXT")
    private String frontFarbuildingPhoto;

    @Column(name = "province")
    private String province;

    @Column(name = "city")
    private String city;

    @Column(name = "address")
    private String address;

    @Column(name = "gmaps")
    @Type(type = "org.hibernate.type.TextType")
    private String gmaps;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "kost_type_man")
    private Boolean kostTypeMan;

    @Column(name = "kost_type_woman")
    private Boolean kostTypeWoman;

    @Column(name = "kost_type_mixed")
    private Boolean kostTypeMixed;

    @Column(name = "parking_motorcycle")
    private Boolean parkingMotorcycle;
    @Column(name = "kost_tv")
    private Boolean kostTv;

    @Column(name = "electric")
    private Boolean electric;

    @Column(name = "laundry")
    private Boolean laundry;

    @Column(name = "refrigerator")
    private Boolean refrigerator;

    @Column(name = "water")
    private Boolean water;

    @Column(name = "wifi")
    private Boolean wifi;

    @Column(name = "dispenser")
    private Boolean dispenser;

    @Column(name = "drying_ground")
    private Boolean drying_ground;

    @Column(name = "kitchen")
    private Boolean kitchen;

    @Column(name = "living_room")
    private Boolean livingRoom;

    @Column(name = "parking_car")
    private Boolean parkingCar;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @CreationTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Column(name = "year_since")
    private String yearSince;

    @Column(name = "rule_id")
    private Long ruleId;

}