package com.project.edentifica.service;

import com.project.edentifica.model.Phone;

import java.util.Optional;
import java.util.Set;


public interface IPhoneService {
    Optional<Phone> insert(Phone phone, String profileId);
    boolean update(Phone phone);
    boolean delete(String id);
    Optional<Phone> findByPhoneNumber(String phoneNumber);
    Optional<Phone> findById(String id);
    Optional<Set<Phone>> findByIdProfileUser(String idProfileUser);
}
