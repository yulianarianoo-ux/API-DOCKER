package co.edu.sena.cimm.distribuidora.servicio;

import co.edu.sena.cimm.distribuidora.dao.CampesinoDAO;
import co.edu.sena.cimm.distribuidora.dao.PedidoDAO;
import co.edu.sena.cimm.distribuidora.dao.ProductoDAO;
import co.edu.sena.cimm.distribuidora.dto.DashboardResumenDTO;
import co.edu.sena.cimm.distribuidora.modelo.Pedido;
import co.edu.sena.cimm.distribuidora.modelo.Producto;

import java.util.List;

/**
 * Capa Servicio: Estadísticas y métricas para el Dashboard Comercial Agropecuario.
 */
public class DashboardService {

    private final PedidoDAO pedidoDAO = new PedidoDAO();
    private final ProductoDAO productoDAO = new ProductoDAO();
    private final CampesinoDAO campesinoDAO = new CampesinoDAO();

    public DashboardResumenDTO obtenerResumen() {
        List<Pedido> pedidos = pedidoDAO.listar();
        List<Producto> productos = productoDAO.listar();

        double totalVentas = pedidos.stream()
                .filter(p -> p.getTotal() != null && !"CANCELADO".equalsIgnoreCase(p.getEstado()))
                .mapToDouble(Pedido::getTotal)
                .sum();

        int totalPedidos = pedidos.size();

        long pedidosPendientes = pedidos.stream()
                .filter(p -> "PENDIENTE".equalsIgnoreCase(p.getEstado()))
                .count();

        long pedidosDespachados = pedidos.stream()
                .filter(p -> "DESPACHADO".equalsIgnoreCase(p.getEstado()))
                .count();

        int totalCosechas = productos.size();

        int totalCampesinos = campesinoDAO.listar().size();

        int totalStockBultos = productos.stream()
                .mapToInt(p -> p.getStock() != null ? p.getStock() : 0)
                .sum();

        double ingresoCampesinoEstimado = totalVentas * 0.88; // 88% directo al productor rural

        return new DashboardResumenDTO(
                totalVentas,
                totalPedidos,
                (int) pedidosPendientes,
                (int) pedidosDespachados,
                totalCosechas,
                totalCampesinos,
                totalStockBultos,
                ingresoCampesinoEstimado
        );
    }
}

