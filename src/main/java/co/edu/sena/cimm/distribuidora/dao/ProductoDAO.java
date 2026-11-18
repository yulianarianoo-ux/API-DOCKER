package co.edu.sena.cimm.distribuidora.dao;

import co.edu.sena.cimm.distribuidora.modelo.Producto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Capa DAO: Acceso a Datos de Cosechas y Productos Campesinos.
 * Gestiona datos quemados en memoria sin necesidad de consultas SQL a base de datos.
 */
public class ProductoDAO {

    private static final Map<Long, Producto> datosQuemados = new ConcurrentHashMap<>();
    private static final AtomicLong generadorId = new AtomicLong(100);

    static {
        inicializarDatosQuemados();
    }

    private static void inicializarDatosQuemados() {
        // Campesino 1: Don Pedro Morales (Tuta, Boyacá)
        guardarRegistro(new Producto(1L, "Papa Criolla Dorada de Boyacá", "Tubérculos",
                "Papa criolla tierna de primera cosecha, recolectada a mano en las laderas frías de Tuta. Piel delgada y suave textura cremosa.",
                78000.0, 120, "Bulto 50kg",
                1L, "Don Pedro Morales", "Finca La Primavera", "Tuta, Boyacá",
                "/images/papa_cosecha.jpg", true, 4.9,
                "Agroecológico Campesino", "Cosecha de Hoy al Amanecer", "Mejor Precio"));

        guardarRegistro(new Producto(2L, "Papa Pastusa Seleccionada Extra", "Tubérculos",
                "Papa pastusa lavada de primera calidad, sin manchas ni picaduras. Excelente rendimiento para restaurantes y hogares.",
                85000.0, 150, "Bulto 50kg",
                1L, "Don Pedro Morales", "Finca La Primavera", "Tuta, Boyacá",
                "/images/papa_cosecha.jpg", true, 4.9,
                "Tradicional con Abono Orgánico", "Frescura 24h", "Directo de Finca"));

        // Campesino 2: Doña Rosa Elena Patiño (Aquitania, Boyacá)
        guardarRegistro(new Producto(3L, "Papa Criolla 100% Orgánica Certificada", "Tubérculos",
                "Cultivada con humus de lombriz roja californiana y agua limpia de nacimiento. Cero agroquímicos sintéticos.",
                92000.0, 75, "Bulto 50kg",
                2L, "Doña Rosa Elena Patiño", "Parcela El Manantial", "Aquitania, Boyacá",
                "/images/papa_cosecha.jpg", true, 5.0,
                "100% Orgánico Certificado", "Cosecha de Hoy", "100% Orgánico"));

        guardarRegistro(new Producto(4L, "Tomate Chonto de Huerto Familiar", "Frutas y Verduras",
                "Tomate chonto firme y jugoso cosechado en su punto exacto de maduración. Empaque limpio en canastilla plástica.",
                36000.0, 90, "Canastilla 22kg",
                2L, "Doña Rosa Elena Patiño", "Parcela El Manantial", "Aquitania, Boyacá",
                "/images/dona_rosa.jpg", true, 5.0,
                "Bio-intensivo Sin Venenos", "Ayer por la tarde", "Más Fresco"));

        // Campesino 3: Don Gonzalo Arango (Pitalito, Huila)
        guardarRegistro(new Producto(5L, "Café Especial Arábica Pitalito (1.800 msnm)", "Café y Derivados",
                "Variedad Castillo y Caturra con notas a panela, chocolate y cítricos suaves. Tostión media artesanal en grano o molido.",
                29000.0, 210, "Bolsa 1kg",
                3L, "Don Gonzalo Arango", "Finca La Serranía", "Pitalito, Huila",
                "/images/don_gonzalo.jpg", true, 4.9,
                "Comercio Justo / Altura", "Lote Cosecha Nueva", "Top Calidad"));

        guardarRegistro(new Producto(6L, "Miel Cruda de Flor de Cafetal", "Panela y Endulzantes",
                "Miel pura de abejas apis mellifera alimentadas en los cafetales florecidos de la serranía. Sin pasteurizar ni diluir.",
                26000.0, 110, "Frasco Vidrio 500g",
                3L, "Don Gonzalo Arango", "Finca La Serranía", "Pitalito, Huila",
                "/images/panela_miel.jpg", true, 4.9,
                "Apicultura Sostenible", "Extracción Reciente", "Pura Abeja"));

        // Campesino 4: Familia Guatibonza (Ventaquemada, Boyacá)
        guardarRegistro(new Producto(7L, "Queso Campesino Boyacense Auténtico", "Lácteos",
                "Queso blanco semigraso fresco pasteurizado con leche entera de vacas normando. Suave y sal marina justa.",
                14000.0, 85, "Bloque 1kg",
                4L, "Familia Guatibonza", "Granja San Isidro", "Ventaquemada, Boyacá",
                "/images/queso_fresco.jpg", true, 4.8,
                "Leche Pura de Altura", "Maduración 24h", "Mejor Precio"));

        guardarRegistro(new Producto(8L, "Cuajada Fresca de Campo en Hoja", "Lácteos",
                "Cuajada boyacense artesanal fresca, tradicional para servir con melao de panela. Textura húmeda y pura.",
                13500.0, 60, "Bloque 1kg",
                4L, "Familia Guatibonza", "Granja San Isidro", "Ventaquemada, Boyacá",
                "/images/queso_fresco.jpg", true, 4.8,
                "Tradición Lechera Familiar", "Elaborado Hoy a las 5 AM", "Artesanal"));

        // Campesino 5: Trapiche Familiar Doña Juana (Villeta, Cundinamarca)
        guardarRegistro(new Producto(9L, "Panela Cuadrada 100% Pura de Caña", "Panela y Endulzantes",
                "Elaborada a fuego lento con leña y bagazo de caña. Sin blanqueadores químicos ni azúcar añadida.",
                48000.0, 130, "Caja 24 und x 500g",
                5L, "Trapiche Familiar Doña Juana", "Finca El Trapiche", "Villeta, Cundinamarca",
                "/images/panela_miel.jpg", true, 4.8,
                "Molienda Artesanal en Cobre", "Lote Semanal", "Comercio Justo"));

        guardarRegistro(new Producto(10L, "Panela Pulverizada Instantánea Natural", "Panela y Endulzantes",
                "Panela en polvo 100% soluble lista para endulzar bebidas frías o calientes, repostería y café campesino.",
                52000.0, 100, "Caja 24 und x 500g",
                5L, "Trapiche Familiar Doña Juana", "Finca El Trapiche", "Villeta, Cundinamarca",
                "/images/panela_miel.jpg", true, 4.9,
                "Sin Químicos Ni Clarificantes", "Empaque Hermético", "100% Natural"));

        // Campesino 6: Doña Carmen & Don Mateo (Granada, Meta)
        guardarRegistro(new Producto(11L, "Aguacate Hass Cremoso de Finca", "Plátanos y Frutas",
                "Aguacates en su estado ideal de sazón, pulpa espesa con alto contenido de aceite vegetal bueno.",
                42000.0, 95, "Canastilla 12kg",
                6L, "Doña Carmen & Don Mateo", "Finca Los Naranjos", "Granada, Meta",
                "/images/frutas_cosecha.jpg", true, 4.9,
                "Agroecología Tropical", "Recogido a Mano Ayer", "Top Cosecha"));

        guardarRegistro(new Producto(12L, "Plátano Hartón Verde Extra Grande", "Plátanos y Frutas",
                "Plátano llanero racimo seleccionado, ideal para patacones, sopas y frituras comerciales.",
                45000.0, 120, "Canastilla 25kg",
                6L, "Doña Carmen & Don Mateo", "Finca Los Naranjos", "Granada, Meta",
                "/images/frutas_cosecha.jpg", true, 4.8,
                "Cultivo Asociado en Bosque", "Cosecha Fresca", "Súper Rendimiento"));
    }

    private static void guardarRegistro(Producto p) {
        datosQuemados.put(p.getId(), p);
    }

    public void insertar(Producto producto) {
        if (producto.getId() == null) {
            producto.setId(generadorId.incrementAndGet());
        }
        datosQuemados.put(producto.getId(), producto);
    }

    public ArrayList<Producto> listar() {
        return new ArrayList<>(datosQuemados.values());
    }

    public Producto obtenerPorId(Long id) {
        if (id == null) return null;
        return datosQuemados.get(id);
    }

    public void actualizar(Producto producto) {
        if (producto != null && producto.getId() != null) {
            datosQuemados.put(producto.getId(), producto);
        }
    }

    public boolean eliminar(Long id) {
        if (id == null) return false;
        return datosQuemados.remove(id) != null;
    }

    public ArrayList<Producto> listarPorCategoria(String categoria) {
        if (categoria == null || categoria.isBlank() || categoria.equalsIgnoreCase("Todas")) {
            return listar();
        }
        List<Producto> lista = datosQuemados.values().stream()
                .filter(p -> p.getCategoria() != null && p.getCategoria().equalsIgnoreCase(categoria.trim()))
                .collect(Collectors.toList());
        return new ArrayList<>(lista);
    }

    public ArrayList<Producto> listarPorCampesino(Long campesinoId) {
        if (campesinoId == null) {
            return listar();
        }
        List<Producto> lista = datosQuemados.values().stream()
                .filter(p -> p.getCampesinoId() != null && p.getCampesinoId().equals(campesinoId))
                .collect(Collectors.toList());
        return new ArrayList<>(lista);
    }

    public ArrayList<Producto> buscarPorTexto(String query) {
        if (query == null || query.isBlank()) {
            return listar();
        }
        String q = query.toLowerCase().trim();
        List<Producto> lista = datosQuemados.values().stream()
                .filter(p -> (p.getNombre() != null && p.getNombre().toLowerCase().contains(q)) ||
                             (p.getDescripcion() != null && p.getDescripcion().toLowerCase().contains(q)) ||
                             (p.getOrigen() != null && p.getOrigen().toLowerCase().contains(q)) ||
                             (p.getNombreCampesino() != null && p.getNombreCampesino().toLowerCase().contains(q)) ||
                             (p.getCategoria() != null && p.getCategoria().toLowerCase().contains(q)))
                .collect(Collectors.toList());
        return new ArrayList<>(lista);
    }

    public boolean descontarStock(Long productoId, int cantidad) {
        Producto p = obtenerPorId(productoId);
        if (p == null || p.getStock() == null || p.getStock() < cantidad) {
            return false;
        }
        p.setStock(p.getStock() - cantidad);
        if (p.getStock() <= 0) {
            p.setDisponible(false);
        }
        return true;
    }
}

