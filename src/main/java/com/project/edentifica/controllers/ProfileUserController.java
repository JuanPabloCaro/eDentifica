package com.project.edentifica.controllers;

import com.project.edentifica.model.Profile;
import com.project.edentifica.model.User;
import com.project.edentifica.model.dto.ProfileDto;
import com.project.edentifica.service.IProfileService;
import daw.com.Pantalla;
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

    /**
     * @param profile Profile object to be updated
     * @return boolean, if user have been updated correctly return true
     */
    @PutMapping("/update")
    public ResponseEntity<Boolean> updateProfile(@RequestBody Profile profile){
        ResponseEntity<Boolean> response;

        Optional<Profile> profileFound = profileService.findById(profile.getId());

        if(profileFound.isPresent()){
            profileService.update(profile);
            response = new ResponseEntity<>(true, HttpStatus.OK);
        }else{
            response = new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }

        return response;
    }

    /**
     * @param id String of Object to be deleted
     * @return ResponseEntity of boolean
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Boolean> deleteProfile(@PathVariable String id ){
        ResponseEntity<Boolean> response;
        Optional<Profile> profileFound=profileService.findById(id);

        if(profileFound.isPresent()){
            profileService.delete(id);
            response = new ResponseEntity<>(true, HttpStatus.OK);
        }else {
            response = new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        return response;
    }

    /**
     * @param id String representing the profile's id to be found.
     * @return ResponseEntity of Profile object
     */
    @GetMapping("/get")
    public ResponseEntity<Profile> getProfileById(@RequestParam("id") String id)
    {
        ResponseEntity<Profile> response;
        Optional<Profile> profileFounded = profileService.findById(id);
        Pantalla.escribirString("\n"+profileFounded.get());

        if(profileFounded.isPresent()){
            response= new ResponseEntity<>(profileFounded.get(),HttpStatus.OK);
            Pantalla.escribirString("\n"+profileFounded.get());
        }else{
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

    //Dto

    /**
     * @param profile Profile object to be updated
     * @return boolean, if user have been updated correctly return true
     */
    @PutMapping("/updatedto")
    public ResponseEntity<Boolean> updatedtoProfile(@RequestBody ProfileDto profile){
        ResponseEntity<Boolean> response;

        Optional<Profile> profileFound = profileService.findById(profile.getId());

        if(profileFound.isPresent()){
            profileService.updateDto(profile);
            response = new ResponseEntity<>(true, HttpStatus.OK);
        }else{
            response = new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }

        return response;
    }

}
