package Modelo;

public class Ventas {
    private int id_venta;
    private int id_usuario;
    private String producto;
    private int precio;
    private int cantidad;
    private String tipoCompra;
    private String fecha;

    public Ventas() { }

    public Ventas(int id_venta, int id_usuario, String producto, int precio, int cantidad, String tipoCompra, String fecha) {
        this.id_venta = id_venta;
        this.id_usuario = id_usuario;
        this.producto = producto;
        this.precio = precio;
        this.cantidad = cantidad;
        this.tipoCompra = tipoCompra;
        this.fecha = fecha;
    }   

    public int getId_venta() {
        return id_venta;
    }

    public void setId_venta(int id_venta) {
        this.id_venta = id_venta;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
            this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipoCompra() {
        return tipoCompra;
    }

    public void setTipoCompra(String tipoCompra) {
        this.tipoCompra = tipoCompra;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
}
