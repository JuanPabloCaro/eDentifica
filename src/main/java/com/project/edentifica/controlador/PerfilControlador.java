package com.project.edentifica.controlador;

import com.project.edentifica.modelo.Perfil;
import com.project.edentifica.servicio.IPerfilServicio;
import com.project.edentifica.servicio.IUsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;


/**
 * Clase que representa el controlador del perfil, desde aqui llamaremos a todos los servicios del perfil.
 *
 * @version 1.0
 * @author Juan Pablo Caro Peñuela
 */

@RestController
@RequestMapping("edentifica/perfiles")
public class PerfilControlador {

    @Autowired
    IPerfilServicio perfilServicio;


    @PostMapping("/insertar")
    public ResponseEntity<Optional<Perfil>> insertarPerfil(@PathVariable Perfil perfil){
        ResponseEntity<Optional<Perfil>> response;
        Optional<Perfil> perfilInsertado=perfilServicio.insertar(perfil);

        if(perfilInsertado.isPresent()){
            response = new ResponseEntity<>(perfilInsertado, HttpStatus.OK);
        }else{
            response  = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return response;
    }


}
