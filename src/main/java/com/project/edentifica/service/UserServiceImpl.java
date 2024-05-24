package com.project.edentifica.service;


import com.project.edentifica.config.DBCacheConfig;
import com.project.edentifica.errors.RollBackException;
import com.project.edentifica.model.*;
import com.project.edentifica.model.dto.UserDto;
import com.project.edentifica.repository.EmailRepository;
import com.project.edentifica.repository.PhoneRepository;
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
    private PhoneRepository phoneDAO;
    @Autowired
    private IEmailService emailService;
    @Autowired
    private EmailRepository emailDAO;
    @Autowired
    private IProfileService profileService;

    /**
     * @param user user object to be inserted
     * @return Optional of User
     */
    @Override
    @Transactional(rollbackFor = RollBackException.class)
    @CacheEvict(cacheNames = DBCacheConfig.CACHE_USER, allEntries = true)
    public Optional<User> insert(User user) {

        Phone phoneUser = user.getPhone();
        Email emailUser = user.getEmail();
        Profile profileUser = user.getProfile();

        if (profileUser != null && phoneUser != null && emailUser != null) {
            // Verificar si el teléfono y el correo electrónico ya existen
            if (phoneDAO.findByPhoneNumber(phoneUser.getPhoneNumber()).isPresent() || emailDAO.findByEmail(emailUser.getEmail()).isPresent()) {
                throw new RollBackException("The user " + user.getName() + " cannot be inserted into the database because the phone number or email already exists");
            }

            try {
                // Insertar el perfil
                Optional<Profile> profileInserted = profileService.insert(user.getProfile());

                // Verificar si el perfil se insertó correctamente
                if (profileInserted.isPresent()) {
                    // Asignar el ID al usuario
                    if (user.getId() == null) {
                        user.setId(UUID.randomUUID().toString());
                    }

                    // Agregar validaciones
                    List<Validation> validations = new ArrayList<>();
                    Validation validation1 = new Validation("Validation1: call and mathematical challenge");
                    Validation validation2 = new Validation("Validation2: taking a picture of the identity document");

                    validation1.setId(UUID.randomUUID().toString());
                    validation2.setId(UUID.randomUUID().toString());
                    validation1.setIsValidated(false);
                    validation2.setIsValidated(false);

                    validations.add(validation1);
                    validations.add(validation2);
                    user.setValidations(validations);

                    // Insertar el teléfono y el correo electrónico
                    phoneService.insert(phoneUser, profileInserted.get().getId());
                    emailService.insert(emailUser, profileInserted.get().getId());

                    // Guardar el usuario en la base de datos
                    return Optional.of(userDAO.save(user));
                } else {
                    throw new RollBackException("The user " + user.getName() + " cannot be inserted into the database because the profile could not be inserted");
                }
            } catch (Exception e) {
                throw new RollBackException("The user " + user.getName() + " cannot be inserted into the database because an error occurred: " + e.getMessage());
            }
        } else {
            throw new RollBackException("The user " + user.getName() + " cannot be inserted into the database because he/she must have a profile, email, and phone number");
        }
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
     * @param profileId Profile object to be found
     * @return Optional of Profile
     */
    @Override
    @Cacheable(value = DBCacheConfig.CACHE_USER)
    public Optional<User> findByProfileId(String profileId) {
        return userDAO.findByProfile(profileService.findById(profileId).get());
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


    //SEARCH USER BY DATA PROFILE
    
    /**
     *
     * @param socialNetwork Social network of the user to find.
     * @return Optional of User.
     */
    @Override
    public Optional<User> findBySocialNetworkProfile(SocialNetwork socialNetwork) {

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



    //SEARCH USER DTO´S

    /**
     * @param email String of the user's email to find
     * @return Optional of User.
     */
    @Override
    public Optional<UserDto> findDtoByEmail(String email) {

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
    public Optional<UserDto> findDtoByPhone(String phone) {

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
        return ObjectMapperUtils.mapAll(userDAO.findAll(), UserDto.class);
    }

    
    /**
     * @param id ObjectId of the user to find.
     * @return Optional of User.
     */
    @Override
    public Optional<UserDto> findDtoById(String id) {
        return userDAO.findById(id).map(u -> ObjectMapperUtils.map(u, UserDto.class));
    }

}



