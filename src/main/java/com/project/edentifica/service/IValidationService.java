package com.project.edentifica.service;

import com.project.edentifica.model.User;
import com.project.edentifica.model.Validation;

import java.util.Optional;

public interface IValidationService {
    public Optional<Validation> insert (Validation validation);
    public boolean update (Validation validation);
    public boolean delete (String id);
}
