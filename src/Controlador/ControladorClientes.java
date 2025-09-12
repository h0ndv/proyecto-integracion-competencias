package Controlador;

import Modelo.Clientes;
import Utils.ValidacionRut;

import java.util.List;

import DAO.ClientesDAO;

public class ControladorClientes {
    private ClientesDAO clientesDAO;

    public ControladorClientes() {
        clientesDAO = new ClientesDAO();
    }

    // Metodo para validar un cliente
    public String validarCliente(String nombre, String rut, String celular, String correo) {
        if (nombre.isEmpty() || rut.isEmpty() || celular.isEmpty() || correo.isEmpty()) {
            return "Debe completar todos los campos";
        }

        String errorRut = ValidacionRut.validarRutConMensaje(rut);
        if (errorRut != null) {
            return errorRut;
        }

        if (celular.length() != 8) {
            return "El numero de celular debe tener 8 digitos";
        }

        if (!Controlador.validarCorreo(correo)) {
            return "El correo no es valido";
        }
        
        try {
            int celularInt = Integer.parseInt(celular);

            Clientes cliente = new Clientes();
            cliente.setNombre(nombre);
            cliente.setRut(rut);
            cliente.setCelular(celularInt);
            cliente.setCorreo(correo);

            clientesDAO.agregarCliente(cliente);
            System.out.println("Cliente " + nombre + " agregado correctamente");
            return null;

        } catch (Exception e) {
            System.out.println("Error al agregar el cliente: " + e.getMessage());
            return "Error al agregar el cliente: " + e.getMessage();
        }
    }

    //Metodo para cargar los clientes de la base de datos
    public List<Clientes> obtenerClientes() {
        return clientesDAO.obtenerClientes();
    }

    // Metodo para eliminar un cliente de la abse de datos
    public void eliminarCliente(Clientes clientes) {
        clientesDAO.eliminarCliente(clientes);
    }

    // Metodo para actualizar un cliente de la base de datos
    public void actualizarCliente(Clientes clientes) {
        clientesDAO.actualizarCliente(clientes);
    }
}
