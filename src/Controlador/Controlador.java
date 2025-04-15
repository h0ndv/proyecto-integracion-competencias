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

    // Metodo para validar el rut ingresado
    public static boolean validarRut(String rut) {
        if (rut == null || rut.length() < 8 || rut.length() > 9) 
            return false;

        rut = rut.replaceAll("[^0-9Kk]", "").toUpperCase();
        if (rut.length() < 8 || rut.length() > 9)
            return false;

        String cuerpo = rut.substring(0, rut.length() - 1);
        char dv = rut.charAt(rut.length() - 1);

        try {
            int rutNum = Integer.parseInt(cuerpo);
            return calcularDV(rutNum) == dv;

        } catch (NumberFormatException e) {
            System.out.println("Error al convertir el rut a numero: " + e.getMessage());
            return false;
        }
    }

    // Metodo para calcular el digito verifiacador del rut
    private static char calcularDV(int rut) {
        int suma = 0, multiplicador = 2;

        while (rut > 0) {
            suma += (rut % 10) * multiplicador;
            rut /= 10;
            multiplicador = (multiplicador == 7) ? 2 : multiplicador + 1;
        }

        int resto = 11 - (suma % 11);
        return (resto == 11) ? '0' : (resto == 10) ? 'K' : (char)(resto + '0');
    }
    
}
