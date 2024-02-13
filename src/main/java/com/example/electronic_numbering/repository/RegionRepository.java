package com.example.electronic_numbering.repository;

import com.example.electronic_numbering.domain.entity.region.DistrictEntity;
import com.example.electronic_numbering.domain.entity.region.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RegionRepository extends JpaRepository<RegionEntity, Long> {
    @Query(value = "SELECT d FROM DistrictEntity d WHERE d.region.id = :regionId")
    List<DistrictEntity> getRegionDistrictsById(Long regionId);

}
