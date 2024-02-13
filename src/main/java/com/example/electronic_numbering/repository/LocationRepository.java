package com.example.electronic_numbering.repository;

import com.example.electronic_numbering.domain.entity.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LocationRepository extends JpaRepository<LocationEntity, UUID> {
}
