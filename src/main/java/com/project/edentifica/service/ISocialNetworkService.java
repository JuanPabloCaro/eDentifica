package com.project.edentifica.service;

import com.project.edentifica.model.NetworkType;
import com.project.edentifica.model.Phone;
import com.project.edentifica.model.SocialNetwork;
import com.project.edentifica.repository.SocialNetworkRepository;

import java.util.Optional;

public interface ISocialNetworkService {

    public Optional<SocialNetwork> insert (SocialNetwork socialNetwork);

    public boolean update(SocialNetwork socialNetwork);

    public boolean delete(String id);
    public Optional<SocialNetwork> findById(String id);


    public Optional<SocialNetwork> findByTypeAndProfileName(NetworkType type, String profileName);
}
