package DAO;

import Conexion.Conexion;
import Modelo.Usuarios;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;

public class UsuariosDAO {
    private static int userLogged = -1;
    private static String nombre;
    private static int cargo = 0;

    // Metodo para validar el usuario desde la base de datos
    public boolean iniciarSesion(Usuarios usuarios) {
        // Consulta SQL
        String sql = "SELECT * FROM tb_usuarios WHERE rut = ? AND pin = ?";
        
        try {
            // Obtener la conexion usando ConexionManager
            Connection connection = Conexion.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
           
            // Se setean los parametros de la consulta
            preparedStatement.setInt(1, usuarios.getRutUsuario());
            preparedStatement.setInt(2, usuarios.getPin());
            
            // Se ejecuta la consulta
            ResultSet resulSet = preparedStatement.executeQuery();
            
            // Si el usuario existe se inicia sesion
            if (resulSet.next()) {
                userLogged = resulSet.getInt("rut");
                nombre = resulSet.getString("nombre");
                cargo = resulSet.getInt("id_cargo");
                System.out.println("CuentaDAO.iniciarsesion() - Sesion iniciada. N° Cuenta: " + userLogged + " Nombre: " + nombre + " Cargo: " + cargo);
            } else {
                System.out.println("CuentaDAO.iniciarSesion() - Datos incorrectos");
                return false;
            }
            
            // Cerrar recursos pero no la conexión
            resulSet.close();
            preparedStatement.close();
            
        } catch (SQLException e) {
            System.out.println("Error al validar el usuario: " + e.getMessage());
            return false;         
        }
        return true;
    }

    // Agregar usuario a la abse de datos
    public void agregarUsuario(Usuarios usuarios) {
        String sql = "INSERT INTO tb_usuarios (id_cargo, nombre, rut, pin, correo) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection connection = Conexion.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            // Setear los valores de la consulta
            preparedStatement.setInt(1, usuarios.getId_cargo());
            preparedStatement.setString(2, usuarios.getNombre());
            preparedStatement.setInt(3, usuarios.getRutUsuario());
            preparedStatement.setInt(4, usuarios.getPin());
            preparedStatement.setString(5, usuarios.getCorreo());
            
            // Ejecutar la consulta
            preparedStatement.executeUpdate();
            System.out.println("Usuario creado " + usuarios.getNombre() + " " + usuarios.getRutUsuario() + " " + usuarios.getPin() + " " + usuarios.getCorreo());
        
        } catch (SQLException e) {
            System.out.println("Error al crear el usuario: " + e.getMessage()); 
        }
    }

    // Metodo para crear un nuevo usuario en la base de datos
    public void crearUsuario(Usuarios usuarios) {
        String sql = "INSERT INTO tb_usuarios (id_cargo, nombre, rut, pin, correo) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection connection = Conexion.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            // Setear los valroes de la consulta
            preparedStatement.setInt(1, usuarios.getId_cargo());
            preparedStatement.setString(2, usuarios.getNombre());
            preparedStatement.setInt(3, usuarios.getRutUsuario());
            preparedStatement.setInt(4, usuarios.getPin());
            preparedStatement.setString(5, usuarios.getCorreo());

            // Ejecutar la consulta
            preparedStatement.executeUpdate();
            System.out.println("Usuario creado " + usuarios.getNombre() + " " + usuarios.getRutUsuario() + " " + usuarios.getPin() + " " + usuarios.getCorreo());
        } catch (SQLException e) {
            System.out.println("Error al crear el usuario: " + e.getMessage());
        }
    }

    // Metodo para obtener el cargo del usuario Adminsitrador/Bodeguero/Vendedor desde la base de datos
    public int obtenerCargo() {
        // Consulta sql
        String sql = "SELECT id_cargo FROM tb_usuarios where rut = ?";

        try {
            Connection connection = Conexion.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, userLogged);

            // Se ejecuta la consulta
            ResultSet resultSet = preparedStatement.executeQuery();

            // Si el usuario existe se obtiene el cargo
            if (resultSet.next()) {
                cargo = resultSet.getInt("id_cargo");
            } 
        } catch (SQLException e) {
            System.out.println("Error al obtener el cargo del usuario:" + e.getMessage());
        }
        return cargo;
    }

    // Metodo para cargar los cargos en el comboBox
    public void cargarCargos(JComboBox<String> jComboBoxCargo) {
        String sql = "SELECT cargo FROM tb_cargo";

        try {
            Connection connection = Conexion.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Se eliminan todos los items del comboBOX
            jComboBoxCargo.removeAllItems();
            
            // Se agregan los items al comboBox
            while (resultSet.next()) {
                jComboBoxCargo.addItem(resultSet.getString("cargo"));
                System.out.println(resultSet.getString("cargo"));
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar los cargos: " + e.getMessage());
        }
    }

    // Metodo para obtener a los usuarios de la base de datos
    public List<Usuarios> obtenerUsuarios() {
        List<Usuarios> listaUsuarios = new ArrayList<>();

        // Consulta sql
        String sql = "SELECT * FROM tb_usuarios INNER JOIN tb_cargo ON tb_usuarios.id_cargo = tb_cargo.id_cargo";

        try {
            Connection connection = Conexion.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Se recorre el resultado de la consulta
            while (resultSet.next()) {
                Usuarios usuarios = new Usuarios();
                usuarios.setNombre(resultSet.getString("nombre"));
                usuarios.setRutUsuario(resultSet.getInt("rut"));
                usuarios.setPin(resultSet.getInt("pin"));
                usuarios.setCorreo(resultSet.getString("correo"));
                usuarios.setCargo(resultSet.getString("cargo"));

                // Se agrega el usuario a la lista
                listaUsuarios.add(usuarios);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los usuarios: " + e.getMessage());
        }
        return listaUsuarios;
    }
    
    // Metodo para eliminar un usuario de la base de datos
    public void eliminarUsuario(Usuarios usuarios) {
        String sql = "DELETE FROM tb_usuarios WHERE rut = ?";

        try {
            Connection connection = Conexion.getInstance().getConnection(); 
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            // Setear el valor de la consulta
            preparedStatement.setInt(1, usuarios.getRutUsuario());

            // Ejecutar la consulta
            preparedStatement.executeUpdate();
            System.out.println("Usuario eliminado " + usuarios.getNombre() + " " + usuarios.getRutUsuario());
            
        } catch (SQLException e) {
            System.out.println("Error al eliminar el usuario: " + e.getMessage());
        }
    }

    // Metodo para modificar un usuarui de la base d atos
    public void modificarUsuario(Usuarios usuarios) {
        String sql = "UPDATE tb_usuarios SET id_cargo = ?, nombre = ?, rut = ?, correo = ? WHERE rut = ?";

        try {
            Connection connection = Conexion.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            // Setear los valores de la consulta
            preparedStatement.setInt(1, usuarios.getId_cargo());
            preparedStatement.setString(2, usuarios.getNombre());
            preparedStatement.setInt(3, usuarios.getRutUsuario());
            preparedStatement.setString(4, usuarios.getCorreo());
            preparedStatement.setInt(5, usuarios.getRutUsuario());
            
            // Ejecutar la consulta
            preparedStatement.executeUpdate();
            preparedStatement.close();
        
        } catch (SQLException e) {
            System.out.println("Error al modificar el usuario: " + e.getMessage());
        }
    }

    // Metodo para cerrar sesion
    public void cerrarSesion() {
        if (userLogged != -1) {
            userLogged = -1;
            nombre = "";
            cargo = -1;
            System.out.println("UsuariosDAO.cerrarseion(): Sesion cerrada. N° Cuenta:" + userLogged);
        }
    }
// end
}