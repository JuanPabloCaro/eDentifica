package com.project.edentifica.model.dto;

import com.project.edentifica.model.Email;
import com.project.edentifica.model.Phone;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDto {
    private String id;
    private String name;
    private String lastName;

}
