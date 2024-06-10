package com.project.edentifica.service;

import com.project.edentifica.model.*;
import com.project.edentifica.model.dto.UserDto;

import java.util.List;
import java.util.Optional;


public interface IUserService {
    Optional<User> insert(User user);
    boolean update(User user);
    boolean delete(String id);
    Optional<User> findById(String id);
    Optional<User> findByEmail(Email email);
    Optional<User> findByProfileId(String profileId);
    List<User> findAll();

    String generateEdentificador();
    long registeredUsers();

    //SEARCH USER BY DATA PROFILE
    Optional<User> findBySocialNetworkProfile(SocialNetwork socialNetwork);
    Optional<User> findByPhoneProfile(Phone phone);
    Optional<User> findByEmailProfile(Email email);

    //SEARCH USER DTO´S
    Optional<UserDto> findDtoByEmail(String email);
    Optional<UserDto> findDtoByPhone(String phone);
    Optional<UserDto> findDtoBySocialNetworkProfile(SocialNetwork socialNetwork);
    List<UserDto> findAllDto();
    Optional<UserDto> findDtoById(String id);

}
