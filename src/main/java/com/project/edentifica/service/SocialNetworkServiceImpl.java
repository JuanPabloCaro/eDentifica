package com.project.edentifica.service;

import com.project.edentifica.model.SocialNetwork;
import com.project.edentifica.repository.SocialNetworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SocialNetworkServiceImpl implements ISocialNetworkService{
    @Autowired
    SocialNetworkRepository socialNetworkDAO;

    /**
     * @param socialNetwork Object of socialNetwork to be inserted.
     * @return Optional of SocialNetwork.
     */
    @Override
    public Optional<SocialNetwork> insert(SocialNetwork socialNetwork) {

        //I assign the id automatically.
        if(socialNetwork.getId() == null){
            socialNetwork.setId(UUID.randomUUID().toString());
        }

        return Optional.of(socialNetworkDAO.insert(socialNetwork));
    }
}
