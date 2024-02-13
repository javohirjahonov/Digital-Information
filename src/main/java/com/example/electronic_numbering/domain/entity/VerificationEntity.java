package com.example.electronic_numbering.domain.entity;

import com.example.electronic_numbering.domain.entity.user.UserEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity(name = "verification")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class VerificationEntity extends BaseEntity{
    @OneToOne
    private UserEntity userId;
    private String code;
}
