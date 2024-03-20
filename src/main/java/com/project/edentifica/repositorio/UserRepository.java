package com.project.edentifica.repositorio;

import com.project.edentifica.modelo.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends MongoRepository<User, ObjectId> {

    Optional<User> findByEmail(String email);
    Optional<User> findByPassword(String password);
    public long count();

}
