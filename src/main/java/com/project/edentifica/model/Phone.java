package com.project.edentifica.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Class that represents a telephone, it has a string phoneNumber that must be unique and a boolean
 * that informs if it has already been validated or not.
 *
 * Clase que representa un telefono, esta tiene un string numero de telefono que debe de ser unico
 * y un boolean que informa si ya ha sido validado o no.
 *
 * @version 1.0
 * @author Juan Pablo Caro Peñuela
 */

@Data
@AllArgsConstructor
//@NoArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder

@Document(collection = "phones")
public class Phone {
    @Id
    @EqualsAndHashCode.Include
    private String id;
    @Indexed(unique = true)
    private String phoneNumber;
    private boolean isVerified;
    private String idProfileUser;
}
