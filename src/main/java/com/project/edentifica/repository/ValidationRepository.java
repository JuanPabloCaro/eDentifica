package com.project.edentifica.repository;

import com.project.edentifica.model.Validation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ValidationRepository extends MongoRepository<Validation,String> {
}
