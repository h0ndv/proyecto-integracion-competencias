package Controlador;

import Modelo.Asistencia;
import DAO.AsistenciaDAO;

import java.util.List;
import javax.swing.JOptionPane;

public class ControladorAsistencia {
    private AsistenciaDAO asistenciaDAO;
    
    public ControladorAsistencia() {
        asistenciaDAO = new AsistenciaDAO();
    }
    
    // Marcar entrada de un usuario
    public String marcarEntrada(int idUsuario) {
        try {
            // Verificar si ya marco entrada hoy
            if (asistenciaDAO.verificarEntradaMarcada(idUsuario)) {
                return "Ya marcaste entrada hoy";
            }
            
            // Marcar entrada
            boolean exito = asistenciaDAO.marcarEntrada(idUsuario);
            
            if (!exito) {
                return "Error al marcar entrada";
            }              
            
            return "Entrada marcada correctamente";           
            
        } catch (Exception e) {
            System.out.println("Error en marcarEntrada: " + e.getMessage());
            return "Error al marcar entrada: " + e.getMessage();
        }
    }
    
    // Marcar salida de un usuario
    public String marcarSalida(int idUsuario) {
        try {
            // Verificar si ya marco salida hoy
            if (asistenciaDAO.yaMarcoSalida(idUsuario)) {
                return "Ya marcaste salida hoy";
            }
            
            // Verificar si marco entrada hoy
            if (!asistenciaDAO.verificarEntradaMarcada(idUsuario)) {
                return "Debes marcar entrada primero";
            }
            
            // Marcar salida
            boolean exito = asistenciaDAO.marcarSalida(idUsuario);
            
            if (!exito) {
                return "Error al marcar salida";
            }
            
            return "Salida marcada correctamente";           
            
            
        } catch (Exception e) {
            System.out.println("Error en marcarSalida: " + e.getMessage());
            return "Error al marcar salida: " + e.getMessage();
        }
    }
    
    // Verificar estado de asistencia del usuario
    public String obtenerEstadoAsistencia(int idUsuario) {
        try {
            boolean marcadoEntrada = asistenciaDAO.verificarEntradaMarcada(idUsuario);
            boolean marcadoSalida = asistenciaDAO.yaMarcoSalida(idUsuario);
            
            // Determinar estado basado en las condiciones usando switch
            int estadoCodigo = (marcadoEntrada ? 1 : 0) + (marcadoSalida ? 2 : 0);
            
            switch (estadoCodigo) {
                case 0: // No marco entrada ni salida
                    return "PENDIENTE_ENTRADA";
                case 1: // marco entrada pero no salida
                    return "PENDIENTE_SALIDA";
                case 3: // marco entrada y salida
                    return "COMPLETO";
                default:
                    return "ERROR";
            }
            
        } catch (Exception e) {
            System.out.println("Error en obtenerEstadoAsistencia: " + e.getMessage());
            return "ERROR";
        }
    }
    
    // Obtener asistencias de un usuario
    public List<Asistencia> obtenerAsistenciasUsuario(int idUsuario) {
        return asistenciaDAO.obtenerAsistenciasUsuario(idUsuario);
    }
    
    // Obtener todas las asistencias
    public List<Asistencia> obtenerTodasAsistencias() {
        return asistenciaDAO.obtenerTodasAsistencias();
    }
    
    // Obtener asistencias por rango de fechas
    public List<Asistencia> obtenerAsistenciasPorFecha(String fechaInicio, String fechaFin) {
        return asistenciaDAO.obtenerAsistenciasPorFecha(fechaInicio, fechaFin);
    }
    
    // Mostrar mensaje de estado de asistencia
    public void mostrarEstadoAsistencia(int idUsuario) {
        String estado = obtenerEstadoAsistencia(idUsuario);
        String mensaje = "";
        
        switch (estado) {
            case "PENDIENTE_ENTRADA":
                mensaje = "Puedes marcar tu entrada";
                break;
            case "PENDIENTE_SALIDA":
                mensaje = "Puedes marcar tu salida";
                break;
            case "COMPLETO":
                mensaje = "Ya completaste tu asistencia de hoy";
                break;
            case "ERROR":
                mensaje = "Error al verificar estado de asistencia";
                break;
            default:
                mensaje = "Estado desconocido";
                break;
        }
        
        JOptionPane.showMessageDialog(null, mensaje, "Estado de Asistencia", JOptionPane.INFORMATION_MESSAGE);
    }
    
    // Validar formato de fecha
    public boolean validarFormatoFecha(String fecha) {
        if (fecha == null || fecha.trim().isEmpty()) {
            return false;
        }
        
        // Formato esperado: YYYY-MM-DD
        return fecha.matches("\\d{4}-\\d{2}-\\d{2}");
    }
    
    // Validar rango de fechas
    public String validarRangoFechas(String fechaInicio, String fechaFin) {
        if (!validarFormatoFecha(fechaInicio)) {
            return "Formato de fecha inicio invalido (usar YYYY-MM-DD)";
        }
        
        if (!validarFormatoFecha(fechaFin)) {
            return "Formato de fecha fin invalido (usar YYYY-MM-DD)";
        }
        
        if (fechaInicio.compareTo(fechaFin) > 0) {
            return "La fecha inicio debe ser anterior a la fecha fin";
        }
        
        return null;
    }
}
