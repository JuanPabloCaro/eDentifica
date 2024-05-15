package com.project.edentifica.repository;

import com.project.edentifica.model.Phone;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.Set;

@Repository
public interface PhoneRepository extends MongoRepository<Phone, String> {
    Optional<Phone> findByPhoneNumber(String phoneNumber);
    Optional<Set<Phone>> findByIdProfileUser(String idProfileUser);
}
