package com.project.edentifica.controllers;


import com.project.edentifica.model.Profile;
import com.project.edentifica.model.User;
import com.project.edentifica.service.CallService;
import com.project.edentifica.service.IProfileService;
import com.project.edentifica.service.IUserService;
import daw.com.Pantalla;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;


@RestController
@RequestMapping("edentifica/users")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IProfileService profileService;

    private final CallService callService;

    @Autowired
    public UserController(CallService callService) {
        this.callService = callService;
    }


    /**
     * @param user User Object to be inserted
     * @return User object.
     */
    @PostMapping("/insert")
    public ResponseEntity<User> insertUser(@RequestBody User user)
    {
        //para generar el id que se envia al servidor de llamadas
        //to generate the id to send to the call server
        Random random=new Random();
        ResponseEntity<User> response;
        //The profile must be inserted first, because the user references a profile, but if the profile is not loaded into the database first, this results in an error.
        Optional<Profile> profile = profileService.insert(user.getProfile());
        Optional<User> userInserted= userService.insert(user);

        if(profile.isPresent() && userInserted.isPresent()){
            response = new ResponseEntity<>(userInserted.get(),HttpStatus.CREATED);

            //Send call
            String text = "Hola, te llama edentifica. Tu debes de ser " + userInserted.get().getName() + ". Revisa los mensajes que te hemos remitido con la informacion sobre su estado.";
            int copies = 1;
            String audioLanguage = "default";
            long userId = generateUserId(); // Generar ID de usuario aleatorio
            String phone = userInserted.get().getPhone().getPhoneNumber(); // Obtener el número de teléfono del request
            callService.sendCall(text,copies,audioLanguage,userId,phone);

        }else{
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    private long generateUserId() {
        return (long) (Math.random() * Long.MAX_VALUE);
    }

    /**
     * @param user User object to be updated
     * @return boolean, if user have been updated correctly return true
     */
    @PutMapping("/update")
    public ResponseEntity<Boolean> updateUser(@RequestBody User user){
        ResponseEntity<Boolean> response;

        Optional<User> userFound = userService.findById(user.getId());

        if(userFound.isPresent()){
            userService.update(user);
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
    public ResponseEntity<Boolean> deleteUser(@PathVariable String id ){
        ResponseEntity<Boolean> response;
        Optional<User> userFound=userService.findById(id);

        if(userFound.isPresent()){
            profileService.delete(userFound.get().getProfile().getId());
            userService.delete(id);
            response = new ResponseEntity<>(true, HttpStatus.OK);
        }else {
            response = new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
        }
        return response;
    }


    /**
     * @return List of all users
     */
    @GetMapping("/getall")
    public ResponseEntity<List<User>> getAllUsers()
    {
        List<User> all = userService.findAll();
        all.forEach(a->Pantalla.escribirString("\n"+a)); //example of id original.
        return new ResponseEntity<>(all,HttpStatus.OK);
    }


    /**
     * @param email String representing the user's email address to be found.
     * @return ResponseEntity of User
     */
    @GetMapping("/get")
    public ResponseEntity<User> getUserByEmail(@RequestParam("email") String email)
    {
        ResponseEntity<User> response;
        Optional<User> user = userService.findByEmail(email);
        Pantalla.escribirString("\n"+user.get());
        if(user.isPresent()){
            response= new ResponseEntity<>(user.get(),HttpStatus.OK);
            Pantalla.escribirString("\n"+user.get());
        }else{
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }



    /**
     * @param password String that represents the password of the user whose id is required.
     * @return ObjectId.
     */
    @GetMapping("/getidbypassword")
    public ResponseEntity<String> getIdByPassword(@RequestParam("password") String password){
        ResponseEntity<String> response;

        Optional<String> id= userService.findByPassword(password);

        if(id.isPresent()){
            response = new ResponseEntity<>(id.get(),HttpStatus.OK);
        }else{
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }
}
