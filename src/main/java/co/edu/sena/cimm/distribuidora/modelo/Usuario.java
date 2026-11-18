package co.edu.sena.cimm.distribuidora.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Capa Modelo: Usuario del sistema (Comprador mayorista, Productor Campesino o Administrador).
 */
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String nombreCompleto;
    private String correo;
    private String password;
    private String rol;
    private String negocio;
    private String telefono;
    private String ciudad;
    private String fotoUrl;
    private Boolean activo;
    private String fechaRegistro;

    public Usuario() {
        this.activo = true;
        this.fechaRegistro = LocalDateTime.now().toString();
    }

    public Usuario(Long id, String nombreCompleto, String correo, String password,
                   String rol, String negocio, String telefono, String ciudad, String fotoUrl) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.password = password;
        this.rol = rol != null ? rol : "COMPRADOR";
        this.negocio = negocio;
        this.telefono = telefono;
        this.ciudad = ciudad;
        this.fotoUrl = fotoUrl;
        this.activo = true;
        this.fechaRegistro = LocalDateTime.now().toString();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getNegocio() {
        return negocio;
    }

    public void setNegocio(String negocio) {
        this.negocio = negocio;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}

