package com.project.edentifica.service;

import com.project.edentifica.model.Email;
import com.project.edentifica.model.Phone;

import java.util.Optional;

public interface IEmailService {
    public Optional<Email> insert (Email email);
    public Optional<Email> findByEmail(String email);
}
