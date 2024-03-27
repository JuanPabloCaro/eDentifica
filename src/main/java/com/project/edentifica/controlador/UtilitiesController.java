package com.project.edentifica.controlador;

import com.project.edentifica.model.*;
import com.project.edentifica.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashSet;

@RestController
@RequestMapping("edentifica/utilities")
public class UtilitiesController {
    @Autowired
    IProfileService profileService;
    @Autowired
    IPhoneService phoneService;
    @Autowired
    IUserService userService;
    @Autowired
    IEmailService emailService;
    @Autowired
    ISocialNetworkService socialNetworkService;

    private static boolean uploadedData =false;

    /**
     * Function to load the test data into the database.
     *
     * Función para cargar los datos de prueba en la base de datos.
     *
     * @return String
     */
    @GetMapping("/loaddata")
    public ResponseEntity<String> loadData(){
        ResponseEntity<String> response;
        Phone t1,t2,t3;
        Profile p1,p2,p3;
        User u1,u2,u3;


        if(!uploadedData){
            uploadedData =true;

            //ORDER TO INSERT DATA:
            // 1.Insert phones
            t1 =Phone.builder().phoneNumber("+34628296047").build();
            t2 =Phone.builder().phoneNumber("+34639647389").build();
            t3 =Phone.builder().phoneNumber("+34655783748").build();

            phoneService.insert(t1);
            phoneService.insert(t2);
            phoneService.insert(t3);


            //2. Insert profiles
            //emails
            HashSet<Email> emailU1= new HashSet<>();
            HashSet<Email> emailU2= new HashSet<>();
            HashSet<Email> emailU3= new HashSet<>();

            //Social networks
            SocialNetwork s1= new SocialNetwork();
            SocialNetwork s2= new SocialNetwork();
            SocialNetwork s3= new SocialNetwork();

            s1.setNetworkType(NetworkType.FACEBOOK);
            s1.setProfileName("/juancaro");

            s2.setNetworkType(NetworkType.TWITTER);
            s2.setProfileName("/pepe");

            s3.setNetworkType(NetworkType.INSTAGRAM);
            s3.setProfileName("/juanpa");


            socialNetworkService.insert(s1);
            socialNetworkService.insert(s2);
            socialNetworkService.insert(s3);


            HashSet<SocialNetwork> socialU1= new HashSet<>();
            HashSet<SocialNetwork> socialU2= new HashSet<>();
            HashSet<SocialNetwork> socialU3= new HashSet<>();

            socialU1.add(s1);
            socialU1.add(s3);
            socialU2.add(s2);

            //Phones
            HashSet<Phone> phoneU1= new HashSet<>();
            HashSet<Phone> phoneU2= new HashSet<>();
            HashSet<Phone> phoneU3= new HashSet<>();

            phoneU1.add(t1);
            phoneU2.add(t2);
            phoneU3.add(t3);

            //build and insert the profiles.
            p1 = Profile.builder().
                    emails(emailU1).
                    description("Descripcion del usuario 1").
                    socialNetworks(socialU1).
                    phones(phoneU1).
                    build();

            p2 = Profile.builder().
                    emails(emailU2).
                    description("Descripcion del usuario 2").
                    socialNetworks(socialU2).
                    phones(phoneU2).
                    build();

            p3 = Profile.builder().
                    emails(emailU3).
                    description("Descripcion del usuario 3").
                    socialNetworks(socialU3).
                    phones(phoneU3).
                    build();

            profileService.insert(p1);
            profileService.insert(p2);
            profileService.insert(p3);






            //3.Insert Users
            //email
            Email e1 = new Email();
            Email e2 = new Email();
            Email e3 = new Email();

            e1.setEmail("jcaropenuela@gmail.com");
            e2.setEmail("pepe@gmail.com");
            e3.setEmail("camila@gmail.com");

            emailService.insert(e1);
            emailService.insert(e2);
            emailService.insert(e3);


            u1= User.builder().
                    email(e1).
                    phone(t1).
                    name("Juan").
                    lastName("caro").
                    dateBirth(LocalDate.of(1999,6,7)).
                    password("123456").
                    profile(p1).
                    build();

            u2= User.builder().
                    email(e2).
                    phone(t2).
                    name("pepe").
                    lastName("perez").
                    dateBirth(LocalDate.of(2004,12,7)).
                    password("654321").
                    profile(p2).
                    build();

            u3= User.builder().
                    email(e3).
                    phone(t3).
                    name("camila").
                    lastName("caro").
                    dateBirth(LocalDate.of(1997,11,30)).
                    password("987654321").
                    profile(p3).
                    build();

            userService.insert(u1);
            userService.insert(u2);
            userService.insert(u3);


            response = new ResponseEntity<>("Data uploaded correctly", HttpStatus.OK);
        }else{
            response = new ResponseEntity<>("The data have already been uploaded previously", HttpStatus.OK);
        }

        return response;

    }

}
