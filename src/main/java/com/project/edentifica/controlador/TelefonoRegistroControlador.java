package com.project.edentifica.controlador;

import com.project.edentifica.modelo.RetoMatematico;
import com.project.edentifica.modelo.TelefonoRegistro;
import com.project.edentifica.servicio.IRetoMatematicoServicio;
import com.project.edentifica.servicio.ITelefonoRegistroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("edentifica/telefonos")
public class TelefonoRegistroControlador {
    /**
     * Inyecto el servicio de telefono y retoMatematico
     */
    @Autowired
    private ITelefonoRegistroServicio telefonoRegistroServicio;
    @Autowired
    private IRetoMatematicoServicio retoMatematicoServicio;

    /**
     * @param tel objeto de telefono que voy a insertar
     * @return un objeto TelefonoRegistro que se inserto en la base de datos correctamente
     */
    @PostMapping("/insertar")
    public ResponseEntity<TelefonoRegistro> insertarTelefono(@RequestBody TelefonoRegistro tel)
    {
        Optional<TelefonoRegistro> telInsertado;
        ResponseEntity<TelefonoRegistro> response;

        //asigno un reto matematico al telefono de registro antes de insertar el telefono en la base de datos
        RetoMatematico reto = retoMatematicoServicio.insertarReto(new RetoMatematico());
        tel.setRetoMate(reto);

        //inserto el telefono en la base de datos
        telInsertado=telefonoRegistroServicio.insertarTel(tel);

        if(telInsertado.isPresent()){
            response = new ResponseEntity<>(telInsertado.get(), HttpStatus.CREATED);
        }else{
            response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return response;
    }
}
