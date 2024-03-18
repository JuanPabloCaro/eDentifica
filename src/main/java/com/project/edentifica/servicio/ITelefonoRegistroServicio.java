package com.project.edentifica.servicio;

import com.project.edentifica.modelo.TelefonoRegistro;
import org.bson.types.ObjectId;

import java.util.Optional;


public interface ITelefonoRegistroServicio {
    public Optional<TelefonoRegistro> insertarTel (TelefonoRegistro tel);
    public Optional<TelefonoRegistro> findByTelefono(String tel);
}
