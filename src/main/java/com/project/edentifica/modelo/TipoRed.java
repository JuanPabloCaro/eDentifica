package com.project.edentifica.modelo;

import java.util.Arrays;

/**
 * Enumerado de las opciones de redes sociales, tambien tiene un metodo para
 * poder acceder al valor del enumerado mediante un string.
 *
 * @version 1.0
 * @author Juan Pablo Caro Peñuela
 */

public enum TipoRed {
    FACEBOOK,
    INSTAGRAM,
    TWITTER;

    /**
     * @Param tipoRed String que represente algun valor del enumerado (facebook,instagram,twitter)
     * @Return El enumerado que se haya seleccionado, o en su defecto si no exite devuelve FACEBOOK
     */
    public TipoRed obtenerTipoRed(String tipoRed){
        return Arrays.stream(TipoRed.values()).filter(t->t.toString().equalsIgnoreCase(tipoRed)).findFirst().orElse(FACEBOOK);
    }

}
