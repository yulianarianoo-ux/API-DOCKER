package co.edu.sena.cimm.distribuidora.controller;

import co.edu.sena.cimm.distribuidora.dto.CrearProductoDTO;
import co.edu.sena.cimm.distribuidora.modelo.Producto;
import co.edu.sena.cimm.distribuidora.servicio.ProductoService;
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
 * Controlador Servlet: Catálogo de Cosechas y Productos Campesinos.
 * Rutas: /api/productos, /api/productos/{id}, /api/productos?categoria=Tubérculos, /api/productos?campesinoId=1
 */
@WebServlet(name = "ProductoController", urlPatterns = {"/api/productos/*", "/productos/*"})
public class ProductoController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final ProductoService productoService = new ProductoService();

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
        RespuestaUtil.configurarCORS(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = RespuestaUtil.obtenerIdDeRuta(req);

        if (id != null) {
            Producto producto = productoService.obtenerPorId(id);
            if (producto != null) {
                RespuestaUtil.enviarExito(resp, "Producto encontrado", producto);
            } else {
                RespuestaUtil.enviarError(resp, HttpServletResponse.SC_NOT_FOUND, "Producto no encontrado con ID: " + id);
            }
            return;
        }

        String categoria = req.getParameter("categoria");
        String campesinoIdParam = req.getParameter("campesinoId");
        String busqueda = req.getParameter("q");

        if (campesinoIdParam != null && !campesinoIdParam.isBlank()) {
            try {
                Long campesinoId = Long.parseLong(campesinoIdParam.trim());
                ArrayList<Producto> lista = productoService.filtrarPorCampesino(campesinoId);
                RespuestaUtil.enviarExito(resp, "Cosechas del campesino ID: " + campesinoId, lista);
                return;
            } catch (NumberFormatException ignored) {
            }
        }

        if (categoria != null && !categoria.isBlank() && !categoria.equalsIgnoreCase("Todas")) {
            ArrayList<Producto> lista = productoService.filtrarPorCategoria(categoria);
            RespuestaUtil.enviarExito(resp, "Productos filtrados por categoría: " + categoria, lista);
            return;
        }

        if (busqueda != null && !busqueda.isBlank()) {
            ArrayList<Producto> lista = productoService.buscarPorTexto(busqueda);
            RespuestaUtil.enviarExito(resp, "Resultados de búsqueda para: " + busqueda, lista);
            return;
        }

        ArrayList<Producto> lista = productoService.listar();
        RespuestaUtil.enviarExito(resp, "Catálogo completo de cosechas campesinas", lista);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            CrearProductoDTO dto = JsonUtil.leerCuerpoPeticion(req, CrearProductoDTO.class);
            Producto creado = productoService.registrarCosecha(dto);
            RespuestaUtil.enviarCreado(resp, "Cosecha registrada con éxito en el catálogo", creado);
        } catch (IllegalArgumentException e) {
            RespuestaUtil.enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            RespuestaUtil.enviarError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error interno al procesar el producto: " + e.getMessage());
        }
    }
}

