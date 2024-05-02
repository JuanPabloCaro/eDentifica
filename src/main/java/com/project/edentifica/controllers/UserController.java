package com.project.edentifica.controllers;


import com.project.edentifica.model.MathematicalChallenge;
import com.project.edentifica.model.Profile;
import com.project.edentifica.model.User;
import com.project.edentifica.model.Validation;
import com.project.edentifica.service.CallService;
import com.project.edentifica.service.IMathematicalChallengeService;
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
    private final CallService callService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IProfileService profileService;
    @Autowired
    private IMathematicalChallengeService mathematicalChallengeService;
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

    @PostMapping("/validation_one")
    public ResponseEntity<Boolean> toDoValidationOne(@RequestBody User user){
        ResponseEntity<Boolean> response;
        Optional<User> userFounded= userService.findById(user.getId());
        //Build mathematical challenge and insert into data base
        //Se construye el reto matematico y se inserta en la base de datos
        MathematicalChallenge mathChallenge=new MathematicalChallenge(user.getId());
        mathematicalChallengeService.insert(mathChallenge);

        //User must be present and validation_one equals false
        //El usuario que llegue al controlador debe de existir en la base de datos y la validacion_uno del usuario debe estar en false
        if(userFounded.isPresent() && !userFounded.get().getValidations().get(0).isValidated()){
            Optional<MathematicalChallenge> mathFounded= mathematicalChallengeService.findByIdUser(user.getId());

            //Mathematical Challenge must be present and must be valid, then we can send call.
            //El reto matematico debe de existir en la base de datos y debe de ser valido por su tiempo de vigencia, despues podemos hacer la llamada.
            if (mathFounded.isPresent() && mathematicalChallengeService.isValid(mathFounded.get())){
                //send call
                //Hacemos la llamada
                String text = "Hola, te llama edentifica. Tu debes de ser " + userFounded.get().getName() + ". Por favor digita la respuesta de la siguiente operacion matematica: ." + mathFounded.get().getNumber1AsWord() + " " + mathFounded.get().getOperationAsWord() + " " + mathFounded.get().getNumber2AsWord();
                int copies = 1;
                String audioLanguage = "default";
                long userId = generateUserId(); // Generar ID de usuario aleatorio
                String phone = userFounded.get().getPhone().getPhoneNumber(); // Obtener el número de teléfono del request
                callService.sendCall(text,copies,audioLanguage,userId,phone);
            }


            //the validations are modified, in this case the user validation one is set to true.
            //se modifican las validaciones, en este caso la validacion uno del usuario se pasa a true.
            List<Validation> newValidations=userFounded.get().getValidations();
            Validation validationModify= newValidations.get(0);
            validationModify.setValidated(true);
            newValidations.set(0,validationModify);

            userFounded.get().setValidations(newValidations);
            userService.update(userFounded.get());

            response = new ResponseEntity<>(true,HttpStatus.CREATED);
        }else{
            response = new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        }

        return response;
    }


    private long generateUserId() {
        return (long) (Math.random() * Long.MAX_VALUE);
    }

}
