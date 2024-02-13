package com.example.electronic_numbering.repository;

import com.example.electronic_numbering.domain.entity.region.DistrictEntity;
import com.example.electronic_numbering.domain.entity.region.NeighborhoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface DistrictRepository extends JpaRepository<DistrictEntity, Long> {
    @Query(value = "SELECT n FROM neighborhoods n WHERE n.district.id = :districtId")
    List<NeighborhoodEntity> getDistrictNeighborhoods(Long districtId);
}
