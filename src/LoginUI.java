import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//Formulario de login
public class LoginUI extends JFrame {
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton entrarButton;
    private JButton registrarseButton;
    private JPanel loginPanel;

    private UsuariosController controller;

    public LoginUI(UsuariosController controller) {
        setContentPane(loginPanel);
        setTitle("Login");
        setSize(300, 230);
        setMinimumSize(new Dimension(300,230));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        this.controller = controller;

        entrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                iniciarSesion();
            }
        });
        registrarseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                abrirRegistro();
            }
        });
    }

    private void iniciarSesion() {
        String usuario = textField1.getText();
        String contrasena = new String(passwordField1.getPassword());

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe ingresar su usuario " +
                    "y contraseña, si no está registrado debe registrarse.");
            return;
        }

        if (controller.validarCredenciales(usuario, contrasena)) {
            //JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso.");
            dispose();
            new MainUI(controller, controller.obtenerUsuario(usuario)); //Abre la ventana principal
        } else {
            JOptionPane.showMessageDialog(this, "Credenciales incorrectas.");
        }
    }

    private void abrirRegistro() {
        dispose();
        new UsuarioUI(controller,null, null); // Abre la ventana de registro
    }

    /*
    public static void main(String[] args) {
        UsuariosController controller = UsuariosController.getInstance();
        LoginUI frame = new LoginUI(controller);
    }
     */
}
