package com.example.electronic_numbering.service.citizen;

import com.example.electronic_numbering.domain.dto.request.user.*;
import com.example.electronic_numbering.domain.dto.response.StandardResponse;
import com.example.electronic_numbering.domain.dto.response.Status;
import com.example.electronic_numbering.domain.entity.citizen.CitizenEntity;
import com.example.electronic_numbering.exception.DataNotFoundException;
import com.example.electronic_numbering.exception.UserBadRequestException;
import com.example.electronic_numbering.repository.CitizenRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CitizenService {
    private final CitizenRepository citizenRepository;
    private final ModelMapper modelMapper;
    private final EntityManager entityManager;

    @Transactional
    public StandardResponse<CitizenEntity> addCitizen(CitizenCreateDto citizenCreateDto) {
        CitizenEntity citizenEntity = modelMapper.map(citizenCreateDto, CitizenEntity.class);
        CitizenEntity citizen = citizenRepository.save(citizenEntity);
        return StandardResponse.<CitizenEntity>builder()
                .status(Status.SUCCESS)
                .message("Citizen successfully added")
                .data(citizen)
                .build();
    }

    @Transactional
    public StandardResponse<CitizenDetailsForFront> updateCitizenInformation(CitizenUpdateRequest update, UUID homeId) {
        CitizenEntity citizen = citizenRepository.findById(homeId)
                .orElseThrow(() -> new UserBadRequestException("Citizen not found"));

        // Update only the fields provided in the update request
        modelMapper.map(update, citizen);

        citizen.setUpdatedDate(LocalDateTime.now());
        citizenRepository.save(citizen);

        return StandardResponse.<CitizenDetailsForFront>builder()
                .status(Status.SUCCESS)
                .message("Citizen updated successfully")
                .data(mappingCitizen(citizen))
                .build();
    }

    private CitizenDetailsForFront mappingCitizen(CitizenEntity citizenEntity) {
        return modelMapper.map(citizenEntity, CitizenDetailsForFront.class);
    }

    @Transactional(readOnly = true)
    public StandardResponse<CitizenEntity> getCitizenInformation(UUID citizenId) {
        CitizenEntity citizen = citizenRepository.findById(citizenId)
                .orElseThrow(() -> new DataNotFoundException("Citizen not found"));
        return StandardResponse.<CitizenEntity>builder()
                .status(Status.SUCCESS)
                .message("Citizen information")
                .data(citizen)
                .build();
    }

    @Transactional(readOnly = true)
    public StandardResponse<List<CitizenEntity>> getAllCitizenInformation() {
        List<CitizenEntity> citizenEntities = citizenRepository.findAll();
        return StandardResponse.<List<CitizenEntity>>builder()
                .status(Status.SUCCESS)
                .message("Citizen information")
                .data(citizenEntities)
                .build();
    }

    @Transactional
    public void cancelCitizen(UUID citizenId) {
        CitizenEntity citizen = citizenRepository.findById(citizenId)
                .orElseThrow(() -> new DataNotFoundException("Citizen not found"));
        citizen.setUpdatedDate(LocalDateTime.now());
        citizenRepository.save(citizen);
    }

    @Transactional
    public void deleteCitizen(UUID citizenId) {
        CitizenEntity citizen = citizenRepository.findById(citizenId)
                .orElseThrow(() -> new DataNotFoundException("Citizen not found"));
        citizenRepository.delete(citizen);
    }

    public Resource writeInformationToPdf(UUID citizenId) throws DocumentException, IOException {
        CitizenEntity citizenEntity = citizenRepository.findById(citizenId)
                .orElseThrow(() -> new DataNotFoundException("Citizen not found"));

        Document document = new Document();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, outputStream);
        document.open();

        String hasCadastreDisplay = citizenEntity.isHasCadastre() ? "Yes" : "No";
        // Font settings
        Font boldFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
        Font normalFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

        // Create a table with 2 columns
        PdfPTable table = new PdfPTable(2);
        table.setWidthPercentage(100);
        table.setSpacingBefore(10f);
        table.setSpacingAfter(10f);

        // Define column widths
        float[] columnWidths = {1f, 2f};
        table.setWidths(columnWidths);

        // Add table headers
        table.addCell(new PdfPCell(new Phrase("Attribute", boldFont)));
        table.addCell(new PdfPCell(new Phrase("Value", boldFont)));

        // Add rows
        addTableEntry(table, "Familiya Ism:", citizenEntity.getFullName(), normalFont);
        addTableEntry(table, "Viloyat:", citizenEntity.getRegion(), normalFont);
        addTableEntry(table, "Tuman:", citizenEntity.getCitizenDistrict(), normalFont);
        addTableEntry(table, "Mahalla:", citizenEntity.getCitizensNeighborhood(), normalFont);
        addTableEntry(table, "Yashash joyi:", citizenEntity.getHomeLocation(), normalFont);
        addTableEntry(table, "Xonadon kodi:", citizenEntity.getHomeCode(), normalFont);
        addTableEntry(table, "Telefon raqami:", citizenEntity.getPhoneNumber(), normalFont);
        addTableEntry(table, "Uy raqami:", citizenEntity.getHomeNumber(), normalFont);
        addTableEntry(table, "Kadastr:", hasCadastreDisplay, normalFont);
        addTableEntry(table, "Chet davlatda:", citizenEntity.getTheNumberOfHouseholdsInAForeignCountry(), normalFont);
        addTableEntry(table, "Uy manzili:", citizenEntity.getHomeAddress(), normalFont);

        // Add table to document
        document.add(table);

        document.close();
        return new ByteArrayResource(outputStream.toByteArray());
    }

    private void addTableEntry(PdfPTable table, String header, String content, Font font) {
        PdfPCell headerCell = new PdfPCell(new Phrase(header, font));
        headerCell.setBorderColor(BaseColor.LIGHT_GRAY);
        headerCell.setPadding(5);
        table.addCell(headerCell);

        PdfPCell contentCell = new PdfPCell(new Phrase(content, font));
        contentCell.setBorderColor(BaseColor.LIGHT_GRAY);
        contentCell.setPadding(5);
        table.addCell(contentCell);
    }

    @Transactional(readOnly = true)
    public List<CitizenEntity> searchCitizen(CitizenSearchRequest searchRequest) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<CitizenEntity> criteriaQuery = criteriaBuilder.createQuery(CitizenEntity.class);
        Root<CitizenEntity> root = criteriaQuery.from(CitizenEntity.class);

        List<Predicate> predicates = new ArrayList<>();

        if (searchRequest.getFullName() != null && !searchRequest.getFullName().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("fullName")), "%" + searchRequest.getFullName().toLowerCase() + "%"));
        }
        if (searchRequest.getRegion() != null) {
            predicates.add(criteriaBuilder.equal(root.get("region"), searchRequest.getRegion()));
        }
        if (searchRequest.getDistrict() != null) {
            predicates.add(criteriaBuilder.equal(root.get("citizenDistrict"), searchRequest.getDistrict()));
        }
        if (searchRequest.getNeighborhood() != null) {
            predicates.add(criteriaBuilder.equal(root.get("citizensNeighborhood"), searchRequest.getNeighborhood()));
        }
        if (searchRequest.getHomeAddress() != null && !searchRequest.getHomeAddress().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("homeAddress")), "%" + searchRequest.getHomeAddress().toLowerCase() + "%"));
        }
        if (searchRequest.getHomeCode() != null && !searchRequest.getHomeCode().isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("homeCode"), searchRequest.getHomeCode()));
        }
        if (searchRequest.getHomeNumber() != null && !searchRequest.getHomeNumber().isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("homeNumber"), searchRequest.getHomeNumber()));
        }
        if (searchRequest.getPhoneNumber() != null && !searchRequest.getPhoneNumber().isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("phoneNumber"), searchRequest.getPhoneNumber()));
        }
        if (searchRequest.isHasCadastre()) {
            predicates.add(criteriaBuilder.isTrue(root.get("hasCadastre")));
        }
        if (searchRequest.getNumberOfFamilyMembers() != null && !searchRequest.getNumberOfFamilyMembers().isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("numberOfFamilyMembers"), searchRequest.getNumberOfFamilyMembers()));
        }
        if (searchRequest.getNumberOfHouseholdsInForeignCountry() != null && !searchRequest.getNumberOfHouseholdsInForeignCountry().isEmpty()) {
            predicates.add(criteriaBuilder.equal(root.get("theNumberOfHouseholdsInAForeignCountry"), searchRequest.getNumberOfHouseholdsInForeignCountry()));
        }
        if (searchRequest.getHomeLocation() != null && !searchRequest.getHomeLocation().isEmpty()) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("homeLocation")), "%" + searchRequest.getHomeLocation().toLowerCase() + "%"));
        }

        criteriaQuery.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }

    @Async
    public void sendVerificationCodeAsync(CitizenEntity citizenEntity) {
        // Implement asynchronous email sending logic here
    }
}
