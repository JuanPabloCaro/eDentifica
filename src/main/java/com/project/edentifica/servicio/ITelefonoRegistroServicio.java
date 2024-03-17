package com.project.edentifica.servicio;

import com.project.edentifica.modelo.TelefonoRegistro;
import java.util.Optional;


public interface ITelefonoRegistroServicio {
    public Optional<TelefonoRegistro> insertarTel (TelefonoRegistro tel);
}
