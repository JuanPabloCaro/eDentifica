package com.project.edentifica.modelo;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;

/**
 * Class that represents a mathematical challenge, it is composed of two integers and
 * an operation symbol (addition or multiplication), it also has a method to know
 * if the time has expired.
 *
 * Clase que representa un reto matematico, este se compone de dos numeros enteros y
 * un simbolo de operacion(suma o multiplicacion), tambien tiene un metodo para saber si
 * se ha cumplido el tiempo de vigencia.
 *
 * @version 1.0
 * @author Juan Pablo Caro Peñuela
 */

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Document(collection="mathematical_challenges")
public class MathematicalChallenge {

    private static final Duration VALIDITY = Duration.ofMinutes(2);

    @Id
    @EqualsAndHashCode.Include
    private ObjectId id;
    private String idUser;
    private Instant timeOfCreation;
    private int number1;
    private int number2;
    //only addition and multiplication, subtraction and division are discarded
    //for the reason of negative numbers and decimal numbers as a result.
    private String operation;

    /**
     * Mathematical challenge builder, generates random numbers and assigns them to number1 and number2,
     * also sets a random operation.
     *
     * Contructor del reto matematico, genera numeros aleatorios y los asigna a number1 y number2,
     * tambien establece una operacion aleatoria.
     */
    public MathematicalChallenge(String idUser){
        Random rand = new Random();
        this.idUser=idUser;
        this.timeOfCreation= Instant.now();
        this.number1 = rand.nextInt(9)+1;
        this.number2 = rand.nextInt(9)+1;
        this.operation = (rand.nextInt(2)+1)==1?"+":"*";
    }

    /**
     * Method to verify if the challenge is still valid.
     *
     * Método para verificar si el reto sigue vigente.
     *
     * @return boolean
     */
    public boolean isValid() {
        // Obtain the difference between the current time and the time of creation of the challenge.
        Duration elapsedTime = Duration.between(this.timeOfCreation, Instant.now());
        // Verify if the elapsed time is less than the term of the challenge.
        return elapsedTime.compareTo(VALIDITY) < 0;
    }

    //PASAR A UN SERVICIO.
//    /**
//     * Este metodo calcula el resultado de la operacion entre el num1 y num2
//     *
//     * @return un entero como resultado de la operacion o un 0 en caso de que no exista operacion.
//     */
//    public int calcularResultado(){ // pasar a un servicio.
//        return switch (this.operacion) {
//            case "+" -> this.num1 + this.num2;
//            case "-" -> this.num1 - this.num2;
//            default -> 0;
//        };
//    }

}
