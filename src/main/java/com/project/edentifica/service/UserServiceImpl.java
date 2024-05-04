package com.project.edentifica.service;


import com.project.edentifica.config.DBCacheConfig;
import com.project.edentifica.model.*;
import com.project.edentifica.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private ProfileRepository profileDAO;

    /**
     * @param user user object to be inserted
     * @return Optional of User
     */
    @Override
    @Transactional
    @CacheEvict(cacheNames = DBCacheConfig.CACHE_USER, allEntries = true)
    public Optional<User> insert(User user) {

        //Hasheo la contraseña antes de inertar al usuario en la base de datos.
        //Hashed the password before inserting the user into the database.
        String pass = user.getPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(pass);

        //Se asigna la contraseña hasheada al usuario, para comprobar la contraseña del usuario se puede utilizar el metodo matches de BCrypt
        //the hashed password is assigned to the user, to check the user's password you can use the matches method of BCrypt
        user.setPassword(hashedPassword);

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
            phoneService.insert(user.getPhone());
        }

        if(user.getEmail()!=null){
            emailService.insert(user.getEmail());
        }

        //The id is assigned automatically to user.
        if(user.getId() == null){
            user.setId(UUID.randomUUID().toString());
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

        if(userDAO.findById(user.getId()).isPresent()){
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
     * @param email String of the user's email to find
     * @return Optional of User.
     */
    @Override
    @Cacheable(value = DBCacheConfig.CACHE_USER)
    public Optional<User> findByEmail(String email) {
        Optional<User> idUserFounded=Optional.empty();
        Optional<Email> e = emailService.findByEmail(email);

        if(e.isPresent()){
            if(userDAO.findByEmail(e.get()).isPresent()) {
                idUserFounded = userDAO.findByEmail(e.get());
            }
        }

        return idUserFounded;
    }


    /**
     * @param phone String of the user's phone number to find
     * @return Optional of User.
     */
    @Override
    @Cacheable(value = DBCacheConfig.CACHE_USER)
    public Optional<User> findByPhone(String phone) {

        Optional<User> userFound= Optional.empty();
        Optional<Phone> phoneUser=phoneService.findByPhone(phone);

        // Se comprueba que el telefono exista en la base de datos
        // We check that the telephone number exists in the database.
        if(phoneUser.isPresent()){

            //se comprueba que algun usuario tenga ese telefono asignado
            //it is checked if any user has that phone assigned
            if(userDAO.findByPhone(phoneUser.get()).isPresent()){
                userFound= Optional.of(userDAO.findByPhone(phoneUser.get()).get());
            }
        }

        return userFound;
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
     *
     * @param password String representing the password of the user to find
     * @return Optional of ObjectId.
     */
    @Override
    public Optional<String> findByPassword(String password) {
        Optional<User> user = userDAO.findByPassword(password);
        Optional<String> id;

        if(user.isPresent()){
            id= Optional.of(user.get().getId());
        }else{
            id= Optional.empty();
        }

        return id;
    }


    /**
     * @return List of users.
     */
    @Override
    public List<User> findAll() {
//        try{
//            Thread.sleep(5000);//es como si fueran 5000 mil consultas al tiempo
//        }catch(InterruptedException e){
//            Thread.currentThread().interrupt();
//        }
        return userDAO.findAll();
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
     * @return long.
     */
    @Override
    @Cacheable(value = DBCacheConfig.CACHE_USER)
    public long registeredUsers() {
        return userDAO.count();
    }
}



