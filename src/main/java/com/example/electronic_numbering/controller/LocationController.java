package com.example.electronic_numbering.controller;

import com.example.electronic_numbering.domain.entity.LocationEntity;
import com.example.electronic_numbering.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@Controller
@RestController("/api/location")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000") // Adjust with the actual origin of your React app
public class LocationController {
    private final LocationService locationService;

    @PostMapping("/save")
    public ResponseEntity<String> saveLocation(@RequestBody LocationEntity location) {
        locationService.saveLocation(location);
        return ResponseEntity.ok("Location saved successfully!");
    }

    @GetMapping("/{id}")
    public ResponseEntity<LocationEntity> getLocation(@PathVariable UUID id) {
        Optional<LocationEntity> optionalLocation = locationService.getLocationById(id);
        return optionalLocation.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
