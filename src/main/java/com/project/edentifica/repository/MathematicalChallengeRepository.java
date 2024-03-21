package com.project.edentifica.repository;

import com.project.edentifica.model.MathematicalChallenge;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MathematicalChallengeRepository extends MongoRepository<MathematicalChallenge, ObjectId> {
}
