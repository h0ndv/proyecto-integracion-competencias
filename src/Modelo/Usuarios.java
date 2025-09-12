package Modelo;

public class Usuarios {
    String rut;
    String nombre;
    int pin;
    String correo;
    int id_cargo;
    String cargo;

    public Usuarios() {
    }

    public Usuarios(String rut, String nombre, int pin, String correo, int id_cargo, String cargo) {
        this.rut = rut;
        this.nombre = nombre;
        this.pin = pin;
        this.correo = correo;
        this.id_cargo = id_cargo;
        this.cargo = cargo;
    }
    
    // Getters y Setters
    public String getRutUsuario() {
        return rut;
    }

    public void setRutUsuario(String rutUsuario) {
        this.rut = rutUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getId_cargo() {
        return id_cargo;
    }
    
    public void setId_cargo(int id_cargo) {
        this.id_cargo = id_cargo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
// end
}
