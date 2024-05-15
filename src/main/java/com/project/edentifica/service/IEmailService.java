package com.project.edentifica.service;

import com.project.edentifica.model.Email;

import java.util.Optional;
import java.util.Set;

public interface IEmailService {
    public Optional<Email> insert (Email email, String profileId);
    public boolean update (Email email);
    public boolean delete (String id);
    public Optional<Email> findByEmail(String email);
    public Optional<Email> findById(String id);
    public Optional<Set<Email>> findByIdProfileUser(String idProfileUser);
}
