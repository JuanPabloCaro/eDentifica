package com.project.edentifica.service;

import com.project.edentifica.model.*;
import org.bson.types.ObjectId;
import com.project.edentifica.model.dto.UserDto;

import java.util.List;
import java.util.Optional;


public interface IUserService {

    public Optional<User> insert (User user);
    public boolean update (User user);
    public boolean delete (String id);
    public Optional<User> findByEmail(String email);
    public Optional<User> findByPhone(String phone);
    public Optional<User> findByProfile(Profile profile);
    public Optional<String> findByPassword(String password);
    public List<User> findAll();
    public Optional<User> findById(String id);
    public long registeredUsers();

    //Dto
    public Optional<UserDto> findByEmailDto(String email);
    public Optional<UserDto> findByPhoneDto(String phone);
    public List<UserDto> findAllDto();
    public Optional<UserDto> findByIdDto(String id);

    public Optional<User> findBySocialNetwork(SocialNetwork socialNetwork);
    public Optional<User> findByPhoneInProfile(String phone);

}
