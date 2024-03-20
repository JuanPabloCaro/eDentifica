package com.project.edentifica.controlador;

import com.project.edentifica.modelo.*;
import com.project.edentifica.servicio.IPerfilServicio;
import com.project.edentifica.servicio.IRetoMatematicoServicio;
import com.project.edentifica.servicio.ITelefonoRegistroServicio;
import com.project.edentifica.servicio.IUsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.HashSet;

@RestController
@RequestMapping("edentifica/utilidades")
public class UtilidadesControlador {
    //Inyecto los servicios
    @Autowired
    IPerfilServicio perfilServicio;
    @Autowired
    ITelefonoRegistroServicio telefonoRegistroServicio;
    @Autowired
    IUsuarioServicio usuarioServicio;
    @Autowired
    IRetoMatematicoServicio retoMatematicoServicio;

    private static boolean datosCargados=false;

    /**
     * Funcion para cargar los datos de prueba en la base de datos
     * @return String con la informacion correspondiente.
     */
    @GetMapping("/cargardatos")
    public ResponseEntity<String> cargarDatos(){
        ResponseEntity<String> response;
        TelefonoRegistro t1,t2,t3;
        User u1,u2,u3;
        Profile p1,p2,p3;

        if(!datosCargados){
            datosCargados=true;

            //ORDEN PARA INSERTAR DATOS:
            // 1.Insertar telefonos
            MathematicalChallenge reto1=new MathematicalChallenge();
            MathematicalChallenge reto2=new MathematicalChallenge();
            MathematicalChallenge reto3=new MathematicalChallenge();

            retoMatematicoServicio.insertarReto(reto1);
            retoMatematicoServicio.insertarReto(reto2);
            retoMatematicoServicio.insertarReto(reto3);

            t1 =TelefonoRegistro.builder().retoMate(reto1).numTelefono("+34628296047").build();
            t2 =TelefonoRegistro.builder().retoMate(reto2).numTelefono("+34639647389").build();
            t3 =TelefonoRegistro.builder().retoMate(reto3).numTelefono("+34655783748").build();

            telefonoRegistroServicio.insertarTel(t1);
            telefonoRegistroServicio.insertarTel(t2);
            telefonoRegistroServicio.insertarTel(t3);

            // 2.Insertar Usuarios
            u1= User.builder().
                    correo("Juan@juan.com").
                    telefono(t1).
                    nombre("Juan").
                    apellidos("caro").
                    fechaNacimiento(LocalDate.of(1999,6,7)).
                    password("123456").
                    build();

            u2= User.builder().
                    correo("pepe@pepe.com").
                    telefono(t2).
                    nombre("pepe").
                    apellidos("perez").
                    fechaNacimiento(LocalDate.of(2004,12,7)).
                    password("654321").
                    build();

            u3= User.builder().
                    correo("camila@camila.com").
                    telefono(t3).
                    nombre("camila").
                    apellidos("caro").
                    fechaNacimiento(LocalDate.of(1997,11,30)).
                    password("987654321").
                    build();

            usuarioServicio.insertar(u1);
            usuarioServicio.insertar(u2);
            usuarioServicio.insertar(u3);

            //3. Insertar Perfiles

            //correos
            HashSet<String> correosU1= new HashSet<>();
            HashSet<String> correosU2= new HashSet<>();
            HashSet<String> correosU3= new HashSet<>();

            correosU1.add("correo1@correo1.com");
            correosU1.add("correo2@correo2.com");
            correosU3.add("unico@unico.com");

            //redes sociales
            SocialNetwork r1= new SocialNetwork();
            SocialNetwork r2= new SocialNetwork();
            SocialNetwork r3= new SocialNetwork();

            r1.setTipoRed(NetworkType.FACEBOOK);
            r1.setNombrePerfil("/juancaro");

            r2.setTipoRed(NetworkType.TWITTER);
            r2.setNombrePerfil("/pepe");

            r3.setTipoRed(NetworkType.INSTAGRAM);
            r3.setNombrePerfil("/juanpa");

            HashSet<SocialNetwork> redesU1= new HashSet<>();
            HashSet<SocialNetwork> redesU2= new HashSet<>();
            HashSet<SocialNetwork> redesU3= new HashSet<>();

            redesU1.add(r1);
            redesU1.add(r3);
            redesU2.add(r2);

            //telefonos
            HashSet<String> telefonosU1= new HashSet<>();
            HashSet<String> telefonosU2= new HashSet<>();
            HashSet<String> telefonosU3= new HashSet<>();

            telefonosU1.add(t1.getNumTelefono());
            telefonosU1.add("+573125149467");
            telefonosU2.add(t2.getNumTelefono());
            telefonosU3.add(t3.getNumTelefono());

            //insertar por ultimo los perfiles
            p1 = Profile.builder().
                    correos(correosU1).
                    usuario(u1).
                    descripcion("Descripcion del usuario 1").
                    redesSociales(redesU1).
                    telefonos(telefonosU1).
                    build();

            p2 = Profile.builder().
                    correos(correosU2).
                    usuario(u2).
                    descripcion("Descripcion del usuario 2").
                    redesSociales(redesU2).
                    telefonos(telefonosU2).
                    build();

            p3 = Profile.builder().
                    correos(correosU3).
                    usuario(u3).
                    descripcion("Descripcion del usuario 3").
                    redesSociales(redesU3).
                    telefonos(telefonosU3).
                    build();

            perfilServicio.insertar(p1);
            perfilServicio.insertar(p2);
            perfilServicio.insertar(p3);


            response = new ResponseEntity<>("Datos cargados correctamente", HttpStatus.OK);
        }else{
            response = new ResponseEntity<>("Los datos se han cargado anteriormente", HttpStatus.OK);
        }




        return response;

    }



}
