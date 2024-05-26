package com.project.edentifica.controllers;

import com.project.edentifica.model.Email;
import com.project.edentifica.model.SocialNetwork;
import com.project.edentifica.service.ISocialNetworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("edentifica/social_networks")
public class SocialNetworkController {
    @Autowired
    private ISocialNetworkService socialNetworkService;


    /**
     * @param socialNetwork SocialNetwork object to be updated
     * @return boolean, if user have been updated correctly return true
     */
    @PutMapping("/update")
    public ResponseEntity<Boolean> updateSocialNetwork(@RequestBody SocialNetwork socialNetwork){
        ResponseEntity<Boolean> response;
        Optional<SocialNetwork> socialNetworkFounded = socialNetworkService.findById(socialNetwork.getId());

        if(socialNetworkFounded.isPresent()){
            socialNetworkService.update(socialNetwork);
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
    public ResponseEntity<Boolean> deleteSocialNetwork(@PathVariable String id ){
        ResponseEntity<Boolean> response;
        Optional<SocialNetwork> socialNetworkFounded=socialNetworkService.findById(id);

        if(socialNetworkFounded.isPresent()){
            socialNetworkService.delete(id);
            response = new ResponseEntity<>(true, HttpStatus.OK);
        }else {
            response = new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        return response;
    }


    /**
     * @param id String representing the socialNetwork's id to be found.
     * @return ResponseEntity of socialNetwork object
     */
    @GetMapping("/get")
    public ResponseEntity<SocialNetwork> getSocialNetworkById(@RequestParam("id") String id)
    {
        ResponseEntity<SocialNetwork> response;
        Optional<SocialNetwork> socialNetworkFounded = socialNetworkService.findById(id);

        if(socialNetworkFounded.isPresent()){
            response= new ResponseEntity<>(socialNetworkFounded.get(),HttpStatus.OK);
        }else{
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

    /**
     * @param idprofile String representing the profile's id to be found Social network.
     * @return ResponseEntity of Set Social Networks object
     */
    @GetMapping("/get/{idprofile}")
    public ResponseEntity<Set<SocialNetwork>> getSocialNetworksByIdProfile(@PathVariable String idprofile)
    {
        ResponseEntity<Set<SocialNetwork>> response;
        Optional<Set<SocialNetwork>> socialNetworks = socialNetworkService.findByIdProfileUser(idprofile);

        if(socialNetworks.isPresent()){
            response= new ResponseEntity<>(socialNetworks.get(),HttpStatus.OK);
        }else{
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }


}
