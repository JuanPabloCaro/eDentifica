package com.project.edentifica.servicio;


import com.project.edentifica.modelo.Usuario;
import com.project.edentifica.repositorio.TelefonoRegistroRepositorio;
import com.project.edentifica.repositorio.UsuarioRepositorio;
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
    private UsuarioRepositorio usuarioDAO;
    @Autowired
    private TelefonoRegistroRepositorio telefonoDAO;

    /**
     * @param usuario objeto del usuario a insertar
     * @return Un optional del usuario que se haya insertado, si no fue posible insertarlo devuelve un optional vacio.
     */
    @Override
    @Transactional
    public Optional<Usuario> insertar(Usuario usuario) {
        String pass;
        Optional<Usuario> usuarioInsertado= Optional.empty();

        if(usuarioDAO.findById(usuario.getId()).isEmpty()){
            //Hasheo la contraseña antes de inertar al usuario en la base de datos.
            pass = usuario.getPassword();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String hashedPassword = passwordEncoder.encode(pass);

            //le asigno la contraseña hasheada al usuario, para comprobar la contraseña del usuario se puede utilizar el metodo matches de BCrypt
            usuario.setPassword(hashedPassword);

            //inserto al usuario
            usuarioInsertado=Optional.of(usuarioDAO.insert(usuario));

        }

        return usuarioInsertado;
    }

    /**
     * @param usuario objeto del usuario para actualizar
     * @return true si se actualizo el usuario correctamente, de lo contrario devuelve false.
     */
    @Override
    public boolean update(Usuario usuario) {
        boolean exito=false;

        if(usuarioDAO.findById(usuario.getId()).isPresent()){
            usuarioDAO.save(usuario);
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
    public Optional<Usuario> findByCorreo(String correo) {
        return usuarioDAO.findByCorreo(correo);
    }

    /**
     * @param telefono String del telefono a buscar.
     * @return un optional que contiene un usuario que tiene el telefono, de lo contrario devuelve un optional vacio.
     */
    @Override
    public Optional<Usuario> findByTelefono(String telefono) {
        Optional<Usuario> usuarioEncontrado= Optional.empty();

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
        Optional<Usuario> user = usuarioDAO.findByPassword(password);
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
    public List<Usuario> findAll() {
        return usuarioDAO.findAll();
    }
}
