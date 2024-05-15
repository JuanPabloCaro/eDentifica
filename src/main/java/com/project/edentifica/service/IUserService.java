package com.project.edentifica.service;

import com.project.edentifica.model.*;
import com.project.edentifica.model.dto.UserDto;

import java.util.List;
import java.util.Optional;


public interface IUserService {
    public Optional<User> insert (User user);
    public boolean update (User user);
    public boolean delete (String id);
    public Optional<User> findById(String id);
    public Optional<User> findByProfile(Profile profile);
    public List<User> findAll();
    public long registeredUsers();

    //Profile
    public Optional<User> findBySocialNetwork(SocialNetwork socialNetwork);
    public Optional<User> findByPhoneProfile(Phone phone);
    public Optional<User> findByEmailProfile(Email email);

    //Dto
    public Optional<UserDto> findByEmailDto(String email);
    public Optional<UserDto> findByPhoneDto(String phone);
    public List<UserDto> findAllDto();
    public Optional<UserDto> findByIdDto(String id);

}
