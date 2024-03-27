package com.project.edentifica.service;

import com.project.edentifica.model.MathematicalChallenge;
import com.project.edentifica.repository.MathematicalChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MathematicalChallengeServiceImpl implements IMathematicalChallengeService {

    @Autowired
    MathematicalChallengeRepository mathChallengeDAO;

    /**
     * @return MathematicalChallenge
     */
    @Override
    public Optional<MathematicalChallenge> insert(MathematicalChallenge challenge) {
        //I assign the id automatically.
        if(challenge.getId() == null){
            challenge.setId(UUID.randomUUID().toString());
        }

        return Optional.of(mathChallengeDAO.save(challenge));
    }

    /**
     * @param challenge MathematicalChallenge Object
     * @return Int, is the result of the operation of this challenge.
     */
    @Override
    public int calcularResultado(MathematicalChallenge challenge) {
        int resultado;
        int num1 = challenge.getNumber1();
        int num2 = challenge.getNumber2();
         switch (challenge.getOperation()){
             case "+":
                 resultado= num1+num2;
                 break;
             case "*":
                 resultado= num1*num2;
                 break;
             default:
                 resultado= num1+num2;
         }

        return resultado;
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
