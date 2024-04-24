package com.project.edentifica.service;

import com.project.edentifica.model.Profile;
import com.project.edentifica.model.dto.ProfileDto;
import org.bson.types.ObjectId;

import java.util.Optional;


public interface IProfileService {
    public Optional<Profile> insert (Profile profile);
    public boolean update (Profile profile);
    public boolean delete (String id);

    //Dto
    public boolean updateDto (ProfileDto profile);
    public boolean deleteDto (String id);

}
