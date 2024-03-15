package com.project.edentifica.repositorio;

import com.project.edentifica.modelo.TelefonoRegistro;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Interfaz del telefono de registro para hacer consultas en la base de datos.
 *
 * @version 1.0
 * @author Juan Pablo Caro Peñuela
 */

@Repository
public interface TelefonoRegistroRepositorio extends MongoRepository<TelefonoRegistro, ObjectId> {
    Optional<TelefonoRegistro> findByNumTelefono(String numTelefono);
}
