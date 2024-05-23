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
public class CitizenSearchRequest {
    private String fullName;
    private RegionEntity region;
    private DistrictEntity district;
    private NeighborhoodEntity neighborhood;
    private String homeAddress;
    private String homeCode;
    private String homeNumber;
    private String phoneNumber;
    private boolean hasCadastre;
    private String numberOfFamilyMembers;
    private String numberOfHouseholdsInForeignCountry;
    private String homeLocation;
}
