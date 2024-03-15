package com.project.edentifica.modelo;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * Clase que representa el telefono para registrar al usuario,
 * este tiene un reto matematico propio y un boolean para saber si ya ha sido verificado.
 *
 * @version 1.0
 * @author Juan Pablo Caro Peñuela
 */

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder

@Document(collection="telefonosRegistro")
public class TelefonoRegistro {
    @Id
    @EqualsAndHashCode.Include
    private ObjectId id;
    private String numTelefono;
    @DBRef
    private RetoMatematico retoMate;
    private boolean verificado;

}
