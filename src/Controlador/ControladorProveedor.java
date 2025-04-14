package Controlador;

import Modelo.Proveedores;

import java.util.List;

import DAO.ProveedoresDAO;

public class ControladorProveedor {
    private ProveedoresDAO proveedoresDAO;

    public ControladorProveedor() {
        proveedoresDAO = new ProveedoresDAO();
    }

    // Metodo para validar los datos del proveedor
    public String validarProveedor(String nombre, String empresa, String celular, String correo) {
        try {
            if (nombre.isEmpty() || empresa.isEmpty() || celular.isEmpty() || correo.isEmpty()) {
                return "Debe completar todos los campos";
            }

            // Numero de telefono debe tener solo 9 numeros
            if (celular.length() != 9) {
                return "El numero de celular debe tener solo 9 digitos";
            } 

            // Validar correo
            if (!Controlador.validarCorreo(correo)) {
                return "El correo no es valido";
            }

            // Crear objeto tipo proveedor
            Proveedores proveedores = new Proveedores();
            proveedores.setNombre(nombre);
            proveedores.setEmpresa(empresa);
            proveedores.setCelular(celular);
            proveedores.setCorreo(correo);

            // Agregar proveedor a la base de datos
            proveedoresDAO.agregarProveedor(proveedores);
            return null;

        } catch (NumberFormatException e) {
            return "Error al convertir los datos a enteros";
        } catch (Exception e) {
            return "Error al agregar el proveedor en la base de datos" + e.getMessage();
        }
    }

    public void eliminarProveedor(Proveedores proveedores) {
        proveedoresDAO.eliminarProveedor(proveedores);
    }

    public void actualizarProveedor(Proveedores proveedores) {
        proveedoresDAO.actualizarProveedor(proveedores);
    }

    // Controlador para cargar los proveedores de la abse de datos
    public List<Proveedores> obtenerProveedores() {
        return proveedoresDAO.obtenerProveedores();
    }
    
    // Controlador modifcar proveedor
    public void modificarProveedor(Proveedores proveedores) {
        proveedoresDAO.modificarProveedor(proveedores);
    }
}
