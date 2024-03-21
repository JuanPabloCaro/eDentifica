package com.project.edentifica.service;

import com.project.edentifica.model.Phone;

import java.util.Optional;


public interface IPhoneService {
    public Optional<Phone> insert (Phone phone);
    public Optional<Phone> findByPhone(String phone);
}
