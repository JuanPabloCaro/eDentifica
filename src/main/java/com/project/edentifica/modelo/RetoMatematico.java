package com.project.edentifica.modelo;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Clase que representa un reto matematico, este se compone de dos numeros enteros y un simbolo
 * de operacion(suma o resta), tambien tiene un metodo que calcula el resultado de el numero1 +/- numero2
 *
 * @version 1.0
 * @author Juan Pablo Caro Peñuela
 */

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder

@Document(collection="retosMatematicos")
public class RetoMatematico {
    @Id
    @EqualsAndHashCode.Include
    private ObjectId id;
    private int num1;
    private int num2;
    private String operacion;

    /**
     * Este metodo calcula el resultado de la operacion entre el num1 y num2
     *
     * @return un entero como resultado de la operacion.
     */
    public int calcularResultado(){
        return switch (this.operacion) {
            case "+" -> this.num1 + this.num2;
            case "-" -> this.num1 - this.num2;
            default -> this.num1 + this.num2;
        };
    }

}
