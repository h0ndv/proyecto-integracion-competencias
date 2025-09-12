package Utils;

/**
 * Clase para validacion robusta de RUT chileno
 * Implementa el algoritmo oficial de validacion de RUT
 */
public class ValidacionRut {
    
    /**
     * Valida si un RUT chileno es valido
     * @param rut RUT a validar (puede contener puntos y guiones)
     * @return true si el RUT es valido, false en caso contrario
     */
    public static boolean isValidRut(String rut) {
        if (rut == null || rut.trim().isEmpty()) {
            return false;
        }
        
        // Limpiar y normalizar el RUT
        String rutLimpio = limpiarRut(rut);
        
        // Validar formato basico
        if (!tieneFormatoValido(rutLimpio)) {
            return false;
        }
        
        // Extraer cuerpo y digito verificador
        String cuerpo = rutLimpio.substring(0, rutLimpio.length() - 1);
        char dv = rutLimpio.charAt(rutLimpio.length() - 1);
        
        // Validar que el cuerpo sea numerico
        if (!cuerpo.matches("\\d+")) {
            return false;
        }
        
        // Calcular digito verificador
        char dvCalculado = calcularDigitoVerificador(cuerpo);
        
        return dv == dvCalculado;
    }
    
    /**
     * Valida un RUT y retorna mensaje de error especifico
     * @param rut RUT a validar
     * @return null si es valido, mensaje de error si no es valido
     */
    public static String validarRutConMensaje(String rut) {
        if (rut == null || rut.trim().isEmpty()) {
            return "El RUT no puede estar vacío";
        }
        
        String rutLimpio = limpiarRut(rut);
        
        if (!tieneFormatoValido(rutLimpio)) {
            return "El RUT debe tener entre 7 y 8 dígitos seguidos de un dígito verificador";
        }
        
        String cuerpo = rutLimpio.substring(0, rutLimpio.length() - 1);
        char dv = rutLimpio.charAt(rutLimpio.length() - 1);
        
        if (!cuerpo.matches("\\d+")) {
            return "El cuerpo del RUT debe contener solo números";
        }
        
        char dvCalculado = calcularDigitoVerificador(cuerpo);
        
        if (dv != dvCalculado) {
            return "El dígito verificador no es válido (debería ser " + dvCalculado + ")";
        }
        
        return null; // RUT válido
    }
    
    /**
     * Formatea un RUT con puntos y guion
     * @param rut RUT sin formato
     * @return RUT formateado (ej: 12.345.678-9)
     */
    public static String formatearRut(String rut) {
        if (rut == null || rut.trim().isEmpty()) {
            return "";
        }
        
        String rutLimpio = limpiarRut(rut);
        
        if (!tieneFormatoValido(rutLimpio)) {
            return rut; // Retornar original si no es válido
        }
        
        String cuerpo = rutLimpio.substring(0, rutLimpio.length() - 1);
        char dv = rutLimpio.charAt(rutLimpio.length() - 1);
        
        // Agregar puntos cada 3 digitos desde la derecha
        StringBuilder cuerpoFormateado = new StringBuilder();
        int contador = 0;
        
        for (int i = cuerpo.length() - 1; i >= 0; i--) {
            if (contador > 0 && contador % 3 == 0) {
                cuerpoFormateado.insert(0, ".");
            }
            cuerpoFormateado.insert(0, cuerpo.charAt(i));
            contador++;
        }
        
        return cuerpoFormateado.toString() + "-" + dv;
    }
    
    /**
     * Limpia un RUT removiendo puntos, guiones y espacios
     * @param rut RUT a limpiar
     * @return RUT limpio en mayusculas
     */
    public static String limpiarRut(String rut) {
        if (rut == null) {
            return "";
        }
        
        return rut.replaceAll("[^0-9Kk]", "").toUpperCase();
    }
    
    /**
     * Verifica si el RUT tiene el formato basico correcto
     * @param rut RUT limpio
     * @return true si tiene formato valido
     */
    private static boolean tieneFormatoValido(String rut) {
        // Debe tener entre 7 y 8 digitos + 1 digito verificador
        return rut.matches("\\d{7,8}[0-9Kk]");
    }
    
    /**
     * Calcula el digito verificador de un RUT
     * @param cuerpo Cuerpo del RUT (solo numeros)
     * @return Digito verificador calculado
     */
    private static char calcularDigitoVerificador(String cuerpo) {
        int suma = 0;
        int multiplicador = 2;
        
        // Procesar digitos de derecha a izquierda
        for (int i = cuerpo.length() - 1; i >= 0; i--) {
            suma += Character.getNumericValue(cuerpo.charAt(i)) * multiplicador;
            multiplicador = (multiplicador == 7) ? 2 : multiplicador + 1;
        }
        
        int resto = 11 - (suma % 11);
        
        return (resto == 11) ? '0' : (resto == 10) ? 'K' : (char) (resto + '0');
    }
    
    /**
     * Extrae solo el cuerpo del RUT (sin digito verificador)
     * @param rut RUT completo
     * @return Cuerpo del RUT
     */
    public static String extraerCuerpo(String rut) {
        String rutLimpio = limpiarRut(rut);
        if (rutLimpio.length() < 2) {
            return "";
        }
        return rutLimpio.substring(0, rutLimpio.length() - 1);
    }
    
    /**
     * Extrae solo el digito verificador del RUT
     * @param rut RUT completo
     * @return Digito verificador
     */
    public static char extraerDigitoVerificador(String rut) {
        String rutLimpio = limpiarRut(rut);
        if (rutLimpio.isEmpty()) {
            return '0';
        }
        return rutLimpio.charAt(rutLimpio.length() - 1);
    }
}
