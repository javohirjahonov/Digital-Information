package com.example.electronic_numbering.domain.dto.request.region;

import com.example.electronic_numbering.domain.entity.region.NeighborhoodEntity;
import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class DistrictNeighborhoodsForFront {
    private List<NeighborhoodEntity> districtNeighborhoods;
}
