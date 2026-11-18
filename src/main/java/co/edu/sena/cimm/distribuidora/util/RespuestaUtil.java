package co.edu.sena.cimm.distribuidora.util;

import co.edu.sena.cimm.distribuidora.dto.RespuestaDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Utilidad para respuestas HTTP JSON estándar y cabeceras CORS en Servlets / Controllers.
 */
public class RespuestaUtil {

    public static void enviarJson(HttpServletResponse response, int status, Object data) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
        configurarCORS(response);

        String json = JsonUtil.toJson(data);
        response.getWriter().write(json);
        response.getWriter().flush();
    }

    public static void configurarCORS(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, PATCH, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization, Accept");
    }

    public static <T> void enviarExito(HttpServletResponse response, String mensaje, T data) throws IOException {
        enviarJson(response, HttpServletResponse.SC_OK, RespuestaDTO.ok(mensaje, data));
    }

    public static void enviarExito(HttpServletResponse response, String mensaje) throws IOException {
        enviarJson(response, HttpServletResponse.SC_OK, RespuestaDTO.ok(mensaje));
    }

    public static void enviarCreado(HttpServletResponse response, String mensaje, Object data) throws IOException {
        enviarJson(response, HttpServletResponse.SC_CREATED, RespuestaDTO.ok(mensaje, data));
    }

    public static void enviarError(HttpServletResponse response, int status, String mensaje) throws IOException {
        enviarJson(response, status, RespuestaDTO.fallo(mensaje));
    }

    public static Long obtenerIdDeRuta(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            return null;
        }
        try {
            String idStr = pathInfo.replaceFirst("^/", "");
            if (idStr.contains("/")) {
                idStr = idStr.substring(0, idStr.indexOf("/"));
            }
            return Long.parseLong(idStr.trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
