package com.example.electronic_numbering.domain.dto.request.user;

import com.example.electronic_numbering.domain.entity.region.DistrictEntity;
import com.example.electronic_numbering.domain.entity.region.NeighborhoodEntity;
import com.example.electronic_numbering.domain.entity.region.RegionEntity;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CitizenDetailsForFront {
    private String fullName;
    private String region;
    private String citizenDistrict;
    private String citizensNeighborhood;
    private String homeCode;
    private boolean hasCadastre;
    private String phoneNumber;
    private String theNumberOfHouseholdsInAForeignCountry; // the number of households in a foreign country
    private String homeAddress;
    private String homeNumber;
    private String homeLocation;
}
