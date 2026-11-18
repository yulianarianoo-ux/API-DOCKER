package co.edu.sena.cimm.distribuidora.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Capa Modelo: Pedido Comercial B2B.
 */
public class Pedido implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String codigoPedido;
    private String nombreCliente;
    private String correoCliente;
    private String telefonoCliente;
    private String tipoEntrega;
    private String direccionEntrega;
    private String metodoPago;
    private Double subtotal;
    private Double costoEnvio;
    private Double total;
    private String estado;
    private List<ItemPedido> items = new ArrayList<>();
    private String fechaCreacion;

    public Pedido() {
        this.fechaCreacion = LocalDateTime.now().toString();
    }

    public Pedido(Long id, String codigoPedido, String nombreCliente, String correoCliente,
                  String telefonoCliente, String tipoEntrega, String direccionEntrega,
                  String metodoPago, Double subtotal, Double costoEnvio, Double total,
                  String estado, List<ItemPedido> items) {
        this.id = id;
        this.codigoPedido = codigoPedido;
        this.nombreCliente = nombreCliente;
        this.correoCliente = correoCliente;
        this.telefonoCliente = telefonoCliente;
        this.tipoEntrega = tipoEntrega;
        this.direccionEntrega = direccionEntrega;
        this.metodoPago = metodoPago;
        this.subtotal = subtotal;
        this.costoEnvio = costoEnvio != null ? costoEnvio : 0.0;
        this.total = total;
        this.estado = estado != null ? estado : "PENDIENTE";
        this.items = items != null ? items : new ArrayList<>();
        this.fechaCreacion = LocalDateTime.now().toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(String codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getCorreoCliente() {
        return correoCliente;
    }

    public void setCorreoCliente(String correoCliente) {
        this.correoCliente = correoCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
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

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Double getCostoEnvio() {
        return costoEnvio;
    }

    public void setCostoEnvio(Double costoEnvio) {
        this.costoEnvio = costoEnvio;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<ItemPedido> getItems() {
        return items;
    }

    public void setItems(List<ItemPedido> items) {
        this.items = items;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}

