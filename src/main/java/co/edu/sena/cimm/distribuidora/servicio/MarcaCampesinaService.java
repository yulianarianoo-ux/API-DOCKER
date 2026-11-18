package co.edu.sena.cimm.distribuidora.servicio;

import co.edu.sena.cimm.distribuidora.dao.MarcaCampesinaDAO;
import co.edu.sena.cimm.distribuidora.modelo.MarcaCampesina;

import java.util.ArrayList;

/**
 * Capa Servicio: Lógica para marcas campesinas y cooperativas aliadas.
 */
public class MarcaCampesinaService {

    private final MarcaCampesinaDAO marcaCampesinaDAO = new MarcaCampesinaDAO();

    public ArrayList<MarcaCampesina> listar() {
        return marcaCampesinaDAO.listar();
    }

    public MarcaCampesina obtenerPorId(Long id) {
        return marcaCampesinaDAO.obtenerPorId(id);
    }

    public void registrar(MarcaCampesina marca) {
        marcaCampesinaDAO.insertar(marca);
    }
}

