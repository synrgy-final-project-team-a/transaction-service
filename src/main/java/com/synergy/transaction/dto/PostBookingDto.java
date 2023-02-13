package com.synergy.transaction.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PostBookingDto {

    @NotNull(message = "name shouldn't null")
    @NotBlank
    private String name;

    @NotNull(message = "gender shouldn't null")
    @NotBlank
    private String gender;

    @NotNull
    @NotBlank
    private String job;

    @NotNull
    @NotBlank
    private String phone_number;

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime check_in;

}
