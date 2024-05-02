package com.project.edentifica.repository;

import com.project.edentifica.model.Email;
import com.project.edentifica.model.Phone;
import com.project.edentifica.model.Profile;
import com.project.edentifica.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> findByEmail(Email email);
    Optional<User> findByPassword(String password);
    Optional<User> findByPhone(Phone phone);
    Optional<User> findByProfile(Profile profile);
    public long count();

}
