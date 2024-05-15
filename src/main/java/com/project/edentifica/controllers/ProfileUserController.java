package com.project.edentifica.controllers;

import com.project.edentifica.model.Email;
import com.project.edentifica.model.Phone;
import com.project.edentifica.model.Profile;
import com.project.edentifica.model.SocialNetwork;
import com.project.edentifica.service.IEmailService;
import com.project.edentifica.service.IPhoneService;
import com.project.edentifica.service.IProfileService;
import com.project.edentifica.service.ISocialNetworkService;
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
    @Autowired
    IEmailService emailService;
    @Autowired
    IPhoneService phoneService;
    @Autowired
    ISocialNetworkService socialNetworkService;


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

        if(profileFounded.isPresent()){
            response= new ResponseEntity<>(profileFounded.get(),HttpStatus.OK);
        }else{
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }


    /**
     * @param profileId String that represent the id of profileUser
     * @param email Email Object to insert
     * @return Boolean
     */
    @PostMapping("/insert_email/{profileId}")
    public ResponseEntity<Boolean> insertEmail(@PathVariable String profileId, @RequestBody Email email){
        ResponseEntity<Boolean> response;
        Optional<Email> emailInserted = emailService.insert(email,profileId);

        if(emailInserted.isPresent()){
            response = new ResponseEntity<>(true, HttpStatus.CREATED);
        }else{
            response = new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        }

        return response;
    }


    /**
     * @param profileId String that represent the id of profileUser
     * @param phone Phone Object to insert
     * @return Boolean
     */
    @PostMapping("/insert_phone/{profileId}")
    public ResponseEntity<Boolean> insertPhone(@PathVariable String profileId, @RequestBody Phone phone){
        ResponseEntity<Boolean> response;
        Optional<Phone> phoneInserted = phoneService.insert(phone,profileId);

        if(phoneInserted.isPresent()){
            response = new ResponseEntity<>(true, HttpStatus.CREATED);
        }else{
            response = new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        }

        return response;
    }


    /**
     * @param profileId String that represent the id of profileUser
     * @param socialNetwork SocialNetwork Object to insert
     * @return Boolean
     */
    @PostMapping("/insert_social_network/{profileId}")
    public ResponseEntity<Boolean> insertSocialNetwork(@PathVariable String profileId, @RequestBody SocialNetwork socialNetwork){
        ResponseEntity<Boolean> response;
        Optional<SocialNetwork> socialNetworkInserted = socialNetworkService.insert(socialNetwork,profileId);

        if(socialNetworkInserted.isPresent()){
            response = new ResponseEntity<>(true, HttpStatus.CREATED);
        }else{
            response = new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        }

        return response;
    }


    //Dto

//    /**
//     * @param profile Profile object to be updated
//     * @return boolean, if user have been updated correctly return true
//     */
//    @PutMapping("/updatedto")
//    public ResponseEntity<Boolean> updatedtoProfile(@RequestBody ProfileDto profile){
//        ResponseEntity<Boolean> response;
//
//        Optional<Profile> profileFound = profileService.findById(profile.getId());
//
//        if(profileFound.isPresent()){
//            profileService.updateDto(profile);
//            response = new ResponseEntity<>(true, HttpStatus.OK);
//        }else{
//            response = new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
//        }
//
//        return response;
//    }

}
