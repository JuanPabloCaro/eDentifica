package com.project.edentifica.clienteRest;

import com.project.edentifica.model.User;
import com.project.edentifica.model.dto.UserDto;
import daw.com.Pantalla;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Clase Main para consultar todos los usuarios dentro de la base de datos
 */

public class ConsultarUsuarios {
    public static void main(String[] args) {
        String URLGET = UtilitiesClientRest.URLBASE + "/users/getall";
        RestTemplate restTemplate= new RestTemplate();
        ResponseEntity<User[]> response;


        try{
            response = restTemplate.getForEntity(URLGET, User[].class);

            for(User u: response.getBody()){
                Pantalla.escribirString("\nuser:" + u);//example of id different
            }

        }catch(HttpClientErrorException e){
            Pantalla.escribirString("\nHubo un error al actualizar el usuario, detalle del error: " + e.getMessage());
        };
    }
}
