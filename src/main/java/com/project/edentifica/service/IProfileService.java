package com.project.edentifica.service;

import com.project.edentifica.model.Profile;
import com.project.edentifica.model.User;
import org.bson.types.ObjectId;

import java.util.Optional;


public interface IProfileService {
    public Optional<Profile> insert (Profile profile);
    public Optional<Profile> addEmailAndPhoneFromUser (User user);
    public boolean update (Profile profile);
    public boolean delete (String id);
    public Optional<Profile> findById(String id);

}
