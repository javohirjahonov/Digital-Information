package com.example.electronic_numbering.controller;

import com.example.electronic_numbering.domain.dto.request.user.*;
import com.example.electronic_numbering.domain.dto.response.JwtResponse;
import com.example.electronic_numbering.domain.dto.response.StandardResponse;
import com.example.electronic_numbering.domain.dto.response.Status;
import com.example.electronic_numbering.domain.entity.citizen.CitizenEntity;
import com.example.electronic_numbering.exception.DataNotFoundException;
import com.example.electronic_numbering.exception.RequestValidationException;
import com.example.electronic_numbering.service.citizen.CitizenService;
import com.itextpdf.text.DocumentException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/citizen")
@RequiredArgsConstructor
public class CitizenController {
    private final CitizenService citizenService;

    @PostMapping("/addCitizen")
    @PreAuthorize(value = "hasRole('USER')")
    public StandardResponse<CitizenEntity> addCitizen(
            @Valid @RequestBody CitizenCreateDto citizenCreateDto,
            BindingResult bindingResult
    ) throws RequestValidationException {
        if (bindingResult.hasErrors()){
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            throw new RequestValidationException(allErrors);
        }
        return citizenService.addCitizen(citizenCreateDto);
    }

    @PutMapping("/update-citizen-information")
    public StandardResponse<CitizenDetailsForFront> updateCitizenInformation(
            @Valid @RequestBody CitizenUpdateRequest update,
            @RequestParam UUID homeId,
            BindingResult bindingResult
    )throws RequestValidationException {
        if (bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            throw new RequestValidationException(allErrors);
        }
        return citizenService.updateCitizenInformation(update, homeId);
    }

    @GetMapping("/cancel-citizen")
    public StandardResponse<String> cancelCitizen(
            @RequestParam UUID citizenId
    ) {
        citizenService.cancelCitizen(citizenId);
        return StandardResponse.<String>builder()
                .status(Status.SUCCESS)
                .message("Cancel citizen")
                .build();
    }

    @DeleteMapping("/delete-citizen")
    public StandardResponse<String> deleteCitizen(
            @RequestParam UUID citizenId
    ) {
        citizenService.deleteCitizen(citizenId);
        return StandardResponse.<String>builder()
                .status(Status.SUCCESS)
                .message("Citizen deleted")
                .build();
    }

    @GetMapping("/get-citizen-information")
    public StandardResponse<CitizenEntity> getCitizenInformation(
            @RequestParam UUID citizenId
    ) {
        StandardResponse<CitizenEntity> citizenInformation = citizenService.getCitizenInformation(citizenId);
        return StandardResponse.<CitizenEntity>builder()
                .status(Status.SUCCESS)
                .data(citizenInformation.getData())
                .message("Citizen Information")
                .build();
    }

    @GetMapping("/get-citizen-pdf")
    public ResponseEntity<Resource> getCitizenInformationPdf(@RequestParam UUID citizenId) {
        try {
            // Call the service method to generate the PDF
            Resource pdfResource = citizenService.writeInformationToPdf(citizenId);

            // Set headers for the response to indicate file download
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDisposition(ContentDisposition.builder("attachment").filename("Citizen_Information.pdf").build());

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(pdfResource);
        } catch (DataNotFoundException | DocumentException | IOException e) {
            e.printStackTrace(); // Handle the exception appropriately
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/get-all-citizen-information")
    public StandardResponse<List<CitizenEntity>> getCitizenInformation() {
        List<CitizenEntity> allCitizenInformation = citizenService.getAllCitizenInformation().getData();
        return StandardResponse.<List<CitizenEntity>>builder()
                .status(Status.SUCCESS)
                .data(allCitizenInformation)
                .message("All Citizen Information")
                .build();
    }

    @PostMapping("/search")
    public ResponseEntity<List<CitizenEntity>> searchCitizens(@RequestBody CitizenSearchRequest searchRequest) {
        return ResponseEntity.ok(citizenService.searchCitizen(searchRequest));
    }
}
