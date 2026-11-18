package co.edu.sena.cimm.distribuidora.controller;

import co.edu.sena.cimm.distribuidora.modelo.Campesino;
import co.edu.sena.cimm.distribuidora.servicio.CampesinoService;
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
 * Controlador Servlet: Productores Campesinos y Familias Rurales.
 * Rutas: /api/campesinos, /api/campesinos/{id}, /api/campesinos?q=Tuta
 */
@WebServlet(name = "CampesinoController", urlPatterns = {"/api/campesinos/*", "/campesinos/*"})
public class CampesinoController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final CampesinoService campesinoService = new CampesinoService();

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
        RespuestaUtil.configurarCORS(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long id = RespuestaUtil.obtenerIdDeRuta(req);

        if (id != null) {
            Campesino c = campesinoService.obtenerPorId(id);
            if (c != null) {
                RespuestaUtil.enviarExito(resp, "Campesino encontrado", c);
            } else {
                RespuestaUtil.enviarError(resp, HttpServletResponse.SC_NOT_FOUND, "Campesino no encontrado con ID: " + id);
            }
            return;
        }

        String busqueda = req.getParameter("q");
        ArrayList<Campesino> lista;
        if (busqueda != null && !busqueda.isBlank()) {
            lista = campesinoService.buscar(busqueda);
        } else {
            lista = campesinoService.listar();
        }

        RespuestaUtil.enviarExito(resp, "Lista de productores campesinos obtenida con éxito", lista);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Campesino campesino = JsonUtil.leerCuerpoPeticion(req, Campesino.class);
            Campesino registrado = campesinoService.registrar(campesino);
            RespuestaUtil.enviarCreado(resp, "Campesino vinculado exitosamente a la plataforma", registrado);
        } catch (IllegalArgumentException e) {
            RespuestaUtil.enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            RespuestaUtil.enviarError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error interno al procesar el campesino");
        }
    }
}

