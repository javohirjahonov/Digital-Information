package com.example.electronic_numbering.domain.dto.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CitizenCreateDto {
    @NotBlank(message = "Fullname cannot be blank")
    private String fullName;
    @NotBlank(message = "Region cannot be blank")
    private String region;
    @NotBlank(message = "District cannot be blank")
    private String citizenDistrict;
    @NotBlank(message = "Neighboard cannot be blank")
    private String citizensNeighborhood;
    @NotBlank(message = "HomeAdsres cannot be blank")
    private String homeAddress;
    @NotBlank(message = "HomeCode cannot be blank")
    private String homeCode;
    @NotBlank(message = "homeNumber cannot be blank")
    private String homeNumber;
    @NotBlank(message = "poneNumber cannot be blank")
    private String phoneNumber;
    private boolean hasCadastre;
    @NotBlank(message = "FamilyNumber cannot be blank")
    private String numberOfFamilyMembers;
    @NotBlank(message = "theNumberOfHouseholdsInAForeignCountry cannot be blank")
    private String theNumberOfHouseholdsInAForeignCountry; // Chet davlatdagilar
    @NotBlank(message = "Home Location cannot be blank")
    private String homeLocation;

    public String getHasCadastreDisplay() {
        return hasCadastre ? "Yes" : "No";
    }
}
