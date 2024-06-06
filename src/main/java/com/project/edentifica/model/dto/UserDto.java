package com.project.edentifica.model.dto;

import com.project.edentifica.model.Email;
import com.project.edentifica.model.Phone;
import com.project.edentifica.model.Validation;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class UserDto {
    private String id;
    private String edentificador;
    private String name;
    private String lastName;
    private List<Validation> validations;
    private Phone phone;
    private Email email;
}
