package com.project.edentifica.repositorio;

import com.project.edentifica.modelo.Profile;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProfileRepository extends MongoRepository<Profile, ObjectId> {

}
