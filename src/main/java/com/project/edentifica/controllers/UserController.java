package com.project.edentifica.controllers;


import com.project.edentifica.model.*;
import com.project.edentifica.service.*;
import com.project.edentifica.model.dto.UserDto;
import daw.com.Pantalla;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("edentifica/users")
public class UserController {
    private final CallService callService;
    @Autowired
    private IUserService userService;
    @Autowired
    private IProfileService profileService;
    @Autowired
    public ISocialNetworkService socialNetworkService;
    @Autowired
    public IPhoneService phoneService;
    @Autowired
    public IEmailService emailService;
    @Autowired
    public IMathematicalChallengeService mathematicalChallengeService;


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
    @GetMapping("/get_all")
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> all = userService.findAll();
        return new ResponseEntity<>(all,HttpStatus.OK);
    }


    /**
     * @param user User Object to send call
     * @return Boolean
     */
    @PostMapping("/validation_one_call")
    public ResponseEntity<Boolean> toDoValidationOne(@RequestBody User user){
        ResponseEntity<Boolean> response;
        Optional<User> userFounded= userService.findById(user.getId());
        //Build mathematical challenge
        //Se construye el reto matematico
        MathematicalChallenge mathChallenge=new MathematicalChallenge(user.getId());

        //User must be present and validation_one equals false
        //El usuario que llegue al controlador debe de existir en la base de datos y la validacion_uno del usuario debe estar en false
//        if(userFounded.isPresent() && userFounded.get().getValidations().get(0).getValidated().equalsIgnoreCase("0")){
        if(userFounded.isPresent() && !userFounded.get().getValidations().get(0).getIsValidated()){
            //Se inserta el reto matematico en la base de datos
            //The mathematical challenge is inserted in the database.
            Optional<MathematicalChallenge> mathFounded= mathematicalChallengeService.insert(mathChallenge);

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



            response = new ResponseEntity<>(true,HttpStatus.CREATED);
        }else{
            response = new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        }
        Pantalla.escribirString("Se lanza la llamada" + response.getBody());
        return response;
    }
    private long generateUserId() {
        return (long) (Math.random() * Long.MAX_VALUE);
    }


    /**
     * @param answer int number
     * @param user User Object to check validation one
     * @return Boolean
     */
    @PostMapping("/answer_math_challenge")
    public ResponseEntity<Boolean> checkAnswer(@RequestParam int answer, @RequestBody User user){
        ResponseEntity<Boolean> response = new ResponseEntity<>(false, HttpStatus.OK);
        Optional<UserDto> userFound= userService.findDtoById(user.getId());

        if(userFound.isPresent()){
            // The user's last math challenge is searched for
            // Se busca el ultimo reto matematico del usuario
            Optional<MathematicalChallenge> mathFound = mathematicalChallengeService.findLatestChallengeByUserId(userFound.get().getId());

            // It is checked if the mathematical challenge is present and at the same time valid.
            // Se comprueba si el reto matematico esta presente y al mismo tiempo es valido
            if(mathFound.isPresent() && mathematicalChallengeService.isValid(mathFound.get())){
                // Challenge response is calculated
                // Se calcula la respuesta del reto
                int result = mathematicalChallengeService.calculateResult(mathFound.get());

                // If the user's answer is correct, the validation is updated.
                // Si la respuesta del usuario es correcta, se procede a actualizar la validacion
                if(result == answer){
                    //the validations are modified, in this case the user validation one is set to true.
                    //se modifican las validaciones, en este caso la validacion uno del usuario se pasa a true.
                    List<Validation> newValidations = user.getValidations();
                    Validation validationModify= newValidations.get(0);
//                    validationModify.setValidated("1");//1 is validated, 0 is not validated
                    validationModify.setIsValidated(true);
                    newValidations.set(0,validationModify);

                    user.setValidations(newValidations);
                    userService.update(user);

//                    response = new ResponseEntity<>(user.getValidations().get(0).getValidated() == "1", HttpStatus.OK);
                    response = new ResponseEntity<>(user.getValidations().get(0).getIsValidated(), HttpStatus.OK);
                }
            }
        }

        return response;
    }


    //SEARCH USER BY DATA PROFILE

    /**
     * @param type String representing the user's network type to be found.
     * @param socialname String representing the user's social name to be found.
     * @return User object
     */
    @GetMapping("/get_by_type_and_social_network/{type}/{socialname}")
    public ResponseEntity<User> getUserBySocialNetwork(@PathVariable String type, @PathVariable String socialname){
        NetworkType typeNet = NetworkType.getNetworkType(type);
        ResponseEntity<User> response;
        Optional<SocialNetwork> socialNetworkFound = socialNetworkService.findByTypeAndSocialName(typeNet, socialname);
        Optional<User> user;

        if(socialNetworkFound.isPresent()){
            user = userService.findBySocialNetworkProfile(socialNetworkFound.get());
            response = new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }

    /**
     * @param phonenumber String representing the user's phone number to be found.
     * @return User object
     */
    @GetMapping("/get_by_phone/{phonenumber}")
    public ResponseEntity<User> getUserByPhoneNumber(@PathVariable String phonenumber){
        ResponseEntity<User> response;
        Optional<Phone> phoneFound = phoneService.findByPhoneNumber(phonenumber);
        Optional<User> user;

        if(phoneFound.isPresent()){
            user = userService.findByPhoneProfile(phoneFound.get());
            response = new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        else{
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }


    /**
     * @param email String representing the user's email name to be found.
     * @return User object
     */
    @GetMapping("/get_by_email/{email}")
    public ResponseEntity<User> getUserByEmailName(@PathVariable String email){
        ResponseEntity<User> response;
        Optional<Email> emailFound = emailService.findByEmail(email);
        Optional<User> user;

        if(emailFound.isPresent()){
            user = userService.findByEmail(emailFound.get());
            response = new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        else{
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }


    //SEARCH USER DTO´S

    /**
     * @return List of all users
     */
    @GetMapping("/get_all_dto")
    public ResponseEntity<List<UserDto>> getAllUsersDto()
    {
        List<UserDto> all = userService.findAllDto();
        return new ResponseEntity<>(all,HttpStatus.OK);
    }

    /**
     * @param email String representing the user's email address to be found.
     * @return User object
     */
    @CrossOrigin(origins = "https://edentifica.com")
    @GetMapping("/get_dto_by_email")
    public ResponseEntity<UserDto> getUserDtoByEmail(@RequestParam("email") String email)
    {
        ResponseEntity<UserDto> response;
        Optional<UserDto> user = userService.findDtoByEmail(email);

        if(user.isPresent()){
            response= new ResponseEntity<>(user.get(),HttpStatus.OK);
        }else{
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

    /**
     * @param phonenumber String representing the user's phoneNumber to be found.
     * @return User object
     */
    @CrossOrigin(origins = "https://edentifica.com")
    @GetMapping("/get_dto_by_phone")
    public ResponseEntity<UserDto> getUserDtoByPhone(@RequestParam("phonenumber") String phonenumber)
    {
        ResponseEntity<UserDto> response;
        Optional<UserDto> user = userService.findDtoByPhone(phonenumber);

        if(user.isPresent()){
            response= new ResponseEntity<>(user.get(),HttpStatus.OK);
        }else{
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }


    /**
     * @param type String representing the user's network type to be found.
     * @param socialname String representing the user's social name to be found.
     * @return UserDto object
     */
    @CrossOrigin(origins = "https://edentifica.com")
    @GetMapping("/get_dto_by_type_and_social_network/{type}/{socialname}")
    public ResponseEntity<UserDto> getUserDtoBySocialNetwork(@PathVariable String type, @PathVariable String socialname){
        NetworkType typeNet = NetworkType.getNetworkType(type);
        ResponseEntity<UserDto> response;
        Optional<SocialNetwork> socialNetworkFound = socialNetworkService.findByTypeAndSocialName(typeNet, socialname);
        Optional<UserDto> user;

        if(socialNetworkFound.isPresent()){
            user = userService.findDtoBySocialNetworkProfile(socialNetworkFound.get());
            if(user.isPresent()){
                response = new ResponseEntity<>(user.get(), HttpStatus.OK);
            }else{
                response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }
        else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return response;
    }



    /**
     * @param id String representing the user's id to be found.
     * @return User object
     */
    @GetMapping("/get_dto_by_id")
    public ResponseEntity<UserDto> getUserDtoById(@RequestParam("id") String id)
    {
        ResponseEntity<UserDto> response;
        Optional<UserDto> user = userService.findDtoById(id);

        if(user.isPresent()){
            response= new ResponseEntity<>(user.get(),HttpStatus.OK);
        }else{
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

}
