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
public class CitizenCreateDto {
    private String fullName;
    private RegionEntity region;
    private DistrictEntity citizenDistrict;
    private NeighborhoodEntity citizensNeighborhood;
    private String homeAddress;
    private String homeCode;
    private String homeNumber;
    private String phoneNumber;
    private boolean hasCadastre;
    private String numberOfFamilyMembers;
    private String theNumberOfHouseholdsInAForeignCountry; // Chet davlatdagilar
    private String homeLocation;
}
