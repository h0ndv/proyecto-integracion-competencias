package Modelo;

public class Clientes {
    private String nombre;
    private String rut;
    private String correo;
    private int celular;
    
    public Clientes() {
    }

    public Clientes(String nombre, String rut, int celular, String correo) {
        this.nombre = nombre;
        this.rut = rut;
        this.celular = celular;
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public int getCelular() {
        return celular;
    }

    public void setCelular(int celular) {
        this.celular = celular;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
}
