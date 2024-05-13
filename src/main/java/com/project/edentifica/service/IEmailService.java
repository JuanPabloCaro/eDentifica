package com.project.edentifica.service;

import com.project.edentifica.model.Email;
import com.project.edentifica.model.Phone;

import java.util.Optional;

public interface IEmailService {
    public Optional<Email> insert (Email email);
    public boolean update (Email email);
    public boolean delete (String id);
    public Optional<Email> findByEmail(String email);
    public Optional<Email> findById(String id);

    public Optional<Email> findByEmailNa(String email);
}
