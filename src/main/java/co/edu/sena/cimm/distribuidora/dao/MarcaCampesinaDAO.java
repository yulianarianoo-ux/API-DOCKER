package co.edu.sena.cimm.distribuidora.dao;

import co.edu.sena.cimm.distribuidora.modelo.MarcaCampesina;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Capa DAO: Acceso a Datos de Cooperativas y Marcas Campesinas Aliadas.
 * Datos quemados en memoria.
 */
public class MarcaCampesinaDAO {

    private static final Map<Long, MarcaCampesina> datosQuemados = new ConcurrentHashMap<>();
    private static final AtomicLong generadorId = new AtomicLong(10);

    static {
        inicializarDatosQuemados();
    }

    private static void inicializarDatosQuemados() {
        guardarRegistro(new MarcaCampesina(1L, "Fedearroz", "Granos y Cereales", "Tolima e Ibagué",
                "https://images.unsplash.com/photo-1586201375761-83865001e31c?auto=format&fit=crop&w=400&q=80",
                "Federación Nacional de Arroceros de Colombia. Arroz de grano seleccionado de pequeños agricultores.",
                4800, "https://www.fedearroz.com.co"));

        guardarRegistro(new MarcaCampesina(2L, "Colanta", "Lácteos y Derivados Campesinos", "Antioquia y Altiplano",
                "https://images.unsplash.com/photo-1550583724-b2692b85b150?auto=format&fit=crop&w=400&q=80",
                "La cooperativa lechera campesina más grande de Colombia. Queso campesino, cuajada y leche entera.",
                14200, "https://www.colanta.com"));

        guardarRegistro(new MarcaCampesina(3L, "Fedepapa Boyacá", "Tubérculos y Raíces", "Ventaquemada y Tunja (Boyacá)",
                "https://images.unsplash.com/photo-1518977676601-b53f82aba655?auto=format&fit=crop&w=400&q=80",
                "Asociación de paperos tradicionales de las montañas frías de Boyacá. Papa pastusa, criolla y R12.",
                6300, "https://www.fedepapa.com"));

        guardarRegistro(new MarcaCampesina(4L, "Café Campesino San Alberto", "Café de Especialidad", "Pitalito y San Agustín (Huila)",
                "https://images.unsplash.com/photo-1514432324607-a09d9b4aefdd?auto=format&fit=crop&w=400&q=80",
                "Granos arábica cosechados a mano por familias cafeteras huilenses a más de 1.700 msnm.",
                2100, "https://cafesanalberto.com"));

        guardarRegistro(new MarcaCampesina(5L, "Panelas El Trapiche Doña Juana", "Panela y Endulzantes", "Hoya del Río Suárez y Villeta",
                "https://images.unsplash.com/photo-1600857544200-b2f666a9a2ec?auto=format&fit=crop&w=400&q=80",
                "Panela 100% pura de caña sin blanqueadores químicos, en pastilla cuadrada y pulverizada.",
                1850, "https://panela.co"));

        guardarRegistro(new MarcaCampesina(6L, "Huevos Oro del Campo", "Avicultura y Granja", "Mesa de los Santos (Santander)",
                "https://images.unsplash.com/photo-1516467508483-a7212febe31a?auto=format&fit=crop&w=400&q=80",
                "Huevos campesinos tipo AA y Jumbo de gallinas libres de jaula criadas en campo abierto.",
                920, "https://huevosorodelcampo.com"));
    }

    private static void guardarRegistro(MarcaCampesina m) {
        datosQuemados.put(m.getId(), m);
    }

    public void insertar(MarcaCampesina marca) {
        if (marca.getId() == null) {
            marca.setId(generadorId.incrementAndGet());
        }
        datosQuemados.put(marca.getId(), marca);
    }

    public ArrayList<MarcaCampesina> listar() {
        return new ArrayList<>(datosQuemados.values());
    }

    public MarcaCampesina obtenerPorId(Long id) {
        if (id == null) return null;
        return datosQuemados.get(id);
    }

    public void actualizar(MarcaCampesina marca) {
        if (marca != null && marca.getId() != null) {
            datosQuemados.put(marca.getId(), marca);
        }
    }

    public boolean eliminar(Long id) {
        if (id == null) return false;
        return datosQuemados.remove(id) != null;
    }
}

