package com.project.edentifica.repository;

import com.project.edentifica.model.NetworkType;
import com.project.edentifica.model.SocialNetwork;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface SocialNetworkRepository extends MongoRepository<SocialNetwork, String> {
    Optional<SocialNetwork> findByNetworkTypeAndSocialName(NetworkType networkType, String socialName);
    Optional<Set<SocialNetwork>> findByIdProfileUser(String idProfileUser);
}
