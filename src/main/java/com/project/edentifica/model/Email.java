package com.project.edentifica.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Class that represents a mail, it has a string mail that must be unique
 * and a boolean that informs if it has already been validated or not.
 *
 * Clase que representa un correo, esta tiene un string correo que debe de ser unico
 * y un boolean que informa si ya ha sido validado o no.
 *
 * @version 1.0
 * @author Juan Pablo Caro Peñuela
 */

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder

@Document(collection = "emails")
public class Email{
    @Id
    @EqualsAndHashCode.Include
    private String id;
    @Indexed(unique = true)
    private String email;
    private boolean isVerified;
}
