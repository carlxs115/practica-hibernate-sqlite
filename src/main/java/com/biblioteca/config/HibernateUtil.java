package com.biblioteca.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Clase de utilidad para gestionar la conexión con la base de datos mediante Hibernate.
 * Proporciona una única instancia de SessionFactory, que se construye al inicio y se reutiliza durante toda la ejecución.
 * Ideal para aplicaciones pequeñas o de prueba.
 */
public class HibernateUtil {

    // Instancia única y estática de SessionFactory, inicializada al cargar la clase
    private static final SessionFactory sessionFactory = buildSessionFactory();

    /**
     * Construye la SessionFactory usando la configuración definida en 'hibernate.cfg.xml'.
     * Si falla (por ejemplo, archivo de configuración incorrecto o driver no encontrado),
     * lanza una excepción en tiempo de ejecución para detener la aplicación.
     */
    private static SessionFactory buildSessionFactory() {
        try {
            return new Configuration().configure().buildSessionFactory();
        } catch (Exception e){
            throw new RuntimeException("Error al cargar el objeto SessionFactory: " + e);
        }
    }

    /**
     * Devuelve la SessionFactory ya construida.
     * Permite obtener sesiones de Hibernate desde cualquier parte del código.
     */
    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

    /**
     * Cierra la SessionFactory liberando recursos.
     * Debe llamarse al finalizar la aplicación.
     */
    public static void cerrar(){
        getSessionFactory().close();
    }
}
