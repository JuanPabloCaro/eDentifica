package com.project.edentifica.service;

import com.project.edentifica.model.Validation;
import com.project.edentifica.repository.ValidationRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class ValidationServiceImpl implements IValidationService{
    @Autowired
    ValidationRepository validationDAO;

    @Override
    public Optional<Validation> insert(Validation validation) {
        return Optional.empty();
    }

    @Override
    public boolean update(Validation validation) {
        return false;
    }

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
}
