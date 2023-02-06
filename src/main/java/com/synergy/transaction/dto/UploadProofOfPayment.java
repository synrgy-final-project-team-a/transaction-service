package com.synergy.transaction.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class UploadProofOfPayment {
    @NotNull
    private MultipartFile file;
}
