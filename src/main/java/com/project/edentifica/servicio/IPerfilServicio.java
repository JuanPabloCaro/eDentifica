package com.project.edentifica.servicio;

import com.project.edentifica.modelo.Profile;
import org.bson.types.ObjectId;

import java.util.Optional;


public interface IPerfilServicio {
    public Optional<Profile> insertar(Profile profile);
    public boolean update (Profile profile);
    public boolean delete (ObjectId id);



}
