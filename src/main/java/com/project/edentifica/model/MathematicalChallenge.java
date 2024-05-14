package com.project.edentifica.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
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
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

@Document(collection="mathematical_challenges")
public class MathematicalChallenge {
    @Id
    @EqualsAndHashCode.Include
    private String id;
    @NonNull
    private String idUser;
    @NonNull
    private Instant timeOfCreation;
    private int number1;
    private int number2;
    //only addition and multiplication, subtraction and division are discarded
    //for the reason of negative numbers and decimal numbers as a result.
    private String operation;


    //HashMap para almacenar los numeros y poder acceder a ellos como palabras
    //HashMap to store numbers and access them as words.
    private static final Map<Integer, String> numberMap = new HashMap<>();
    static {
        numberMap.put(1, "uno");
        numberMap.put(2, "dos");
        numberMap.put(3, "tres");
        numberMap.put(4, "cuatro");
        numberMap.put(5, "cinco");
        numberMap.put(6, "seis");
        numberMap.put(7, "siete");
        numberMap.put(8, "ocho");
        numberMap.put(9, "nueve");
    }


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
        //I keep the creation time in UTC+0 time zone, so that it is the same no matter where the user is located.
        //Mantengo la hora de creación en la zona horaria UTC+0, para que sea la misma independientemente de dónde se encuentre el usuario.
        this.timeOfCreation= Instant.now();
        this.number1 = rand.nextInt(9)+1;
        this.number2 = rand.nextInt(9)+1;
        this.operation = (rand.nextInt(4)+1)%2==0?"*":"+";
    }

    public String getNumber1AsWord() {
        return convertNumberToWord(number1);
    }

    public String getNumber2AsWord() {
        return convertNumberToWord(number2);
    }

    public String getOperationAsWord(){
        return switch (this.operation) {
            case "+" -> "mas";
            case "*" -> "por";
            default -> "";
        };
    }

    private String convertNumberToWord(int number) {
        return numberMap.getOrDefault(number, "número no válido");
    }
}
