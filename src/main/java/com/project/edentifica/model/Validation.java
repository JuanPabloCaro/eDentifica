package com.project.edentifica.model;

import lombok.*;
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
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder

public class Validation {
    @Id
    @EqualsAndHashCode.Include
    private String id;
    @NonNull
    private String challenge;
    private boolean isValidated;
}
