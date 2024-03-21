package com.project.edentifica.model;

import java.util.Arrays;

/**
 * Enumeration of the social network options, it also has a method to access
 * the value of the enumeration through a string.
 *
 * Enumerado de las opciones de redes sociales, tambien tiene un metodo para
 * poder acceder al valor del enumerado mediante un string.
 *
 * @version 1.0
 * @author Juan Pablo Caro Peñuela
 */

public enum NetworkType {
    FACEBOOK,
    INSTAGRAM,
    TWITTER;

    /**
     * @Param networkType String representing some value of the enumerated (facebook,instagram,twitter) that you want to get.
     * @Return The selected enumeration, or if not available, FACEBOOK is returned
     */
    public NetworkType getNetworkType(String networkType){
        return Arrays.stream(NetworkType.values()).
                filter(t->t.toString().equalsIgnoreCase(networkType)).
                findFirst().
                orElse(FACEBOOK);
    }

}
