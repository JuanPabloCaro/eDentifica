package com.project.edentifica.controlador;


import com.project.edentifica.modelo.User;
import com.project.edentifica.servicio.IUsuarioServicio;
import daw.com.Pantalla;
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
    public ResponseEntity<List<User>> obtenerTodosUsuarios()
    {
        List<User> todos = usuarioServicio.findAll();
        return new ResponseEntity<>(todos,HttpStatus.OK);
    }


    @GetMapping("/consultar/{correo}")
    public ResponseEntity<User> obtenerUsuarioPorCorreo(@PathVariable String correo)
    {
        ResponseEntity<User> response;
        Optional<User> usuario = usuarioServicio.findByCorreo(correo);

        if(usuario.isPresent()){
            response= new ResponseEntity<>(usuario.get(),HttpStatus.OK);
        }else{
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }



    /**
     * @param telefono String que representa el telefono del usuario a consultar
     * @return String con el nombre del usuario encontrado.
     */
    @GetMapping("/consultarNombre/{telefono}")
    public ResponseEntity<String> obtenerNombreUsuarioPorTelefono(@PathVariable String telefono)
    {
        String nombre;
        ResponseEntity<String> response;
        Optional<User> usuario = usuarioServicio.findByTelefono(telefono);

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
     * @param users Objeto usuario a insertar en la base de datos
     * @return el usuario para poder obtener su id asignado
     */
    @PostMapping("/insertar")
    public ResponseEntity<User> insertarUsuario(@RequestBody User users)
    {
        ResponseEntity<User> response;

        Optional<User> user=usuarioServicio.insertar(users);

        if(user.isPresent()){
            response = new ResponseEntity<>(user.get(),HttpStatus.CREATED);
        }else{
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return response;
    }

    @PutMapping("/actualizar")
    public ResponseEntity<User> actualizarUsuario(@RequestBody User user){
        ResponseEntity<User> response;

//        //estas lineas son necesarias para encontrar el id, primero hay que pasarlo
//        // a hexadecimal y despues crear un objeto del id con al hexadecimal obtenido,
//        // de lo contrario es un Not Found
//        ObjectId id = usuario.getId();
//        Pantalla.escribirString(id.toString());
//        Pantalla.escribirString("\n65f894059e7f2036df32c094");
//        ObjectId idABuscar= new ObjectId("\n65f894059e7f2036df32c094"); // id de camila

        // me toca buscar por correo para actualizar el usuario,
        // ya que el id que llega de usuario como parametro es distinto al de la base de datos
        if(usuarioServicio.update(user)){
            Pantalla.escribirString("entra");
            response = new ResponseEntity<>(user,HttpStatus.OK);
        }else{
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return response;
    }



}
