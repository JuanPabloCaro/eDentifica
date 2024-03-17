package com.project.edentifica.repositorio;

import com.project.edentifica.modelo.RetoMatematico;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RetoMatematicoRepositorio extends MongoRepository<RetoMatematico, ObjectId> {
}
