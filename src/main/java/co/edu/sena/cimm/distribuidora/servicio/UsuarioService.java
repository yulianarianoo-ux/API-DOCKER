package co.edu.sena.cimm.distribuidora.servicio;

import co.edu.sena.cimm.distribuidora.dao.UsuarioDAO;
import co.edu.sena.cimm.distribuidora.dto.LoginRequestDTO;
import co.edu.sena.cimm.distribuidora.dto.LoginResponseDTO;
import co.edu.sena.cimm.distribuidora.dto.RegistroRequestDTO;
import co.edu.sena.cimm.distribuidora.modelo.Usuario;

import java.util.ArrayList;

/**
 * Capa Servicio: Lógica del negocio para Usuarios y Autenticación.
 */
public class UsuarioService {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public ArrayList<Usuario> listar() {
        return usuarioDAO.listar();
    }

    public Usuario obtenerPorId(Long id) {
        return usuarioDAO.obtenerPorId(id);
    }

    public Usuario buscarPorCorreo(String correo) {
        return usuarioDAO.buscarPorCorreo(correo);
    }

    public LoginResponseDTO autenticar(LoginRequestDTO request) {
        if (request == null || request.getCorreo() == null || request.getPassword() == null) {
            throw new IllegalArgumentException("Correo y contraseña son obligatorios");
        }

        Usuario usuario = usuarioDAO.autenticar(request.getCorreo().trim(), request.getPassword().trim());
        if (usuario == null) {
            return null; // Credenciales inválidas
        }

        String tokenSimulado = "jwt-sena-campo-" + usuario.getId() + "-" + System.currentTimeMillis();
        return new LoginResponseDTO(
                tokenSimulado,
                usuario.getId(),
                usuario.getNombreCompleto(),
                usuario.getCorreo(),
                usuario.getRol(),
                usuario.getNegocio(),
                usuario.getTelefono(),
                usuario.getCiudad(),
                usuario.getFotoUrl()
        );
    }

    public Usuario registrar(RegistroRequestDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Los datos de registro son obligatorios");
        }
        if (dto.getCorreo() == null || dto.getCorreo().isBlank()) {
            throw new IllegalArgumentException("El correo electrónico es obligatorio");
        }
        if (dto.getPassword() == null || dto.getPassword().length() < 4) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 4 caracteres");
        }

        if (usuarioDAO.buscarPorCorreo(dto.getCorreo()) != null) {
            throw new IllegalArgumentException("Ya existe una cuenta registrada con este correo");
        }

        Usuario nuevo = new Usuario();
        nuevo.setNombreCompleto(dto.getNombreCompleto() != null ? dto.getNombreCompleto().trim() : "Usuario Campesino");
        nuevo.setCorreo(dto.getCorreo().trim().toLowerCase());
        nuevo.setPassword(dto.getPassword().trim());
        nuevo.setRol(dto.getRol() != null ? dto.getRol() : "COMPRADOR");
        nuevo.setNegocio(dto.getNegocio());
        nuevo.setTelefono(dto.getTelefono());
        nuevo.setCiudad(dto.getCiudad());
        nuevo.setFotoUrl(dto.getFotoUrl() != null ? dto.getFotoUrl() : "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?auto=format&fit=crop&w=200&q=80");

        usuarioDAO.insertar(nuevo);
        return nuevo;
    }
}

