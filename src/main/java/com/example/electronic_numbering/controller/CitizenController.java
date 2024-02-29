package com.example.electronic_numbering.controller;

import com.example.electronic_numbering.domain.dto.request.user.*;
import com.example.electronic_numbering.domain.dto.response.JwtResponse;
import com.example.electronic_numbering.domain.dto.response.StandardResponse;
import com.example.electronic_numbering.domain.dto.response.Status;
import com.example.electronic_numbering.domain.entity.citizen.CitizenEntity;
import com.example.electronic_numbering.exception.RequestValidationException;
import com.example.electronic_numbering.service.citizen.CitizenService;
import com.itextpdf.text.DocumentException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.security.Principal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/citizen")
@RequiredArgsConstructor
public class CitizenController {
    private final CitizenService citizenService;

    @PostMapping("/addCitizen")
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
        citizenService.deletedCitizen(citizenId);
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
                .message("Citizen deleted")
                .build();
    }

    @GetMapping("/get-citizen-pdf")
    public StandardResponse<CitizenInformationForPdf> getCitizenInformationPdf(
            @RequestParam UUID citizenId
    ) throws DocumentException, FileNotFoundException {
        CitizenInformationForPdf citizenInformationForPdf = citizenService.writeInformationToPdf(citizenId);
        return StandardResponse.<CitizenInformationForPdf>builder()
                .status(Status.SUCCESS)
                .data(citizenInformationForPdf)
                .message("Citizen deleted")
                .build();
    }

}
