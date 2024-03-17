package com.project.edentifica.repositorio;

import com.project.edentifica.modelo.TelefonoRegistro;
import com.project.edentifica.modelo.Usuario;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UsuarioRepositorio extends MongoRepository<Usuario, ObjectId> {

    Optional<Usuario> findByCorreo(String correo);
    Optional<Usuario> findByPassword(String password);
    Optional<Usuario> findByTelefono(TelefonoRegistro telefono);

    public long count();

}
