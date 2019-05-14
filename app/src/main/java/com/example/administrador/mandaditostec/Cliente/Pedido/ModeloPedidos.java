package com.example.administrador.mandaditostec.Cliente.Pedido;

public class ModeloPedidos {
    private String id;
    private String direccion;
    private String pedido;
    private String hora;
    private String mandadero;

    public String getMandadero() {
        return mandadero;
    }

    public void setMandadero(String mandadero) {
        this.mandadero = mandadero;
    }

    public ModeloPedidos(String id, String direccion, String pedido, String hora, String mandadero) {
        this.id = id;
        this.direccion = direccion;
        this.pedido = pedido;
        this.hora = hora;
        this.mandadero = mandadero;
    }


    public ModeloPedidos(){}

    public void setId(String id) {
        this.id = id;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setPedido(String pedido) {
        this.pedido = pedido;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getId() {
        return id;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getPedido() {
        return pedido;
    }

    public String getHora() {
        return hora;
    }
}
