package Modelo;

public class Asistencia {
    private int idAsistencia;
    private int idUsuario;
    private String fecha;
    private String horaEntrada;
    private String horaSalida;
    private String estado;
    private String nombreUsuario;
    
    public Asistencia() {
    }
    
    public Asistencia(int idAsistencia, int idUsuario, String fecha, String horaEntrada, String horaSalida, String estado) {
        this.idAsistencia = idAsistencia;
        this.idUsuario = idUsuario;
        this.fecha = fecha;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.estado = estado;
    }
    
    // Getters y Setters
    public int getIdAsistencia() {
        return idAsistencia;
    }
    
    public void setIdAsistencia(int idAsistencia) {
        this.idAsistencia = idAsistencia;
    }
    
    public int getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    public String getFecha() {
        return fecha;
    }
    
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    public String getHoraEntrada() {
        return horaEntrada;
    }
    
    public void setHoraEntrada(String horaEntrada) {
        this.horaEntrada = horaEntrada;
    }
    
    public String getHoraSalida() {
        return horaSalida;
    }
    
    public void setHoraSalida(String horaSalida) {
        this.horaSalida = horaSalida;
    }
    
    public String getEstado() {
        return estado;
    }
    
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    
    // Metodo para obtener el tiempo trabajado
    public String getTiempoTrabajado() {
        if (horaEntrada != null && horaSalida != null && !horaEntrada.isEmpty() && !horaSalida.isEmpty()) {
            try {
                String[] entrada = horaEntrada.split(":");
                String[] salida = horaSalida.split(":");
                
                int horaEnt = Integer.parseInt(entrada[0]);
                int minEnt = Integer.parseInt(entrada[1]);
                int horaSal = Integer.parseInt(salida[0]);
                int minSal = Integer.parseInt(salida[1]);
                
                int totalMinEnt = horaEnt * 60 + minEnt;
                int totalMinSal = horaSal * 60 + minSal;
                
                int duracionMin = totalMinSal - totalMinEnt;
                
                if (duracionMin < 0) {
                    duracionMin += 24 * 60; // Manejar cambio de dia
                }
                
                int horas = duracionMin / 60;
                int minutos = duracionMin % 60;
                
                return String.format("%02d:%02d", horas, minutos);
            } catch (Exception e) {
                return "00:00";
            }
        }
        return "00:00";
    }
}
