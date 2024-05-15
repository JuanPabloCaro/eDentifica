package com.project.edentifica.service;

import com.project.edentifica.model.Email;

import java.util.Optional;
import java.util.Set;

public interface IEmailService {
    Optional<Email> insert(Email email, String profileId);
    boolean update(Email email);
    boolean delete(String id);
    Optional<Email> findByEmail(String email);
    Optional<Email> findById(String id);
    Optional<Set<Email>> findByIdProfileUser(String idProfileUser);
}
