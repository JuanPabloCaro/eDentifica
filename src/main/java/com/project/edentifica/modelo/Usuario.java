package com.project.edentifica.modelo;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;
import java.time.LocalDate;

/**
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

@Document(collection="usuarios")
public class Usuario {
    @Id
    @EqualsAndHashCode.Include
    private ObjectId id;
    private String nombre;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private String telefono;
    @Indexed(unique = true)
    private String correo;
    private String password;

}
