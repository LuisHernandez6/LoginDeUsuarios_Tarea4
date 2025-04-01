import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static Boolean conectadoConBD = null;

    public static void main(String[] args) {
        UsuariosController controller = UsuariosController.getInstance();
        LoginUI frame = new LoginUI(controller); //Abre el formulario de login

        // Intentamos establecer conexion con la BD
        try {
            Connection cnn = ConexionMySQL.getInstance().conectar();
            if (cnn != null && cnn.isValid(1)){
                conectadoConBD = true;
                JOptionPane.showMessageDialog(frame, "Conexion con la Base de Datos establecida satisfactoriamente.");
                ConexionMySQL.getInstance().crearTablaUsuarios();
            }
            else {
                mensajeErrorBD();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            mensajeErrorBD();
        }
    }

    private static void mensajeErrorBD() {
        JOptionPane.showMessageDialog(
                null,
                "No se pudo establecer conexion con la Base de Datos. Cambios a la lista de usuarios\ntan solo se almacenaran temporalmente en la RAM hasta que reinicie el programa.",
                "Message",
                JOptionPane.WARNING_MESSAGE);
    }
}