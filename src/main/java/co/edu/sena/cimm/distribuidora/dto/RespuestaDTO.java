package co.edu.sena.cimm.distribuidora.dto;

import java.time.LocalDateTime;

/**
 * Envoltorio estándar de respuesta JSON para la API.
 * Incluye tanto 'exito' como 'exitoso' para compatibilidad total con cualquier cliente.
 */
public class RespuestaDTO<T> {

    private boolean exito;
    private boolean exitoso;
    private String mensaje;
    private T datos;
    private String fechaHora;

    public RespuestaDTO() {
        this.fechaHora = LocalDateTime.now().toString();
    }

    public static <T> RespuestaDTO<T> ok(String mensaje, T datos) {
        RespuestaDTO<T> r = new RespuestaDTO<>();
        r.exito = true;
        r.exitoso = true;
        r.mensaje = mensaje;
        r.datos = datos;
        return r;
    }

    public static <T> RespuestaDTO<T> ok(String mensaje) {
        return ok(mensaje, null);
    }

    public static <T> RespuestaDTO<T> fallo(String mensaje) {
        RespuestaDTO<T> r = new RespuestaDTO<>();
        r.exito = false;
        r.exitoso = false;
        r.mensaje = mensaje;
        r.datos = null;
        return r;
    }

    public boolean isExito() {
        return exito;
    }

    public void setExito(boolean exito) {
        this.exito = exito;
        this.exitoso = exito;
    }

    public boolean isExitoso() {
        return exitoso;
    }

    public void setExitoso(boolean exitoso) {
        this.exitoso = exitoso;
        this.exito = exitoso;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public T getDatos() {
        return datos;
    }

    public void setDatos(T datos) {
        this.datos = datos;
    }

    public String getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(String fechaHora) {
        this.fechaHora = fechaHora;
    }
}
