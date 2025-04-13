package Modelo;

import Conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;

public class ProductosDAO {
    public void insertarProducto(Productos productos) {
        // Consulta sql
        String sql = "INSERT into tb_productos (nombre, id_categoria, cantidad, precio) VALUES (?, ?, ?, ?)";

        try {
            // Obtener la conexion
            Connection connection = Conexion.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Setear los valores de la consulta
            preparedStatement.setString(1, productos.getNombreProducto());
            preparedStatement.setInt(2, productos.getCategoria());
            preparedStatement.setInt(3, productos.getCantidad());
            preparedStatement.setInt(4, productos.getPrecio());

            // Ejecutar la consulta
            preparedStatement.executeUpdate();
            System.out.println("Producto" + productos.getNombreProducto() + "Agregado a la base de datos");
        
        } catch (SQLException e) {
            System.out.println("Error al agregar el producto a la base de datos: " + e.getMessage());
        }
    }

    public void eliminarProducto(Productos productos) {
        // Consulta sql
        String sql = "DELETE FROM tb_productos where nombre = ?";

        try {
            Connection connection = Conexion.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Setear los valores de la consulta
            preparedStatement.setString(1, productos.getNombreProducto());

            // Ejecutar la consulta
            preparedStatement.executeUpdate();
            System.out.println("Producto eliminado de la base de datos");

        } catch (SQLException e) {
            System.out.println("Error al eliminar el producto de la base de datos: " + e.getMessage());
        }
    }

    public void actualizarProducto(Productos productos) {
        // Consulta sql
        String sql = "UPDATE tb_productos SET WHERE nombre = ? categoria = ?, cantidad = ?, precio = ?";

        try {
            Connection connection = Conexion.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Setear los valores de la consulta
            preparedStatement.setString(1, productos.getNombreProducto());
            preparedStatement.setInt(2, productos.getCategoria());
            preparedStatement.setInt(3, productos.getCantidad());
            preparedStatement.setInt(4, productos.getPrecio());

            // Ejecutar la consulta
            preparedStatement.executeUpdate();
            System.out.println("Producto actualizado en la base de datos");
            
        } catch (SQLException e) {
            System.out.println("Error al actualizar el producto en la base de datos:" + e.getMessage());
        }
    }

    // Metodo para cargar las categorias de la abse de daros en el comboBox
    public void cargarCategorias(JComboBox<String> JComboBoxCategorias) {
        // Consulta sql
        String sql = "SELECT * from tb_cat_productos";

        try {
            Connection connection = Conexion.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Limpiar el comboBox
            JComboBoxCategorias.removeAllItems();

            // Recorrer el resultado de la consulta
            while (resultSet.next()) {
                JComboBoxCategorias.addItem(resultSet.getString("nombre"));
                System.out.println(resultSet.getString("id_categoria"));
            }

        } catch (SQLException e) {
            System.out.println("Error al cargar las categorias en el comboBox:" + e.getMessage());
        }
    }

    // Metodo para obtener los productos de la base de datos
    public List<Productos> obtenerProductos() {
        List<Productos> listaProductos = new ArrayList<>();

        // Consulta sql
        String sql = "SELECT nombre, id_categoria, cantidad, precio FROM tb_productos";

        try {
            Connection connection = Conexion.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            // Recorrer el resultado de la consulta
            while (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                int categoria = resultSet.getInt("id_categoria");
                int cantidad = resultSet.getInt("cantidad");
                int precio = resultSet.getInt("precio");

                // Crear objeto tipo producto
                Productos producto = new Productos();
                producto.setNombreProducto(nombre);
                producto.setCategoria(categoria);
                producto.setCantidad(cantidad);
                producto.setPrecio(precio);

                // Agregar el producto a la lista
                listaProductos.add(producto);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener los productos de la base de datos: " + e.getMessage());
        }
        return listaProductos;
    }

    // Metodo para modificar un producto de la base de datos
    public void modificarProducto(Productos productos) {
        // Consulta sql
        String sql = "UPDATE tb_productos SET nombre = ?, id_categoria = ?, cantidad = ?, precio = ? WHERE nombre = ?"; 

        try {
            Connection connection = Conexion.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            // Setear los valores de la consulta
            preparedStatement.setString(1, productos.getNombreProducto());
            preparedStatement.setInt(2, productos.getCategoria());
            preparedStatement.setInt(3, productos.getCantidad());
            preparedStatement.setInt(4, productos.getPrecio());
            preparedStatement.setString(5, productos.getNombreProducto());

            // Ejecutar la consulta
            preparedStatement.executeUpdate();
            System.out.println("Producto" + productos.getNombreProducto() + "modificado en la base de datos");

        } catch (SQLException e) {
            System.out.println("Error al modificar el producto en la base de datos: " + e.getMessage());
        }
    }
// end
}
