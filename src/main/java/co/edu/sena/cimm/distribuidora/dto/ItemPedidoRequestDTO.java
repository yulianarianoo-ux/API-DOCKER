package co.edu.sena.cimm.distribuidora.dto;

import java.io.Serializable;

/**
 * Capa DTO: Elemento dentro de una solicitud de pedido.
 */
public class ItemPedidoRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long productoId;
    private Integer cantidad;

    public ItemPedidoRequestDTO() {
    }

    public ItemPedidoRequestDTO(Long productoId, Integer cantidad) {
        this.productoId = productoId;
        this.cantidad = cantidad;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
}
