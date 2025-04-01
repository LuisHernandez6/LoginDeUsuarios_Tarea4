import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConexionMySQL {
    private static final String URL = "jdbc:mysql://localhost:3306/bd_20231804_tarea4";
    private static final String USER = "root";
    private static final String PASSWORD = "12345";
    private static ConexionMySQL instancia;
    private Connection conexion;

    //Patron de diseño Singleton
    public static ConexionMySQL getInstance() {
        if (instancia == null) {
            instancia = new ConexionMySQL();
        }
        return instancia;
    }

    //Intenta conectar a la BD de MySQL
    public Connection conectar() {
        try {
            if (conexion != null && conexion.isValid(0)){
                return conexion;
            }
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            //System.out.println("Conexión OK");
            return conexion;
        } catch (SQLException e) {
            //System.out.println("Error al conectar a la base de datos");
            e.printStackTrace();
            return null;
        }
    }

    //Metodo para verificar si una tabla existe
    public boolean tableExists(String tableName) {
        boolean exists = false;
        try (Connection conn = conectar()) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet resultSet = metaData.getTables(null, null, tableName, null);
            if (resultSet.next()) {
                exists = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    //Metodo para crear la tabla si no existe
    public void crearTablaUsuarios() {
        if (!tableExists("usuarios")) {
            try (Connection conn = conectar()) {
                Statement stmt = conn.createStatement();

                String query = """
                CREATE Table usuarios (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    usuario NVARCHAR(30) NOT NULL,
                    nombre NVARCHAR(30) NOT NULL,
                    apellido NVARCHAR(30) NOT NULL,
                    telefono VARCHAR(30) NOT NULL,
                    correo NVARCHAR(100) NOT NULL,
                    contrasena NVARCHAR(30) NOT NULL
                )""";

                stmt.executeUpdate(query);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
