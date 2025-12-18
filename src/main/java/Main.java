import com.biblioteca.config.HibernateUtil;
import com.biblioteca.model.Libro;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Clase principal de la aplicación. Contiene el 'main' y métodos estáticos
 * para realizar operaciones CRUD (Crear, Leer, Actualizar, Eliminar) sobre la entidad 'Libro'.
 *
 * Funcionamiento general:
 * - Crea 3 libros de ejemplo y los inserta.
 * - Muestra todos los libros.
 * - Busca por autor.
 * - Actualiza disponibilidad por ID.
 * - Elimina un libro por ID.
 * - Muestra el estado final.
 * - Cierra la SessionFactory antes de terminar.
 *
 * Cada operación abre su propia sesión de Hibernate y gestiona transacciones manualmente.
 */
public class Main {
    public static void main(String[] args) {

        // Creación de instancias de libros (objetos en memoria, aún no en BD)
        Libro libro1 = new Libro("Star Wars: Heir to the Empire", "Timothy Zahn", true);
        Libro libro2 = new Libro("La Caída de Númenor", "J. R. R. Tolkien", true);
        Libro libro3 = new Libro("Frankestein: or, The Modern Prometheus", "Mary Shelley", true);

        // Inserción en la base de datos
        insertarLibro(libro1);
        insertarLibro(libro2);
        insertarLibro(libro3);

        // Listado completo de libros almacenados
        System.out.println("\nListado de libros:");
        listarLibros();

        // Búsqueda específica por autor
        System.out.println("\nBúsqueda de libro por autor: 'Timothy Zahn'");
        buscarLibroPorAutor("Timothy Zahn");

        // Modificación de un campo (disponibilidad) usando ID
        System.out.println("\nActualización de disponibilidad del libro con ID: 1");
        actualizarDisponibilidad(1, false);

        // Eliminación física de un registro por ID
        System.out.println("\nEliminación del libro con ID: 2");
        eliminarLibro(2);

        // Estado final después de las operaciones
        System.out.println("\nListado de libros:");
        listarLibros();

        // Liberación de recursos globales de Hibernate (conexiones, etc.)
        HibernateUtil.cerrar();
    }

    /**
     * Inserta un nuevo libro en la base de datos.
     * Abre una sesión, inicia una transacción, persiste el objeto y hace commit.
     * Usa try-with-resources para garantizar que la sesión se cierre incluso si hay error.
     */
    public static void insertarLibro(Libro libro){
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            Transaction tx = session.beginTransaction(); //Inicia la transacción
            session.persist(libro); //Marca el objeto para guardarlo en la base de datos
            tx.commit(); //Confirma la transacción
        }
    }

    /**
     * Recupera y muestra todos los libros almacenados.
     * Ejecuta una consulta HQL ("FROM Libro") y recorre la lista resultante.
     * La sesión se cierra manualmente en 'finally' porque no usa try-with-resources aquí.
     */
    public static void listarLibros(){
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            Query<Libro> query = session.createQuery("FROM com.biblioteca.model.Libro", Libro.class); //Se crea una consulta HQL para obtener los libros
            List<Libro> libros = query.list(); //Ejecuta la consulta y obtiene una lista

            if (libros.isEmpty()){
                System.out.println("No hay libros registrados en la base de datos");
            } else {
                for (Libro libro : libros){
                    System.out.println(libro);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error. No se pudo listar los libros: " + e.getMessage());
        } finally {
            session.close(); //Se cierra la sesión
        }
    }

    /**
     * Busca libros cuyo autor coincida exactamente con el nombre proporcionado.
     * Usa un parámetro nombrado (':autor') para evitar riesgos de inyección SQL/HQL.
     */
    public static void buscarLibroPorAutor(String autor){
        Session session = HibernateUtil.getSessionFactory().openSession();

        try {
            // Consulta HQL con parámetro
            Query<Libro> query = session.createQuery("FROM com.biblioteca.model.Libro WHERE autor = :autor", Libro.class);
            query.setParameter("autor", autor);
            List<Libro> libros = query.list();

            if (libros.isEmpty()){
                System.out.println("No se ha encontrado ningún libro del autor: " + autor);
            } else {
                for (Libro libro : libros){
                    System.out.println(libro);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Error. No se pudo buscar libros: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    /**
     * Actualiza el estado de disponibilidad de un libro existente, identificado por su ID.
     * Recupera el objeto de la BD, modifica su atributo y lo fusiona (merge) para persistir cambios.
     * Si el libro no existe, muestra mensaje y revierte la transacción.
     */
    public static void actualizarDisponibilidad(int id, boolean nuevaDisponibilidad){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            //Obtiene el libro de la base de datos por su ID
            Libro libro = session.get(Libro.class, id);

            if (libro != null){
                libro.setDisponible(nuevaDisponibilidad);
                session.merge(libro); //Actualiza el objeto en la base de datos
                tx.commit();
                System.out.println("Disponibilidad del libro con ID: " + id + " actualizada a " + nuevaDisponibilidad);
            } else {
                System.out.println("No se ha encontrado ningún libro con ID: " + id);
                if (tx != null){
                    tx.rollback();
                }
            }
        } catch (Exception e){
            //Si da error, revierte la transacción
            if (tx != null){
                tx.rollback();
            }
            throw new RuntimeException("Error. No se pudo actualizar la disponibilidad: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    /**
     * Elimina un libro de la base de datos usando su ID.
     * Primero lo carga (para asegurar que existe), luego lo marca para eliminación (remove).
     * Si no existe, no se elimina nada y se revierte la transacción.
     */
    public static void eliminarLibro(int id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            //Obtiene el libro de la base de datos por su ID
            Libro libro = session.get(Libro.class, id);

            if (libro != null){
                session.remove(libro); //Marca el objeto para ser eliminado
                tx.commit();
                System.out.println("Se ha eliminado correctamente el libro con ID: " + id);
            } else {
                System.out.println("No se ha encontrado ningún libro con ID: " + id);
                if (tx != null){
                    tx.rollback();
                }
            }
        } catch (Exception e){
            //Si da error, revierte la transacción
            if (tx != null){
                tx.rollback();
            }
            throw new RuntimeException("Error. No se pudo eliminar el libro: " + e.getMessage());
        } finally {
            session.close();
        }
    }
}
