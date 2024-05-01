package com.project.edentifica.service;

import com.project.edentifica.config.DBCacheConfig;
import com.project.edentifica.model.SocialNetwork;
import com.project.edentifica.repository.SocialNetworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SocialNetworkServiceImpl implements ISocialNetworkService{
    @Autowired
    SocialNetworkRepository socialNetworkDAO;

    /**
     * @param socialNetwork Object of socialNetwork to be inserted.
     * @return Optional of SocialNetwork.
     */
    @Override
    @CacheEvict(cacheNames = DBCacheConfig.CACHE_SOCIAL_NETWORK, allEntries = true)
    public Optional<SocialNetwork> insert(SocialNetwork socialNetwork) {

        //I assign the id automatically.
        if(socialNetwork.getId() == null){
            socialNetwork.setId(UUID.randomUUID().toString());
        }

        return Optional.of(socialNetworkDAO.insert(socialNetwork));
    }

    /**
     * @param socialNetwork Object to be updated
     * @return boolean
     */
    @Override
    @CacheEvict(cacheNames = DBCacheConfig.CACHE_SOCIAL_NETWORK, allEntries = true)
    public boolean update(SocialNetwork socialNetwork) {
        boolean succes = false;

        if(socialNetworkDAO.findById(socialNetwork.getId()).isPresent()){
            socialNetworkDAO.save(socialNetwork);
            succes = true;
        }

        return succes;
    }

    /**
     * @param id String of id`s SocialNetwork Object to delete.
     * @return boolean.
     */
    @Override
    @CacheEvict(cacheNames = DBCacheConfig.CACHE_SOCIAL_NETWORK, allEntries = true)
    public boolean delete(String id) {
        boolean succes = false;

        if(socialNetworkDAO.findById(id).isPresent()){
            socialNetworkDAO.deleteById(id);
            succes = true;
        }

        return succes;
    }

    /**
     * @param id String of SocialNetwork Object to find
     * @return Optional of Object founded
     */
    @Override
    @Cacheable(value = DBCacheConfig.CACHE_SOCIAL_NETWORK)
    public Optional<SocialNetwork> findById(String id) {
        return socialNetworkDAO.findById(id);
    }
}
