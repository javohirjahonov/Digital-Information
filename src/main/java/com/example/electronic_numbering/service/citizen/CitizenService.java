package com.example.electronic_numbering.service.citizen;

import com.example.electronic_numbering.domain.dto.request.user.*;
import com.example.electronic_numbering.domain.dto.response.StandardResponse;
import com.example.electronic_numbering.domain.dto.response.Status;
import com.example.electronic_numbering.domain.entity.citizen.CitizenEntity;
import com.example.electronic_numbering.domain.entity.region.DistrictEntity;
import com.example.electronic_numbering.domain.entity.region.NeighborhoodEntity;
import com.example.electronic_numbering.domain.entity.region.RegionEntity;
import com.example.electronic_numbering.exception.DataNotFoundException;
import com.example.electronic_numbering.exception.UserBadRequestException;
import com.example.electronic_numbering.repository.CitizenRepository;
import com.example.electronic_numbering.repository.DistrictRepository;
import com.example.electronic_numbering.repository.NeighborhoodRepository;
import com.example.electronic_numbering.repository.RegionRepository;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CitizenService {
    private final CitizenRepository citizenRepository;
    private final RegionRepository regionRepository;
    private final DistrictRepository districtRepository;
    private final NeighborhoodRepository neighborhoodRepository;
    private final ModelMapper modelMapper;
    public StandardResponse<CitizenEntity> addCitizen(CitizenCreateDto citizenCreateDto) {
        CitizenEntity citizenEntity = modelMapper.map(citizenCreateDto, CitizenEntity.class);
        CitizenEntity citizen = citizenRepository.save(citizenEntity);
        return StandardResponse.<CitizenEntity> builder()
                .status(Status.SUCCESS)
                .message("Citizen successfully added")
                .data(citizen
                ).build();
    }


    public StandardResponse<CitizenDetailsForFront> updateCitizenInformation(CitizenUpdateRequest update, UUID homeId) {
        CitizenEntity citizen = citizenRepository.findById(homeId)
                .orElseThrow(() -> new UserBadRequestException("User not found"));

        // Update only the fields provided in the update request
        if (update.getFullName() != null) {
            citizen.setFullName(update.getFullName());
        }
        if (update.getRegion() != null) {
            citizen.setRegion(update.getRegion());
        }
        if (update.getCitizenDistrict() != null) {
            citizen.setCitizenDistrict(update.getCitizenDistrict());
        }
        if (update.getCitizensNeighborhood() != null) {
            citizen.setCitizensNeighborhood(update.getCitizensNeighborhood());
        }
        if (update.getHomeCode() != null) {
            citizen.setHomeCode(update.getHomeCode());
        }
        if (update.isHasCadastre()) { // Assuming 'isHasCadastre' is a boolean field
            citizen.setHasCadastre(true);
        }
        if (update.getPhoneNumber() != null) {
            citizen.setPhoneNumber(update.getPhoneNumber());
        }
        if (update.getTheNumberOfHouseholdsInAForeignCountry() != null) {
            citizen.setTheNumberOfHouseholdsInAForeignCountry(update.getTheNumberOfHouseholdsInAForeignCountry());
        }
        if (update.getHomeAddress() != null) {
            citizen.setHomeAddress(update.getHomeAddress());
        }
        if (update.getHomeNumber() != null) {
            citizen.setHomeNumber(update.getHomeNumber());
        }
        if (update.getHomeLocation() != null) {
            citizen.setHomeLocation(update.getHomeLocation());
        }

        // Set the updated date
        citizen.setUpdatedDate(LocalDateTime.now());

        // Save the updated citizen entity
        citizenRepository.save(citizen);

        // Return the updated user details
        return StandardResponse.<CitizenDetailsForFront>builder()
                .status(Status.SUCCESS)
                .message("User updated successfully")
                .data(mappingCitizen(citizen))
                .build();
    }

    public CitizenDetailsForFront mappingCitizen(CitizenEntity citizenEntity){
        // Mapping logic goes here
        return modelMapper.map(citizenEntity, CitizenDetailsForFront.class);
    }

    public StandardResponse<CitizenEntity> getCitizenInformation(UUID citizenId) {
        CitizenEntity citizen = citizenRepository.findById(citizenId).orElseThrow(() ->
                new DataNotFoundException("Citizen not found"));
        return StandardResponse.<CitizenEntity>builder()
                .status(Status.SUCCESS)
                .message("Citizen information")
                .data(citizen)
                .build();

    }

    public StandardResponse<List<CitizenEntity>> getAllCitizenInformation() {
        List<CitizenEntity> citizenEntities = citizenRepository.findAll();
        return StandardResponse.<List<CitizenEntity>>builder()
                .status(Status.SUCCESS)
                .message("Citizen information")
                .data(citizenEntities)
                .build();

    }

    public void cancelCitizen(UUID citizenId) {
        CitizenEntity citizen = citizenRepository.findById(citizenId).orElseThrow(() -> new DataNotFoundException("Citizen not found"));
        citizen.setUpdatedDate(LocalDateTime.now());
        citizenRepository.save(citizen);
        StandardResponse.<String>builder()
                .status(Status.SUCCESS)
                .message("Successfully canceled")
                .build();
    }

    public void deletedCitizen(UUID citizenId) {
        CitizenEntity citizen = citizenRepository.findById(citizenId).orElseThrow(() -> new DataNotFoundException("Citizen not found"));
        citizenRepository.delete(citizen);
        StandardResponse.<String>builder()
                .status(Status.SUCCESS)
                .message("Successfully deleted")
                .build();
    }

    public CitizenInformationForPdf writeInformationToPdf(UUID citizenId) throws DocumentException, FileNotFoundException {
        CitizenEntity citizenEntity = citizenRepository.findById(citizenId)
                .orElseThrow(() -> new DataNotFoundException("Citizen Not Found"));
        RegionEntity region = regionRepository.findById(citizenEntity.getRegion().getId())
                .orElseThrow(() -> new DataNotFoundException("Region not found"));
        DistrictEntity district = districtRepository.findById(citizenEntity.getCitizenDistrict().getId())
                .orElseThrow(() -> new DataNotFoundException("District not found"));
        NeighborhoodEntity neighborhood = neighborhoodRepository.findById(citizenEntity.getCitizensNeighborhood().getId())
                .orElseThrow(() -> new DataNotFoundException("Neighborhood not found "));

        Document document = new Document();
        String filePath = "Citizen_Information.pdf"; // Specify the file path
        PdfWriter.getInstance(document, new FileOutputStream(filePath));
        document.open();

        // Add citizen information to the document as paragraphs
        document.add(new Paragraph("Full Name: " + citizenEntity.getFullName()));
        document.add(new Paragraph("Region: " + region.getNameUz()));
        document.add(new Paragraph("District: " + district.getNameUz()));
        document.add(new Paragraph("Neighborhood: " + neighborhood.getName_oz()));
        document.add(new Paragraph("Home Code: " + citizenEntity.getHomeCode()));
        document.add(new Paragraph("Has Cadastre: " + citizenEntity.isHasCadastre()));
        document.add(new Paragraph("Phone Number: " + citizenEntity.getPhoneNumber()));
        document.add(new Paragraph("Households in a Foreign Country: " + citizenEntity.getTheNumberOfHouseholdsInAForeignCountry()));
        document.add(new Paragraph("Home Address: " + citizenEntity.getHomeAddress()));
        document.add(new Paragraph("Home Number: " + citizenEntity.getHomeNumber()));
        document.add(new Paragraph("Home Location: " + citizenEntity.getHomeLocation()));

        document.close();
        return new CitizenInformationForPdf(); // Return an empty object or any other necessary response
    }


}
