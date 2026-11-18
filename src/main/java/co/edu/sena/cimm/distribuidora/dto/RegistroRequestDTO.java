package co.edu.sena.cimm.distribuidora.dto;

import java.io.Serializable;

/**
 * Capa DTO: Datos requeridos para registrar una nueva cuenta de Comprador o Vendedor Campesino.
 */
public class RegistroRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nombre;
    private String nombreCompleto;
    private String email;
    private String correo;
    private String password;
    private String contrasena;
    private String rol; // "COMPRADOR" o "VENDEDOR_CAMPESINO"
    private String empresaOFinca;
    private String negocio;
    private String telefono;
    private String ciudad;
    private String fotoUrl;

    public RegistroRequestDTO() {
    }

    public String getNombre() {
        return nombre != null ? nombre : nombreCompleto;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
        this.nombreCompleto = nombre;
    }

    public String getNombreCompleto() {
        return nombreCompleto != null ? nombreCompleto : nombre;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
        this.nombre = nombreCompleto;
    }

    public String getEmail() {
        if (email != null && !email.trim().isEmpty()) return email.trim();
        if (correo != null && !correo.trim().isEmpty()) return correo.trim();
        return null;
    }

    public void setEmail(String email) {
        this.email = email;
        this.correo = email;
    }

    public String getCorreo() {
        return getEmail();
    }

    public void setCorreo(String correo) {
        this.correo = correo;
        this.email = correo;
    }

    public String getPassword() {
        if (password != null && !password.trim().isEmpty()) return password.trim();
        if (contrasena != null && !contrasena.trim().isEmpty()) return contrasena.trim();
        return null;
    }

    public void setPassword(String password) {
        this.password = password;
        this.contrasena = password;
    }

    public String getContrasena() {
        return getPassword();
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
        this.password = contrasena;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getEmpresaOFinca() {
        return empresaOFinca != null ? empresaOFinca : negocio;
    }

    public void setEmpresaOFinca(String empresaOFinca) {
        this.empresaOFinca = empresaOFinca;
        this.negocio = empresaOFinca;
    }

    public String getNegocio() {
        return negocio != null ? negocio : empresaOFinca;
    }

    public void setNegocio(String negocio) {
        this.negocio = negocio;
        this.empresaOFinca = negocio;
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
}
