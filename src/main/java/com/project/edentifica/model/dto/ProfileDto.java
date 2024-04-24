package com.project.edentifica.model.dto;

import com.project.edentifica.model.Email;
import com.project.edentifica.model.Phone;
import com.project.edentifica.model.SocialNetwork;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder

public class ProfileDto {
    @Id
    @EqualsAndHashCode.Include
    private String id;
    private Set<Phone> phones;
    private Set<Email> emails;
    private Set<SocialNetwork> socialNetworks;

}
