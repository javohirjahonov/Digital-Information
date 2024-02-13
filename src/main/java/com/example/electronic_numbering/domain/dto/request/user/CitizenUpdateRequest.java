package com.example.electronic_numbering.domain.dto.request.user;

import com.example.electronic_numbering.domain.entity.region.DistrictEntity;
import com.example.electronic_numbering.domain.entity.region.NeighborhoodEntity;
import com.example.electronic_numbering.domain.entity.region.RegionEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CitizenUpdateRequest {
    private String fullName;
    private RegionEntity region;
    private DistrictEntity citizenDistrict;
    private NeighborhoodEntity citizensNeighborhood;
    private String homeCode;
    private boolean hasCadastre;
    private String phoneNumber;
    private String theNumberOfHouseholdsInAForeignCountry; // the number of households in a foreign country
    private String homeAddress;
    private String homeNumber;
    private String homeLocation;
}
