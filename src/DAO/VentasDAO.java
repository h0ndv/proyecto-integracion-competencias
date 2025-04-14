package DAO;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conexion.Conexion;
import Modelo.Ventas;

public class VentasDAO {
    public void agregarVenta(Ventas venta) {
        String sql = "INSERT INTO tb_ventas (id_usuario, producto, precio, cantidad) VALUES (?, ?, ?, ?)";
        // String sql = "INSERT INTO tb_det_in_ventas ()"
        
        try {
            Connection connection = Conexion.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            // sETear los valores de la consulta
            preparedStatement.setInt(1, venta.getId_usuario());
            preparedStatement.setString(2, venta.getProducto());
            preparedStatement.setInt(3, venta.getPrecio());
            preparedStatement.setInt(4, venta.getCantidad());
            
            // Ejecutar la consulta
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al agregar la venta: " + e.getMessage());
        }
    }

    // Eliminar una venta
    public void eliminarVenta(int id_venta) {
        // Consulta sql
        String sql = "DELETE FROM tb_ventas WHERE id_venta = ?";
        try {
            Connection connection = Conexion.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id_venta);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al eliminar la venta: " + e.getMessage());
        }
    } 

    // Obtener todas las ventas
    public List<Ventas> obtenerVentas() {
        List<Ventas> listaVentas = new ArrayList<>();
        
        // Consulta sql        
        String sql = "SELECT * FROM tb_ventas";
        try {
            Connection connection = Conexion.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            // Recorrer el resultado de la consulta
            while (resultSet.next()) {
                Ventas venta = new Ventas();
                venta.setProducto(resultSet.getString("fecha"));
                venta.setCantidad(resultSet.getInt("total"));
                venta.setPrecio(resultSet.getInt("tipo_compra"));

                // Agregar venta a la lista
                listaVentas.add(venta);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener las ventas: " + e.getMessage());
        }
        return listaVentas;
    }
}
