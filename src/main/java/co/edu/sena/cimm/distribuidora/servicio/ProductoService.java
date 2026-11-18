package co.edu.sena.cimm.distribuidora.servicio;

import co.edu.sena.cimm.distribuidora.dao.CampesinoDAO;
import co.edu.sena.cimm.distribuidora.dao.ProductoDAO;
import co.edu.sena.cimm.distribuidora.dto.CrearProductoDTO;
import co.edu.sena.cimm.distribuidora.modelo.Campesino;
import co.edu.sena.cimm.distribuidora.modelo.Producto;

import java.util.ArrayList;

/**
 * Capa Servicio: Lógica del negocio para cosechas y productos agrícolas.
 */
public class ProductoService {

    private final ProductoDAO productoDAO = new ProductoDAO();
    private final CampesinoDAO campesinoDAO = new CampesinoDAO();

    public ArrayList<Producto> listar() {
        return productoDAO.listar();
    }

    public Producto obtenerPorId(Long id) {
        return productoDAO.obtenerPorId(id);
    }

    public ArrayList<Producto> filtrarPorCategoria(String categoria) {
        return productoDAO.listarPorCategoria(categoria);
    }

    public ArrayList<Producto> filtrarPorCampesino(Long campesinoId) {
        return productoDAO.listarPorCampesino(campesinoId);
    }

    public ArrayList<Producto> buscarPorTexto(String query) {
        return productoDAO.buscarPorTexto(query);
    }

    public Producto registrarCosecha(CrearProductoDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Los datos de la cosecha son obligatorios");
        }
        if (dto.getNombre() == null || dto.getNombre().isBlank()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }
        if (dto.getPrecio() == null || dto.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a cero");
        }
        if (dto.getStock() == null || dto.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }

        Producto nuevo = new Producto();
        nuevo.setNombre(dto.getNombre().trim());
        nuevo.setCategoria(dto.getCategoria() != null ? dto.getCategoria() : "Otros");
        nuevo.setDescripcion(dto.getDescripcion());
        nuevo.setPrecio(dto.getPrecio());
        nuevo.setStock(dto.getStock());
        nuevo.setUnidadMedida(dto.getUnidadMedida() != null ? dto.getUnidadMedida() : "Bulto 50kg");
        nuevo.setCampesinoId(dto.getCampesinoId());
        nuevo.setMetodoCultivo(dto.getMetodoCultivo() != null ? dto.getMetodoCultivo() : "Tradicional Campesino");
        nuevo.setTiempoCosecha(dto.getTiempoCosecha() != null ? dto.getTiempoCosecha() : "Cosecha Fresca");
        nuevo.setDestacado(dto.getDestacado() != null ? dto.getDestacado() : "Directo de Finca");
        nuevo.setDisponible(dto.getStock() > 0);
        nuevo.setCalificacion(5.0);

        if (dto.getImagenUrl() != null && !dto.getImagenUrl().isBlank()) {
            nuevo.setImagenUrl(dto.getImagenUrl().trim());
        } else {
            nuevo.setImagenUrl("/images/papa_cosecha.jpg");
        }

        // Vincular información del campesino si existe
        if (dto.getCampesinoId() != null) {
            Campesino c = campesinoDAO.obtenerPorId(dto.getCampesinoId());
            if (c != null) {
                nuevo.setNombreCampesino(c.getNombre());
                nuevo.setFinca(c.getFinca());
                nuevo.setOrigen(c.getMunicipio() + ", " + c.getDepartamento());
            }
        }

        if (nuevo.getNombreCampesino() == null || nuevo.getNombreCampesino().isBlank()) {
            nuevo.setNombreCampesino(dto.getNombreCampesino() != null ? dto.getNombreCampesino() : "Campesino Productor");
            nuevo.setFinca(dto.getFinca() != null ? dto.getFinca() : "Finca Familiar");
            nuevo.setOrigen(dto.getOrigen() != null ? dto.getOrigen() : "Boyacá, Colombia");
        }

        productoDAO.insertar(nuevo);
        return nuevo;
    }

    public void actualizar(Producto producto) {
        productoDAO.actualizar(producto);
    }

    public boolean eliminar(Long id) {
        return productoDAO.eliminar(id);
    }
}

