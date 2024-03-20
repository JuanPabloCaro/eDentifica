package com.project.edentifica.repositorio;

import com.project.edentifica.modelo.Phone;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;



@Repository
public interface PhoneRepository extends MongoRepository<Phone, ObjectId> {
    Optional<Phone> findByNumberPhone(String numberPhone);
}
