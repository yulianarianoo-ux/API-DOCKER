package co.edu.sena.cimm.distribuidora.dto;

import java.io.Serializable;

/**
 * Capa DTO: Respuesta al autenticar un usuario exitosamente.
 */
public class LoginResponseDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean autenticado;
    private Long id;
    private String nombre;
    private String nombreCompleto;
    private String email;
    private String correo;
    private String rol;
    private String empresaOFinca;
    private String negocio;
    private String telefono;
    private String ciudad;
    private String fotoUrl;
    private String token;
    private String mensaje;

    public LoginResponseDTO() {
        this.autenticado = true;
    }

    public LoginResponseDTO(String token, Long id, String nombreCompleto, String correo,
                            String rol, String negocio, String telefono, String ciudad, String fotoUrl) {
        this.autenticado = true;
        this.token = token;
        this.id = id;
        this.nombre = nombreCompleto;
        this.nombreCompleto = nombreCompleto;
        this.email = correo;
        this.correo = correo;
        this.rol = rol;
        this.empresaOFinca = negocio;
        this.negocio = negocio;
        this.telefono = telefono;
        this.ciudad = ciudad;
        this.fotoUrl = fotoUrl;
        this.mensaje = "Autenticación exitosa";
    }

    public LoginResponseDTO(boolean autenticado, Long id, String nombre, String email,
                            String rol, String empresaOFinca, String telefono, String ciudad,
                            String fotoUrl, String token, String mensaje) {
        this.autenticado = autenticado;
        this.id = id;
        this.nombre = nombre;
        this.nombreCompleto = nombre;
        this.email = email;
        this.correo = email;
        this.rol = rol;
        this.empresaOFinca = empresaOFinca;
        this.negocio = empresaOFinca;
        this.telefono = telefono;
        this.ciudad = ciudad;
        this.fotoUrl = fotoUrl;
        this.token = token;
        this.mensaje = mensaje;
    }

    public boolean isAutenticado() {
        return autenticado;
    }

    public void setAutenticado(boolean autenticado) {
        this.autenticado = autenticado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        return email != null ? email : correo;
    }

    public void setEmail(String email) {
        this.email = email;
        this.correo = email;
    }

    public String getCorreo() {
        return correo != null ? correo : email;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
        this.email = correo;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
