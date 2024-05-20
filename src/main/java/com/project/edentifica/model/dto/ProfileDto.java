package com.project.edentifica.model.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class ProfileDto {
    private String id;
    private String description;
    private String urlImageProfile;
    private LocalDate dateBirth;

}
