package Controlador;

import javax.swing.JTextField;
import javax.swing.JOptionPane;

public class Controlador {

    public Controlador () { }

    // Metodo para verificar si el campo esta vacio
    public boolean verificarCampoVacio(JTextField campo, String mensaje) {
        if (campo.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, mensaje);
            return false;
        }
        return true;
    }

    // Metodo para validar ingreso solo numeros y si es mayor a 0
    public boolean validarNumero(JTextField campo, String mensaje) {
        if (!campo.getText().matches("[0-9]+")) {
            JOptionPane.showMessageDialog(null, mensaje);
            return false;
        }

        if (Integer.parseInt(campo.getText()) <= 0) {
            JOptionPane.showMessageDialog(null, mensaje);
            return false;
        }
        return true;
    }

    // Metodo para validar correo
    public static boolean validarCorreo(String correo) {
        String regex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        return correo != null && correo.matches(regex);
    }
}
