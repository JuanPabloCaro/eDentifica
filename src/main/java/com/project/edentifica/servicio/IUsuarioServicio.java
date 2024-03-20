package com.project.edentifica.servicio;

import com.project.edentifica.modelo.User;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;


public interface IUsuarioServicio {

    public Optional<User> insertar (User user);
    public boolean update (User user);
    public boolean delete (ObjectId id);
    public Optional<User> findByCorreo(String correo);
    public Optional<User> findByTelefono(String telefono);
    public Optional<String> findByPassword(String password);
    public long usuariosRegistrados();
    public List<User> findAll();
    public Optional<User> findById(ObjectId id);

}
