package com.project.edentifica.servicio;


import com.project.edentifica.modelo.User;
import com.project.edentifica.repositorio.PhoneRepository;
import com.project.edentifica.repositorio.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicioImpl implements IUsuarioServicio{

    /**
     * Inyecto los repositorios para hacer las consultas a la base de datos
     */
    @Autowired
    private UserRepository usuarioDAO;
    @Autowired
    private PhoneRepository telefonoDAO;

    /**
     * @param user objeto del usuario a insertar
     * @return Un optional del usuario que se haya insertado, si no fue posible insertarlo devuelve un optional vacio.
     */
    @Override
    @Transactional
    public Optional<User> insertar(User user) {
        String pass;
        Optional<User> usuarioInsertado= Optional.empty();
        Optional<TelefonoRegistro> tel = Optional.of(user.getTelefono());

        // antes de insertar el usuario debe de tener un telefono registrado
        if(telefonoDAO.findById(tel.get().getId()).isPresent()){
            //Hasheo la contraseña antes de inertar al usuario en la base de datos.
            pass = user.getPassword();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(pass);

            //le asigno la contraseña hasheada al usuario, para comprobar la contraseña del usuario se puede utilizar el metodo matches de BCrypt
            user.setPassword(hashedPassword);

            //inserto al usuario
            usuarioInsertado=Optional.of(usuarioDAO.save(user));

        }

        return usuarioInsertado;
    }

    /**
     * @param user objeto del usuario para actualizar
     * @return true si se actualizo el usuario correctamente, de lo contrario devuelve false.
     */
    @Override
    public boolean update(User user) {
        boolean exito=false;

        if(usuarioDAO.findByCorreo(user.getCorreo()).isPresent()){
            usuarioDAO.save(user);
            exito=true;
        }
        return exito;
    }

    /**
     * @param id String que representa el id del usuario que quiere eliminar
     * @return true si el usuario se elimino correctamente, de lo contrario devuelve false.
     */
    @Override
    public boolean delete(ObjectId id) {
        boolean exito = false;

        if(usuarioDAO.findById(id).isPresent()){
            usuarioDAO.deleteById(id);
            exito = true;
        }

        return exito;
    }

    /**
     * @param correo String del correo a buscar
     * @return un optional que contiene un usuario que tiene el correo, de lo contrario devuelve un optional vacio.
     */
    @Override
    public Optional<User> findByCorreo(String correo) {
        return usuarioDAO.findByCorreo(correo);
    }

    /**
     * @param telefono String del telefono a buscar.
     * @return un optional que contiene un usuario que tiene el telefono, de lo contrario devuelve un optional vacio.
     */
    @Override
    public Optional<User> findByTelefono(String telefono) {
        Optional<User> usuarioEncontrado= Optional.empty();

        if(telefonoDAO.findByNumTelefono(telefono).isPresent()){
            usuarioEncontrado= usuarioDAO.findByTelefono(telefonoDAO.findByNumTelefono(telefono).get());
        }

        return usuarioEncontrado;
    }

    /**
     *
     * @param password String que representa la contraseña a buscar
     * @return Optional con el objectId del usuario encontrado.
     */
    @Override
    public Optional<String> findByPassword(String password) {
        Optional<User> user = usuarioDAO.findByPassword(password);
        Optional<String> id;

        if(user.isPresent()){
            id= Optional.of(user.get().getId().toHexString());
        }else{
            id= Optional.empty();
        }

        return id;
    }

    /**
     * @return el numero de usuario registrados en la base de datos.
     */
    @Override
    public long usuariosRegistrados() {
        return usuarioDAO.count();
    }

    /**
     * @return una lista con todos los usuarios
     */
    @Override
    public List<User> findAll() {
        return usuarioDAO.findAll();
    }

    /**
     *
     * @param id
     * @return usuario encontrado
     */
    @Override
    public Optional<User> findById(ObjectId id) {
        return usuarioDAO.findById(id);
    }
}
