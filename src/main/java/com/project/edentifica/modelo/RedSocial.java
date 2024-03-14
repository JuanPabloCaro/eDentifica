package com.project.edentifica.modelo;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;


/**
 * Clase que representa una red social, se compone de un enumerado para validar que la
 * red social exista dentro de las opciones(facebook, instagram y twitter) y de un string que repreenta
 * el nombre del perfil en esa red social.
 *
 * @version 1.0
 * @author Juan Pablo Caro Peñuela
 */
@Data
@AllArgsConstructor
@EqualsAndHashCode
@Builder

public class RedSocial {
    @Id
    private ObjectId id;
    private TipoRed tipoRed;
    private String nombrePerfil;
}
