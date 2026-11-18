package co.edu.sena.cimm.distribuidora.dao;

import co.edu.sena.cimm.distribuidora.modelo.Campesino;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * Capa DAO: Acceso a Datos de Campesinos y Productores Rurales.
 * Siguiendo el estándar formativo SENA CIMM, los datos están gestionados en memoria (quemados)
 * sin requerir conexión a base de datos externa ni consultas SQL.
 */
public class CampesinoDAO {

    private static final Map<Long, Campesino> datosQuemados = new ConcurrentHashMap<>();
    private static final AtomicLong generadorId = new AtomicLong(10);

    static {
        inicializarDatosQuemados();
    }

    private static void inicializarDatosQuemados() {
        guardarRegistro(new Campesino(1L, "Don Pedro Morales", "Finca La Primavera", "La Colorada", "Tuta",
                "Boyacá", "/images/don_pedro.jpg", 4.9, 420, 38, "311 482 9102",
                "Papas Nativas, Papa Criolla y Tubérculos Andinos",
                "Tres generaciones sembrando la tierra con abono orgánico y respeto a los ciclos lunares en las laderas frías de Tuta.",
                true));

        guardarRegistro(new Campesino(2L, "Doña Rosa Elena Patiño", "Parcela El Manantial", "El Retiro", "Aquitania",
                "Boyacá", "/images/dona_rosa.jpg", 5.0, 512, 24, "314 290 8831",
                "Tomate Chonto Orgánico, Hortalizas y Hierbas Aromáticas",
                "Lideresa comunitaria campesina que cultiva sin agroquímicos a orillas del Lago de Tota, protegiendo las fuentes de agua.",
                true));

        guardarRegistro(new Campesino(3L, "Don Gonzalo Arango", "Finca La Serranía", "San Joaquín", "Pitalito",
                "Huila", "/images/don_gonzalo.jpg", 4.9, 380, 31, "320 671 4452",
                "Café Arábica Especial de Altura (1.800 msnm) y Miel de Cafetal",
                "Caficultor apasionado por el grano seleccionado a mano, secado al sol en marquesinas tradicionales.",
                true));

        guardarRegistro(new Campesino(4L, "Familia Guatibonza", "Granja San Isidro", "Tierra Quemada", "Ventaquemada",
                "Boyacá", "/images/queso_fresco.jpg", 4.8, 630, 40, "313 881 2004",
                "Queso Campesino Boyacense, Cuajada Fresca y Lácteos Puros",
                "Tradición ganadera de vacas normando alimentadas con pasto estrella de alta montaña, sin conservantes.",
                false));

        guardarRegistro(new Campesino(5L, "Trapiche Familiar Doña Juana", "Finca El Trapiche", "Cune", "Villeta",
                "Cundinamarca", "/images/panela_miel.jpg", 4.8, 490, 28, "315 772 1993",
                "Panela 100% Pura de Caña, Melado y Miel Virgen",
                "Caña de azúcar cortada en su punto óptimo de brix y cocida en paila de cobre a fuego lento de bagazo.",
                true));

        guardarRegistro(new Campesino(6L, "Doña Carmen & Don Mateo", "Finca Los Naranjos", "Canaguaro", "Granada",
                "Meta", "/images/frutas_cosecha.jpg", 4.9, 315, 19, "318 903 5511",
                "Aguacate Hass Cremoso, Plátano Hartón y Frutas Exóticas",
                "Asociación de familias campesinas del Ariari dedicadas a la fruticultura sostenible y comercio justo directo.",
                true));
    }

    private static void guardarRegistro(Campesino c) {
        datosQuemados.put(c.getId(), c);
    }

    public void insertar(Campesino campesino) {
        if (campesino.getId() == null) {
            campesino.setId(generadorId.incrementAndGet());
        }
        datosQuemados.put(campesino.getId(), campesino);
    }

    public ArrayList<Campesino> listar() {
        return new ArrayList<>(datosQuemados.values());
    }

    public Campesino obtenerPorId(Long id) {
        if (id == null) return null;
        return datosQuemados.get(id);
    }

    public void actualizar(Campesino campesino) {
        if (campesino != null && campesino.getId() != null) {
            datosQuemados.put(campesino.getId(), campesino);
        }
    }

    public boolean eliminar(Long id) {
        if (id == null) return false;
        return datosQuemados.remove(id) != null;
    }

    public ArrayList<Campesino> buscar(String query) {
        if (query == null || query.isBlank()) {
            return listar();
        }
        String q = query.toLowerCase().trim();
        List<Campesino> resultado = datosQuemados.values().stream()
                .filter(c -> (c.getNombre() != null && c.getNombre().toLowerCase().contains(q)) ||
                             (c.getFinca() != null && c.getFinca().toLowerCase().contains(q)) ||
                             (c.getMunicipio() != null && c.getMunicipio().toLowerCase().contains(q)) ||
                             (c.getDepartamento() != null && c.getDepartamento().toLowerCase().contains(q)) ||
                             (c.getEspecialidad() != null && c.getEspecialidad().toLowerCase().contains(q)))
                .collect(Collectors.toList());
        return new ArrayList<>(resultado);
    }
}

