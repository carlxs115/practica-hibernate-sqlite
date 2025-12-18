package com.biblioteca.model;

import jakarta.persistence.*;

/**
 * Entidad JPA que representa un libro en la base de datos.
 * Está mapeada a la tabla 'libros' y contiene los campos básicos:
 * - id (clave primaria autoincremental),
 * - título,
 * - autor,
 * - disponibilidad (true = disponible para préstamo / false = libro prestado (no disponible)).
 * Hibernate se encargará de convertir objetos de esta clase en filas y viceversa.
 */

@Entity
@Table(name = "libros")
public class Libro {

    /**
     * Identificador único del libro. Generado automáticamente por la base de datos
     * gracias a @GeneratedValue(strategy = GenerationType.IDENTITY).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // Título del libro (almacenado en columna 'titulo')
    private String titulo;

    // Autor del libro (almacenado en columna 'autor')
    private String autor;

    // Indica si el libro está disponible para préstamo (true/false)
    private boolean disponible;

    //Constructor vacío requerido por Hibernate/JPA para instanciar objetos.
    public Libro(){}

    /**
     * Constructor con parámetros para crear un nuevo libro antes de guardarlo.
     * No asigna 'id', ya que este lo generará la base de datos.
     */
    public Libro(String titulo, String autor, boolean disponible) {
        this.titulo = titulo;
        this.autor = autor;
        this.disponible = disponible;
    }

    /**
     * Getters y setters estándar: permiten a Hibernate leer/escribir los campos
     * y también se usan en la lógica de negocio (por ejemplo, para modificar disponibilidad).
     */

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    /**
     * Representación en texto del libro, útil para imprimir en consola.
     * Incluye todos los campos relevantes en formato legible.
     */
    @Override
    public String toString() {
        return "\nID del Libro: " + id +
                "\nTítulo: " + titulo +
                "\nAutor: " + autor +
                "\nDisponibilidad: " + disponible;
    }
}
