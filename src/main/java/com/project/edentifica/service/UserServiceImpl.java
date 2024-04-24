package com.project.edentifica.service;


import com.project.edentifica.model.Email;
import com.project.edentifica.model.Phone;
import com.project.edentifica.model.User;
import com.project.edentifica.model.dto.UserDto;
import com.project.edentifica.repository.EmailRepository;
import com.project.edentifica.repository.PhoneRepository;
import com.project.edentifica.repository.ProfileRepository;
import com.project.edentifica.repository.UserRepository;
import com.project.edentifica.serviceDto.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userDAO;
    @Autowired
    private PhoneRepository phoneDAO;
    @Autowired
    private EmailRepository emailDAO;
    @Autowired
    private ProfileRepository profileDAO;

    /**
     * @param user user object to be inserted
     * @return Optional of User
     */
    @Override
    @Transactional
    public Optional<User> insert(User user) {

        //Hasheo la contraseña antes de inertar al usuario en la base de datos.
        String pass = user.getPassword();
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(pass);

        //le asigno la contraseña hasheada al usuario, para comprobar la contraseña del usuario se puede utilizar el metodo matches de BCrypt
        user.setPassword(hashedPassword);


        //I assign the id automatically.
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
    public boolean update(User user) {
        boolean exito=false;

        if(userDAO.findById(user.getId()).isPresent()){
            userDAO.save(user);
            exito=true;
        }
        return exito;
    }

    /**
     * @param id String representing the id of the user you want to delete.
     * @return boolean.
     */
    @Override
    public boolean delete(String id) {
        boolean exito = false;

        if(userDAO.findById(id).isPresent()){
            userDAO.deleteById(id);
            exito = true;
        }

        return exito;
    }


    /**
     * @param email String of the user's email to find
     * @return Optional of User.
     */
    @Override
    public Optional<User> findByEmail(String email) {
        Optional<User> idUserFounded=Optional.empty();
        Optional<Email> e = emailDAO.findByEmail(email);

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
    public Optional<User> findByPhone(String phone) {

        Optional<User> userFound= Optional.empty();
        Optional<Phone> phoneUser=phoneDAO.findByPhoneNumber(phone);

        // Se comprueba que el telefono exista en la base de datos
        if(phoneUser.isPresent()){
            //se comprueba que algun usuario tenga ese telefono asignado
            if(userDAO.findByPhone(phoneUser.get()).isPresent()){
                // se devuelve al usuario encontrado
                userFound= Optional.of(userDAO.findByPhone(phoneUser.get()).get());
            }
        }

        return userFound;
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
        return userDAO.findAll();
    }

    /**
     *
     * @param id ObjectId of the user to find.
     * @return Optional of User.
     */
    @Override
    public Optional<User> findById(String id) {
        return userDAO.findById(id);
    }

    /**
     * @return long.
     */
    @Override
    public long registeredUsers() {
        return userDAO.count();
    }

    //Dto

    @Override
    public Optional<UserDto> findByEmailDto(String email) {
        return emailDAO.findByEmail(email).map(u -> ObjectMapperUtils.map(u, UserDto.class));
    }
}
