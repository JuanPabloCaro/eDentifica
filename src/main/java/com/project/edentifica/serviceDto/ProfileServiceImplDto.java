package com.project.edentifica.serviceDto;

import com.project.edentifica.model.Profile;
import com.project.edentifica.model.dto.ProfileDto;
import com.project.edentifica.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileServiceImplDto implements IProfileServiceDto {

    @Autowired
    ProfileRepository profileDAO;

    /**
     * @param profile object of type profile to insert.
     * @return an optional profile if inserted correctly otherwise it returns an empty optional.
     */
    @Override
    public Optional<ProfileDto> insert(ProfileDto profile) {

        return Optional.empty();
        //return Optional.of(profileDAO.save(ObjectMapperUtils.map(profile, Profile.class)));
    }

    /**
     * @param profile object of type profile to update.
     * @return boolean.
     */
    @Override
    public boolean update(ProfileDto profile) {
        boolean exito = false;

        if (profileDAO.findById(profile.getId()).isPresent()) {
            profileDAO.save(ObjectMapperUtils.map(profile, Profile.class));
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
