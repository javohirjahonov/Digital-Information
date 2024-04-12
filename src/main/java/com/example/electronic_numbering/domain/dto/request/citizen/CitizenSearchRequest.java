package com.example.electronic_numbering.domain.dto.request.citizen;

import com.example.electronic_numbering.domain.entity.region.DistrictEntity;
import com.example.electronic_numbering.domain.entity.region.NeighborhoodEntity;
import com.example.electronic_numbering.domain.entity.region.RegionEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CitizenSearchRequest {
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
    private String theNumberOfHouseholdsInAForeignCountry; // the number of households in a foreign country
    private String homeLocation;
}
