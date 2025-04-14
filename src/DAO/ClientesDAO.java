package DAO;

import Conexion.Conexion;
import Modelo.Clientes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientesDAO {
    // Metodo para agregar cliente
    public void agregarCliente(Clientes clientes) {
        String sql = "INSERT INTO tb_cliente (nombre, rut, celular, correo) VALUES (?, ?, ?, ?)";
        
        try {
            Connection connection = Conexion.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Setear los valores de la consulta
            preparedStatement.setString(1, clientes.getNombre());
            preparedStatement.setString(2, clientes.getRut());
            preparedStatement.setInt(3, clientes.getCelular());
            preparedStatement.setString(4, clientes.getCorreo());

            // Ejecutar la consulta
            preparedStatement.executeUpdate();
            System.out.println("Cliente" + clientes.getNombre() + "Agregado a la base de datos");
            
        } catch (SQLException e) {
            System.out.println("Error al agregar el cliente a la base de datos: " + e.getMessage());
        }
    }

    // Metodo para eliminar cliente
    public void eliminarCliente(Clientes clientes) {
        String sql = "DELETE FROM tb_cliente WHERE rut = ?";

        try {
            Connection connection = Conexion.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Setear el valor de la consulta
            preparedStatement.setString(1, clientes.getRut());

            // Ejecutar la consulta
            preparedStatement.executeUpdate();
            System.out.println("Cliente" + clientes.getRut() + "Eliminado de la base de datos");
            
        } catch (SQLException e) {
            System.out.println("Error al eliminar el cliente de la base de datos: " + e.getMessage());
        }
    }

    // Metodo para actualizar cliente de la base de datos
    public void actualizarCliente(Clientes clientes) {
        String sql = "UPDATE tb_cliente SET nombre = ?, rut = ?, celular = ?, correo = ? WHERE rut = ?";

        try {
            Connection connection = Conexion.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Setear los valores de la consulta
            preparedStatement.setString(1, clientes.getNombre());
            preparedStatement.setString(2, clientes.getRut());
            preparedStatement.setInt(3, clientes.getCelular());
            preparedStatement.setString(4, clientes.getCorreo());
            preparedStatement.setString(5, clientes.getRut());

            // Ejecutar la consulta
            preparedStatement.executeUpdate();
            System.out.println("Cliente" + clientes.getNombre() + "Actualizado en la base de datos");

        } catch (SQLException e) {
            System.out.println("Error al actualizar el cliente en la base de datos: " + e.getMessage());
        }
    }

    public List<Clientes> obtenerClientes() {
        List<Clientes> listaClientes = new ArrayList<>();
        String sql = "SELECT * FROM tb_cliente";

        try {
            Connection connection = Conexion.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            // Recorrer el resultado de la consulta
            while (resultSet.next()) {
                Clientes cliente = new Clientes();
                cliente.setNombre(resultSet.getString("nombre"));
                cliente.setRut(resultSet.getString("rut"));
                cliente.setCelular(resultSet.getInt("celular"));
                cliente.setCorreo(resultSet.getString("correo"));

                // Agregar el cliente a la lista
                listaClientes.add(cliente);
            }

        } catch (SQLException e) {  
            System.out.println("Error al obtener los clientes de la base de datos: " + e.getMessage());
        }
        return listaClientes;
    }
}   
