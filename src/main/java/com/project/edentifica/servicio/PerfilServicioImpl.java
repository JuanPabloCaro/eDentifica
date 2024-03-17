package com.project.edentifica.servicio;

import com.project.edentifica.modelo.Perfil;
import com.project.edentifica.repositorio.PerfilRepositorio;
import com.project.edentifica.repositorio.UsuarioRepositorio;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PerfilServicioImpl implements IPerfilServicio{

    //Se Inyecta el repositorio del perfil y del usuario
    @Autowired
    PerfilRepositorio perfilDAO;
    UsuarioRepositorio usuarioDAO;

    /**
     * @param perfil objeto de tipo perfil para insertar
     * @return un optional de perfil si se inserta correctamente, el usuario debe existir para que se inserte.
     */
    @Override
    public Optional<Perfil> insertar(Perfil perfil) {
        Optional<Perfil> perfilInsertado=Optional.empty();

        if(usuarioDAO.existsById(perfil.getUsuario().getId())){
            perfilInsertado= Optional.of(perfilDAO.insert(perfil));
        }

        return perfilInsertado;
    }


    /**
     * @param perfil objeto de tipo perfil para actualizar
     * @return true si se actualizo correctamente, de lo contrario devuelve un false.
     */
    @Override
    public boolean update(Perfil perfil) {
        boolean exito = false;

        if(perfilDAO.existsById(perfil.getId())){
            perfilDAO.save(perfil);
            exito = true;
        }

        return exito;
    }


    /**
     *
     * @param id string que representa el identificador del perfil que quiere eliminar
     * @return true si elimino el perfil correctamente, de lo contrario devuelve un false
     */
    @Override
    public boolean delete(ObjectId id) {
        boolean exito = false;

        if(perfilDAO.existsById(id)){
            perfilDAO.deleteById(id);
            exito = true;
        }

        return exito;
    }
}
