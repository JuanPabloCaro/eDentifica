package com.project.edentifica.service;

import com.project.edentifica.model.NetworkType;
import com.project.edentifica.model.SocialNetwork;

import java.util.Optional;
import java.util.Set;

public interface ISocialNetworkService {
    Optional<SocialNetwork> insert(SocialNetwork socialNetwork, String profileId);
    boolean update(SocialNetwork socialNetwork);
    boolean delete(String id);
    Optional<SocialNetwork> findById(String id);
    Optional<SocialNetwork> findByTypeAndSocialName(NetworkType type, String socialName);
    Optional<Set<SocialNetwork>> findByIdProfileUser(String idProfileUser);
}
