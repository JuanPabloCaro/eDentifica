package com.project.edentifica.service;

import com.project.edentifica.config.DBCacheConfig;
import com.project.edentifica.model.*;
import com.project.edentifica.model.dto.UserDto;
import com.project.edentifica.repository.*;
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

//        if(profile.getPhones() == null){
//            Set<Phone> phones= new HashSet<>();
//            profile.setPhones(phones);
//        }
//        else{
//            for(Phone p: profile.getPhones()){
//                p.setIdProfileUser(profile.getId());
//                phoneService.insert(p);
//            }
//        }
//
//        if(profile.getEmails() == null ){
//            Set<Email> emails= new HashSet<>();
//            profile.setEmails(emails);
//        }
//        else{
//            for(Email e: profile.getEmails()){
//                e.setIdProfileUser(profile.getId());
//                emailService.insert(e);
//            }
//        }
//
//        if(profile.getSocialNetworks() == null){
//            Set<SocialNetwork> socialNetworks= new HashSet<>();
//            profile.setSocialNetworks(socialNetworks);
//        }
//        else{
//            for(SocialNetwork s: profile.getSocialNetworks()){
//                s.setIdProfileUser(profile.getId());
//                socialNetworkService.insert(s);
//            }
//        }

        return Optional.of(profileDAO.save(profile));
    }


    /**
     * @param profile object of type profile to update.
     * @return boolean.
     */
    @Override
    @CacheEvict(cacheNames = DBCacheConfig.CACHE_PROFILE, allEntries = true)
    public boolean update(Profile profile) {
        boolean success = false;

        if(profileDAO.existsById(profile.getId())){
            profileDAO.save(profile);
            success = true;
        }

        return success;
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
            Optional<Set<Phone>> phonesProfile = phoneService.findByIdProfileUser(profileFound.get().getId());
            Optional<Set<Email>> emailsProfile = emailService.findByIdProfileUser(profileFound.get().getId());
            Optional<Set<SocialNetwork>> socialNetworksProfile = socialNetworkService.findByIdProfileUser(profileFound.get().getId());

            phonesProfile.ifPresent(phones -> phones.forEach(p -> phoneService.delete(p.getId())));
            emailsProfile.ifPresent(emails -> emails.forEach(e -> emailService.delete(e.getId())));
            socialNetworksProfile.ifPresent(socialNetworks -> socialNetworks.forEach(s -> socialNetworkService.delete(s.getId())));

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


    //SEARCH PROFILE DTO´S

    /**
     * @param id ObjectId of the user to find.
     * @return Optional of User.
     */
    @Override
    public Optional<ProfileDto> findDtoById(String id) {
        return profileDAO.findById(id).map(u -> ObjectMapperUtils.map(u, ProfileDto.class));
    }

}
