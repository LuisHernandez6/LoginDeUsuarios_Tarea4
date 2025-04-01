import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

//Pantalla principal
public class MainUI extends JFrame {
    private JTable table1;
    private JButton nuevoButton;
    private JButton actualizarButton;
    private JButton eliminarButton;
    private JButton cerrarSeccionButton;
    private JPanel mainPanel;

    private UsuariosController controller;
    private DefaultTableModel tableModel;
    private Integer usuarioSeleccionado;

    private void createUIComponents() {
        this.controller = UsuariosController.getInstance();

        String[] columnas = {"ID", "Nombre completo", "Teléfono", "Correo electrónico", "Usuario"};
        String[][] data = new String[0][5];

        this.tableModel = new DefaultTableModel(data, columnas);
        table1 = new JTable(this.tableModel);
        table1.setDefaultEditor(Object.class, null);  //Deshabilita la edicion de celdas

        // Efectivamente ocultamos la columna ID
        table1.getColumnModel().getColumn(0).setMinWidth(0);
        table1.getColumnModel().getColumn(0).setMaxWidth(0);
        table1.getColumnModel().getColumn(0).setWidth(0);

        // ListSelectionListener para capturar la seleccion de filas de la tabla
        ListSelectionModel selectionModel = table1.getSelectionModel();
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Verificar si la seleccion ha cambiado
                if (!e.getValueIsAdjusting()) {  //Evitar que se ejecute mas veces si hay multiples cambios
                    int selectedRow = table1.getSelectedRow();  //Obtener la fila seleccionada
                    if (selectedRow != -1) {  //Si se selecciono una fila
                        usuarioSeleccionado = Integer.valueOf((String)table1.getValueAt(selectedRow, 0));
                    }
                }
            }
        });
    }

    public MainUI(UsuariosController controller, Usuario seccion) {
        setContentPane(mainPanel);
        setTitle("Usuarios registrados");
        setSize(600, 320);
        setMinimumSize(new Dimension(600, 320));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        refrescarTabla();

        nuevoButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new UsuarioUI(controller,null, MainUI.this); //Abre el formulario de registro
            }
        });

        actualizarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (usuarioSeleccionado == null){mensajeSeleccione(); return;}
                new UsuarioUI(controller, controller.obtenerUsuario(usuarioSeleccionado), MainUI.this); //Abre el formulario de actualizar
            }
        });

        eliminarButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (usuarioSeleccionado == null){mensajeSeleccione(); return;}
                if (usuarioSeleccionado == seccion.getId()){
                    JOptionPane.showMessageDialog(MainUI.this, "No puede eliminar el usuario de la sesión actual.");
                    return;
                }

                //Ventana de confirmacion
                int respuesta = JOptionPane.showConfirmDialog(
                        null,
                        "¿Seguro que quiere eliminar al usuario \""+controller.obtenerUsuario(usuarioSeleccionado).getNombreUsuario()+"\"?",
                        "Eliminar",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE
                );

                //Verificar la respuesta
                if (respuesta == JOptionPane.YES_OPTION) {
                    controller.eliminarUsuario(usuarioSeleccionado);
                    refrescarTabla();
                    usuarioSeleccionado = null;
                }
            }
        });

        cerrarSeccionButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginUI(controller); //Regresar al login
            }
        });
    }

    private void mensajeSeleccione() {
        JOptionPane.showMessageDialog(this, "Seleccione un usuario de la lista.");
    }

    //Actualiza todos los elementos de la tabla
    public void refrescarTabla() {
        tableModel.setRowCount(0);
        String[][] data = new String[controller.obtenerUsuarios().size()][5];

        for (int i = 0; i < controller.obtenerUsuarios().size(); i++) {
            Usuario usuario = controller.obtenerUsuarios().get(i);
            data[i][0] = String.valueOf(usuario.getId());
            data[i][1] = usuario.getNombre() + " " + usuario.getApellido();
            data[i][2] = usuario.getTelefono();
            data[i][3] = usuario.getCorreo();
            data[i][4] = usuario.getNombreUsuario();
        }
        for (String[] fila : data) {
            tableModel.addRow(fila);
        }
        table1.setPreferredSize(new Dimension(320,table1.getRowHeight() * table1.getRowCount()+1));
    }

    /*
    public static void main(String[] args) {
        MainUI frame = new MainUI(UsuariosController.getInstance(),null);
    }
     */
}
