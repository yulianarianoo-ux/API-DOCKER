package co.edu.sena.cimm.distribuidora.dao;

import co.edu.sena.cimm.distribuidora.modelo.ItemPedido;
import co.edu.sena.cimm.distribuidora.modelo.Pedido;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Capa DAO: Acceso a Datos de Pedidos Comerciales B2B.
 * Gestiona pedidos en memoria (datos quemados) para desarrollo ágil y sin BD externa.
 */
public class PedidoDAO {

    private static final Map<Long, Pedido> datosQuemados = new ConcurrentHashMap<>();
    private static final AtomicLong generadorId = new AtomicLong(200);

    static {
        inicializarDatosQuemados();
    }

    private static void inicializarDatosQuemados() {
        // Pedido 1: Despachado
        List<ItemPedido> items1 = List.of(
                new ItemPedido(1L, "Papa Pastusa Seleccionada (Bulto 50kg)", 5, 88000.0),
                new ItemPedido(3L, "Arroz de Campo Grano Largo (Bulto 50kg)", 3, 115000.0)
        );
        guardarRegistro(new Pedido(1L, "PED-B2B-101", "Supermercado El Trébol", "comprador@tienda.com",
                "+57 310 987 6543", "DOMICILIO_BODEGA", "Calle 140 # 19-32, Bodega 4, Bogotá",
                "TRANSFERENCIA_BANCOLOMBIA", 785000.0, 25000.0, 810000.0, "DESPACHADO", items1));

        // Pedido 2: Pendiente
        List<ItemPedido> items2 = List.of(
                new ItemPedido(4L, "Queso Campesino Boyacense (Bloque 1kg)", 20, 14500.0),
                new ItemPedido(7L, "Panela Pulverizada 100% Caña", 4, 52000.0)
        );
        guardarRegistro(new Pedido(2L, "PED-B2B-102", "Restaurante Campestre El Fogón", "restaurante@fogon.co",
                "+57 320 554 1122", "RETIRO_EN_PUNTO", "Acopio Central Corabastos Puerta 6",
                "NEQUI", 498000.0, 0.0, 498000.0, "PENDIENTE", items2));
    }

    private static void guardarRegistro(Pedido p) {
        datosQuemados.put(p.getId(), p);
    }

    public void insertar(Pedido pedido) {
        if (pedido.getId() == null) {
            pedido.setId(generadorId.incrementAndGet());
        }
        if (pedido.getCodigoPedido() == null || pedido.getCodigoPedido().isBlank()) {
            pedido.setCodigoPedido("PED-B2B-" + pedido.getId());
        }
        datosQuemados.put(pedido.getId(), pedido);
    }

    public ArrayList<Pedido> listar() {
        List<Pedido> lista = new ArrayList<>(datosQuemados.values());
        lista.sort((p1, p2) -> p2.getId().compareTo(p1.getId()));
        return new ArrayList<>(lista);
    }

    public Pedido obtenerPorId(Long id) {
        if (id == null) return null;
        return datosQuemados.get(id);
    }

    public void actualizar(Pedido pedido) {
        if (pedido != null && pedido.getId() != null) {
            datosQuemados.put(pedido.getId(), pedido);
        }
    }

    public boolean actualizarEstado(Long id, String nuevoEstado) {
        Pedido p = obtenerPorId(id);
        if (p != null && nuevoEstado != null && !nuevoEstado.isBlank()) {
            p.setEstado(nuevoEstado.trim().toUpperCase());
            return true;
        }
        return false;
    }

    public ArrayList<Pedido> listarPorCliente(String correo) {
        if (correo == null || correo.isBlank()) {
            return listar();
        }
        List<Pedido> lista = datosQuemados.values().stream()
                .filter(p -> p.getCorreoCliente() != null && p.getCorreoCliente().equalsIgnoreCase(correo.trim()))
                .sorted((p1, p2) -> p2.getId().compareTo(p1.getId()))
                .collect(Collectors.toList());
        return new ArrayList<>(lista);
    }

    public boolean eliminar(Long id) {
        if (id == null) return false;
        return datosQuemados.remove(id) != null;
    }
}

