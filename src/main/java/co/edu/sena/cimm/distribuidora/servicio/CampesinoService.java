package co.edu.sena.cimm.distribuidora.servicio;

import co.edu.sena.cimm.distribuidora.dao.CampesinoDAO;
import co.edu.sena.cimm.distribuidora.modelo.Campesino;

import java.util.ArrayList;

/**
 * Capa Servicio: Lógica del negocio para los productores campesinos.
 * Estándar formativo SENA CIMM.
 */
public class CampesinoService {

    private final CampesinoDAO campesinoDAO = new CampesinoDAO();

    public ArrayList<Campesino> listar() {
        return campesinoDAO.listar();
    }

    public Campesino obtenerPorId(Long id) {
        return campesinoDAO.obtenerPorId(id);
    }

    public Campesino registrar(Campesino campesino) {
        if (campesino == null) {
            throw new IllegalArgumentException("Los datos del campesino no pueden ser nulos");
        }
        if (campesino.getNombre() == null || campesino.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del campesino es obligatorio");
        }
        campesinoDAO.insertar(campesino);
        return campesino;
    }

    public ArrayList<Campesino> buscar(String query) {
        return campesinoDAO.buscar(query);
    }

    public void actualizar(Campesino campesino) {
        campesinoDAO.actualizar(campesino);
    }

    public boolean eliminar(Long id) {
        return campesinoDAO.eliminar(id);
    }
}

