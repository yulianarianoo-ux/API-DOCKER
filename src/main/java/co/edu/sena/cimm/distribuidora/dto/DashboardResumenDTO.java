package co.edu.sena.cimm.distribuidora.dto;

import java.io.Serializable;

/**
 * Capa DTO: Resumen de métricas de la distribuidora para la administración.
 */
public class DashboardResumenDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private long totalProductos;
    private long totalPedidos;
    private long pedidosPendientes;
    private long pedidosDespachados;
    private long pedidosEntregados;
    private double totalVentasDinero;
    private long totalCampesinosVinculados;
    private long totalStockBultos;
    private double ingresoCampesinoEstimado;

    public DashboardResumenDTO() {
    }

    public DashboardResumenDTO(long totalProductos, long totalPedidos, long pedidosPendientes,
                               long pedidosDespachados, long pedidosEntregados,
                               double totalVentasDinero, long totalCampesinosVinculados) {
        this.totalProductos = totalProductos;
        this.totalPedidos = totalPedidos;
        this.pedidosPendientes = pedidosPendientes;
        this.pedidosDespachados = pedidosDespachados;
        this.pedidosEntregados = pedidosEntregados;
        this.totalVentasDinero = totalVentasDinero;
        this.totalCampesinosVinculados = totalCampesinosVinculados;
    }

    public DashboardResumenDTO(double totalVentasDinero, int totalPedidos, int pedidosPendientes,
                               int pedidosDespachados, int totalProductos, int totalCampesinosVinculados,
                               int totalStockBultos, double ingresoCampesinoEstimado) {
        this.totalVentasDinero = totalVentasDinero;
        this.totalPedidos = totalPedidos;
        this.pedidosPendientes = pedidosPendientes;
        this.pedidosDespachados = pedidosDespachados;
        this.totalProductos = totalProductos;
        this.totalCampesinosVinculados = totalCampesinosVinculados;
        this.totalStockBultos = totalStockBultos;
        this.ingresoCampesinoEstimado = ingresoCampesinoEstimado;
    }

    public long getTotalProductos() {
        return totalProductos;
    }

    public void setTotalProductos(long totalProductos) {
        this.totalProductos = totalProductos;
    }

    public long getTotalPedidos() {
        return totalPedidos;
    }

    public void setTotalPedidos(long totalPedidos) {
        this.totalPedidos = totalPedidos;
    }

    public long getPedidosPendientes() {
        return pedidosPendientes;
    }

    public void setPedidosPendientes(long pedidosPendientes) {
        this.pedidosPendientes = pedidosPendientes;
    }

    public long getPedidosDespachados() {
        return pedidosDespachados;
    }

    public void setPedidosDespachados(long pedidosDespachados) {
        this.pedidosDespachados = pedidosDespachados;
    }

    public long getPedidosEntregados() {
        return pedidosEntregados;
    }

    public void setPedidosEntregados(long pedidosEntregados) {
        this.pedidosEntregados = pedidosEntregados;
    }

    public double getTotalVentasDinero() {
        return totalVentasDinero;
    }

    public void setTotalVentasDinero(double totalVentasDinero) {
        this.totalVentasDinero = totalVentasDinero;
    }

    public long getTotalCampesinosVinculados() {
        return totalCampesinosVinculados;
    }

    public void setTotalCampesinosVinculados(long totalCampesinosVinculados) {
        this.totalCampesinosVinculados = totalCampesinosVinculados;
    }

    public long getTotalStockBultos() {
        return totalStockBultos;
    }

    public void setTotalStockBultos(long totalStockBultos) {
        this.totalStockBultos = totalStockBultos;
    }

    public double getIngresoCampesinoEstimado() {
        return ingresoCampesinoEstimado;
    }

    public void setIngresoCampesinoEstimado(double ingresoCampesinoEstimado) {
        this.ingresoCampesinoEstimado = ingresoCampesinoEstimado;
    }
}
