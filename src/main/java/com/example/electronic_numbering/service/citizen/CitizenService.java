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
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private final EntityManager entityManager;
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

        citizen.setUpdatedDate(LocalDateTime.now());

        citizenRepository.save(citizen);

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

    public Resource writeInformationToPdf(UUID citizenId) throws DocumentException, IOException {
        CitizenEntity citizenEntity = citizenRepository.findById(citizenId)
                .orElseThrow(() -> new DataNotFoundException("Citizen Not Found"));
        RegionEntity region = regionRepository.findById(citizenEntity.getRegion().getId())
                .orElseThrow(() -> new DataNotFoundException("Region not found"));
        DistrictEntity district = districtRepository.findById(citizenEntity.getCitizenDistrict().getId())
                .orElseThrow(() -> new DataNotFoundException("District not found"));
        NeighborhoodEntity neighborhood = neighborhoodRepository.findById(citizenEntity.getCitizensNeighborhood().getId())
                .orElseThrow(() -> new DataNotFoundException("Neighborhood not found "));

        // Create PDF document
        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // Add citizen information to the document as paragraphs
        document.add(new Paragraph("Ism Familiya: " + citizenEntity.getFullName()));
        document.add(new Paragraph("Viloyat: " + region.getNameUz()));
        document.add(new Paragraph("Tuman: " + district.getNameUz()));
        document.add(new Paragraph("Mahalla: " + neighborhood.getName_oz()));
        document.add(new Paragraph("Honadon kodi: " + citizenEntity.getHomeCode()));
        document.add(new Paragraph("Kadastr bormi : " + citizenEntity.isHasCadastre()));
        document.add(new Paragraph("Telefon raqami: " + citizenEntity.getPhoneNumber()));
        document.add(new Paragraph("Chet elda mavjud honadon a'zolari soni: " + citizenEntity.getTheNumberOfHouseholdsInAForeignCountry()));
        document.add(new Paragraph("Honadon manzili: " + citizenEntity.getHomeAddress()));
        document.add(new Paragraph("Honadon raqami: " + citizenEntity.getHomeNumber()));
        document.add(new Paragraph("Honadon manzili: " + citizenEntity.getHomeLocation()));

        // Close document
        document.close();

        // Create ByteArrayResource from byte array
        ByteArrayResource resource = new ByteArrayResource(outputStream.toByteArray());

        return resource;
    }

    public List<CitizenEntity> searchCitizen(String fullName, RegionEntity region, DistrictEntity district, NeighborhoodEntity neighborhood, String homeAddress, String homeCode, String homeNumber, String phoneNumber, boolean hasCadastre, String numberOfFamilyMembers, String numberOfHouseholdsInForeignCountry, String homeLocation) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CitizenEntity> criteriaQuery = criteriaBuilder.createQuery(CitizenEntity.class);
        Root<CitizenEntity> root = criteriaQuery.from(CitizenEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        if (fullName != null && !fullName.isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), "%" + fullName.toLowerCase() + "%"));
        }
        if (region != null) {
            predicates.add(criteriaBuilder.equal(root.get("region"), region));
        }
        if (district != null) {
            predicates.add(criteriaBuilder.equal(root.get("citizenDistrict"), district));
        }
        if (neighborhood != null) {
            predicates.add(criteriaBuilder.equal(root.get("citizensNeighborhood"), neighborhood));
        }
        if (homeAddress != null && !homeAddress.isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("homeAddress")), "%" + homeAddress.toLowerCase() + "%"));
        }
        if (homeCode != null && !homeCode.isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("homeCode"), homeCode));
        }
        if (homeNumber != null && !homeNumber.isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("homeNumber"), homeNumber));
        }
        if (phoneNumber != null && !phoneNumber.isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("phoneNumber"), phoneNumber));
        }
        if (hasCadastre) {
            predicates.add(criteriaBuilder.isTrue(root.get("hasCadastre")));
        }
        if (numberOfFamilyMembers != null && !numberOfFamilyMembers.isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("numberOfFamilyMembers"), numberOfFamilyMembers));
        }
        if (numberOfHouseholdsInForeignCountry != null && !numberOfHouseholdsInForeignCountry.isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("theNumberOfHouseholdsInAForeignCountry"), numberOfHouseholdsInForeignCountry));
        }
        if (homeLocation != null && !homeLocation.isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("homeLocation")), "%" + homeLocation.toLowerCase() + "%"));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
