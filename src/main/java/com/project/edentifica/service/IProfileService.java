package com.project.edentifica.service;

import com.project.edentifica.model.Profile;
import org.bson.types.ObjectId;

import java.util.Optional;


public interface IProfileService {
    public Optional<Profile> insert (Profile profile);
    public boolean update (Profile profile);
    public boolean delete (ObjectId id);



}
