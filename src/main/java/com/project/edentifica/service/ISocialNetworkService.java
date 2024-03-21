package com.project.edentifica.service;

import com.project.edentifica.model.Phone;
import com.project.edentifica.model.SocialNetwork;
import com.project.edentifica.repository.SocialNetworkRepository;

import java.util.Optional;

public interface ISocialNetworkService {

    public Optional<SocialNetwork> insert (SocialNetwork socialNetwork);

}
