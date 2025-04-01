import java.util.ArrayList;

//Abstraccion a traves de la interfaz CRUD
public interface CRUD<T> {
    void crear(T entity);
    ArrayList<T> leer(Integer id);
    void actualizar(T entity);
    void eliminar(int id);
}
