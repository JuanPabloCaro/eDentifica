package com.project.edentifica.controlador;


import com.project.edentifica.model.User;
import com.project.edentifica.service.IUserService;
import daw.com.Pantalla;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("edentifica/users")
public class UserController {

    @Autowired
    private IUserService userService;


    /**
     * @return List of all users
     */
    @GetMapping("/getall")
    public ResponseEntity<List<User>> getAllUsers()
    {
        List<User> all = userService.findAll();
        //all.forEach(a->Pantalla.escribirString(""+a)); //example of id original.
        return new ResponseEntity<>(all,HttpStatus.OK);
    }


    /**
     * @param email String representing the user's email address to be found.
     * @return User object
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
    public ResponseEntity<ObjectId> getIdByPassword(@RequestParam("password") String password){
        ResponseEntity<ObjectId> response;

        Optional<ObjectId> id= userService.findByPassword(password);

        if(id.isPresent()){
            response = new ResponseEntity<>(id.get(),HttpStatus.OK);
        }else{
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }




    /**
     * @param user User Object to be inserted
     * @return User object.
     */
    @PostMapping("/insert")
    public ResponseEntity<User> insertUser(@RequestBody User user)
    {
        ResponseEntity<User> response;

        Optional<User> userInserted= userService.insert(user);

        if(userInserted.isPresent()){
            response = new ResponseEntity<>(userInserted.get(),HttpStatus.CREATED);
        }else{
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return response;
    }


    /**
     * @param email String of email
     * @param lastname String of lastName
     * @return ResponseEntity of user.
     **/
    @PutMapping("/updatelastname/{email}/{lastname}")
    public ResponseEntity<User> updateUser(@PathVariable String email, @PathVariable String lastname){
        ResponseEntity<User> response;
        Optional<User> user = userService.findByEmail(email);

        if(user.isPresent()){
            user.get().setLastName(lastname);
            userService.update(user.get());
            response= new ResponseEntity<>(user.get(),HttpStatus.OK);
        }else{
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

}
