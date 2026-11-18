package co.edu.sena.cimm.distribuidora.modelo;

import java.io.Serializable;

/**
 * Capa Modelo: Representa a un campesino o productor agropecuario independiente.
 * Estructura estándar SENA CIMM.
 */
public class Campesino implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nombre;
    private String finca;
    private String vereda;
    private String municipio;
    private String departamento;
    private String fotoUrl;
    private Double calificacion;
    private Integer totalVentas;
    private Integer aniosTradicion;
    private String telefono;
    private String especialidad;
    private String historia;
    private Boolean certificadoOrganico;

    public Campesino() {
    }

    public Campesino(Long id, String nombre, String finca, String vereda, String municipio,
                     String departamento, String fotoUrl, Double calificacion, Integer totalVentas,
                     Integer aniosTradicion, String telefono, String especialidad, String historia,
                     Boolean certificadoOrganico) {
        this.id = id;
        this.nombre = nombre;
        this.finca = finca;
        this.vereda = vereda;
        this.municipio = municipio;
        this.departamento = departamento;
        this.fotoUrl = fotoUrl;
        this.calificacion = calificacion;
        this.totalVentas = totalVentas;
        this.aniosTradicion = aniosTradicion;
        this.telefono = telefono;
        this.especialidad = especialidad;
        this.historia = historia;
        this.certificadoOrganico = certificadoOrganico != null ? certificadoOrganico : false;
    }

    public String getUbicacionCompleta() {
        return (vereda != null ? "Vereda " + vereda + ", " : "") +
                (municipio != null ? municipio + " (" : "") +
                (departamento != null ? departamento + ")" : "");
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

    public String getFinca() {
        return finca;
    }

    public void setFinca(String finca) {
        this.finca = finca;
    }

    public String getVereda() {
        return vereda;
    }

    public void setVereda(String vereda) {
        this.vereda = vereda;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public Double getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }

    public Integer getTotalVentas() {
        return totalVentas;
    }

    public void setTotalVentas(Integer totalVentas) {
        this.totalVentas = totalVentas;
    }

    public Integer getAniosTradicion() {
        return aniosTradicion;
    }

    public void setAniosTradicion(Integer aniosTradicion) {
        this.aniosTradicion = aniosTradicion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    public Boolean getCertificadoOrganico() {
        return certificadoOrganico;
    }

    public void setCertificadoOrganico(Boolean certificadoOrganico) {
        this.certificadoOrganico = certificadoOrganico;
    }
}

