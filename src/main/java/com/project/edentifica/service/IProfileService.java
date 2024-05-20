package com.project.edentifica.service;

import com.project.edentifica.model.Profile;
import com.project.edentifica.model.dto.ProfileDto;
import com.project.edentifica.model.User;
import com.project.edentifica.model.dto.UserDto;

import java.util.Optional;

public interface IProfileService {
    Optional<Profile> insert(Profile profile);
    boolean update(Profile profile);
    boolean delete(String id);
    Optional<Profile> findById(String id);

    //SEARCH PROFILE DTO´S
    Optional<ProfileDto> findDtoById(String id);

}
