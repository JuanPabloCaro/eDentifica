package com.project.edentifica.controlador;

import com.project.edentifica.model.Profile;
import com.project.edentifica.service.IProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("edentifica/profiles")
public class ProfileUserController {

    @Autowired
    IProfileService profileService;

    /**
     * @param profile Profile object to be inserted.
     * @return Optional of profile.
     */
    @PostMapping("/insert")
    public ResponseEntity<Optional<Profile>> insertProfile(@RequestBody Profile profile){
        ResponseEntity<Optional<Profile>> response;
        Optional<Profile> profileInserted= profileService.insert(profile);

        if(profileInserted.isPresent()){
            response = new ResponseEntity<>(profileInserted, HttpStatus.OK);
        }else{
            response  = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return response;
    }


}
