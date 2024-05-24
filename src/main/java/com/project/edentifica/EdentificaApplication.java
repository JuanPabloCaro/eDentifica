package com.project.edentifica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Proyecto eDentifica:
 * Estandarizar la autenticación de personas a nivel global. eDentifica dará fe de que el
 * usuario de la aplicación es realmente quien dice ser, de tal forma que todos los mensaje (correo,
 * perfil RRSS, mensaje por WhatsApp / Telegram, etc.) firmado a través de eDentifica
 * tendrá el aval y la validez que se dará de una forma inequívoca.
 * Gracias a eDentifica se limitará el anonimato virtual, se invitará a que nadie acepte
 * contenido que no esté con perfil de eDentifica.
 *
 * @version 1.0
 * @author Juan Pablo Caro Peñuela
 * @since 2024-03-8
 */
//ToDo
// implementar validacion de token en todos los servicios
@SpringBootApplication
public class EdentificaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdentificaApplication.class, args);
	}

}
