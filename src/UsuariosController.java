import java.util.ArrayList;

//Controller para la comunicacion entre los formularios y los datos de usuarios
public class UsuariosController {
    private static UsuariosController instancia;
    private static UsuariosCRUD usuarioCRUD;
    private ArrayList<Usuario> usuarios;

    private UsuariosController() {
        usuarios = new ArrayList<>();
    }

    //Patron de diseño Singleton
    public static UsuariosController getInstance() {
        if (instancia == null) {
            instancia = new UsuariosController();
            usuarioCRUD = UsuariosCRUD.getInstance();
        }
        return instancia;
    }

    public void agregarUsuario(Usuario usuario) {
        if (Main.conectadoConBD){
            usuarioCRUD.crear(usuario);
        }
        usuario.setId(ultimoId()+1);
        usuarios.add(usuario);
    }

    //Polimorfismo de sobrecarga a traves de dos metodos con distintos parametros
    public Usuario obtenerUsuario(String nombreUsuario) {
        ArrayList<Usuario> usuariosTemp = usuarios;
        if (Main.conectadoConBD){
            usuariosTemp = usuarioCRUD.leer(null);
        }
        for (Usuario usuario : usuariosTemp) {
            if (usuario.getNombreUsuario().equals(nombreUsuario)) {
                return usuario;
            }
        }
        return null;
    }

    public Usuario obtenerUsuario(int id) {
        if (Main.conectadoConBD){
            for (Usuario usuario : usuarioCRUD.leer(id)) {
                return usuario;
            }
        }
        for (Usuario usuario : usuarios) {
            if (usuario.getId() == id) {
                return usuario;
            }
        }
        return null;
    }

    //Retorna todos los usuarios
    public ArrayList<Usuario> obtenerUsuarios() {
        if (Main.conectadoConBD){
            return usuarioCRUD.leer(null);
        }
        return usuarios;
    }

    //Autentifica las credenciales de login
    public boolean validarCredenciales(String nombreUsuario, String contrasena) {
        Usuario usuario = obtenerUsuario(nombreUsuario);
        return usuario != null && usuario.getContrasena().equals(contrasena);
    }

    //Verifica que ambas contraseñas sean iguales
    public boolean verificarContrasenas(String contrasena, String confirmarContrasena) {
        return contrasena.equals(confirmarContrasena);
    }

    //Se asegura de que la contraseña se pueda utilizar.
    public boolean contrasenaDisponible(String contrasena) {
        ArrayList<Usuario> usuariosTemp = usuarios;
        if (Main.conectadoConBD){
            usuariosTemp = usuarioCRUD.leer(null);
        }
        for (Usuario usuario : usuariosTemp) {
            if (usuario.getContrasena().equals(contrasena)) {
                return false;
            }
        }
        return true;
    }

    //Elimina un usuario de la lista
    public void eliminarUsuario(int id) {
        if (Main.conectadoConBD){
            usuarioCRUD.eliminar(id);
        }
        usuarios.removeIf(usuario -> usuario.getId() == id);
    }

    //Actualiza un usuario de la lista
    public void actualizarUsuario(Usuario usuarioActualizado) {
        if (Main.conectadoConBD){
            usuarioCRUD.actualizar(usuarioActualizado);
        }
        for (int i = 0; i < usuarios.size(); i++) {
            if (usuarios.get(i).getId() == usuarioActualizado.getId()) {
                usuarios.set(i, usuarioActualizado);
                break;
            }
        }
    }

    //Retorna el id del usuario que esta al final de la lista
    private int ultimoId() {
        ArrayList<Usuario> usuariosTemp = usuarios;
        if (Main.conectadoConBD){
            usuariosTemp = usuarioCRUD.leer(null);
        }
        if (usuariosTemp.isEmpty()) {
            return 0;
        }
        return usuariosTemp.get(usuariosTemp.size() - 1).getId();
    }
}