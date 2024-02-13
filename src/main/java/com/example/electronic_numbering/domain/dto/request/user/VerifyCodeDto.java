package com.example.electronic_numbering.domain.dto.request.user;

import lombok.Data;

@Data
public class VerifyCodeDto {
    private String email;
    private String code;
}
