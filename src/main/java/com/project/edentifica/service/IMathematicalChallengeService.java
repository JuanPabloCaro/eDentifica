package com.project.edentifica.service;

import com.project.edentifica.model.MathematicalChallenge;

import java.util.Optional;


public interface IMathematicalChallengeService {
    public Optional<MathematicalChallenge> insert (MathematicalChallenge challenge);
    public boolean delete (String id);
    public Optional<MathematicalChallenge> findById(String id);
    public Optional<MathematicalChallenge> findByIdUser(String idUser);

    public boolean isValid(MathematicalChallenge challenge);

    public int calculateResult(MathematicalChallenge challenge);

    public long deleteExpiredMathematicalChallenges();

}
