package com.example.electronic_numbering.filter;

import com.example.electronic_numbering.service.AuthenticationService;
import com.example.electronic_numbering.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
@Component
public class JwtFilterToken extends OncePerRequestFilter {
    private AuthenticationService authenticationService;
    private JwtService jwtService;
    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String token = request.getHeader("authorization");

        if(token == null || !token.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        token = token.substring(7);

        Jws<Claims> claimsJws = jwtService.extractToken(token);

        authenticationService.authenticate(claimsJws.getBody(), request);
        filterChain.doFilter(request, response);
    }
}
