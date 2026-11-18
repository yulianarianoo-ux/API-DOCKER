package co.edu.sena.cimm.distribuidora.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Capa DTO: Datos enviados por un comprador mayorista para formalizar un pedido.
 */
public class CrearPedidoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombreCliente;
    private String emailCliente;
    private String correoCliente;
    private String telefono;
    private String telefonoCliente;
    private String tipoEntrega;
    private String direccionEntrega;
    private String metodoPago;
    private List<ItemPedidoRequestDTO> items;

    public CrearPedidoDTO() {
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getEmailCliente() {
        return emailCliente != null ? emailCliente : correoCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
        this.correoCliente = emailCliente;
    }

    public String getCorreoCliente() {
        return correoCliente != null ? correoCliente : emailCliente;
    }

    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
        this.emailCliente = correoCliente;
    }

    public String getTelefono() {
        return telefono != null ? telefono : telefonoCliente;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
        this.telefonoCliente = telefono;
    }

    public String getTelefonoCliente() {
        return telefonoCliente != null ? telefonoCliente : telefono;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
        this.telefono = telefonoCliente;
    }

    public String getTipoEntrega() {
        return tipoEntrega;
    }

    public void setTipoEntrega(String tipoEntrega) {
        this.tipoEntrega = tipoEntrega;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public List<ItemPedidoRequestDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemPedidoRequestDTO> items) {
        this.items = items;
    }
}
