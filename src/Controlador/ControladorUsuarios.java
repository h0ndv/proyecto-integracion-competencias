package Controlador;

import Modelo.Usuarios;
import Modelo.UsuariosDAO;

import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

import Vista.Vista;

public class ControladorUsuarios {
    private UsuariosDAO usuariosDAO;
    
    public ControladorUsuarios() {
        usuariosDAO = new UsuariosDAO();
    }

    // Metodo para validar inicio de sesion
    public boolean validarUsuario(String rut, String pin) {
        // Validaciones
        // Ingresar solo numeros en los campos
        if (!rut.matches("[0-9]+") || !pin.matches("[0-9]+")) {
            JOptionPane.showMessageDialog(null, "Debe ingresar solo numeros");
            return false;
        }
        
        // Se convierten las variables numeroCuenta y pin a tipo int
        try {
            int Rut = Integer.parseInt(rut);
            int Pin = Integer.parseInt(pin);

            // Crear un objeto tipo cuenta y setear parametros de la cuenta
            Usuarios usuarios = new Usuarios();
            usuarios.setRutUsuario(Rut);
            usuarios.setPin(Pin);

            // Validar el usuario en la base de datos 
            boolean esValido = usuariosDAO.iniciarSesion(usuarios);

            // Si los datos son incorrectos se muestra un mensaje de error
            if (!esValido) {
                System.out.println("ControladorUsuarios.validarUsuario() - Datos incorrectos");
                return false;
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Debe ingresar solo numeros");
            return false;
        }
        System.out.println("ControladorUsuarios.validarUsuario() - Datos correctos" + rut + " " + pin);
        return true;
    }
    // Controlador para validar el usuario  
    public String validarUsuario(int cargo,String nombre, String rut, String clave, String correo) {
        if (nombre.isEmpty() || rut.isEmpty() || clave.isEmpty() || correo.isEmpty()) {
            return "Debe completar todos los campos";
        }

        if (rut.length() != 9) {
            return "El rut debe tener 9 digitos";
        }

        if (cargo != 1 && cargo != 2 && cargo != 3) {
            return "Cargo no valido";
        }

        if (!Controlador.validarCorreo(correo)) {
            return "El correo no es valido";
        }

        try {
            int rutInt = Integer.parseInt(rut);
            int claveInt = Integer.parseInt(clave);

            Usuarios usuarios = new Usuarios();
            usuarios.setNombre(nombre);
            usuarios.setRutUsuario(rutInt);
            usuarios.setPin(claveInt);
            usuarios.setCorreo(correo);
            usuarios.setId_cargo(cargo);

            crearUsuario(usuarios);
            System.out.println("Usuario creado " + usuarios.getNombre() + " " + usuarios.getRutUsuario() + " " + usuarios.getPin() + " " + usuarios.getCorreo());
            return null;
            
        } catch (NumberFormatException e) {
            return "Debe ingresar solo numeros";
        } 
    }

    // Metodo para cerrar la sesion
    public void cerrarSesion() {
        // Se cierra la sesion
        usuariosDAO.cerrarSesion();

        // Se abre la ventana de inicio de sesion
        Vista vista = new Vista();
        vista.setVisible(true);
    }

    // Controlador par amodificar un usuario
    public void modificarUsuario(Usuarios usuarios) {
        usuariosDAO.modificarUsuario(usuarios);
    }

    // Controlador para eliminar un usuario
    public void eliminarUsuario(Usuarios usuarios) {
        usuariosDAO.eliminarUsuario(usuarios);
    }

    // Metodo para obtener el cargo del usuario
    public int obtenerCargo() {
        return usuariosDAO.obtenerCargo();
    }

    // Metodo para cargar los cargos en el comboBox
    public void cargarCargos(JComboBox<String> jComboBoxCargo) {
        usuariosDAO.cargarCargos(jComboBoxCargo);
    }

    // Controlador para crear un usuario
    public void crearUsuario(Usuarios usuarios) {
        usuariosDAO.crearUsuario(usuarios);
    }

    // Controlador para obtener los usuarios
    public List<Usuarios> obtenerUsuarios() {
        return usuariosDAO.obtenerUsuarios();
    }
}

