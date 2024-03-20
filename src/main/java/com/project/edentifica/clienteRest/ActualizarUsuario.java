package com.project.edentifica.clienteRest;

import com.project.edentifica.modelo.User;
import daw.com.Pantalla;
import daw.com.Teclado;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

/**
 * Clase que permite actualizar un usuario por medio de su correo electronico
 */
public class ActualizarUsuario {
    public static void main(String[] args) {

        //comprobar si el usuario existe
        UtilidadesCliente.
                existeUsuario().
                ifPresentOrElse(ActualizarUsuario::actualizar,()-> Pantalla.escribirString("Usuario no existe"));

    }

    private static void actualizar(User user) {
        String URLACTUALIZAR = UtilidadesCliente.URLBASE + "/usuarios/actualizar";
        RestTemplate restTemplate= new RestTemplate();
        ResponseEntity<User> response;
        Optional<String> usuarioActualizado;

        user.setApellidos(Teclado.leerString("\nEscriba los apellidos actualizados:"));

        try{
            restTemplate.put(URLACTUALIZAR, User.class, user);
            usuarioActualizado = Optional.of("\nEl usuario con nombre: "+ user.getNombre() +" fue actualizado con exito!");
        }catch(HttpClientErrorException e){
            usuarioActualizado = Optional.of("\nHubo un error al actualizar el usuario, detalle del error: " + e.getMessage());
        };

        Pantalla.escribirString(usuarioActualizado.get());

    }
}
