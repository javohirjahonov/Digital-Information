package com.example.electronic_numbering.domain.entity.region;

import com.example.electronic_numbering.domain.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "regions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_uz")
    private String nameUz;

    @Column(name = "name_oz")
    private String nameOz;

    @Column(name = "name_ru")
    private String nameRu;
}
