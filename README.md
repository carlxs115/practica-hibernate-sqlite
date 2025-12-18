# practica-hibernate-sqlite

## Descripción
Proyecto académico en Java que implementa operaciones CRUD básicas con Hibernate y SQLite simulando la gestión de libros de una biblioteca. Incluye configuración, entidad Libro, gestión manual de sesiones y transacciones, y documentación completa mediante comentarios. Ideal como ejemplo introductorio a persistencia con JPA/Hibernate.

## Estructura principal
pruebapractica2_hibernate_sqlite/
├── pom.xml # Configuración de Maven (dependencias: Hibernate, SQLite, JPA, SLF4J)
├── src/
│ └── main/
│ ├── java/
│ │ ├── HibernateUtil.java # Gestión de SessionFactory (inicialización y cierre)
│ │ ├── Libro.java # Entidad JPA mapeada a la tabla 'libros'
│ │ └── Main.java # Clase principal con flujo CRUD completo
│ └── resources/
│ └── hibernate.cfg.xml # Configuración de conexión, dialecto y mapeo
└── biblioteca.db # Base de datos SQLite (generada al ejecutar)

## Tecnologías
- Java 24  
- Maven  
- Hibernate ORM 6.4.4  
- SQLite JDBC  
- JPA (Jakarta Persistence API)
