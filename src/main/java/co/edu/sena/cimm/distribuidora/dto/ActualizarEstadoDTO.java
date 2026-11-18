package co.edu.sena.cimm.distribuidora.dto;

import java.io.Serializable;

/**
 * Capa DTO: Datos para cambiar el estado de un pedido (ej: DESPACHADO, ENTREGADO).
 */
public class ActualizarEstadoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nuevoEstado;
    private String estado;

    public ActualizarEstadoDTO() {
    }

    public ActualizarEstadoDTO(String nuevoEstado) {
        this.nuevoEstado = nuevoEstado;
        this.estado = nuevoEstado;
    }

    public String getNuevoEstado() {
        return nuevoEstado != null ? nuevoEstado : estado;
    }

    public void setNuevoEstado(String nuevoEstado) {
        this.nuevoEstado = nuevoEstado;
        this.estado = nuevoEstado;
    }

    public String getEstado() {
        return estado != null ? estado : nuevoEstado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
        this.nuevoEstado = estado;
    }
}
