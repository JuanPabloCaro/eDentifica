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
    public Optional<User> findByProfileId(String profileId);
    public List<User> findAll();
    public long registeredUsers();

    //SEARCH USER BY DATA PROFILE
    public Optional<User> findBySocialNetworkProfile(SocialNetwork socialNetwork);
    public Optional<User> findByPhoneProfile(Phone phone);
    public Optional<User> findByEmailProfile(Email email);

    //SEARCH USER DTO´S
    public Optional<UserDto> findDtoByEmail(String email);
    public Optional<UserDto> findDtoByPhone(String phone);
    public List<UserDto> findAllDto();
    public Optional<UserDto> findDtoById(String id);

}
