package com.project.edentifica.repository;

import com.project.edentifica.model.Phone;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;



@Repository
public interface PhoneRepository extends MongoRepository<Phone, ObjectId> {
    Optional<Phone> findByNumberPhone(String numberPhone);
}
