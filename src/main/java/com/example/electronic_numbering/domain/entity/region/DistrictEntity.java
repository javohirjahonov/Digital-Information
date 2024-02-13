package com.example.electronic_numbering.domain.entity.region;

import com.example.electronic_numbering.domain.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "districts")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DistrictEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "region_id")
    @JsonIgnore
    private RegionEntity region;

    @Column(name = "name_uz")
    private String nameUz;

    @Column(name = "name_oz")
    private String nameOz;

    @Column(name = "name_ru")
    private String nameRu;

}
