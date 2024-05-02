package com.project.edentifica.service;

import com.project.edentifica.config.DBCacheConfig;
import com.project.edentifica.model.*;
import com.project.edentifica.repository.*;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
public class ProfileServiceImpl implements IProfileService {

    @Autowired
    ProfileRepository profileDAO;
    @Autowired
    PhoneRepository phoneDAO;
    @Autowired
    EmailRepository emailDAO;
    @Autowired
    SocialNetworkRepository socialNetworkDAO;

    /**
     * @param profile object of type profile to insert.
     * @return an optional profile if inserted correctly otherwise it returns an empty optional.
     */
    @Override
    @CacheEvict(cacheNames = DBCacheConfig.CACHE_PROFILE, allEntries = true)
    public Optional<Profile> insert(Profile profile) {

        if(profile.getPhones() == null){
            Set<Phone> phones= new HashSet<>();
            profile.setPhones(phones);

        }

        if(profile.getEmails() == null ){
            Set<Email> emails= new HashSet<>();
            profile.setEmails(emails);
        }

        if(profile.getSocialNetworks() == null){
            Set<SocialNetwork> socialNetworks= new HashSet<>();
            profile.setSocialNetworks(socialNetworks);
        }

        //I assign the id automatically.
        if(profile.getId() == null){
            profile.setId(UUID.randomUUID().toString());
        }

        return Optional.of(profileDAO.save(profile));
    }

    /**
     * @param user object of type User that is owner of profile to insert.
     * @return an optional profile if inserted correctly otherwise it returns an empty optional.
     */
    @Override
    @CacheEvict(cacheNames = DBCacheConfig.CACHE_PROFILE, allEntries = true)
    public Optional<Profile> addEmailAndPhoneFromUser(User user) {
        //add phone from user of Profile
        //agrego el telefono del usuario como telefono de perfil.
        if(profileDAO.findById(user.getProfile().getId()).isPresent()){
            Set<Phone> phones= user.getProfile().getPhones();
            phones.add(phoneDAO.findById(user.getPhone().getId()).get());
            user.getProfile().setPhones(phones);
        }

        //add email from user of profile
        //agrego el email del usuario como email de perfil
        if(profileDAO.findById(user.getProfile().getId()).isPresent()){
            Set<Email> emails= user.getProfile().getEmails();
            emails.add(emailDAO.findById(user.getEmail().getId()).get());
            user.getProfile().setEmails(emails);
        }

        return Optional.of(profileDAO.save(user.getProfile()));
    }


    /**
     * @param profile object of type profile to update.
     * @return boolean.
     */
    @Override
    @CacheEvict(cacheNames = DBCacheConfig.CACHE_PROFILE, allEntries = true)
    public boolean update(Profile profile) {
        boolean exito = false;

        if(profileDAO.existsById(profile.getId())){
            profileDAO.save(profile);
            exito = true;
        }

        return exito;
    }


    /**
     * @param id string representing the identifier of the profile to be deleted.
     * @return boolean.
     */
    @Override
    @CacheEvict(cacheNames = DBCacheConfig.CACHE_PROFILE, allEntries = true)
    public boolean delete(String id) {
        boolean succes = false;
        Optional<Profile> profileFound = profileDAO.findById(id);

        // si el perfil existe, elimino los telefonos, los emails y las redes sociales que tenga asociados.
        // if the profile exists, I delete the associated phone numbers, emails and social networks.
        if(profileFound.isPresent()){
            if (profileFound.get().getPhones() != null){
                profileFound.get().getPhones().forEach(p->phoneDAO.delete(p));
            }
            if (profileFound.get().getEmails() != null){
                profileFound.get().getEmails().forEach(e->emailDAO.delete(e));
            }
            if (profileFound.get().getSocialNetworks() != null){
                profileFound.get().getSocialNetworks().forEach(s->socialNetworkDAO.delete(s));
            }

            profileDAO.deleteById(id);
            succes = true;
        }

        return succes;
    }

    /**
     * @param id String of profile Object to find
     * @return Optional of Object founded
     */
    @Override
    @Cacheable(value = DBCacheConfig.CACHE_PROFILE)
    public Optional<Profile> findById(String id) {
        return profileDAO.findById(id);
    }
}
