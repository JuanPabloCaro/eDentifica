graph TD;
    A(Base de Datos) --> B(Redis);
    B --> C(Consulta de Datos);
    C --> D{Cache Hit?};
    D -- Sí --> E(Datos desde Redis);
    D -- No --> F(Consulta a Base de Datos);
    F --> G(Guardar Datos en Redis);
    G --> H(Datos a Cliente);
