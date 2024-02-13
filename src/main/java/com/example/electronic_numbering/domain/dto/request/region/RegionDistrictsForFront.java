package com.example.electronic_numbering.domain.dto.request.region;

import com.example.electronic_numbering.domain.entity.region.DistrictEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class RegionDistrictsForFront {
    private List<DistrictEntity> regionDistricts;
}
