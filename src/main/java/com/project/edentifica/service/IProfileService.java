package com.project.edentifica.service;

import com.project.edentifica.model.Profile;
import com.project.edentifica.model.dto.ProfileDto;
import com.project.edentifica.model.User;
import com.project.edentifica.model.dto.UserDto;

import java.util.Optional;

public interface IProfileService {
    public Optional<Profile> insert (Profile profile);
    public boolean update (Profile profile);
    public boolean delete (String id);
    public Optional<Profile> findById(String id);

    //SEARCH PROFILE DTO´S
    public Optional<ProfileDto> findDtoById(String id);

}
