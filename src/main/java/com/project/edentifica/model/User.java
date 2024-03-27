package com.project.edentifica.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

/**
 * Class that represents a user with the basic data for registration,
 * it is declared that the email must be unique.
 *
 * Clase que representa un usuario con los datos basicos para su registro,
 * se declara que el correo electronico debe de ser unico.
 *
 * @version 1.0
 * @author Juan Pablo Caro Peñuela
 */

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder

@Document(collection="users")
public class User {
    @Id
    @EqualsAndHashCode.Include
    private ObjectId id;
    private String name;
    private String lastName;
    private LocalDate dateBirth;
    private Phone phone;
    @Indexed(unique = true)
    private Email email;
    private String password;// pending removal to implement firebase
    @DBRef
    private Profile profile;
    private Set<String> idProfiles;
    private List<Validation> validations;//must have level 1 validation and level 2 validation

}
