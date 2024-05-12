package com.project.edentifica.service;

import com.project.edentifica.model.Email;
import com.project.edentifica.model.Phone;

import java.util.Optional;


public interface IPhoneService {
    public Optional<Phone> insert (Phone phone);
    public boolean update (Phone phone);
    public boolean delete (String id);
    public Optional<Phone> findByPhone(String phone);
    public Optional<Phone> findById(String id);

    public Optional<Phone> findByPhoneNum(String phoneNumber);
}
