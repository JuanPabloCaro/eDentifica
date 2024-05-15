package com.project.edentifica.service;

import com.project.edentifica.model.NetworkType;
import com.project.edentifica.model.SocialNetwork;

import java.util.Optional;
import java.util.Set;

public interface ISocialNetworkService {
    public Optional<SocialNetwork> insert (SocialNetwork socialNetwork, String profileId);
    public boolean update(SocialNetwork socialNetwork);
    public boolean delete(String id);
    public Optional<SocialNetwork> findById(String id);
    public Optional<SocialNetwork> findByTypeAndSocialName(NetworkType type, String socialName);
    public Optional<Set<SocialNetwork>> findByIdProfileUser(String idProfileUser);
}
