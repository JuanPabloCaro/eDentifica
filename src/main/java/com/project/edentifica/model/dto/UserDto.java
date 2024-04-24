package com.project.edentifica.model.dto;

import com.project.edentifica.model.Email;
import com.project.edentifica.model.Phone;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder

public class UserDto {
    @Id
    @EqualsAndHashCode.Include
    private String id;
    private String name;
    private String lastName;
    private LocalDate dateBirth;
    private Phone phone;
    @Indexed(unique = true)
    private Email email;

}
