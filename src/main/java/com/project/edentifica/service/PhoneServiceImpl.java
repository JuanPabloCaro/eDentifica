package com.project.edentifica.service;

import com.project.edentifica.config.DBCacheConfig;
import com.project.edentifica.errors.RollBackException;
import com.project.edentifica.model.Phone;
import com.project.edentifica.repository.PhoneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
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
    public Optional<Phone> insert( Phone phone, String profileId) {


        if(phone.getId() == null && phoneDAO.findByPhoneNumber(phone.getPhoneNumber()).isEmpty()){
            //I assign the id automatically.
            phone.setId(UUID.randomUUID().toString());


            //I assign the id profile
            if(phone.getIdProfileUser()==null){
                phone.setIdProfileUser(profileId);
            }

            return Optional.of(phoneDAO.save(phone));
        }else {
            throw new RollBackException("The phone cannot be inserted into database because the phone already exists into database");
        }
    }


    /**
     * @param phone Phone Object to be updated
     * @return boolean
     */
    @Override
    @CacheEvict(cacheNames = DBCacheConfig.CACHE_PHONE, allEntries = true)
    public boolean update(Phone phone) {
        boolean succes = false;

        if(phoneDAO.existsById(phone.getId()) && phoneDAO.findByPhoneNumber(phone.getPhoneNumber()).isEmpty()){
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

        if(phoneDAO.existsById(id)){
            phoneDAO.deleteById(id);
            succes = true;
        }

        return succes;
    }


    /**
     * @param phoneNumber String of number phone to find
     * @return an optional with the phone, otherwise the optional is empty.
     */
    @Override
    @Cacheable(value = DBCacheConfig.CACHE_PHONE)
    public Optional<Phone> findByPhoneNumber(String phoneNumber) {
        return phoneDAO.findByPhoneNumber(phoneNumber);
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
     * @param idProfileUser String of profile´s user to find
     * @return Optional of Phone Hashset
     */
    @Override
    @Cacheable(value = DBCacheConfig.CACHE_PHONE)
    public Optional<Set<Phone>> findByIdProfileUser(String idProfileUser) {
        return phoneDAO.findByIdProfileUser(idProfileUser);
    }
}
