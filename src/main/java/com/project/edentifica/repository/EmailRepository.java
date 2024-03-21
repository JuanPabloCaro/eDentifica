package com.project.edentifica.repository;

import com.project.edentifica.model.Email;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailRepository extends MongoRepository<Email, ObjectId> {
    Optional<Email> findByEmail(String email);
}
