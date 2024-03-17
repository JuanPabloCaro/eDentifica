package com.project.edentifica.servicio;

import com.project.edentifica.modelo.Perfil;
import org.bson.types.ObjectId;

import java.util.Optional;


public interface IPerfilServicio {
    public Optional<Perfil> insertar(Perfil perfil);
    public boolean update (Perfil perfil);
    public boolean delete (ObjectId id);



}
