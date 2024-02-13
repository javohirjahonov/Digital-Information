package com.example.electronic_numbering.domain.dto.request.user;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserResponseForFront {
    private UUID id;
    private String name;
}
