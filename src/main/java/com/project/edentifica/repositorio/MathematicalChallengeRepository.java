package com.project.edentifica.repositorio;

import com.project.edentifica.modelo.MathematicalChallenge;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MathematicalChallengeRepository extends MongoRepository<MathematicalChallenge, ObjectId> {
}
