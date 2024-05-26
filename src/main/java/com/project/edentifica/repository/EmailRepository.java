package com.project.edentifica.repository;

import com.project.edentifica.model.Email;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface EmailRepository extends MongoRepository<Email, String> {
    Optional<Email> findByEmail(String email);
    Optional<Set<Email>> findByIdProfileUser(String idProfileUser);
}
