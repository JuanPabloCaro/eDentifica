package com.project.edentifica.service;

import com.project.edentifica.model.Phone;

import java.util.Optional;
import java.util.Set;


public interface IPhoneService {
    public Optional<Phone> insert (Phone phone, String profileId);
    public boolean update (Phone phone);
    public boolean delete (String id);
    public Optional<Phone> findByPhoneNumber(String phoneNumber);
    public Optional<Phone> findById(String id);
    public Optional<Set<Phone>> findByIdProfileUser(String idProfileUser);
}
