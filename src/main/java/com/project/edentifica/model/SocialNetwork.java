package com.project.edentifica.model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


/**
 * Class that represents a social network, it is composed of an enumerated to validate that
 * the social network exists within the options (facebook, instagram and twitter) and a string
 * that represents the name of the profile in that social network.
 *
 * Clase que representa una red social, se compone de un enumerado para validar que la
 * red social exista dentro de las opciones(facebook, instagram y twitter) y de un string
 * que repreenta el nombre del perfil en esa red social.
 *
 * @version 1.0
 * @author Juan Pablo Caro Peñuela
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder

@Document(collection="social_networks")
public class SocialNetwork {
    @Id
    private String id;
    private NetworkType networkType;
    private String profileName;
    private boolean isVerified;

    private String idProfileUser;
}
