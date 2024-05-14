package com.project.edentifica.service;

import com.project.edentifica.config.DBCacheConfig;
import com.project.edentifica.model.Phone;
import com.project.edentifica.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
    @CacheEvict(cacheNames = DBCacheConfig.CACHE_PHONE, allEntries = true)
    public Optional<Phone> insert( Phone phone) {

        //I assign the id automatically.
        if(phone.getId() == null){
            phone.setId(UUID.randomUUID().toString());
        }

        return Optional.of(phoneDAO.save(phone));
    }

    /**
     * @param phone Phone Object to be updated
     * @return boolean
     */
    @Override
    @CacheEvict(cacheNames = DBCacheConfig.CACHE_PHONE, allEntries = true)
    public boolean update(Phone phone) {
        boolean succes = false;

        if(phoneDAO.findById(phone.getId()).isPresent()){
            phoneDAO.save(phone);
            succes = true;
        }

        return succes;
    }

    /**
     * @param id String of id`s phone Object to delete.
     * @return boolean.
     */
    @Override
    @CacheEvict(cacheNames = DBCacheConfig.CACHE_PHONE, allEntries = true)
    public boolean delete(String id) {
        boolean succes = false;

        if(phoneDAO.findById(id).isPresent()){
            phoneDAO.deleteById(id);
            succes = true;
        }

        return succes;
    }


    /**
     * @param phone String of number phone to find
     * @return an optional with the phone, otherwise the optional is empty.
     */
    @Override
    @Cacheable(value = DBCacheConfig.CACHE_PHONE)
    public Optional<Phone> findByPhone(String phone) {

        return phoneDAO.findByPhoneNumber(phone);
    }


    /**
     * @param id String of Phone Object to find
     * @return Optional of Object founded
     */
    @Override
    @Cacheable(value = DBCacheConfig.CACHE_PHONE)
    public Optional<Phone> findById(String id) {
        return phoneDAO.findById(id);
    }

    /**
     * @param phoneNumber String of phone number to find
     * @return an optional with the phone, otherwise the optional is empty.
     */
    @Override
    @Cacheable(value = DBCacheConfig.CACHE_PHONE)
    public Optional<Phone> findByPhoneNum(String phoneNumber) {
        return phoneDAO.findByPhoneNumber(phoneNumber);
    }
}
