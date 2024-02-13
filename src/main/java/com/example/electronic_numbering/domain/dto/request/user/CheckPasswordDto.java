package com.example.electronic_numbering.domain.dto.request.user;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CheckPasswordDto {
    private String password;
}
