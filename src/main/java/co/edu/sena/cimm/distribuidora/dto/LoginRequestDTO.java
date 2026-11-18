package co.edu.sena.cimm.distribuidora.dto;

import java.io.Serializable;

/**
 * Capa DTO: Datos requeridos para iniciar sesión.
 */
public class LoginRequestDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String email;
    private String correo;
    private String usuario;

    private String password;
    private String contrasena;
    private String clave;

    public LoginRequestDTO() {
    }

    public LoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        if (email != null && !email.trim().isEmpty()) return email.trim();
        if (correo != null && !correo.trim().isEmpty()) return correo.trim();
        if (usuario != null && !usuario.trim().isEmpty()) return usuario.trim();
        return null;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCorreo() {
        return getEmail();
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getUsuario() {
        return getEmail();
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        if (password != null && !password.trim().isEmpty()) return password.trim();
        if (contrasena != null && !contrasena.trim().isEmpty()) return contrasena.trim();
        if (clave != null && !clave.trim().isEmpty()) return clave.trim();
        return null;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getContrasena() {
        return getPassword();
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getClave() {
        return getPassword();
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
