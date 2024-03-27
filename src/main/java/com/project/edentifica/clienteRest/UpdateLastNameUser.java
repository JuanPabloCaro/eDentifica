package com.project.edentifica.clienteRest;

import com.project.edentifica.model.User;
import daw.com.Pantalla;
import daw.com.Teclado;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

/**
 * Clase que permite actualizar un usuario por medio de su correo electronico
 */
public class UpdateLastNameUser {
    public static void main(String[] args) {

        String URLCONSULTAR = UtilitiesClientRest.URLBASE + "/users/updatelastname/{email}/{lastname}";
        RestTemplate restTemplate= new RestTemplate();

        String email = Teclado.leerString("\nCorreo del usuario a modificar?");
        String lastName = Teclado.leerString("\nApellidos modificados?");

        try{
            restTemplate.put(URLCONSULTAR,null,email,lastName);
            Pantalla.escribirString("\n usuario actualizado con exito");
        }catch(HttpClientErrorException e){
            Pantalla.escribirString("\n No se pudo actualizar el usuario: " + e.getMessage());
        };

    }

}
