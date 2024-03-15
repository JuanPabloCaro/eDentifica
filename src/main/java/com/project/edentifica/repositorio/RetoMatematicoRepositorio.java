package com.project.edentifica.repositorio;

import com.project.edentifica.modelo.RetoMatematico;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Interfaz del reto matematico para hacer consultas en la base de datos.
 *
 * @version 1.0
 * @author Juan Pablo Caro Peñuela
 */

@Repository
public interface RetoMatematicoRepositorio extends MongoRepository<RetoMatematico, ObjectId> {
}
