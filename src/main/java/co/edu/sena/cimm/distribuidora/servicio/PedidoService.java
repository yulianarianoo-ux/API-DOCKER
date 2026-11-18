package co.edu.sena.cimm.distribuidora.servicio;

import co.edu.sena.cimm.distribuidora.dao.PedidoDAO;
import co.edu.sena.cimm.distribuidora.dao.ProductoDAO;
import co.edu.sena.cimm.distribuidora.dto.CrearPedidoDTO;
import co.edu.sena.cimm.distribuidora.dto.ItemPedidoRequestDTO;
import co.edu.sena.cimm.distribuidora.modelo.ItemPedido;
import co.edu.sena.cimm.distribuidora.modelo.Pedido;
import co.edu.sena.cimm.distribuidora.modelo.Producto;

import java.util.ArrayList;
import java.util.List;

/**
 * Capa Servicio: Lógica del negocio para Pedidos Comerciales B2B.
 */
public class PedidoService {

    private final PedidoDAO pedidoDAO = new PedidoDAO();
    private final ProductoDAO productoDAO = new ProductoDAO();

    public ArrayList<Pedido> listar() {
        return pedidoDAO.listar();
    }

    public Pedido obtenerPorId(Long id) {
        return pedidoDAO.obtenerPorId(id);
    }

    public ArrayList<Pedido> listarPorCliente(String correo) {
        return pedidoDAO.listarPorCliente(correo);
    }

    public Pedido crearPedido(CrearPedidoDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("La información del pedido es obligatoria");
        }
        if (dto.getNombreCliente() == null || dto.getNombreCliente().isBlank()) {
            throw new IllegalArgumentException("El nombre del comprador o razón social es obligatorio");
        }
        if (dto.getItems() == null || dto.getItems().isEmpty()) {
            throw new IllegalArgumentException("El pedido debe contener al menos un producto");
        }

        List<ItemPedido> itemsProcesados = new ArrayList<>();
        double subtotal = 0.0;

        for (ItemPedidoRequestDTO itemDto : dto.getItems()) {
            Producto prod = productoDAO.obtenerPorId(itemDto.getProductoId());
            if (prod == null) {
                throw new IllegalArgumentException("Producto no encontrado con ID: " + itemDto.getProductoId());
            }
            int cantidad = itemDto.getCantidad() != null && itemDto.getCantidad() > 0 ? itemDto.getCantidad() : 1;
            double precio = prod.getPrecio() != null ? prod.getPrecio() : 0.0;

            // Descontar inventario disponible
            productoDAO.descontarStock(prod.getId(), cantidad);

            ItemPedido item = new ItemPedido(prod.getId(), prod.getNombre(), cantidad, precio);
            itemsProcesados.add(item);
            subtotal += item.getSubtotal();
        }

        double costoEnvio = (dto.getTipoEntrega() != null && dto.getTipoEntrega().equalsIgnoreCase("RETIRO_EN_PUNTO")) ? 0.0 : 25000.0;
        double total = subtotal + costoEnvio;

        Pedido pedido = new Pedido();
        pedido.setNombreCliente(dto.getNombreCliente().trim());
        pedido.setCorreoCliente(dto.getCorreoCliente());
        pedido.setTelefonoCliente(dto.getTelefonoCliente());
        pedido.setTipoEntrega(dto.getTipoEntrega() != null ? dto.getTipoEntrega() : "DOMICILIO_BODEGA");
        pedido.setDireccionEntrega(dto.getDireccionEntrega());
        pedido.setMetodoPago(dto.getMetodoPago() != null ? dto.getMetodoPago() : "TRANSFERENCIA_BANCOLOMBIA");
        pedido.setSubtotal(subtotal);
        pedido.setCostoEnvio(costoEnvio);
        pedido.setTotal(total);
        pedido.setEstado("PENDIENTE");
        pedido.setItems(itemsProcesados);

        pedidoDAO.insertar(pedido);
        return pedido;
    }

    public boolean actualizarEstado(Long id, String nuevoEstado) {
        return pedidoDAO.actualizarEstado(id, nuevoEstado);
    }
}

