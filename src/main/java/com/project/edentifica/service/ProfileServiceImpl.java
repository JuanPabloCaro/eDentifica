package com.project.edentifica.service;

import com.project.edentifica.model.Profile;
import com.project.edentifica.repository.ProfileRepository;
import com.project.edentifica.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProfileServiceImpl implements IProfileService {

    @Autowired
    ProfileRepository profileDAO;

    /**
     * @param profile object of type profile to insert.
     * @return an optional profile if inserted correctly otherwise it returns an empty optional.
     */
    @Override
    public Optional<Profile> insert(Profile profile) {

        //I assign the id automatically.
        if(profile.getId() == null){
            profile.setId(UUID.randomUUID().toString());
        }

        return Optional.of(profileDAO.save(profile));
    }


    /**
     * @param profile object of type profile to update.
     * @return boolean.
     */
    @Override
    public boolean update(Profile profile) {
        boolean exito = false;

        if(profileDAO.existsById(profile.getId())){
            profileDAO.save(profile);
            exito = true;
        }

        return exito;
    }


    /**
     * @param id string representing the identifier of the profile to be deleted.
     * @return boolean.
     */
    @Override
    public boolean delete(String id) {
        boolean exito = false;

        if(profileDAO.existsById(id)){
            profileDAO.deleteById(id);
            exito = true;
        }

        return exito;
    }
}
