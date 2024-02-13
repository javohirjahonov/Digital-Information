package com.example.electronic_numbering.service;
import com.example.electronic_numbering.domain.dto.request.region.RegionCreateDto;
import com.example.electronic_numbering.domain.dto.request.region.RegionDistrictsForFront;
import com.example.electronic_numbering.domain.dto.response.StandardResponse;
import com.example.electronic_numbering.domain.dto.response.Status;
import com.example.electronic_numbering.domain.entity.region.DistrictEntity;
import com.example.electronic_numbering.domain.entity.region.RegionEntity;
import com.example.electronic_numbering.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.swing.plaf.synth.Region;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RegionService {
    private final UserService userService;
    private final RegionRepository regionRepository;
    private final ModelMapper modelMapper;

    public StandardResponse<RegionEntity> addRegion(RegionCreateDto regionCreateDto) {
        RegionEntity region = modelMapper.map(regionCreateDto, RegionEntity.class);
        RegionEntity regionEntity = regionRepository.save(region);
        return StandardResponse.<RegionEntity> builder()
                .status(Status.SUCCESS)
                .message("Region saved")
                .data(regionEntity
                ).build();
    }
    public StandardResponse<RegionDistrictsForFront> getRegionDistricts(Long regionId) {
        List<DistrictEntity> regionDistricts = regionRepository.getRegionDistrictsById(regionId);
        RegionDistrictsForFront regionDistrictsForFront = RegionDistrictsForFront.builder()
                .regionDistricts(regionDistricts).build();
        return StandardResponse.<RegionDistrictsForFront> builder()
                .status(Status.SUCCESS)
                .message("Region saved")
                .data(regionDistrictsForFront)
                .build();
    }

    public List<RegionEntity> getRegions() {
        List<RegionEntity> allRegions = regionRepository.findAll();
        return allRegions;
    }
}
