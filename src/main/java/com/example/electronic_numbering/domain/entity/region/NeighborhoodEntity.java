package com.example.electronic_numbering.domain.entity.region;

import com.example.electronic_numbering.domain.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity(name = "neighborhoods")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NeighborhoodEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "district_id")
    @JsonIgnore
    private DistrictEntity district;

    @Column(name = "name_uz")
    private String name_uz;

    @Column(name = "name_oz")
    private String name_oz;

    @Column(name = "name_ru")
    private String name_ru;
}
