package com.project.edentifica.servicio;

import com.project.edentifica.modelo.RetoMatematico;
import com.project.edentifica.repositorio.RetoMatematicoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RetoMatematicoServicioImpl implements IRetoMatematicoServicio {

    @Autowired
    RetoMatematicoRepositorio RetoMateDAO;

    /**
     * @return el reto matematico que se inserto en la base de datos
     */
    @Override
    public RetoMatematico insertarReto(RetoMatematico reto) {
        return RetoMateDAO.insert(reto);
    }
}
