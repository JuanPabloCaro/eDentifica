package com.project.edentifica.service;

import com.project.edentifica.config.DBCacheConfig;
import com.project.edentifica.model.MathematicalChallenge;
import com.project.edentifica.repository.MathematicalChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MathematicalChallengeServiceImpl implements IMathematicalChallengeService {

    @Autowired
    MathematicalChallengeRepository mathChallengeDAO;

    //vigencia del reto
    //validity of challenge
    @Value("${mathchallenge.validity}")
    private int validity;

    /**
     * @return MathematicalChallenge
     */
    @Override
    @CacheEvict(cacheNames = DBCacheConfig.CACHE_MATHEMATICAL_CHALLENGE, allEntries = true)
    public Optional<MathematicalChallenge> insert(MathematicalChallenge challenge) {
        //I assign the id automatically.
        if(challenge.getId() == null){
            challenge.setId(UUID.randomUUID().toString());
        }

        return Optional.of(mathChallengeDAO.save(challenge));
    }

    @Override
    @CacheEvict(cacheNames = DBCacheConfig.CACHE_MATHEMATICAL_CHALLENGE, allEntries = true)
    public boolean delete(String id) {
        boolean succes = false;

        if(mathChallengeDAO.existsById(id)){
            mathChallengeDAO.deleteById(id);
            succes = true;
        }

        return succes;
    }

    /**
     * @param id String of MathematicalChallenge Object to find
     * @return Optional of Object founded
     */
    @Override
    @Cacheable(value = DBCacheConfig.CACHE_MATHEMATICAL_CHALLENGE)
    public Optional<MathematicalChallenge> findById(String id) {
        return mathChallengeDAO.findById(id);
    }


    /**
     *  Method to verify if the challenge is still valid.
     *  Método para verificar si el reto sigue vigente.
     *
     * @param challenge object type MathematicalChallenge
     * @return boolean
     */
    @Override
    public boolean isValid(MathematicalChallenge challenge) {
        // Obtener el tiempo actual en UTC+0 como OffsetDateTime
        // Get current time in UTC+0 as OffsetDateTime
        OffsetDateTime currentTime = OffsetDateTime.now(ZoneOffset.UTC);

        // Obtener la diferencia entre la hora actual y la hora de creación del reto.
        // Obtain the difference between the current time and the time of creation of the challenge.
        Duration elapsedTime = Duration.between(challenge.getTimeOfCreation(), currentTime);

        // Compruebe si el tiempo transcurrido es inferior al plazo del reto.
        // Verify if the elapsed time is less than the term of the challenge.
        return elapsedTime.compareTo(Duration.ofMinutes(validity)) <= 0;
    }



    /**
     * @param challenge MathematicalChallenge Object
     * @return Int, is the result of the operation of this challenge.
     */
    @Override
    public int calculateResult(MathematicalChallenge challenge) {
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
                                                                filter(c -> !isValid(c)).
                                                                toList();
        //the size of the list to be deleted is saved
        long numberDeleted = expiredChallenge.size();

        expiredChallenge.forEach(c -> mathChallengeDAO.delete(c));

        return numberDeleted;
    }
}
