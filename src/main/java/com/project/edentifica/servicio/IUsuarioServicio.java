package com.project.edentifica.servicio;

import com.project.edentifica.modelo.Usuario;
import org.bson.types.ObjectId;

import java.util.List;
import java.util.Optional;

/**
 * Interfaz con los metodos a implementar en el servicio.
 *
 * @version 1.0
 * @author Juan Pablo Caro Peñuela
 */
public interface IUsuarioServicio {

    public Optional<Usuario> insertar (Usuario usuario);
    public boolean update (Usuario usuario);
    public boolean delete (ObjectId id);
    public Optional<Usuario> findByCorreo(String correo);
    public Optional<Usuario> findByTelefono(String telefono);
    public long usuariosRegistrados();
    public List<Usuario> findAll();

}
