package com.project.edentifica.servicio;

import com.project.edentifica.modelo.MathematicalChallenge;
import com.project.edentifica.repositorio.MathematicalChallengeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RetoMatematicoServicioImpl implements IRetoMatematicoServicio {

    @Autowired
    MathematicalChallengeRepository RetoMateDAO;

    /**
     * @return el reto matematico que se inserto en la base de datos
     */
    @Override
    public MathematicalChallenge insertarReto(MathematicalChallenge reto) {
        return RetoMateDAO.save(reto);
    }
}
