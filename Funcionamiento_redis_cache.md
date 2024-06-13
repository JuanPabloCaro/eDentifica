graph TD;
    A[Usuario] --> B(Backend);
    B --> C{¿Datos en Cache?};
    C -- Sí --> D(Respuesta desde Redis);
    C -- No --> E(Consulta a Base de Datos);
    E --> F(Actualizar Cache);
    E --> G(Respuesta al Usuario);
    F --> G;
