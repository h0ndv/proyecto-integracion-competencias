package Controlador;

public class Controlador {

    public Controlador () { }

    // Metodo para verificar si el campo esta vacio
    public static boolean verificarCampoVacio(String mensaje) {
        if (mensaje.isEmpty()) {
            return true;
        }
        return false;
    }

    // Metodo para validar ingreso solo numeros y si es mayor a 0
    public static boolean validarNumero(String mensaje) {
        if (!mensaje.matches("[0-9]+")) {
            return true;
        }

        if (Integer.parseInt(mensaje) <= 0) {
            return true;
        }
        return false;
    }

    // Metodo para validar correo
    public static boolean validarCorreo(String correo) {
        String regex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        return correo != null && correo.matches(regex);
    }

    // Metodo para validar el rut ingresado
    public static String validarRut(String rut) {
        if (rut == null || rut.isEmpty()) {
            return "El RUT no puede estar vacio";
        }
    
        rut = rut.replaceAll("[^0-9Kk]", "").toUpperCase();
    
        if (rut.length() < 8 || rut.length() > 9) {
            return "El RUT debe tener entre 8 y 9 digitos";
        }
    
        String cuerpo = rut.substring(0, rut.length() - 1);
        char dv = rut.charAt(rut.length() - 1);
    
        try {
            int rutNum = Integer.parseInt(cuerpo);
            char dvCalculado = calcularDV(rutNum);
    
            if (dv != dvCalculado) {
                return "El digito verificador no es valido (deberia ser " + dvCalculado + ")";
            }
        } catch (NumberFormatException e) {
            return "El cuerpo del RUT debe contener solo numeros.";
        }
    
        return null;
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
