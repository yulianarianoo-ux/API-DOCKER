package co.edu.sena.cimm.distribuidora.controller;

import co.edu.sena.cimm.distribuidora.dto.ActualizarEstadoDTO;
import co.edu.sena.cimm.distribuidora.dto.CrearPedidoDTO;
import co.edu.sena.cimm.distribuidora.modelo.Pedido;
import co.edu.sena.cimm.distribuidora.servicio.PedidoService;
import co.edu.sena.cimm.distribuidora.util.JsonUtil;
import co.edu.sena.cimm.distribuidora.util.RespuestaUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Controlador Servlet: Pedidos Comerciales B2B.
 * Rutas: /api/pedidos, /api/pedidos/{id}, /api/pedidos/{id}/estado
 */
@WebServlet(name = "PedidoController", urlPatterns = {"/api/pedidos/*", "/pedidos/*"})
public class PedidoController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final PedidoService pedidoService = new PedidoService();

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
        RespuestaUtil.configurarCORS(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = RespuestaUtil.obtenerIdDeRuta(req);

        if (id != null) {
            Pedido pedido = pedidoService.obtenerPorId(id);
            if (pedido != null) {
                RespuestaUtil.enviarExito(resp, "Pedido encontrado", pedido);
            } else {
                RespuestaUtil.enviarError(resp, HttpServletResponse.SC_NOT_FOUND, "Pedido no encontrado con ID: " + id);
            }
            return;
        }

        String correoCliente = req.getParameter("cliente");
        if (correoCliente != null && !correoCliente.isBlank()) {
            ArrayList<Pedido> lista = pedidoService.listarPorCliente(correoCliente);
            RespuestaUtil.enviarExito(resp, "Pedidos del cliente: " + correoCliente, lista);
            return;
        }

        ArrayList<Pedido> lista = pedidoService.listar();
        RespuestaUtil.enviarExito(resp, "Listado de pedidos comerciales B2B", lista);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        // Ruta: /api/pedidos/{id}/estado
        if (pathInfo != null && pathInfo.contains("/estado")) {
            Long id = RespuestaUtil.obtenerIdDeRuta(req);
            if (id == null) {
                RespuestaUtil.enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, "ID de pedido inválido en la ruta");
                return;
            }

            try {
                ActualizarEstadoDTO dto = JsonUtil.leerCuerpoPeticion(req, ActualizarEstadoDTO.class);
                if (dto == null || dto.getEstado() == null || dto.getEstado().isBlank()) {
                    RespuestaUtil.enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, "El nuevo estado es requerido");
                    return;
                }

                boolean actualizado = pedidoService.actualizarEstado(id, dto.getEstado());
                if (actualizado) {
                    Pedido p = pedidoService.obtenerPorId(id);
                    RespuestaUtil.enviarExito(resp, "Estado del pedido actualizado a: " + dto.getEstado(), p);
                } else {
                    RespuestaUtil.enviarError(resp, HttpServletResponse.SC_NOT_FOUND, "Pedido no encontrado con ID: " + id);
                }
            } catch (Exception e) {
                RespuestaUtil.enviarError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al actualizar estado: " + e.getMessage());
            }
            return;
        }

        // Ruta: /api/pedidos (crear nuevo pedido)
        try {
            CrearPedidoDTO dto = JsonUtil.leerCuerpoPeticion(req, CrearPedidoDTO.class);
            Pedido creado = pedidoService.crearPedido(dto);
            RespuestaUtil.enviarCreado(resp, "Pedido comercial creado exitosamente. Código: " + creado.getCodigoPedido(), creado);
        } catch (IllegalArgumentException e) {
            RespuestaUtil.enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            RespuestaUtil.enviarError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error interno al procesar el pedido: " + e.getMessage());
        }
    }
}

