package com.project.edentifica.repositorio;

import com.project.edentifica.modelo.TelefonoRegistro;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;



@Repository
public interface TelefonoRegistroRepositorio extends MongoRepository<TelefonoRegistro, ObjectId> {
    Optional<TelefonoRegistro> findByNumTelefono(String numTelefono);
}
