package com.example.electronic_numbering.repository;

import com.example.electronic_numbering.domain.entity.citizen.CitizenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface CitizenRepository extends JpaRepository<CitizenEntity, UUID>, JpaSpecificationExecutor<CitizenEntity> {

}
