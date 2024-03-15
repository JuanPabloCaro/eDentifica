package com.project.edentifica.repositorio;

import com.project.edentifica.modelo.TelefonoRegistro;
import com.project.edentifica.modelo.Usuario;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Interfaz del usuario para hacer consultas en la base de datos.
 *
 * @version 1.0
 * @author Juan Pablo Caro Peñuela
 */

@Repository
public interface UsuarioRepositorio extends MongoRepository<Usuario, ObjectId> {

    Optional<Usuario> findByCorreo(String correo);
    Optional<Usuario> findByTelefono(TelefonoRegistro telefono);

    public long count();

}
