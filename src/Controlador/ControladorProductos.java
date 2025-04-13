package Controlador;

import Modelo.Productos;
import Modelo.ProductosDAO;

import java.util.List;

import javax.swing.JComboBox;

public class ControladorProductos {
    private final ProductosDAO productosDAO = new ProductosDAO();

    // Constructor 
    public ControladorProductos() {    }

    public String validarProducto(String nombre, String precio, String cantidad, String categoria) {
        try {
            
            // Se convierten los datos a enteros
            int precioInt = Integer.parseInt(precio);
            int cantidadInt = Integer.parseInt(cantidad);
            int categoriaInt = Integer.parseInt(categoria);
            
            // Validaciones
            if (categoriaInt < 0 || categoriaInt > 5) {
                System.out.println(categoriaInt);
                return "Categoria invalida";
            }

            if (cantidadInt <= 0) {
                return "La cantidad del producto debe ser mayor que 0";
            } 

            if (precioInt <= 0) {
                return "El precio del producto debe ser mayor que 0";
            }
            
            // Se crea un objeto tipo producto
            Productos producto = new Productos();
            producto.setNombreProducto(nombre);
            producto.setPrecio(precioInt);
            producto.setCantidad(cantidadInt);
            producto.setCategoria(categoriaInt);

            // Si se cumplen las validaciones se inserta el producto en la base de datos
            productosDAO.insertarProducto(producto);
            return null;

        } catch (NumberFormatException e) {
            return "Error al convertir los datos a enteros";
        } catch (Exception e) {
            return "Error al agregar el producto en la base de datos" + e.getMessage();
        }
    }

    // Metodo para cargar las categorias en un comboBox
    public void cargarCategorias(JComboBox<String> JComboBoxCategorias) {
        productosDAO.cargarCategorias(JComboBoxCategorias);
    }

    // Modificar producto
    public void modificarProducto(Productos producto) {
        productosDAO.modificarProducto(producto);
    }

    // Eliminar producto
    public void eliminarProducto(Productos producto) {
        productosDAO.eliminarProducto(producto);
    }

    // Metodo para consultar los productos de la base de datos
    public List<Productos> obtenerProductos() {
        return productosDAO.obtenerProductos();
    }
    
}
