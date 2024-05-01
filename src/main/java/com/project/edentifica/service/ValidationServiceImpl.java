package com.project.edentifica.service;

import com.project.edentifica.config.DBCacheConfig;
import com.project.edentifica.model.Validation;
import com.project.edentifica.repository.ValidationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;

import java.util.Optional;
import java.util.UUID;

public class ValidationServiceImpl implements IValidationService{
    @Autowired
    ValidationRepository validationDAO;

    /**
     * @param validation Object Validation to be inserted
     * @return Optional of Validation Object
     */
    @Override
    public Optional<Validation> insert(Validation validation) {
        //I assign the id automatically.
        if(validation.getId() == null){
            validation.setId(UUID.randomUUID().toString());
        }

        return Optional.of(validationDAO.save(validation));
    }

    /**
     * @param validation Object Validation to be updated
     * @return boolean
     */
    @Override
    public boolean update(Validation validation) {
        boolean succes = false;

        if(validationDAO.findById(validation.getId()).isPresent()){
            validationDAO.save(validation);
            succes = true;
        }

        return succes;
    }

    /**
     *
     * @param id String of id`s validation Object to delete.
     * @return boolean
     */
    @Override
    public boolean delete(String id) {
        boolean succes=false;
        Optional<Validation> validationFound= validationDAO.findById(id);

        if(validationFound.isPresent()){
            validationDAO.deleteById(id);
            succes=true;
        }

        return succes;
    }

    /**
     * @param id String of Validation Object to find
     * @return Optional of Object founded
     */
    @Override
    public Optional<Validation> findById(String id) {
        return validationDAO.findById(id);
    }
}
