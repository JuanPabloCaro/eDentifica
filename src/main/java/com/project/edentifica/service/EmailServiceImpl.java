package com.project.edentifica.service;

import com.project.edentifica.config.DBCacheConfig;
import com.project.edentifica.errors.RollBackException;
import com.project.edentifica.model.Email;
import com.project.edentifica.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class EmailServiceImpl implements IEmailService{
    @Autowired
    EmailRepository emailDAO;

    /**
     * @param email email object to be inserted
     * @return Optional of email.
     */
    @Override
    @CacheEvict(cacheNames = DBCacheConfig.CACHE_EMAIL, allEntries = true)
    public Optional<Email> insert(Email email, String profileId) {


        if(email.getId() == null && emailDAO.findByEmail(email.getEmail()).isEmpty()){
            //I assign the id automatically.
            email.setId(UUID.randomUUID().toString());


            //I assign the id profile
            if(email.getIdProfileUser()==null){
                email.setIdProfileUser(profileId);
            }

            return Optional.of(emailDAO.save(email));
        }else{
            throw new RollBackException("The email cannot be inserted into database because already exists");
        }
    }


    /**
     * @param email Object type email
     * @return boolean
     */
    @Override
    @CacheEvict(cacheNames = DBCacheConfig.CACHE_EMAIL, allEntries = true)
    public boolean update(Email email) {
        boolean succes = false;

        if(emailDAO.existsById(email.getId())){
            emailDAO.save(email);
            succes = true;
        }

        return succes;
    }


    /**
     * @param id String of id`s email Object to delete.
     * @return boolean.
     */
    @Override
    @CacheEvict(cacheNames = DBCacheConfig.CACHE_EMAIL, allEntries = true)
    public boolean delete(String id) {
        boolean succes = false;

        if(emailDAO.existsById(id)){
            emailDAO.deleteById(id);
            succes = true;
        }

        return succes;
    }


    /**
     * @param email String of email to find.
     * @return Optional of email.
     */
    @Override
    @Cacheable(value = DBCacheConfig.CACHE_EMAIL)
    public Optional<Email> findByEmail(String email) {
        return emailDAO.findByEmail(email);
    }


    /**
     * @param id String of Email Object to find
     * @return Optional of Object founded
     */
    @Override
    @Cacheable(value = DBCacheConfig.CACHE_EMAIL)
    public Optional<Email> findById(String id) {
        return emailDAO.findById(id);
    }


    /**
     * @param idProfileUser String´s idProfileUser to find
     * @return Optional of Email Hashset
     */
    @Override
    @Cacheable(value = DBCacheConfig.CACHE_EMAIL)
    public Optional<Set<Email>> findByIdProfileUser(String idProfileUser) {
        return emailDAO.findByIdProfileUser(idProfileUser);
    }

}
