import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

//CRUD para comunicarse con la BD y ejecutar queries
public class UsuariosCRUD implements CRUD<Usuario> {
    private static UsuariosCRUD instancia;
    private final ConexionMySQL bdMySql = ConexionMySQL.getInstance();
    private Connection conn;
    private PreparedStatement stmt;

    //Patron de diseño Singleton
    public static UsuariosCRUD getInstance() {
        if (instancia == null) {
            instancia = new UsuariosCRUD();
        }
        return instancia;
    }

    @Override
    public void crear(Usuario usuario) {
        try {
            String query = "INSERT INTO usuarios (usuario, nombre, apellido, telefono, correo, contrasena) VALUES (?, ?, ?, ?, ?, ?)";
            conn = bdMySql.conectar();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, usuario.getNombreUsuario());
            stmt.setString(2, usuario.getNombre());
            stmt.setString(3, usuario.getApellido());
            stmt.setString(4, usuario.getTelefono());
            stmt.setString(5, usuario.getCorreo());
            stmt.setString(6, usuario.getContrasena());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarObjetos();
        }
    }

    @Override
    public ArrayList<Usuario> leer(Integer id) { //Si el argumento es null se va a leer la tabla completa
        ArrayList<Usuario> usuarios = new ArrayList<>();
        try {
            conn = bdMySql.conectar();
            if (id == null) {
                String query = "SELECT * FROM usuarios";
                stmt = conn.prepareStatement(query);
            }
            else {
                String query = "SELECT * FROM usuarios WHERE id = ?";
                stmt = conn.prepareStatement(query);
                stmt.setInt(1, id);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                int usuarioId = rs.getInt(1);
                String usuarioNombre = rs.getString(2);
                String nombre = rs.getString(3);
                String apellido = rs.getString(4);
                String telefono = rs.getString(5);
                String correo = rs.getString(6);
                String contrasena = rs.getString(7);

                Usuario usuario = new Usuario(usuarioNombre, nombre, apellido, telefono, correo, contrasena, usuarioId);
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarObjetos();
        }
        return usuarios;
    }

    @Override
    public void actualizar(Usuario usuario) {
        try {
            String query = "UPDATE usuarios SET usuario = ?, nombre = ?, apellido = ?, telefono = ?, correo = ?, contrasena = ? WHERE id = ?";
            conn = bdMySql.conectar();
            stmt = conn.prepareStatement(query);
            stmt.setString(1, usuario.getNombreUsuario());
            stmt.setString(2, usuario.getNombre());
            stmt.setString(3, usuario.getApellido());
            stmt.setString(4, usuario.getTelefono());
            stmt.setString(5, usuario.getCorreo());
            stmt.setString(6, usuario.getContrasena());
            stmt.setInt(7, usuario.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarObjetos();
        }
    }

    @Override
    public void eliminar(int id) {
        try {
            String query = "DELETE FROM usuarios WHERE id = ?";
            conn = bdMySql.conectar();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrarObjetos();
        }
    }

    //Cierra los objetos para reducir la probabilidad de fugas de memoria
    private void cerrarObjetos() {
        try {
            if (stmt != null) { stmt.close(); }
            //if (conn != null) { conn.close(); }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
