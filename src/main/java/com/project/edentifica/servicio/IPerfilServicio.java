package com.project.edentifica.servicio;

import com.project.edentifica.modelo.Perfil;
import org.bson.types.ObjectId;

import java.util.Optional;

/**
 * Interfaz con los metodos a implementar en el servicio del perfil.
 *
 * @version 1.0
 * @author Juan Pablo Caro Peñuela
 */
public interface IPerfilServicio {
    public Optional<Perfil> insertar(Perfil perfil);
    public boolean update (Perfil perfil);
    public boolean delete (ObjectId id);



}
