package com.example.electronic_numbering.domain.dto.request.role;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class HospitalAssignDto {
    @NotBlank(message = "Email must not be blank")
    private String email;
    @NotBlank(message = "Hospital id must not be blank")
    private UUID hospitalId;

}
