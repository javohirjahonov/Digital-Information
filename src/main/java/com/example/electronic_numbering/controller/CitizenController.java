package com.example.electronic_numbering.controller;

import com.example.electronic_numbering.domain.dto.request.user.*;
import com.example.electronic_numbering.domain.dto.response.JwtResponse;
import com.example.electronic_numbering.domain.dto.response.StandardResponse;
import com.example.electronic_numbering.domain.dto.response.Status;
import com.example.electronic_numbering.domain.entity.citizen.CitizenEntity;
import com.example.electronic_numbering.exception.RequestValidationException;
import com.example.electronic_numbering.service.citizen.CitizenService;
import com.itextpdf.text.DocumentException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
                .message("Citizen Information")
                .build();
    }

    @GetMapping("/get-citizen-pdf")
    public StandardResponse<String> getCitizenInformationPdf(
            HttpServletResponse response,
            @RequestParam UUID citizenId
    ) {
        try {
            // Call the service method to generate the PDF
            CitizenInformationForPdf citizenInformation = citizenService.writeInformationToPdf(citizenId);

            // Set response content type
            response.setContentType("application/pdf");

            // Set headers for the response to indicate file download
            response.setHeader("Content-Disposition", "attachment; filename=Citizen_Information.pdf");

            // Get the file input stream for the generated PDF
            FileInputStream inputStream = new FileInputStream("Citizen_Information.pdf");

            // Copy the PDF content to the response output stream
            ServletOutputStream outputStream = response.getOutputStream();
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();

            // Close streams
            inputStream.close();
            outputStream.close();
            return StandardResponse.<String>builder()
                    .status(Status.SUCCESS)
                    .message("Citizen information PDF generated successfully")
                    .build();
        } catch (FileNotFoundException | DocumentException e) {
            e.printStackTrace(); // Handle the exception appropriately
            return StandardResponse.<String>builder()
                    .status(Status.ERROR)
                    .message("Failed to generate PDF: " + e.getMessage())
                    .build();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
}
