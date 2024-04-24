package com.project.edentifica.serviceDto;

import com.project.edentifica.model.User;
import com.project.edentifica.model.dto.UserDto;
import com.project.edentifica.repository.EmailRepository;
import com.project.edentifica.repository.PhoneRepository;
import com.project.edentifica.repository.ProfileRepository;
import com.project.edentifica.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplDto implements IUserServiceDto {

    @Autowired
    private UserRepository userDAO;
    @Autowired
    private PhoneRepository phoneDAO;
    @Autowired
    private EmailRepository emailDAO;
    @Autowired
    private ProfileRepository profileDAO;

    /*
     * REVISAR
     * @param user user object to be inserted
     * @return Optional of User

    @Override
    public Optional<UserDto> insert(UserDto user) {

        //return Optional.empty();
        return Optional.of(userDAO.save(ObjectMapperUtils.map(user, UserDto.class)));

    }
    */

    /*
    @Override
    public boolean update(UserDto user) {
        boolean exito = false;

        if (userDAO.findById(user.getId()).isPresent()) {
            userDAO.save(ObjectMapperUtils.map(user, User.class));
            exito = true;
        }

        return exito;
    }
    */

    /*
    @Override
    public boolean delete(String id) {
        boolean exito = false;

        if (userDAO.findById(id).isPresent()) {
            userDAO.deleteById(id);
            exito = true;
        }

        return exito;
    }
    */

    @Override
    public Optional<UserDto> findByEmailDto(String email) {
        return emailDAO.findByEmail(email).map(u -> ObjectMapperUtils.map(u, UserDto.class));
    }

    @Override
    public Optional<UserDto> findByPhone(String phone) {
        return phoneDAO.findByPhoneNumber(phone).map(u -> ObjectMapperUtils.map(u, UserDto.class));
    }

    /**
     * @return List of users.
     */
    @Override
    public List<UserDto> findAll() {
        return ObjectMapperUtils.mapAll((List<User>) userDAO.findAll(), UserDto.class);
    }

    /**
     *
     * @param id ObjectId of the user to find.
     * @return Optional of User.
     */
    @Override
    public Optional<UserDto> findById(String id) {
        return userDAO.findById(id).map(u -> ObjectMapperUtils.map(u, UserDto.class));
    }

}
