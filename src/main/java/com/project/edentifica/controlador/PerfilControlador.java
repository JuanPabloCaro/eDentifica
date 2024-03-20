package com.project.edentifica.controlador;

import com.project.edentifica.modelo.Profile;
import com.project.edentifica.servicio.IPerfilServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("edentifica/perfiles")
public class PerfilControlador {

    @Autowired
    IPerfilServicio perfilServicio;

    /**
     *
     * @param profile objeto de tipo perfil a insertar
     * @return Optional del perfil insertado, de lo contrario devuelve un optional vacio.
     */
    @PostMapping("/insertar")
    public ResponseEntity<Optional<Profile>> insertarPerfil(@RequestBody Profile profile){
        ResponseEntity<Optional<Profile>> response;
        Optional<Profile> perfilInsertado=perfilServicio.insertar(profile);

        if(perfilInsertado.isPresent()){
            response = new ResponseEntity<>(perfilInsertado, HttpStatus.OK);
        }else{
            response  = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return response;
    }


}
