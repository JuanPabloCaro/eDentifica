package com.project.edentifica.clienteRest;

import com.project.edentifica.modelo.Usuario;
import daw.com.Teclado;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

/**
 * Clase Utilidades, contiene de forma estatica la urlbase para conectarse por http y funciones para facilitar la busqueda de usuarios
 */
public class UtilidadesCliente {
    public final static String URLBASE="http://localhost:8080/edentifica";

    //funciones
    public static Optional<Usuario> existeUsuario(){
        String URLCONSULTAR = URLBASE + "/usuarios/consultar/{correo}";
        RestTemplate restTemplate= new RestTemplate();
        ResponseEntity<Usuario> response;
        Optional<Usuario> usuario;

        String correo = Teclado.leerString("\nCorreo del usuario a modificar?");

        try{
            response = restTemplate.getForEntity(URLCONSULTAR, Usuario.class,correo);
            usuario = Optional.of(response.getBody());
        }catch(HttpClientErrorException e){
            usuario = Optional.empty();
        };

        return usuario;

    }

}
