package com.example.electronic_numbering.domain.dto.response;

import org.springframework.http.HttpStatus;

public record ApiErrorResponse(
        String message,
        HttpStatus httpStatus,
        int code
) {
}
