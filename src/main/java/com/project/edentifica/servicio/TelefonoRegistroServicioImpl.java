package com.project.edentifica.servicio;

import com.project.edentifica.modelo.TelefonoRegistro;
import com.project.edentifica.repositorio.TelefonoRegistroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TelefonoRegistroServicioImpl implements ITelefonoRegistroServicio {
    /**
     * Inyecto los repositorios para hacer las consultas a la base de datos
     */
    @Autowired
    TelefonoRegistroRepositorio telefonoRegistroDAO;

    /**
     *
     * @param tel Objeto del telefono Registro
     * @return un optional con el telefono registrado, en el caso contrario el optional es vacio.
     */
    @Override
    public Optional<TelefonoRegistro> insertarTel(TelefonoRegistro tel) {

        return Optional.of(telefonoRegistroDAO.insert(tel));
    }
}
