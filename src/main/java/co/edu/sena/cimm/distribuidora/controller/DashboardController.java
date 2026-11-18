package co.edu.sena.cimm.distribuidora.controller;

import co.edu.sena.cimm.distribuidora.dto.DashboardResumenDTO;
import co.edu.sena.cimm.distribuidora.servicio.DashboardService;
import co.edu.sena.cimm.distribuidora.util.RespuestaUtil;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Controlador Servlet: Métricas e Indicadores del Dashboard Comercial.
 * Ruta: /api/dashboard
 */
@WebServlet(name = "DashboardController", urlPatterns = {"/api/dashboard/*", "/dashboard/*"})
public class DashboardController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final DashboardService dashboardService = new DashboardService();

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
        RespuestaUtil.configurarCORS(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        DashboardResumenDTO resumen = dashboardService.obtenerResumen();
        RespuestaUtil.enviarExito(resp, "Métricas comerciales obtenidas exitosamente", resumen);
    }
}

