package com.example.electronic_numbering.domain.dto.response;

import com.example.electronic_numbering.domain.dto.request.user.UserDetailsForFront;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class JwtResponse {
    private String accessToken;
    private String refreshToken;
    private UserDetailsForFront user;
}
