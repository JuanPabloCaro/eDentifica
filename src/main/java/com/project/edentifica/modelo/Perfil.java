package com.project.edentifica.modelo;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

/**
 * Clase que representa el perfil de un usuario, este contiene los datos que el usuario
 * quiere validar bajo su nombre.
 *
 * @version 1.0
 * @author Juan Pablo Caro Peñuela
 */

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder


@Document(collection="perfiles")
public class Perfil {
    @Id
    @EqualsAndHashCode.Include
    private ObjectId id;
    @DBRef
    private Usuario usuario;
    private byte[] imagen;
    private String descripcion;
    private List<String> telefonos;
    private List<String> correos;
    private List<RedSocial> redesSociales;
}
