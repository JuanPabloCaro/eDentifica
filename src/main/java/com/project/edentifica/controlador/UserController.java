package com.project.edentifica.controlador;


import com.project.edentifica.model.Profile;
import com.project.edentifica.model.User;
import com.project.edentifica.model.dto.UserDto;
import com.project.edentifica.service.IEmailService;
import com.project.edentifica.service.IPhoneService;
import com.project.edentifica.service.IProfileService;
import com.project.edentifica.service.IUserService;
import daw.com.Pantalla;
import org.apache.coyote.Response;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("edentifica/users")
public class UserController {

    @Autowired
    private IUserService userService;
    @Autowired
    private IProfileService profileService;


    /**
     * @param user User Object to be inserted
     * @return User object.
     */
    @PostMapping("/insert")
    public ResponseEntity<User> insertUser(@RequestBody User user)
    {
        ResponseEntity<User> response;
        //The profile must be inserted first, because the user references a profile, but if the profile is not loaded into the database first, this results in an error.
        Optional<Profile> profile = profileService.insert(user.getProfile());
        Optional<User> userInserted= userService.insert(user);

        if(profile.isPresent() && userInserted.isPresent()){
            response = new ResponseEntity<>(userInserted.get(),HttpStatus.CREATED);
        }else{
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return response;
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
    public ResponseEntity<List<UserDto>> getAllUsers()
    {
        List<UserDto> all = userService.findAllDto();
        all.forEach(a->Pantalla.escribirString("\n"+a)); //example of id original.
        return new ResponseEntity<>(all,HttpStatus.OK);
    }


    /**
     * @param email String representing the user's email address to be found.
     * @return User object
     */
    @GetMapping("/get")
    public ResponseEntity<UserDto> getUserByEmail(@RequestParam("email") String email)
    {
        ResponseEntity<UserDto> response;
        Optional<UserDto> user = userService.findByEmailDto(email);
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


//    /**
//     * @param email String of email
//     * @param lastname String of lastName
//     * @return ResponseEntity of user.
//     **/
//    @PutMapping("/updatelastname/{email}/{lastname}")
//    public ResponseEntity<User> updateUser(@PathVariable String email, @PathVariable String lastname){
//        ResponseEntity<User> response;
//        Optional<User> user = userService.findByEmail(email);
//
//        if(user.isPresent()){
//            user.get().setLastName(lastname);
//            userService.update(user.get());
//            response= new ResponseEntity<>(user.get(),HttpStatus.OK);
//        }else{
//            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//
//        return response;
//    }

}
