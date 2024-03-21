package com.project.edentifica.service;


import com.project.edentifica.model.Phone;
import com.project.edentifica.model.User;
import com.project.edentifica.repository.PhoneRepository;
import com.project.edentifica.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userDAO;
    @Autowired
    private PhoneRepository phoneDAO;

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
    public boolean delete(ObjectId id) {
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

        return userDAO.findByEmail(email);
    }

    /**
     * @param phone String of the user's phone number to find
     * @return Optional of User.
     */
    @Override
    public Optional<User> findByPhone(String phone) {

        Optional<User> userFound= Optional.empty();
        Optional<Phone> phoneUser=phoneDAO.findByNumberPhone(phone);

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
    public Optional<ObjectId> findByPassword(String password) {
        Optional<User> user = userDAO.findByPassword(password);
        Optional<ObjectId> id;

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
    public Optional<User> findById(ObjectId id) {
        return userDAO.findById(id);
    }

    /**
     * @return long.
     */
    @Override
    public long registeredUsers() {
        return userDAO.count();
    }
}
