package com.project.edentifica.clienteRest;

import com.project.edentifica.model.User;
import daw.com.Pantalla;
import daw.com.Teclado;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

/**
 * Class Utilities, contains in static form the urlbase to connect and
 * functions to facilitate the search for users.
 *
 * Clase Utilidades, contiene de forma estatica la urlbase para conectarse
 * por http y funciones para facilitar la busqueda de usuarios
 */
public class UtilitiesClientRest {
    public final static String URLBASE="http://localhost:8080/edentifica";

    /**
     * Function to know if the user exists, there is an error, the user that arrives
     * has a different id than the one in the database.
     *
     * Funcion para saber si el usuario existe, hay un error, el usuario que llega tiene
     * un id diferente al de la base de datos.
     *
     * @return User Object
     */
    public static Optional<User> existUser(){
        String URLCONSULTAR = URLBASE + "/users/get";
        RestTemplate restTemplate= new RestTemplate();
        User response;
        Optional<User> user;

        String email = Teclado.leerString("\nCorreo del usuario a modificar?");

        // Construye la URL con el email como Query Parameter
        URI uri = UriComponentsBuilder.fromHttpUrl(URLCONSULTAR)
                .queryParam("email", email)
                .build()
                .toUri();

        try{
            response = restTemplate.getForObject(uri, User.class);
            Pantalla.escribirString("\n"+ response);
            user = Optional.of(response);
        }catch(HttpClientErrorException e){
            user = Optional.empty();
        };

        return user;

    }

}
