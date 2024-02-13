package com.example.electronic_numbering.repository;

import com.example.electronic_numbering.domain.entity.VerificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VerificationRepository extends JpaRepository<VerificationEntity, UUID> {
    @Query(value = "select v from verification v where v.userId.email = ?1")
    Optional<VerificationEntity> findByUserEmail(String email);

}
