package DAO;

import Conexion.Conexion;
import Modelo.Asistencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AsistenciaDAO {
    
    // Metodo para marcar entrada
    public boolean marcarEntrada(int idUsuario) {
        String sql = "INSERT INTO tb_asistencia (id_usuario, fecha, hora_entrada, estado) VALUES (?, ?, ?, ?)";
        
        try {
            Connection connection = Conexion.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String hora = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
            
            preparedStatement.setInt(1, idUsuario);
            preparedStatement.setString(2, fecha);
            preparedStatement.setString(3, hora);
            preparedStatement.setString(4, "ENTRADA");
            
            int filasAfectadas = preparedStatement.executeUpdate();
            preparedStatement.close();
            
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al marcar entrada: " + e.getMessage());
            return false;
        }
    }
    
    // Metodo para marcar salida
    public boolean marcarSalida(int idUsuario) {
        String sql = "UPDATE tb_asistencia SET hora_salida = ?, estado = ? WHERE id_usuario = ? AND fecha = ? AND estado = 'ENTRADA'";
        
        try {
            Connection connection = Conexion.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            String hora = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
            
            preparedStatement.setString(1, hora);
            preparedStatement.setString(2, "COMPLETO");
            preparedStatement.setInt(3, idUsuario);
            preparedStatement.setString(4, fecha);
            
            int filasAfectadas = preparedStatement.executeUpdate();
            preparedStatement.close();
            
            return filasAfectadas > 0;
            
        } catch (SQLException e) {
            System.out.println("Error al marcar salida: " + e.getMessage());
            return false;
        }
    }
    
    // Verificar si ya marco entrada hoy
    public boolean verificarEntradaMarcada(int idUsuario) {
        String sql = "SELECT COUNT(*) FROM tb_asistencia WHERE id_usuario = ? AND fecha = ? AND estado IN ('ENTRADA', 'COMPLETO')";
        
        try {
            Connection connection = Conexion.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            
            preparedStatement.setInt(1, idUsuario);
            preparedStatement.setString(2, fecha);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                resultSet.close();
                preparedStatement.close();
                return count > 0;
            }
            
            resultSet.close();
            preparedStatement.close();
            return false;
            
        } catch (SQLException e) {
            System.out.println("Error al verificar entrada: " + e.getMessage());
            return false;
        }
    }
    
    // Verificar si ya marco salida hoy
    public boolean yaMarcoSalida(int idUsuario) {
        String sql = "SELECT COUNT(*) FROM tb_asistencia WHERE id_usuario = ? AND fecha = ? AND estado = 'COMPLETO'";
        
        try {
            Connection connection = Conexion.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            String fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            
            preparedStatement.setInt(1, idUsuario);
            preparedStatement.setString(2, fecha);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                resultSet.close();
                preparedStatement.close();
                return count > 0;
            }
            
            resultSet.close();
            preparedStatement.close();
            return false;
            
        } catch (SQLException e) {
            System.out.println("Error al verificar salida: " + e.getMessage());
            return false;
        }
    }
    
    // Obtener asistencias de un usuario
    public List<Asistencia> obtenerAsistenciasUsuario(int idUsuario) {
        List<Asistencia> asistencias = new ArrayList<>();
        String sql = "SELECT a.*, u.nombre FROM tb_asistencia a " +
                    "INNER JOIN tb_usuarios u ON a.id_usuario = u.id_usuario " +
                    "WHERE a.id_usuario = ? ORDER BY a.fecha DESC, a.hora_entrada DESC";
        
        System.out.println("DEBUG DAO: Consultando asistencias para usuario ID: " + idUsuario);
        System.out.println("DEBUG DAO: SQL: " + sql);
        
        try {
            Connection connection = Conexion.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, idUsuario);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("DEBUG DAO: Consulta ejecutada exitosamente");
            
            int contador = 0;
            while (resultSet.next()) {
                contador++;
                System.out.println("DEBUG DAO: Procesando fila " + contador);
                
                Asistencia asistencia = new Asistencia();
                asistencia.setIdAsistencia(resultSet.getInt("id_asistencia"));
                asistencia.setIdUsuario(resultSet.getInt("id_usuario"));
                asistencia.setFecha(resultSet.getString("fecha"));
                asistencia.setHoraEntrada(resultSet.getString("hora_entrada"));
                asistencia.setHoraSalida(resultSet.getString("hora_salida"));
                asistencia.setEstado(resultSet.getString("estado"));
                asistencia.setNombreUsuario(resultSet.getString("nombre"));
                
                System.out.println("DEBUG DAO: Asistencia creada - Fecha: " + asistencia.getFecha() + 
                                 ", Entrada: " + asistencia.getHoraEntrada() + 
                                 ", Salida: " + asistencia.getHoraSalida() + 
                                 ", Estado: " + asistencia.getEstado());
                
                asistencias.add(asistencia);
            }
            
            System.out.println("DEBUG DAO: Total de filas procesadas: " + contador);
            
            resultSet.close();
            preparedStatement.close();
            
        } catch (SQLException e) {
            System.out.println("Error al obtener asistencias: " + e.getMessage());
        }
        
        return asistencias;
    }
    
    // Obtener todas las asistencias (para administrador)
    public List<Asistencia> obtenerTodasAsistencias() {
        List<Asistencia> asistencias = new ArrayList<>();
        String sql = "SELECT a.*, u.nombre FROM tb_asistencia a " +
                    "INNER JOIN tb_usuarios u ON a.id_usuario = u.id_usuario " +
                    "ORDER BY a.fecha DESC, u.nombre ASC";
        
        try {
            Connection connection = Conexion.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                Asistencia asistencia = new Asistencia();
                asistencia.setIdAsistencia(resultSet.getInt("id_asistencia"));
                asistencia.setIdUsuario(resultSet.getInt("id_usuario"));
                asistencia.setFecha(resultSet.getString("fecha"));
                asistencia.setHoraEntrada(resultSet.getString("hora_entrada"));
                asistencia.setHoraSalida(resultSet.getString("hora_salida"));
                asistencia.setEstado(resultSet.getString("estado"));
                asistencia.setNombreUsuario(resultSet.getString("nombre"));
                
                asistencias.add(asistencia);
            }
            
            resultSet.close();
            preparedStatement.close();
            
        } catch (SQLException e) {
            System.out.println("Error al obtener todas las asistencias: " + e.getMessage());
        }
        
        return asistencias;
    }
    
    // Obtener asistencias por rango de fechas
    public List<Asistencia> obtenerAsistenciasPorFecha(String fechaInicio, String fechaFin) {
        List<Asistencia> asistencias = new ArrayList<>();
        String sql = "SELECT a.*, u.nombre FROM tb_asistencia a " +
                    "INNER JOIN tb_usuarios u ON a.id_usuario = u.id_usuario " +
                    "WHERE a.fecha BETWEEN ? AND ? " +
                    "ORDER BY a.fecha DESC, u.nombre ASC";
        
        try {
            Connection connection = Conexion.getInstance().getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, fechaInicio);
            preparedStatement.setString(2, fechaFin);
            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while (resultSet.next()) {
                Asistencia asistencia = new Asistencia();
                asistencia.setIdAsistencia(resultSet.getInt("id_asistencia"));
                asistencia.setIdUsuario(resultSet.getInt("id_usuario"));
                asistencia.setFecha(resultSet.getString("fecha"));
                asistencia.setHoraEntrada(resultSet.getString("hora_entrada"));
                asistencia.setHoraSalida(resultSet.getString("hora_salida"));
                asistencia.setEstado(resultSet.getString("estado"));
                asistencia.setNombreUsuario(resultSet.getString("nombre"));
                
                asistencias.add(asistencia);
            }
            
            resultSet.close();
            preparedStatement.close();
            
        } catch (SQLException e) {
            System.out.println("Error al obtener asistencias por fecha: " + e.getMessage());
        }
        
        return asistencias;
    }
}
