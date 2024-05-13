package com.project.edentifica.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mongodb.lang.Nullable;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

/**
 * Class that represents the profile of a user, it contains the data that the user
 * wants to validate under his name.
 *
 * Clase que representa el perfil de un usuario, este contiene los datos que el usuario
 * quiere validar bajo su nombre.
 *
 * @version 1.0
 * @author Juan Pablo Caro Peñuela
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder


@Document(collection="profiles")
public class Profile {
    @Id
    @EqualsAndHashCode.Include
    private String id;
    private String description;
    private Set<Phone> phones;
    private Set<Email> emails;
    private Set<SocialNetwork> socialNetworks;
    private boolean isMultiuser;
    private Set<String> idUsers;
    private String idAdmin;
}
