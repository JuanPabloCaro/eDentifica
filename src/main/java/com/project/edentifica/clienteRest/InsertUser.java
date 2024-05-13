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
        SocialNetwork s = new SocialNetwork();
        SocialNetwork s1 = new SocialNetwork();

        Phone phone2 = new Phone();
        Phone phone3 = new Phone();
        phone2.setPhoneNumber("9989");
        phone3.setPhoneNumber("1121");

        s.setNetworkType(NetworkType.FACEBOOK);
        s.setProfileName("ivannn189");
        s1.setNetworkType(NetworkType.INSTAGRAM);
        s1.setProfileName("189ivannn");
        Set<SocialNetwork> redes = new HashSet<>();
        redes.add(s);
        redes.add(s1);
        p.setSocialNetworks(redes);

        String nombre = Teclado.leerString("nombre: ");
        u.setName(nombre);

        String correo = Teclado.leerString("correo: ");
        e.setEmail(correo);

        String phoneNumber= Teclado.leerString("telefono?");
        phone.setPhoneNumber(phoneNumber);


        Set<Phone> telefonos = new HashSet<>();
        telefonos.add(phone3);
        telefonos.add(phone2);
        p.setPhones(telefonos);


        u.setPhone(phone);
        u.setEmail(e);
        u.setPassword("123456");
        u.setProfile(p);


        try{
            response = restTemplate.postForEntity(URLGET, u,User.class);
            Pantalla.escribirString("\nuser inserted: "+ response.getBody());
        }catch(HttpClientErrorException ex){
            Pantalla.escribirString("\nHubo un error al actualizar el usuario, detalle del error: " + ex.getMessage());
        };
    }

}
