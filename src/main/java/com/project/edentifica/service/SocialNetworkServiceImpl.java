package com.project.edentifica.service;

import com.project.edentifica.config.DBCacheConfig;
import com.project.edentifica.errors.RollBackException;
import com.project.edentifica.model.NetworkType;
import com.project.edentifica.model.SocialNetwork;
import com.project.edentifica.repository.SocialNetworkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
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
    public Optional<SocialNetwork> insert(SocialNetwork socialNetwork, String profileId) {
        if(socialNetwork.getId() == null && socialNetworkDAO.findByNetworkTypeAndSocialName(socialNetwork.getNetworkType(),socialNetwork.getSocialName()).isEmpty()){
            //I assign the id automatically.
            socialNetwork.setId(UUID.randomUUID().toString());

            //I assign the id profile
            if(socialNetwork.getIdProfileUser()==null){
                socialNetwork.setIdProfileUser(profileId);
            }

            return Optional.of(socialNetworkDAO.save(socialNetwork));

        }else {
            throw new RollBackException("The phone cannot be inserted into database because the phone already exists into database");
        }

    }


    /**
     * @param socialNetwork Object to be updated
     * @return boolean
     */
    @Override
    @CacheEvict(cacheNames = DBCacheConfig.CACHE_SOCIAL_NETWORK, allEntries = true)
    public boolean update(SocialNetwork socialNetwork) {
        boolean succes = false;

        if(socialNetworkDAO.existsById(socialNetwork.getId())){
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


    /**
     * @param type Enum of social network to find
     * @param socialName String of profile name to find
     * @return an optional with the social network, otherwise the optional is empty.
     */
    @Override
    @Cacheable(value = DBCacheConfig.CACHE_SOCIAL_NETWORK)
    public Optional<SocialNetwork> findByTypeAndSocialName(NetworkType type, String socialName) {
        return socialNetworkDAO.findByNetworkTypeAndSocialName(type, socialName);
    }


    /**
     * @param idProfileUser String of profile´s user to find
     * @return Optional of SocialNetwork Hashset
     */
    @Override
    @Cacheable(value = DBCacheConfig.CACHE_SOCIAL_NETWORK)
    public Optional<Set<SocialNetwork>> findByIdProfileUser(String idProfileUser) {
        return socialNetworkDAO.findByIdProfileUser(idProfileUser);
    }
}
