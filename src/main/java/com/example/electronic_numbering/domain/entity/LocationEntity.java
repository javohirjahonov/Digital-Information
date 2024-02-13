package com.example.electronic_numbering.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity(name = "locations")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LocationEntity extends BaseEntity {
    private Double latitude;
    private Double longitude;

}
