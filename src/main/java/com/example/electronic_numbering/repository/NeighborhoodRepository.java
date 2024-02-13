package com.example.electronic_numbering.repository;

import com.example.electronic_numbering.domain.entity.region.NeighborhoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NeighborhoodRepository extends JpaRepository<NeighborhoodEntity, Long> {

}
