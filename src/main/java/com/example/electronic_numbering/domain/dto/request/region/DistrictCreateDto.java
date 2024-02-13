package com.example.electronic_numbering.domain.dto.request.region;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DistrictCreateDto {
    private String nameOz;
    private String nameUz;
    private String nameRu;
    private Long regionId;
}
