package com.project.edentifica.servicio;

import com.project.edentifica.modelo.Usuario;
import com.project.edentifica.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServicioImpl implements IUsuarioServicio{

    /**
     * Inyecto los repositorios para hacer las consultas a la base de datos
     */
    @Autowired
    private UsuarioRepositorio usuarioDAO;

    /**
     * @param usuario objeto del usuario
     * @return Un optional del usuario que se haya insertado, si no fue posible insertarlo devuelve un optional vacio.
     */
    @Override
    public Optional<Usuario> insertar(Usuario usuario) {

        Optional<Usuario> usuarioInsertado= Optional.empty();

        if(usuarioDAO.findById(String.valueOf(usuario.getId())).isEmpty()){
            usuarioInsertado=Optional.of(usuarioDAO.insert(usuario));
        }

        return usuarioInsertado;
    }

    /**
     * @param usuario objeto del usuario
     * @return true si se actualizo el usuario correctamente, de lo contrario devuelve false.
     */
    @Override
    public boolean update(Usuario usuario) {
        boolean exito=false;

        if(usuarioDAO.findById(String.valueOf(usuario.getId())).isPresent()){
            usuarioDAO.save(usuario);
            exito=true;
        }
        return exito;
    }

    /**
     * @param id String que representa el id del usuario
     * @return true si el usuario se elimino correctamente, de lo contrario devuelve false.
     */
    @Override
    public boolean delete(String id) {
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
        return usuarioDAO.findByTelefono(telefono);
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
