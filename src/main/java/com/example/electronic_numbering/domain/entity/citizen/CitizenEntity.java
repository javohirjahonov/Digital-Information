package com.example.electronic_numbering.domain.entity.citizen;

import com.example.electronic_numbering.domain.entity.BaseEntity;
import com.example.electronic_numbering.domain.entity.region.DistrictEntity;
import com.example.electronic_numbering.domain.entity.region.NeighborhoodEntity;
import com.example.electronic_numbering.domain.entity.region.RegionEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity(name = "citizen")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CitizenEntity extends BaseEntity {
    @Column(nullable = false)
    private String fullName;
    @Column(nullable = false)
    private String region;
    @Column(nullable = false)
    private String citizenDistrict;
    @Column(nullable = false)
    private String citizensNeighborhood;
    @Column(nullable = false)
    private String homeAddress;
    @Column(nullable = false)
    private String homeCode;
    @Column(nullable = false)
    private String homeNumber;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private boolean hasCadastre;
    @Column(nullable = false)
    private String numberOfFamilyMembers;
    @Column(nullable = false)
    private String theNumberOfHouseholdsInAForeignCountry; // the number of households in a foreign country
    @Column(nullable = false)
    private String homeLocation;
}
