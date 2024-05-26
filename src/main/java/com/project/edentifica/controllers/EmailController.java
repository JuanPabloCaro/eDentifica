package com.project.edentifica.controllers;

import com.project.edentifica.model.Email;
import com.project.edentifica.service.IEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("edentifica/emails")
public class EmailController {
    @Autowired
    private IEmailService emailService;


    /**
     * @param email Email object to be updated
     * @return boolean, if user have been updated correctly return true
     */
    @PutMapping("/update")
    public ResponseEntity<Boolean> updateEmail(@RequestBody Email email){
        ResponseEntity<Boolean> response;
        Optional<Email> emailFounded = emailService.findById(email.getId());

        if(emailFounded.isPresent()){
            emailService.update(email);
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
    public ResponseEntity<Boolean> deleteEmail(@PathVariable String id ){
        ResponseEntity<Boolean> response;
        Optional<Email> emailFounded=emailService.findById(id);

        if(emailFounded.isPresent()){
            emailService.delete(id);
            response = new ResponseEntity<>(true, HttpStatus.OK);
        }else {
            response = new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        return response;
    }


    /**
     * @param id String representing the email's id to be found.
     * @return ResponseEntity of Email object
     */
    @GetMapping("/get")
    public ResponseEntity<Email> getEmailById(@RequestParam("id") String id)
    {
        ResponseEntity<Email> response;
        Optional<Email> emailFounded = emailService.findById(id);

        if(emailFounded.isPresent()){
            response= new ResponseEntity<>(emailFounded.get(),HttpStatus.OK);
        }else{
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

    /**
     * @param idprofile String representing the profile's id to be found emails.
     * @return ResponseEntity of Set Emails object
     */
    @GetMapping("/get/{idprofile}")
    public ResponseEntity<Set<Email>> getEmailsByIdProfile(@PathVariable String idprofile)
    {
        ResponseEntity<Set<Email>> response;
        Optional<Set<Email>> emails = emailService.findByIdProfileUser(idprofile);

        if(emails.isPresent()){
            response= new ResponseEntity<>(emails.get(),HttpStatus.OK);
        }else{
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

}
