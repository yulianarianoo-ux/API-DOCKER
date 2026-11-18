package co.edu.sena.cimm.distribuidora.controller;

import co.edu.sena.cimm.distribuidora.dto.LoginRequestDTO;
import co.edu.sena.cimm.distribuidora.dto.LoginResponseDTO;
import co.edu.sena.cimm.distribuidora.dto.RegistroRequestDTO;
import co.edu.sena.cimm.distribuidora.modelo.Usuario;
import co.edu.sena.cimm.distribuidora.servicio.UsuarioService;
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
 * Controlador Servlet: Autenticación, Login y Registro de Usuarios.
 * Rutas: /api/auth/login, /api/auth/registro, /api/auth/usuarios
 */
@WebServlet(name = "AuthController", urlPatterns = {"/api/auth/*", "/auth/*"})
public class AuthController extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final UsuarioService usuarioService = new UsuarioService();

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) {
        RespuestaUtil.configurarCORS(resp);
        resp.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo != null && pathInfo.contains("/usuarios")) {
            ArrayList<Usuario> usuarios = usuarioService.listar();
            RespuestaUtil.enviarExito(resp, "Lista de usuarios del sistema", usuarios);
            return;
        }
        RespuestaUtil.enviarError(resp, HttpServletResponse.SC_NOT_FOUND, "Ruta no encontrada");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();

        if (pathInfo != null && pathInfo.contains("/login")) {
            try {
                LoginRequestDTO dto = JsonUtil.leerCuerpoPeticion(req, LoginRequestDTO.class);
                LoginResponseDTO respuesta = usuarioService.autenticar(dto);

                if (respuesta != null) {
                    RespuestaUtil.enviarExito(resp, "Inicio de sesión exitoso. Bienvenido " + respuesta.getNombreCompleto(), respuesta);
                } else {
                    RespuestaUtil.enviarError(resp, HttpServletResponse.SC_UNAUTHORIZED, "Credenciales incorrectas. Verifique su correo y contraseña");
                }
            } catch (IllegalArgumentException e) {
                RespuestaUtil.enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            } catch (Exception e) {
                RespuestaUtil.enviarError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error interno en autenticación: " + e.getMessage());
            }
            return;
        }

        if (pathInfo != null && pathInfo.contains("/registro")) {
            try {
                RegistroRequestDTO dto = JsonUtil.leerCuerpoPeticion(req, RegistroRequestDTO.class);
                Usuario nuevo = usuarioService.registrar(dto);

                LoginResponseDTO respuesta = new LoginResponseDTO(
                        "jwt-sena-campo-" + nuevo.getId() + "-" + System.currentTimeMillis(),
                        nuevo.getId(),
                        nuevo.getNombreCompleto(),
                        nuevo.getCorreo(),
                        nuevo.getRol(),
                        nuevo.getNegocio(),
                        nuevo.getTelefono(),
                        nuevo.getCiudad(),
                        nuevo.getFotoUrl()
                );
                RespuestaUtil.enviarCreado(resp, "Cuenta registrada exitosamente", respuesta);
            } catch (IllegalArgumentException e) {
                RespuestaUtil.enviarError(resp, HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
            } catch (Exception e) {
                RespuestaUtil.enviarError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error interno en registro: " + e.getMessage());
            }
            return;
        }

        RespuestaUtil.enviarError(resp, HttpServletResponse.SC_NOT_FOUND, "Endpoint no encontrado");
    }
}

