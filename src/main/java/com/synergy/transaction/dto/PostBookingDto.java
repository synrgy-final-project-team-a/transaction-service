package com.synergy.transaction.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PostBookingDto {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String gender;

    @NotNull
    @NotBlank
    private String job;

    @NotNull
    @NotBlank
    private String phoneNumber;

    @NotNull
    @NotBlank
    private LocalDateTime checkIn;

    @NotNull
    @NotBlank
    private Long priceId;
}
