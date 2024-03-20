package com.project.edentifica.servicio;

import com.project.edentifica.modelo.Profile;
import com.project.edentifica.repositorio.ProfileRepository;
import com.project.edentifica.repositorio.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PerfilServicioImpl implements IPerfilServicio{

    //Se Inyecta el repositorio del perfil y del usuario
    @Autowired
    ProfileRepository perfilDAO;
    @Autowired
    UserRepository usuarioDAO;

    /**
     * SOLUCIONAR ERROR DE INSERCION, No reconoce el id del usuario
     *
     * @param profile objeto de tipo perfil para insertar
     * @return un optional de perfil si se inserta correctamente, el usuario debe existir para que se inserte.
     */
    @Override
    public Optional<Profile> insertar(Profile profile) {
        Optional<Profile> perfilInsertado=Optional.empty();

        if(usuarioDAO.existsById(profile.getUsuario().getId())){
            perfilInsertado= Optional.of(perfilDAO.save(profile));
        }

        return perfilInsertado;
    }


    /**
     * @param profile objeto de tipo perfil para actualizar
     * @return true si se actualizo correctamente, de lo contrario devuelve un false.
     */
    @Override
    public boolean update(Profile profile) {
        boolean exito = false;

        if(perfilDAO.existsById(profile.getId())){
            perfilDAO.save(profile);
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
