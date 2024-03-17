package com.project.edentifica.modelo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mongodb.lang.Nullable;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import java.util.Set;

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
    @Nullable
    @JsonInclude(JsonInclude.Include.NON_NULL) // Esto asegura que la imagen nula no se incluya en la serialización JSON
    private byte[] imagen;
    private String descripcion;
    private Set<String> telefonos;
    private Set<String> correos;
    private Set<RedSocial> redesSociales;
}
