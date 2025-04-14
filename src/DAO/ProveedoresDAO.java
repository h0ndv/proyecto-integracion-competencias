package DAO;

import Conexion.Conexion;
import Modelo.Proveedores;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class ProveedoresDAO {

    public ProveedoresDAO() { }

    // Metodo para agregar un proveedor a la base de datos
    public void agregarProveedor(Proveedores proveedores) {
        String sql = "INSERT INTO tb_proveedor (nombre, empresa, celular, correo) VALUES (?, ?, ?, ?)";

        try {
            Connection connection = Conexion.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Setear los valores de la consulta
            preparedStatement.setString(1, proveedores.getNombre());
            preparedStatement.setString(2, proveedores.getEmpresa());
            preparedStatement.setString(3, proveedores.getCelular());
            preparedStatement.setString(4, proveedores.getCorreo());

            // Ejecutar la consulta
            preparedStatement.executeUpdate();
            System.out.println("Proveedor creado correctamente " + proveedores.getNombre() + " " + proveedores.getEmpresa() + " " + proveedores.getCelular());
        } catch (SQLException e) {
            System.out.println("Error al crear el proveedor: " + e.getMessage());
        }
    }

    // Metodo para eliminar un proveedor de la base de datos
    public void eliminarProveedor(Proveedores proveedores) {
        String sql = "DELETE FROM tb_proveedor where nombre = ? and empresa = ? and celular = ? and correo = ?";

        try {
            Connection connection = Conexion.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Setear los valores de la consulta
            preparedStatement.setString(1, proveedores.getNombre());
            preparedStatement.setString(2, proveedores.getEmpresa());
            preparedStatement.setString(3, proveedores.getCelular());
            preparedStatement.setString(4, proveedores.getCorreo());

            // Ejecutar la consulta
            preparedStatement.executeUpdate();
            System.out.println("Proveedor " + proveedores.getNombre() + " eliminado correctamente");
        } catch (SQLException e) {
            System.out.println("Error al eliminar el proveedor: " + e.getMessage());
        }
    }

    // Metodo para actualizar un proveedor de la base de datos
    public void actualizarProveedor(Proveedores proveedores) {
        String sql = "UPDATE tb_proveedor SET nombre = ?, empresa = ?, celular = ?, correo = ? WHERE nombre = ?";

        try {
            Connection connection = Conexion.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            // Setear los valores de la consulta
            preparedStatement.setString(1, proveedores.getNombre());
            preparedStatement.setString(2, proveedores.getEmpresa());
            preparedStatement.setString(3, proveedores.getCelular());
            preparedStatement.setString(4, proveedores.getCorreo());
            preparedStatement.setString(5, proveedores.getNombre());

            // Ejecutar la consulta
            preparedStatement.executeUpdate();
            System.out.println("Proveedor actualizado correctamente" + proveedores.getNombre() + " " + proveedores.getEmpresa() + " " + proveedores.getCelular());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el proveedor", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error al actualizar el proveedor: " + e.getMessage());
        }   
    }

    // Funcion para obtener todos los proveedores de la base de datos y guardarlos en una lista
    public List<Proveedores> obtenerProveedores() {
        List<Proveedores> listaProveedores = new ArrayList<>();
        // Consulta sql
        String sql = "SELECT * FROM tb_proveedor";

        try {
            Connection connection = Conexion.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            // Recorrer el resultado de la cosnultaa
            while (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                String empresa = resultSet.getString("empresa");
                String celular = resultSet.getString("celular");
                String correo = resultSet.getString("correo");
                
                //
                Proveedores proveedores = new Proveedores(nombre, empresa, celular, correo);
                listaProveedores.add(proveedores);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener los proveedores: " + e.getMessage());
        }
        return listaProveedores;
    }

    // Modificar los datos de un proveedor de la base de datos
    public void modificarProveedor(Proveedores proveedores) {
        // Consulta sql
        String sql = "UPDATE tb_proveedor SET nombre = ?, empresa = ?, celular = ?, correo = ? WHERE nombre = ?";
        
        try {
            Connection connection = Conexion.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            // Setear los valores de la consulta
            preparedStatement.setString(1, proveedores.getNombre());
            preparedStatement.setString(2, proveedores.getEmpresa());
            preparedStatement.setString(3, proveedores.getCelular());
            preparedStatement.setString(4, proveedores.getCorreo());
            preparedStatement.setString(5, proveedores.getNombre());

            // Ejecutar la consulta
            preparedStatement.executeUpdate();
            System.out.println("Proveedor actualizado correctamente" + proveedores.getNombre() + " " + proveedores.getEmpresa() + " " + proveedores.getCelular());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar el proveedor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.out.println("Error al actualizar el proveedor: " + e.getMessage());
        }
    }                   
// end
}
