package com.project.edentifica.service;

import com.project.edentifica.model.MathematicalChallenge;

import java.util.Optional;


public interface IMathematicalChallengeService {
    public Optional<MathematicalChallenge> insert (MathematicalChallenge challenge);

    public long deleteExpiredMathematicalChallenges();

}
