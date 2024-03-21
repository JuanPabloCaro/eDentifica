package com.project.edentifica.service;

import com.project.edentifica.model.User;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;


public interface IUserService {

    public Optional<User> insert (User user);
    public boolean update (User user);
    public boolean delete (ObjectId id);
    public Optional<User> findByEmail(String email);
    public Optional<User> findByPhone(String phone);
    public Optional<ObjectId> findByPassword(String password);
    public List<User> findAll();
    public Optional<User> findById(ObjectId id);
    public long registeredUsers();

}
