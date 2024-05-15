package com.project.edentifica.service;

import com.project.edentifica.model.MathematicalChallenge;

import java.util.Optional;


public interface IMathematicalChallengeService {
    Optional<MathematicalChallenge> insert(MathematicalChallenge challenge);
    boolean delete(String id);
    Optional<MathematicalChallenge> findById(String id);
    Optional<MathematicalChallenge> findByIdUser(String idUser);
    boolean isValid(MathematicalChallenge challenge);
    int calculateResult(MathematicalChallenge challenge);
    long deleteExpiredMathematicalChallenges();

}
