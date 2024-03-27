package com.project.edentifica.service;

import com.project.edentifica.model.MathematicalChallenge;
import com.project.edentifica.repository.MathematicalChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MathematicalChallengeServiceImpl implements IMathematicalChallengeService {

    @Autowired
    MathematicalChallengeRepository mathChallengeDAO;

    /**
     * @return MathematicalChallenge
     */
    @Override
    public Optional<MathematicalChallenge> insert(MathematicalChallenge challenge) {
        return Optional.of(mathChallengeDAO.save(challenge));
    }


    /**
     * This service eliminates the mathematical challenges that
     * are no longer valid (all those whose validity time has expired).
     *
     * Este servicio elimina los retos matematicos que ya no
     * son validos (todos aquellos que su tiempo de vigencia haya expirado).
     *
     * @return long
     */
    @Override
    public long deleteExpiredMathematicalChallenges() {
        //challenges that are no longer valid are saved in a list.
        List<MathematicalChallenge> expiredChallenge= mathChallengeDAO.
                                                                findAll().
                                                                stream().
                                                                filter(c -> !c.isValid()).
                                                                toList();
        //the size of the list to be deleted is saved
        long numberDeleted = expiredChallenge.size();

        expiredChallenge.forEach(c -> mathChallengeDAO.delete(c));

        return numberDeleted;
    }
}
