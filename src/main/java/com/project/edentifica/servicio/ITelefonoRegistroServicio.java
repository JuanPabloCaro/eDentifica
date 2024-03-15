package com.project.edentifica.servicio;

import com.project.edentifica.modelo.TelefonoRegistro;
import java.util.Optional;

/**
 * Interfaz con los metodos a implementar en el servicio del telefono.
 *
 * @version 1.0
 * @author Juan Pablo Caro Peñuela
 */
public interface ITelefonoRegistroServicio {
    public Optional<TelefonoRegistro> insertarTel (TelefonoRegistro tel);
}
