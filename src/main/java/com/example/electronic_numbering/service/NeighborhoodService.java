package com.example.electronic_numbering.service;

import com.example.electronic_numbering.domain.dto.request.region.NeighborhoodEntityCreateDto;
import com.example.electronic_numbering.domain.dto.response.StandardResponse;
import com.example.electronic_numbering.domain.dto.response.Status;
import com.example.electronic_numbering.domain.entity.region.NeighborhoodEntity;
import com.example.electronic_numbering.repository.NeighborhoodRepository;
import com.example.electronic_numbering.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NeighborhoodService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    public StandardResponse<NeighborhoodEntity> addNeighborhood(NeighborhoodEntityCreateDto neighborhoodEntityCreateDto) {
        NeighborhoodEntity neighborhoodEntity = modelMapper.map(neighborhoodEntityCreateDto, NeighborhoodEntity.class);
        NeighborhoodEntity neighborhood = neighborhoodRepository.save(neighborhoodEntity);
        return StandardResponse.<NeighborhoodEntity> builder()
                .status(Status.SUCCESS)
                .message("Successfully signed up")
                .data(neighborhood
                ).build();
    }private final NeighborhoodRepository neighborhoodRepository;

}
