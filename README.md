# practica-hibernate-sqlite

## Descripción
Proyecto académico en Java que implementa operaciones CRUD básicas con Hibernate y SQLite simulando la gestión de libros de una biblioteca. Incluye configuración, entidad Libro, gestión manual de sesiones y transacciones, y documentación completa mediante comentarios. Ideal como ejemplo introductorio a persistencia con JPA/Hibernate.

## Estructura principal
```
pruebapractica2_hibernate_sqlite/
├── pom.xml
├── src/
│   └── main/
│       ├── java/
│       │   ├── HibernateUtil.java
│       │   ├── Libro.java
│       │   └── Main.java
│       └── resources/
│           └── hibernate.cfg.xml
└── biblioteca.db
```
## Tecnologías
- Java 24  
- Maven  
- Hibernate ORM 6.4.4  
- SQLite JDBC  
- JPA (Jakarta Persistence API)

---

## Cómo descargar y ejecutar el proyecto
```bash
# Clona el repositorio
git clone https://github.com/carlxs115/practica-hibernate-sqlite.git

# Entra al directorio
cd practica-hibernate-sqlite

# Compila y ejecuta
mvn compile exec:java -Dexec.mainClass="Main"
```

---

## Licencia
Este proyecto está bajo la licencia MIT. Con intención de fines educativos.
```text
Copyright © 2025 Señor Riera
```
