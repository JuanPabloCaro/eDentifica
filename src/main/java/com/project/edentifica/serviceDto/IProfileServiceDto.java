package com.project.edentifica.serviceDto;

import com.project.edentifica.model.dto.ProfileDto;

import java.util.Optional;

public interface IProfileServiceDto {
    public Optional<ProfileDto> insert (ProfileDto profile);
    public boolean update (ProfileDto profile);
    public boolean delete (String id);
}
