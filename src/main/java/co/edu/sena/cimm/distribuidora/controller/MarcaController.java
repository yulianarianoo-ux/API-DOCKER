package co.edu.sena.cimm.distribuidora.controller;

import co.edu.sena.cimm.distribuidora.modelo.MarcaCampesina;
import co.edu.sena.cimm.distribuidora.servicio.MarcaCampesinaService;
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
 * Controlador Servlet: Marcas Campesinas y Cooperativas Aliadas.
 * Rutas: /api/marcas, /api/marcas/{id}
 */
@WebServlet(name = "MarcaController", urlPatterns = {"/api/marcas/*", "/marcas/*"})
public class MarcaController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final MarcaCampesinaService marcaService = new MarcaCampesinaService();

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
        RespuestaUtil.configurarCORS(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = RespuestaUtil.obtenerIdDeRuta(req);

        if (id != null) {
            MarcaCampesina marca = marcaService.obtenerPorId(id);
            if (marca != null) {
                RespuestaUtil.enviarExito(resp, "Marca campesina encontrada", marca);
            } else {
                RespuestaUtil.enviarError(resp, HttpServletResponse.SC_NOT_FOUND, "Marca no encontrada con ID: " + id);
            }
            return;
        }

        ArrayList<MarcaCampesina> lista = marcaService.listar();
        RespuestaUtil.enviarExito(resp, "Lista de cooperativas y marcas campesinas aliadas", lista);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            MarcaCampesina marca = JsonUtil.leerCuerpoPeticion(req, MarcaCampesina.class);
            marcaService.registrar(marca);
            RespuestaUtil.enviarCreado(resp, "Marca registrada exitosamente", marca);
        } catch (Exception e) {
            RespuestaUtil.enviarError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al registrar la marca: " + e.getMessage());
        }
    }
}

