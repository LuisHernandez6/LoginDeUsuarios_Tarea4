import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//Se encarga de registrar y modificar a los usuarios
public class UsuarioUI extends JFrame {
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JTextField textField5;
    private JPasswordField passwordField1;
    private JPasswordField passwordField2;
    private JButton registrarButton;
    private JPanel registroPanel;

    private UsuariosController controller;
    private Usuario usuarioExistente;
    private MainUI mainForm;

    public UsuarioUI(UsuariosController controller, Usuario usuarioExistente, MainUI mainForm) {
        setContentPane(registroPanel);
        setTitle(usuarioExistente == null ? "Registro" : "Actualizar");
        setSize(400, 310);
        setMinimumSize(new Dimension(300,230));
        setDefaultCloseOperation(mainForm == null ? JFrame.EXIT_ON_CLOSE : JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        this.controller = controller;
        this.usuarioExistente = usuarioExistente;
        this.mainForm = mainForm;

        //Si se trata de una actualizacion coloca los datos en el formulario
        if (usuarioExistente != null) {
            textField1.setText(usuarioExistente.getNombreUsuario());
            textField2.setText(usuarioExistente.getNombre());
            textField3.setText(usuarioExistente.getApellido());
            textField4.setText(usuarioExistente.getTelefono());
            textField5.setText(usuarioExistente.getCorreo());
            registrarButton.setText("Actualizar");
        }

        registrarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                registrarUsuario();
            }
        });
    }

    private void registrarUsuario() {
        String nombreUsuario = textField1.getText().trim();
        String nombre = textField2.getText().trim();
        String apellido = textField3.getText().trim();
        String telefono = textField4.getText().trim();
        String correo = textField5.getText().trim();
        String contrasena = new String(passwordField1.getPassword()).trim();
        String confirmarContrasena = new String(passwordField2.getPassword()).trim();

        int count = 0;
        String camposVacios = "";

        //Verificamos que todos los campos estan llenos
        if (nombreUsuario.isEmpty()) {
            camposVacios += ", Nombre de usuario"; ++count;
        }
        if (nombre.isEmpty()) {
            camposVacios += ", Nombre"; ++count;
        }
        if (apellido.isEmpty()) {
            camposVacios += ", Apellido"; ++count;
        }
        if (telefono.isEmpty()) {
            camposVacios += ", Teléfono"; ++count;
        }
        if (correo.isEmpty()) {
            camposVacios += ", Correo electrónico"; ++count;
        }
        if (contrasena.isEmpty()) {
            camposVacios += ", Contraseña"; ++count;
        }
        if (confirmarContrasena.isEmpty()) {
            camposVacios += ", Confirmar contraseña"; ++count;
        }

        if (count > 0) {
            camposVacios = camposVacios.substring(2);  //Elimina los dos primeros caracteres
            if (count > 1) {
                JOptionPane.showMessageDialog(this, "Los campos \""+camposVacios+"\" estan vacios.");
                return;
            }
            JOptionPane.showMessageDialog(this, "El campo \""+camposVacios+"\" esta vacio.");
            return;
        }

        if (!controller.verificarContrasenas(contrasena, confirmarContrasena)) {
            JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden.");
            return;
        }

        if (!controller.contrasenaDisponible(contrasena)) {
            if (!(usuarioExistente != null && usuarioExistente.getContrasena().equals(contrasena))) {
                JOptionPane.showMessageDialog(this, "Contraseña actualmente no disponible.");
                return;
            }
        }

        if (controller.obtenerUsuario(nombreUsuario) != null) {
            if (!(usuarioExistente != null && usuarioExistente.getNombreUsuario().equals(nombreUsuario))) {
                JOptionPane.showMessageDialog(this, "Nombre de usuario ya existente.");
                return;
            }
        }

        Usuario nuevoUsuario = new Usuario(nombreUsuario, nombre, apellido, telefono, correo, contrasena);

        if (usuarioExistente == null) {
            controller.agregarUsuario(nuevoUsuario);
            JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente.");
            dispose();
            if (mainForm == null){
                new LoginUI(controller); //Regresa al login
            }
            else{
                mainForm.refrescarTabla();
            }
        }
        else{
            nuevoUsuario.setId(usuarioExistente.getId());
            controller.actualizarUsuario(nuevoUsuario);
            JOptionPane.showMessageDialog(this, "Usuario actualizado exitosamente.");
            dispose();
            mainForm.refrescarTabla();
        }
    }
}
