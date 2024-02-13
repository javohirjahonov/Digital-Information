package com.example.electronic_numbering.controller;

import com.example.electronic_numbering.domain.dto.request.region.*;
import com.example.electronic_numbering.domain.dto.response.StandardResponse;
import com.example.electronic_numbering.domain.entity.region.DistrictEntity;
import com.example.electronic_numbering.domain.entity.region.NeighborhoodEntity;
import com.example.electronic_numbering.domain.entity.region.RegionEntity;
import com.example.electronic_numbering.exception.RequestValidationException;
import com.example.electronic_numbering.service.DistrictService;
import com.example.electronic_numbering.service.NeighborhoodService;
import com.example.electronic_numbering.service.RegionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController("/region")
@RequiredArgsConstructor
public class RegionController {
    private final RegionService regionService;
    private final DistrictService districtService;
    private final NeighborhoodService neighborhoodService;

    @PostMapping("/addRegion")
    public StandardResponse<RegionEntity> addRegion(
            @Valid @RequestBody RegionCreateDto regionCreateDto,
            BindingResult bindingResult
            ) throws RequestValidationException {
        if (bindingResult.hasErrors()){
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            throw new RequestValidationException(allErrors);
        }
        return regionService.addRegion(regionCreateDto);
    }

    @GetMapping("/getAllRegions")
    public List<RegionEntity> getAllRegions() {
        return regionService.getRegions();
    }
    @GetMapping("/getRegionDistricts")
    public StandardResponse<RegionDistrictsForFront> getDistrictsByRegionId(
            @RequestParam Long regionId
    ) {
        return regionService.getRegionDistricts(regionId);
    }

    @PostMapping("/addDistrict")
    public StandardResponse<DistrictEntity> addDistrict(
            @Valid @RequestBody DistrictCreateDto districtCreateDto,
            BindingResult bindingResult
            ) throws RequestValidationException {
        if (bindingResult.hasErrors()){
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            throw new RequestValidationException(allErrors);
        }
        return districtService.addDistrict(districtCreateDto);
    }

    @GetMapping("/getDistrictsNeighborhoods")
    public StandardResponse<DistrictNeighborhoodsForFront> getDistrictsNeighborhoods(
            @RequestParam Long districtId
    ) {
        return districtService.getDistrictNeighborhoods(districtId);
    }

    @PostMapping("/addNeighbor")
    public StandardResponse<NeighborhoodEntity> addNeighbor (
            @Valid @RequestBody NeighborhoodEntityCreateDto neighborhoodEntityCreateDto,
            BindingResult bindingResult
    ) throws RequestValidationException {
        if (bindingResult.hasErrors()){
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            throw new RequestValidationException(allErrors);
        }
        return neighborhoodService.addNeighborhood(neighborhoodEntityCreateDto);
    }
}
