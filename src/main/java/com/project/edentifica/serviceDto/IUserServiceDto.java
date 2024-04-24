package com.project.edentifica.serviceDto;

import com.project.edentifica.model.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface IUserServiceDto {

    //public Optional<UserDto> insert (UserDto user);
    //public boolean update (UserDto user);
    //public boolean delete (String id);
    public Optional<UserDto> findByEmailDto(String email);
    public Optional<UserDto> findByPhone(String phone);
    public List<UserDto> findAll();
    public Optional<UserDto> findById(String id);
}
