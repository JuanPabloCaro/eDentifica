package com.project.edentifica.service;

import com.project.edentifica.model.Phone;
import com.project.edentifica.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PhoneServiceImpl implements IPhoneService {
    @Autowired
    PhoneRepository phoneDAO;

    /**
     * @param phone Phone object to be inserted.
     * @return an optional with the phone, otherwise the optional is empty.
     */
    @Override
    public Optional<Phone> insert( Phone phone) {

        //I assign the id automatically.
        if(phone.getId() == null){
            phone.setId(UUID.randomUUID().toString());
        }

        return Optional.of(phoneDAO.save(phone));
    }

    /**
     * @param phone String of number phone to find
     * @return an optional with the phone, otherwise the optional is empty.
     */
    @Override
    public Optional<Phone> findByPhone(String phone) {

        return phoneDAO.findByPhoneNumber(phone);
    }
}
