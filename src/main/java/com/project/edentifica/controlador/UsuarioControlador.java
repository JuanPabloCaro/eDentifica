package com.project.edentifica.controlador;


import com.project.edentifica.modelo.Usuario;
import com.project.edentifica.servicio.IUsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("edentifica/usuarios")
public class UsuarioControlador {

    /**
     * Inyecto los servicios para hacer las consultas.
     */
    @Autowired
    private IUsuarioServicio usuarioServicio;


    /**
     * @return una lista de todos los usuarios
     */
    @GetMapping("/consultar")
    public ResponseEntity<List<Usuario>> obtenerTodosUsuarios()
    {
        List<Usuario> todos = usuarioServicio.findAll();
        return new ResponseEntity<>(todos,HttpStatus.OK);
    }

    /**
     * @param telefono String que representa el telefono del usuario a consultar
     * @return String con el nombre del usuario encontrado.
     */
    @GetMapping("/consultarTel/{telefono}")
    public ResponseEntity<String> obtenerNombreUsuarioPorTelefono(@PathVariable String telefono)
    {
        String nombre;
        ResponseEntity<String> response;
        Optional<Usuario> usuario = usuarioServicio.findByTelefono(telefono);

        if(usuario.isPresent()){
            nombre=usuario.get().getNombre();
            response= new ResponseEntity<>(nombre,HttpStatus.OK);
        }else{
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

    /**
     *
     * @param password String de la contraseña de la cual se quiere obtener el id del usuario.
     * @return ObjectId del usuario encontrado, de lo contrario devuelve un not found.
     */
    @GetMapping("/consultaridporpassword")
    public ResponseEntity<String> consultarPorPassword(@RequestParam("password") String password){
        ResponseEntity<String> response;

        Optional<String> id= usuarioServicio.findByPassword(password);

        if(id.isPresent()){
            response = new ResponseEntity<>(id.get(),HttpStatus.OK);
        }else{
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
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

    @PutMapping("/actualizar")
    public ResponseEntity<Usuario> actualizarUsuarioPorId(@RequestBody Usuario usuario){
        ResponseEntity<Usuario> response;
        Usuario usuarioActualizado;

        if(usuarioServicio.findById(usuario.getId()).isPresent()){
            if(usuarioServicio.update(usuario)){
                usuarioActualizado= usuarioServicio.findById(usuario.getId()).get();
                response = new ResponseEntity<>(usuarioActualizado,HttpStatus.OK);
            }else{
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

        }else{
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }



}
