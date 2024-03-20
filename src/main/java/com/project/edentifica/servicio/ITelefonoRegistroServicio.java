package com.project.edentifica.servicio;

import java.util.Optional;


public interface ITelefonoRegistroServicio {
    public Optional<TelefonoRegistro> insertarTel (TelefonoRegistro tel);
    public Optional<TelefonoRegistro> findByTelefono(String tel);
}
