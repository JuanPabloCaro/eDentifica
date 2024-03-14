package com.project.edentifica.controlador;

import com.project.edentifica.modelo.Usuario;
import com.project.edentifica.servicio.IUsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Clase que representa el controlador del usuario, desde aqui llamaremos a todos los servicios del usuario.
 *
 * @version 1.0
 * @author Juan Pablo Caro Peñuela
 */

@RestController
@RequestMapping("edentifica/usuario")
public class UsuarioControlador {

    /**
     * Inyecto los servicios para hacer las consultas.
     */
    @Autowired
    private IUsuarioServicio usuarioServicio;


    @GetMapping("/consultar")
    public ResponseEntity<List<Usuario>> obtenerTodosUsuarios()
    {
        List<Usuario> todos = usuarioServicio.findAll();
        return new ResponseEntity<>(todos,HttpStatus.OK);
    }



    /**
     * @param usuario Objeto usuario a insertar en la base de datos
     * @return el usuario para poder obtener su id asignado
     */
    @PostMapping("/insertar")
    public ResponseEntity<Usuario> insertarUsuario(@RequestBody Usuario usuario)
    {
        Optional<Usuario> usuarioInsertado;
        ResponseEntity<Usuario> response;

        usuarioInsertado=usuarioServicio.insertar(usuario);

        if(usuarioInsertado.isPresent()){
            response = new ResponseEntity<>(usuarioInsertado.get(),HttpStatus.CREATED);
        }else{
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return response;
    }
}
