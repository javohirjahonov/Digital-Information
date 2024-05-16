package com.example.electronic_numbering.config;

import com.example.electronic_numbering.filter.JwtFilterToken;
import com.example.electronic_numbering.service.AuthenticationService;
import com.example.electronic_numbering.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig implements WebMvcConfigurer {
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final String[] permitAll = {"/swagger-ui/**", "/v3/api-docs/**", "/user/auth/**", "/api/location/**", "/region/**"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors.disable()) // Disable CORS completely
                .csrf().disable()
                .authorizeHttpRequests(authorize -> {
                    authorize
                            .requestMatchers(permitAll).permitAll() // Allow predefined paths
                            .requestMatchers(HttpMethod.OPTIONS).permitAll() // Allow OPTIONS preflight
                            .anyRequest().authenticated(); // Require authentication for all other requests
                })
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(new JwtFilterToken(authenticationService, jwtService), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Implement CORS policy as needed
        registry.addMapping("/**")
                .allowedOriginPatterns("*") // Adjust the pattern to match your domain(s)
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .exposedHeaders(HttpHeaders.AUTHORIZATION)
                .allowCredentials(true)
                .maxAge(3600);
    }
}
