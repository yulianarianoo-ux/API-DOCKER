package co.edu.sena.cimm.distribuidora.dto;

import java.io.Serializable;

/**
 * Capa DTO: Datos requeridos para registrar una nueva cosecha o producto agrícola campesino.
 */
public class CrearProductoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombre;
    private String categoria;
    private String descripcion;
    private Double precio;
    private Double precioMayorista;
    private Integer stock;
    private String unidadMedida;
    private Long campesinoId;
    private String nombreCampesino;
    private String campesinoProductor;
    private String finca;
    private String origen;
    private String region;
    private String imagenUrl;
    private String metodoCultivo;
    private String tiempoCosecha;
    private String fechaCosecha;
    private String destacado;
    private String etiquetaDestacada;

    public CrearProductoDTO() {
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
        return precio != null ? precio : precioMayorista;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
        this.precioMayorista = precio;
    }

    public Double getPrecioMayorista() {
        return precioMayorista != null ? precioMayorista : precio;
    }

    public void setPrecioMayorista(Double precioMayorista) {
        this.precioMayorista = precioMayorista;
        this.precio = precioMayorista;
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
        return nombreCampesino != null ? nombreCampesino : campesinoProductor;
    }

    public void setNombreCampesino(String nombreCampesino) {
        this.nombreCampesino = nombreCampesino;
        this.campesinoProductor = nombreCampesino;
    }

    public String getCampesinoProductor() {
        return campesinoProductor != null ? campesinoProductor : nombreCampesino;
    }

    public void setCampesinoProductor(String campesinoProductor) {
        this.campesinoProductor = campesinoProductor;
        this.nombreCampesino = campesinoProductor;
    }

    public String getFinca() {
        return finca;
    }

    public void setFinca(String finca) {
        this.finca = finca;
    }

    public String getOrigen() {
        return origen != null ? origen : region;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
        this.region = origen;
    }

    public String getRegion() {
        return region != null ? region : origen;
    }

    public void setRegion(String region) {
        this.region = region;
        this.origen = region;
    }

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }

    public String getMetodoCultivo() {
        return metodoCultivo;
    }

    public void setMetodoCultivo(String metodoCultivo) {
        this.metodoCultivo = metodoCultivo;
    }

    public String getTiempoCosecha() {
        return tiempoCosecha != null ? tiempoCosecha : fechaCosecha;
    }

    public void setTiempoCosecha(String tiempoCosecha) {
        this.tiempoCosecha = tiempoCosecha;
        this.fechaCosecha = tiempoCosecha;
    }

    public String getFechaCosecha() {
        return fechaCosecha != null ? fechaCosecha : tiempoCosecha;
    }

    public void setFechaCosecha(String fechaCosecha) {
        this.fechaCosecha = fechaCosecha;
        this.tiempoCosecha = fechaCosecha;
    }

    public String getDestacado() {
        return destacado != null ? destacado : etiquetaDestacada;
    }

    public void setDestacado(String destacado) {
        this.destacado = destacado;
        this.etiquetaDestacada = destacado;
    }

    public String getEtiquetaDestacada() {
        return etiquetaDestacada != null ? etiquetaDestacada : destacado;
    }

    public void setEtiquetaDestacada(String etiquetaDestacada) {
        this.etiquetaDestacada = etiquetaDestacada;
        this.destacado = etiquetaDestacada;
    }
}
