package com.project.edentifica.clienteRest;

import com.project.edentifica.modelo.Usuario;
import daw.com.Pantalla;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

/**
 * Clase Main para consultar todos los usuarios dentro de la base de datos
 */

public class ConsultarUsuarios {
    public static void main(String[] args) {
        String URLCONSULTAR = UtilidadesCliente.URLBASE + "/usuarios/consultar";
        RestTemplate restTemplate= new RestTemplate();
        ResponseEntity<Usuario[]> response;


        try{
            response = restTemplate.getForEntity(URLCONSULTAR,Usuario[].class);

            for(Usuario u: response.getBody()){
                Pantalla.escribirString("\nuser:" + u);
            }

        }catch(HttpClientErrorException e){
            Pantalla.escribirString("\nHubo un error al actualizar el usuario, detalle del error: " + e.getMessage());
        };
    }
}
