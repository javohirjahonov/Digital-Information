package com.example.electronic_numbering.config;

import com.example.electronic_numbering.domain.entity.user.UserEntity;
import com.example.electronic_numbering.exception.DataNotFoundException;
import com.example.electronic_numbering.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository authUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        UserEntity authUser = authUserRepository.findByEmail(username)
                .orElseThrow(() -> new DataNotFoundException("User not found with email: " + username));

        return authUser;
    }
}
