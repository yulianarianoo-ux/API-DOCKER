package co.edu.sena.cimm.distribuidora.modelo;

import java.io.Serializable;

/**
 * Capa Modelo: Cooperativa o Marca Campesina Aliada.
 */
public class MarcaCampesina implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nombre;
    private String categoria;
    private String region;
    private String logoUrl;
    private String descripcion;
    private Integer campesinosAsociados;
    private String sitioWeb;

    public MarcaCampesina() {
    }

    public MarcaCampesina(Long id, String nombre, String categoria, String region,
                          String logoUrl, String descripcion, Integer campesinosAsociados, String sitioWeb) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.region = region;
        this.logoUrl = logoUrl;
        this.descripcion = descripcion;
        this.campesinosAsociados = campesinosAsociados;
        this.sitioWeb = sitioWeb;
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

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCampesinosAsociados() {
        return campesinosAsociados;
    }

    public void setCampesinosAsociados(Integer campesinosAsociados) {
        this.campesinosAsociados = campesinosAsociados;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }
}

