package Modelo;

public class Proveedores {
    private String nombre;
    private String empresa;
    private String celular;
    private String correo;
    
    public Proveedores() {}

    public Proveedores(String nombre, String empresa, String celular, String correo) {
        this.nombre = nombre;
        this.empresa = empresa;
        this.celular = celular;
        this.correo = correo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCorreo() {
        return correo;
    }
    
    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
