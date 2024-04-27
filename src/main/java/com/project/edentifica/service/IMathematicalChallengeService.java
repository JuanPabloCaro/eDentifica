package com.project.edentifica.service;

import com.project.edentifica.model.MathematicalChallenge;

import java.util.Optional;


public interface IMathematicalChallengeService {
    public Optional<MathematicalChallenge> insert (MathematicalChallenge challenge);

    public boolean isValid(MathematicalChallenge challenge);

    public int calculateResult(MathematicalChallenge challenge);

    public long deleteExpiredMathematicalChallenges();

}
