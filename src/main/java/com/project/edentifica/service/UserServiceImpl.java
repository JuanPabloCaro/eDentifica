package com.project.edentifica.service;


import com.project.edentifica.config.DBCacheConfig;
import com.project.edentifica.model.*;
import com.project.edentifica.model.dto.UserDto;
import com.project.edentifica.repository.ProfileRepository;
import com.project.edentifica.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userDAO;
    @Autowired
    private IPhoneService phoneService;
    @Autowired
    private IEmailService emailService;
    @Autowired
    private ISocialNetworkService socialNetworkService;
    @Autowired
    private IProfileService profileService;

    /**
     * @param user user object to be inserted
     * @return Optional of User
     */
    @Override
    @Transactional
    @CacheEvict(cacheNames = DBCacheConfig.CACHE_USER, allEntries = true)
    public Optional<User> insert(User user) {
        //The id is assigned automatically to user.
        if(user.getId() == null){
            user.setId(UUID.randomUUID().toString());
        }

        //Se agregan las Validaciones
        //Validations are added
        List<Validation> validations= new ArrayList<>();
        Validation validation1= new Validation("Validation1: call and mathematical challenge");
        Validation validation2= new Validation("Validation2: taking a picture of the identity document");

        validation1.setId(UUID.randomUUID().toString());
        validation2.setId(UUID.randomUUID().toString());

        validations.add(validation1);
        validations.add(validation2);
        user.setValidations(validations);

        //Se insertan los telefonos y los correos
        //Phone numbers and e-mails are inserted
        if(user.getPhone()!= null){
            //Se asigna el id del profileUser al objeto phone
            //The id of the profileUser is assigned to the phone object
            user.getPhone().setIdProfileUser(user.getProfile().getId());
            phoneService.insert(user.getPhone());
        }

        if(user.getEmail()!=null){
            //Se asigna el id del profileUser al objeto email
            //The id of the profileUser is assigned to the email object
            user.getEmail().setIdProfileUser(user.getProfile().getId());
            emailService.insert(user.getEmail());
        }

        return Optional.of(userDAO.save(user));
    }


    /**
     * @param user user object to be updated
     * @return boolean.
     */
    @Override
    @CacheEvict(cacheNames = DBCacheConfig.CACHE_USER, allEntries = true)
    public boolean update(User user) {
        boolean succes=false;

        if(userDAO.existsById(user.getId())){
            userDAO.save(user);
            succes=true;
        }
        return succes;
    }


    /**
     * This function deletes the user, but first it deletes the objects associated with the user.
     *
     * Esta función se encarga de eliminar el usuario, pero antes elimina los objetos asociados a el.
     *
     * @param id String representing the id of the user you want to delete.
     * @return boolean.
     */
    @Override
    @CacheEvict(cacheNames = DBCacheConfig.CACHE_USER, allEntries = true)
    public boolean delete(String id) {
        boolean succes = false;
        Optional<User> userFound= userDAO.findById(id);

        // si el usuario existe, elimino el telefono, el email.
        // if the user exists, I delete the associated phone, email.
        if(userFound.isPresent()){
            if (userFound.get().getPhone() != null) {
                phoneService.delete(userFound.get().getPhone().getId());
            }
            if (userFound.get().getEmail() != null){
                emailService.delete(userFound.get().getEmail().getId());
            }
            userDAO.deleteById(id);
            succes = true;
        }

        return succes;
    }


    /**
     * @param id ObjectId of the user to find.
     * @return Optional of User.
     */
    @Override
    @Cacheable(value = DBCacheConfig.CACHE_USER)
    public Optional<User> findById(String id) {
        return userDAO.findById(id);
    }


    /**
     * @param profile Profile object to be found
     * @return Optional of Profile
     */
    @Override
    public Optional<User> findByProfile(Profile profile) {
        return userDAO.findByProfile(profile);
    }


    /**
     * @return List of users.
     */
    @Override
    public List<User> findAll() {return userDAO.findAll();}


    /**
     * @return long.
     */
    @Override
    @Cacheable(value = DBCacheConfig.CACHE_USER)
    public long registeredUsers() {
        return userDAO.count();
    }
    
    
    //Profile
    
    /**
     *
     * @param socialNetwork Social network of the user to find.
     * @return Optional of User.
     */
    @Override
    public Optional<User> findBySocialNetwork(SocialNetwork socialNetwork) {

        return userDAO.findByProfile(profileService.findById(socialNetwork.getIdProfileUser()).get());
    }

    
    /**
     * @param phone Phone of the user to find.
     * @return Optional of User.
     */
    @Override
    public Optional<User> findByPhoneProfile(Phone phone) {
        return userDAO.findByProfile(profileService.findById(phone.getIdProfileUser()).get());
    }


    /**
     * @param email Email of the user to find.
     * @return Optional of User.
     */
    @Override
    public Optional<User> findByEmailProfile(Email email) {
        return userDAO.findByProfile(profileService.findById(email.getIdProfileUser()).get());
    }


    
    //Dto

    /**
     * @param email String of the user's email to find
     * @return Optional of User.
     */
    @Override
    public Optional<UserDto> findByEmailDto(String email) {

        Optional<UserDto> userFounded = Optional.empty();
        Optional<Email> e = emailService.findByEmail(email);

        if(e.isPresent()){
            if(userDAO.findByEmail(e.get()).isPresent()) {
                userFounded = userDAO.findByEmail(e.get()).
                                        map(u -> ObjectMapperUtils.map(u, UserDto.class));
            }
        }
        return userFounded;

    }

    /**
     * @param phone String of the user's phone number to find
     * @return Optional of User.
     */
    @Override
    public Optional<UserDto> findByPhoneDto(String phone) {

        Optional<UserDto> userFounded = Optional.empty();
        Optional<Phone> p = phoneService.findByPhoneNumber(phone);

        if(p.isPresent()){
            if(userDAO.findByPhone(p.get()).isPresent()){
                userFounded = userDAO.findByPhone(p.get()).
                                        map(u -> ObjectMapperUtils.map(u, UserDto.class));
            }
        }
        return userFounded;
    }

    /**
     * @return List of users.
     */
    @Override
    public List<UserDto> findAllDto() {
        return ObjectMapperUtils.mapAll((List<User>) userDAO.findAll(), UserDto.class);
    }

    /**
     *
     * @param id ObjectId of the user to find.
     * @return Optional of User.
     */
    @Override
    public Optional<UserDto> findByIdDto(String id) {
        return userDAO.findById(id).map(u -> ObjectMapperUtils.map(u, UserDto.class));
    }


}



