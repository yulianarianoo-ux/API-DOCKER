package co.edu.sena.cimm.distribuidora.modelo;

import java.io.Serializable;

/**
 * Capa Modelo: Cosecha o Producto Agrícola de Campesinos.
 * Estructura estándar SENA CIMM.
 */
public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nombre;
    private String categoria;
    private String descripcion;
    private Double precio;
    private Integer stock;
    private String unidadMedida;
    private Long campesinoId;
    private String nombreCampesino;
    private String finca;
    private String origen;
    private String imagenUrl;
    private Boolean disponible;
    private Double calificacion;
    private String metodoCultivo;
    private String tiempoCosecha;
    private String destacado;

    public Producto() {
    }

    public Producto(Long id, String nombre, String categoria, String descripcion, Double precio,
                    Integer stock, String unidadMedida, Long campesinoId, String nombreCampesino,
                    String finca, String origen, String imagenUrl, Boolean disponible,
                    Double calificacion, String metodoCultivo, String tiempoCosecha, String destacado) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.unidadMedida = unidadMedida;
        this.campesinoId = campesinoId;
        this.nombreCampesino = nombreCampesino;
        this.finca = finca;
        this.origen = origen;
        this.imagenUrl = imagenUrl;
        this.disponible = disponible != null ? disponible : true;
        this.calificacion = calificacion != null ? calificacion : 5.0;
        this.metodoCultivo = metodoCultivo;
        this.tiempoCosecha = tiempoCosecha;
        this.destacado = destacado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Long getCampesinoId() {
        return campesinoId;
    }

    public void setCampesinoId(Long campesinoId) {
        this.campesinoId = campesinoId;
    }

    public String getNombreCampesino() {
        return nombreCampesino;
    }

    public void setNombreCampesino(String nombreCampesino) {
        this.nombreCampesino = nombreCampesino;
    }

    public String getFinca() {
        return finca;
    }

    public void setFinca(String finca) {
        this.finca = finca;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }

    public String getMetodoCultivo() {
        return metodoCultivo;
    }

    public void setMetodoCultivo(String metodoCultivo) {
        this.metodoCultivo = metodoCultivo;
    }

    public String getTiempoCosecha() {
        return tiempoCosecha;
    }

    public void setTiempoCosecha(String tiempoCosecha) {
        this.tiempoCosecha = tiempoCosecha;
    }

    public String getDestacado() {
        return destacado;
    }

    public void setDestacado(String destacado) {
        this.destacado = destacado;
    }
}

