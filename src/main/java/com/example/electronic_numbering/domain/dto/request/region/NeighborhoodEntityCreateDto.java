package com.example.electronic_numbering.domain.dto.request.region;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NeighborhoodEntityCreateDto {
    private String name;
    private UUID districtId;
}
