package com.project.edentifica.modelo;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Random;

/**
 * Clase que representa un reto matematico, este se compone de dos numeros enteros y un simbolo
 * de operacion(suma o resta), tambien tiene un metodo que calcula el resultado de el numero1 +/- numero2
 *
 * @version 1.0
 * @author Juan Pablo Caro Peñuela
 */

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Document(collection="retosMatematicos")
public class RetoMatematico {
    @Id
    @EqualsAndHashCode.Include
    private ObjectId id;
    private int num1;
    private int num2;
    private String operacion;

    /**
     * Contructor del reto matematico, genera numeros aleatorios y los asigna a num1 y num2,
     * tambien establece una operacion aleatoria
     *
     */
    public RetoMatematico(){
        Random rand = new Random();
        this.num1 = rand.nextInt(9)+1;
        this.num2 = rand.nextInt(9)+1;
        this.operacion = (rand.nextInt(2)+1)==1?"+":"-";
    }

    /**
     * Este metodo calcula el resultado de la operacion entre el num1 y num2
     *
     * @return un entero como resultado de la operacion o un 0 en caso de que no exista operacion.
     */
    public int calcularResultado(){
        return switch (this.operacion) {
            case "+" -> this.num1 + this.num2;
            case "-" -> this.num1 - this.num2;
            default -> 0;
        };
    }

}
