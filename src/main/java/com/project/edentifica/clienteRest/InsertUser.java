package com.project.edentifica.clienteRest;

import com.project.edentifica.model.*;
import daw.com.Pantalla;
import daw.com.Teclado;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Set;

public class InsertUser {
    public static void main(String[] args) {
        String URLGET = UtilitiesClientRest.URLBASE + "/users/insert";
        RestTemplate restTemplate= new RestTemplate();
        ResponseEntity<User> response;

        User u= new User();
        Email e= new Email();
        Phone phone=new Phone();
        Profile p = new Profile();




        String nombre = Teclado.leerString("nombre: ");
        u.setName(nombre);

        String correo = Teclado.leerString("correo: ");
        e.setEmail(correo);
        u.setEmail(e);

        String phoneNumber= Teclado.leerString("telefono?");
        phone.setPhoneNumber(phoneNumber);
        u.setPhone(phone);

        u.setProfile(p);


        try{
            response = restTemplate.postForEntity(URLGET, u,User.class);
            Pantalla.escribirString("\nuser inserted: "+ response.getBody());
        }catch(HttpClientErrorException ex){
            Pantalla.escribirString("\nHubo un error al inertar el usuario, detalle del error: " + ex.getMessage());
        };
    }

}
