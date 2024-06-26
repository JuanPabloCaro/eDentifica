package com.project.edentifica.repository;

import com.project.edentifica.model.*;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(Email email);
    Optional<User> findByPhone(Phone phone);
    Optional<User> findByProfile(Profile profile);
    Optional<User> findByEdentificador(String edentificador);

    public long count();

}
