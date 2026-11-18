package co.edu.sena.cimm.distribuidora.dao;

import co.edu.sena.cimm.distribuidora.modelo.Usuario;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Capa DAO: Acceso a Datos de Usuarios y Autenticación.
 * Datos quemados en memoria con cuentas de demostración para compradores y campesinos.
 */
public class UsuarioDAO {

    private static final Map<Long, Usuario> datosQuemados = new ConcurrentHashMap<>();
    private static final Map<String, Long> indiceCorreo = new ConcurrentHashMap<>();
    private static final AtomicLong generadorId = new AtomicLong(10);

    static {
        inicializarDatosQuemados();
    }

    private static void inicializarDatosQuemados() {
        // 1. Comprador Mayorista
        guardarRegistro(new Usuario(1L, "Don Fernando Arciniegas", "comprador@tienda.com", "123456",
                "COMPRADOR", "Supermercado La Canasta", "+57 310 987 6543", "Bogotá D.C.",
                "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?auto=format&fit=crop&w=200&q=80"));

        // 2. Restaurante Comprador
        guardarRegistro(new Usuario(2L, "Chef Carlos Morales", "restaurante@fogon.co", "123456",
                "COMPRADOR", "Restaurante Campestre El Fogón", "+57 320 554 1122", "Villavicencio, Meta",
                "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?auto=format&fit=crop&w=200&q=80"));

        // 3. Hogar / Familia Compradora
        guardarRegistro(new Usuario(3L, "Familia Gómez Suárez", "familia@hogar.com", "123456",
                "COMPRADOR", "Hogar Familiar Gómez", "+57 315 443 2211", "Tunja, Boyacá",
                "https://images.unsplash.com/photo-1494790108377-be9c29b29330?auto=format&fit=crop&w=200&q=80"));

        // 4. Don Pedro Morales (Vendedor Campesino)
        guardarRegistro(new Usuario(4L, "Don Pedro Morales", "donpedro@finca.com", "123456",
                "VENDEDOR_CAMPESINO", "Finca La Primavera (Tuta, Boyacá)", "+57 311 482 9102", "Tuta, Boyacá",
                "/images/don_pedro.jpg"));

        // 5. Doña Rosa Elena Patiño (Vendedora Campesina)
        guardarRegistro(new Usuario(5L, "Doña Rosa Elena Patiño", "donarosa@finca.com", "123456",
                "VENDEDOR_CAMPESINO", "Parcela El Manantial (Aquitania, Boyacá)", "+57 314 290 8831", "Aquitania, Boyacá",
                "/images/dona_rosa.jpg"));

        // 6. Don Gonzalo Arango (Caficultor Vendedor)
        guardarRegistro(new Usuario(6L, "Don Gonzalo Arango", "gonzalo@finca.com", "123456",
                "VENDEDOR_CAMPESINO", "Finca La Serranía (Pitalito, Huila)", "+57 320 671 4452", "Pitalito, Huila",
                "/images/don_gonzalo.jpg"));

        // 7. Administrador de la Plataforma
        guardarRegistro(new Usuario(7L, "Administrador AgroCampo B2B", "admin@distribuidora.co", "admin123",
                "ADMIN", "Central de Abastos Mayorista", "+57 300 123 4567", "Bogotá D.C.",
                "https://images.unsplash.com/photo-1534528741775-53994a69daeb?auto=format&fit=crop&w=200&q=80"));
    }

    private static void guardarRegistro(Usuario u) {
        datosQuemados.put(u.getId(), u);
        if (u.getCorreo() != null) {
            indiceCorreo.put(u.getCorreo().toLowerCase().trim(), u.getId());
        }
    }

    public void insertar(Usuario usuario) {
        if (usuario.getId() == null) {
            usuario.setId(generadorId.incrementAndGet());
        }
        datosQuemados.put(usuario.getId(), usuario);
        if (usuario.getCorreo() != null) {
            indiceCorreo.put(usuario.getCorreo().toLowerCase().trim(), usuario.getId());
        }
    }

    public ArrayList<Usuario> listar() {
        return new ArrayList<>(datosQuemados.values());
    }

    public Usuario obtenerPorId(Long id) {
        if (id == null) return null;
        return datosQuemados.get(id);
    }

    public Usuario buscarPorCorreo(String correo) {
        if (correo == null || correo.isBlank()) return null;
        Long id = indiceCorreo.get(correo.toLowerCase().trim());
        if (id == null) return null;
        return datosQuemados.get(id);
    }

    public Usuario autenticar(String correo, String password) {
        Usuario u = buscarPorCorreo(correo);
        if (u != null && u.getPassword() != null && u.getPassword().equals(password)) {
            return u;
        }
        return null;
    }

    public void actualizar(Usuario usuario) {
        if (usuario != null && usuario.getId() != null) {
            datosQuemados.put(usuario.getId(), usuario);
            if (usuario.getCorreo() != null) {
                indiceCorreo.put(usuario.getCorreo().toLowerCase().trim(), usuario.getId());
            }
        }
    }

    public boolean eliminar(Long id) {
        if (id == null) return false;
        Usuario u = datosQuemados.remove(id);
        if (u != null && u.getCorreo() != null) {
            indiceCorreo.remove(u.getCorreo().toLowerCase().trim());
            return true;
        }
        return false;
    }
}

