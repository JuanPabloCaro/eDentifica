package com.project.edentifica.repository;

import com.project.edentifica.model.SocialNetwork;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SocialNetworkRepository extends MongoRepository<SocialNetwork, String> {
}
