-----------------------------------------------------------------------------------------
# eDentifica Backend
-----------------------------------------------------------------------------------------
eDentifica es una aplicación diseñada para que las personas puedan registrar sus correos, teléfonos y redes sociales, contribuyendo así a evitar futuros fraudes o estafas. 
La validación de datos se realiza a través de una llamada con un reto matemático o mediante la captura de una foto del rostro, asegurando que no se trata de un robot ni de 
una suplantación de identidad.

## Descripción del Proyecto:
El proyecto eDentifica tiene como objetivo principal estandarizar la autenticación de personas a nivel global. A través de un proceso de validación robusto, eDentifica busca
garantizar que las identidades registradas sean auténticas, minimizando el riesgo de fraudes y suplantaciones.

## Instalación y Requisitos Previos

### Requisitos del Sistema
Sistema Operativo: Windows 10 o superior
Procesador: Intel Core i5 o superior
Almacenamiento: 512GB SSD mínimo
Memoria RAM: 8GB mínimo

### Herramientas Necesarias
Java JDK
Spring Boot
Redis
IntelliJ IDEA (recomendado)

### Clonar el Repositorio
Para clonar el repositorio, sigue estos pasos:

Accede al siguiente enlace: https://github.com/JuanPabloCaro/eDentifica
Clona el repositorio en tu máquina local.

  git clone https://github.com/JuanPabloCaro/eDentifica.git 
  cd edentifica

## Configuración del Entorno
Para la configuración necesaria, es ideal crear una URL pública desde Visual Studio Code para que cualquier persona pueda acceder al servidor durante la etapa de desarrollo. 
Para producción, es preferible desplegar el servidor en la nube. Aquí se explica cómo crear una URL pública desde Visual Studio Code utilizando "Forward a port":

1. Abre Visual Studio Code.
2. Instala la extensión "Remote - SSH".
3. Conéctate a tu máquina remota.
4. Utiliza la opción "Forward a port" para exponer el puerto de tu servidor.


## Estructura del Proyecto
El proyecto está organizado en las siguientes carpetas:

Modelos: Contiene las clases de modelo y DTOs.
Repositorios: Clases para interactuar con la base de datos.
Servicios: Contiene la lógica de negocio.
Controladores: Define los endpoints de la API.
Manejo de Errores: Clases para gestionar errores y excepciones.
Configuración: Beans y configuración del servidor.
Cliente REST: Cliente para realizar pruebas.

## Instrucciones de Ejecución

### Configuración de Redis y MongoDB
1. Asegúrate de que Redis esté en ejecución:
  redis-server

2. Verifica que Redis esté funcionando:
  redis-cli ping
Deberías recibir la respuesta PONG.

3. Configura MongoDB y habilita la conexión desde la IP de tu máquina anfitriona. Aquí están los detalles de conexión:
Cluster: edentifica
Usuario: jcaropenuela
Contraseña: 6P7TaYgb62D9cHhV
Nombre de la Base de Datos: edentifica
URI de Conexión: mongodb+srv://jcaropenuela:6P7TaYgb62D9cHhV@edentifica.tarmvwe.mongodb.net/?retryWrites=true&w=majority&appName=edentifica

4. Verifica que el servidor esté funcionando accediendo a la URL base: https://localhost:8080/edentifica

5. Ejecuta el servidor Spring boot.

## Endpoints y API
### Insertar Usuario
URL: /insert
Método: POST
Descripción: Inserta un nuevo usuario.
Request Body: {"name": "John Doe", "email": "john.doe@example.com", ...}
Response: 201 Created con el objeto User insertado o 400 Bad Request.
### Actualizar Usuario
URL: /update
Método: PUT
Descripción: Actualiza un usuario existente.
Request Body: {"id": "123", "name": "John Doe", "email": "john.doe@example.com", ...}
Response: 200 OK si se actualiza correctamente, 404 Not Found si el usuario no existe.
### Eliminar Usuario
URL: /delete/{id}
Método: DELETE
Descripción: Elimina un usuario por ID.
Response: 200 OK si se elimina correctamente, 404 Not Found si el usuario no existe.
### Obtener Todos los Usuarios
URL: /get_all
Método: GET
Descripción: Obtiene una lista de todos los usuarios.
Response: 200 OK con una lista de usuarios.
### Validación por Llamada
URL: /validation_one_call
Método: POST
Descripción: Envía una llamada por WhatsApp con un reto matemático.
Request Body: {"id": "123", ...}
Response: 201 Created si se envía correctamente, 400 Bad Request.
### Responder al Reto Matemático
URL: /answer_math_challenge
Método: POST
Descripción: Comprueba la respuesta al reto matemático.
Request Param: answer=42
Request Body: {"id": "123", ...}
Response: 200 OK si la respuesta es correcta, 400 Bad Request.
### Buscar Usuario por Red Social
URL: /get_by_type_and_social_network/{type}
Método: GET
Descripción: Busca un usuario por tipo y nombre de red social.
Request Param: socialname=exampleName
Response: 200 OK con el usuario, 404 Not Found.
### Buscar Usuario por Número de Teléfono
URL: /get_by_phone/{phonenumber}
Método: GET
Descripción: Busca un usuario por número de teléfono.
Response: 200 OK con el usuario, 404 Not Found.
### Buscar Usuario por Email
URL: /get_by_email/{email}
Método: GET
Descripción: Busca un usuario por email.
Response: 200 OK con el usuario, 404 Not Found.
### Obtener Todos los Usuarios DTO
URL: /get_all_dto
Método: GET
Descripción: Obtiene una lista de todos los usuarios DTO.
Response: 200 OK con una lista de usuarios DTO.
### Buscar Usuario DTO por Email
URL: /get_dto_by_email
Método: GET
Request Param: email=example@example.com
Descripción: Busca un usuario DTO por email.
Response: 200 OK con el usuario DTO, 404 Not Found.
### Buscar Usuario DTO por Número de Teléfono
URL: /get_dto_by_phone
Método: GET
Request Param: phonenumber=123456789
Descripción: Busca un usuario DTO por número de teléfono.
Response: 200 OK con el usuario DTO, 404 Not Found.
### Buscar Usuario DTO por Red Social
URL: /get_dto_by_type_and_social_network/{type}
Método: GET
Request Param: socialname=exampleName
Descripción: Busca un usuario DTO por red social.
Response: 200 OK con el usuario DTO, 404 Not Found.
### Buscar Usuario DTO por ID
URL: /get_dto_by_id
Método: GET
Request Param: id=123
Descripción: Busca un usuario DTO por ID.
Response: 200 OK con el usuario DTO, 404 Not Found.

## Manejo de Errores
El manejo de errores se realiza mediante una clase global de manejo de excepciones que implementa rollback en caso de errores. Los mensajes de error comunes incluyen:

400 Bad Request: Solicitud inválida.
401 Unauthorized: No autorizado.
500 Internal Server Error: Error interno del servidor.

## Contribuciones
Por el momento, las contribuciones no están habilitadas ya que es un proyecto privado.

## Licencia
El proyecto no tiene una licencia específica asignada. Para cualquier consulta al respecto, por favor contacta al autor.

## Contacto
Autor: eDentifica

Correo: jcaropenuela@gmail.com / informacion@edentifica.com
Página web: edentifica.com




