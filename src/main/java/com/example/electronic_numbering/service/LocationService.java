package com.example.electronic_numbering.service;

import com.example.electronic_numbering.domain.entity.LocationEntity;
import com.example.electronic_numbering.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    public void saveLocation(LocationEntity location) {
        locationRepository.save(location);
    }

    public Optional<LocationEntity> getLocationById(UUID id) {
        return locationRepository.findById(id);
    }
}
