package com.project.edentifica.modelo;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

/**
 * Class representing a validation, must have a mandatory challenge name.
 *
 * Clase que representa una validacion, debe de tener un nombre de reto obligatorio.
 *
 * @version 1.0
 * @author Juan Pablo Caro Peñuela
 */

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder

public class Validation {
    @Id
    @EqualsAndHashCode.Include
    private ObjectId id;
    @NonNull
    private String challenge;
    private boolean isValidated;
}
