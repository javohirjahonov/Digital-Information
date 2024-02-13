package com.example.electronic_numbering.service;

import com.example.electronic_numbering.domain.dto.request.region.DistrictCreateDto;
import com.example.electronic_numbering.domain.dto.request.region.DistrictNeighborhoodsForFront;
import com.example.electronic_numbering.domain.dto.response.StandardResponse;
import com.example.electronic_numbering.domain.dto.response.Status;
import com.example.electronic_numbering.domain.entity.region.DistrictEntity;
import com.example.electronic_numbering.domain.entity.region.NeighborhoodEntity;
import com.example.electronic_numbering.domain.entity.region.RegionEntity;
import com.example.electronic_numbering.exception.DataNotFoundException;
import com.example.electronic_numbering.repository.DistrictRepository;
import com.example.electronic_numbering.repository.RegionRepository;
import com.example.electronic_numbering.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DistrictService {
    private final DistrictRepository districtRepository;
    private final RegionRepository regionRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    public StandardResponse<DistrictEntity> addDistrict(DistrictCreateDto districtCreateDto) {
        DistrictEntity districtEntity = modelMapper.map(districtCreateDto, DistrictEntity.class);
        RegionEntity region = regionRepository.findById(districtCreateDto.getRegionId()).orElseThrow(() ->
                new DataNotFoundException("Region not found"));
        DistrictEntity district = districtRepository.save(districtEntity);
        return StandardResponse.<DistrictEntity> builder()
                .status(Status.SUCCESS)
                .message("Successfully signed up")
                .data(district
                ).build();
    }

    public StandardResponse<DistrictNeighborhoodsForFront> getDistrictNeighborhoods(Long districtId) {
        List<NeighborhoodEntity> districtNeighborhoods = districtRepository.getDistrictNeighborhoods(districtId);
        DistrictNeighborhoodsForFront districtNeighborhoodsForFront = DistrictNeighborhoodsForFront.builder()
                .districtNeighborhoods(districtNeighborhoods).build();
        return StandardResponse.<DistrictNeighborhoodsForFront> builder()
                .status(Status.SUCCESS)
                .message("Successfully signed up")
                .data(districtNeighborhoodsForFront
                ).build();
    }

}
