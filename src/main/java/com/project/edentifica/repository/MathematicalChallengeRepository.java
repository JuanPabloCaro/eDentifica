package com.project.edentifica.repository;

import com.project.edentifica.model.MathematicalChallenge;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MathematicalChallengeRepository extends MongoRepository<MathematicalChallenge, String> {
//    Optional<MathematicalChallenge> findByUserId(String idUser);
    List<MathematicalChallenge> findByIdUser(String idUser);
}
