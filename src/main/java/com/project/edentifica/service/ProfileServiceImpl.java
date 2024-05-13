package com.project.edentifica.service;

import com.project.edentifica.config.DBCacheConfig;
import com.project.edentifica.model.*;
import com.project.edentifica.repository.*;
import org.bson.types.ObjectId;
import com.project.edentifica.model.dto.ProfileDto;
import com.project.edentifica.repository.ProfileRepository;
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

    @Autowired
    ISocialNetworkService socialNetworkService;
    @Autowired
    IPhoneService phoneService;
    @Autowired
    IEmailService emailService;

    /**
     * @param profile object of type profile to insert.
     * @return an optional profile if inserted correctly otherwise it returns an empty optional.
     */
    @Override
    @CacheEvict(cacheNames = DBCacheConfig.CACHE_PROFILE, allEntries = true)
    public Optional<Profile> insert(Profile profile) {

        //I assign the id automatically.
        if(profile.getId() == null){
            profile.setId(UUID.randomUUID().toString());
        }

        if(profile.getPhones() == null){
            Set<Phone> phones= new HashSet<>();
            profile.setPhones(phones);
        }
        else{
            for(Phone p: profile.getPhones()){
                p.setIdProfileUser(profile.getId());
                phoneService.insert(p);
            }
        }

        if(profile.getEmails() == null ){
            Set<Email> emails= new HashSet<>();
            profile.setEmails(emails);
        }
        else{
            for(Email e: profile.getEmails()){
                e.setIdProfileUser(profile.getId());
                emailService.insert(e);
            }
        }

        if(profile.getSocialNetworks() == null){
            Set<SocialNetwork> socialNetworks= new HashSet<>();
            profile.setSocialNetworks(socialNetworks);
        }
        else{
            for(SocialNetwork s: profile.getSocialNetworks()){
                s.setIdProfileUser(profile.getId());
                socialNetworkService.insert(s);
            }
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
        Optional<Profile> profile = profileDAO.findById(user.getProfile().getId());
        Optional<Phone> phoneUser= phoneDAO.findById(user.getPhone().getId());
        Optional<Email> emailUser= emailDAO.findById(user.getEmail().getId());

        //add phone from user of Profile
        //agrego el telefono del usuario como telefono de perfil.
        if(profile.isPresent()){
            Set<Phone> phones= user.getProfile().getPhones();

            if(phoneUser.isPresent()){
                phoneUser.get().setIdProfileUser(profile.get().getId());
                phones.add(phoneUser.get());
            }

            user.getProfile().setPhones(phones);
        }


        //add email from user of profile
        //agrego el email del usuario como email de perfil
        if(profile.isPresent()){
            Set<Email> emails= user.getProfile().getEmails();
            if(emailUser.isPresent()){
                emailUser.get().setIdProfileUser(profile.get().getId());
                emails.add(emailUser.get());
            }

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

    //Dto

    /**
     * @param profile object of type profile to update.
     * @return boolean.
     */
    @Override
    public boolean updateDto(ProfileDto profile) {
        boolean exito = false;

        if (profileDAO.findById(profile.getId()).isPresent()) {
            profileDAO.save(ObjectMapperUtils.map(profile, Profile.class));
            exito = true;
        }

        return exito;
    }

    /**
     * @param id string representing the identifier of the profile to be deleted.
     * @return boolean.
     */
    @Override
    public boolean deleteDto(String id) {
        boolean exito = false;

        if(profileDAO.existsById(id)){
            profileDAO.deleteById(id);
            exito = true;
        }

        return exito;
    }


}
