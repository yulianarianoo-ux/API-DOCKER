package co.edu.sena.cimm.distribuidora;

import co.edu.sena.cimm.distribuidora.controller.*;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.net.ServerSocket;

/**
 * Servidor Tomcat Embebido para pruebas inmediatas y ejecución directa en NetBeans
 * con un solo clic (Run File), estándar formativo SENA CIMM.
 */
public class ServidorPrincipal {

    public static void main(String[] args) throws Exception {
        int puertoDeseado = 8080;
        String portEnv = System.getenv("PORT");
        if (portEnv != null && !portEnv.isBlank()) {
            try {
                puertoDeseado = Integer.parseInt(portEnv.trim());
            } catch (NumberFormatException ignored) {
            }
        }

        // Si el puerto está ocupado (por ejemplo, otra ejecución previa en NetBeans),
        // busca automáticamente el siguiente puerto libre para evitar Address already in use: bind
        int puerto = buscarPuertoDisponible(puertoDeseado);

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(puerto);
        tomcat.getConnector(); // Inicializa el conector HTTP

        // Directorio base de la aplicación web (HTML, CSS, JS, imágenes, WEB-INF)
        File webappDir = new File("src/main/webapp");
        if (!webappDir.exists()) {
            webappDir = new File(".");
        }

        Context ctx = tomcat.addWebapp("", webappDir.getAbsolutePath());

        // Registrar los Controladores para ejecución directa
        Tomcat.addServlet(ctx, "CampesinoController", new CampesinoController());
        ctx.addServletMappingDecoded("/api/campesinos/*", "CampesinoController");
        ctx.addServletMappingDecoded("/campesinos/*", "CampesinoController");

        Tomcat.addServlet(ctx, "ProductoController", new ProductoController());
        ctx.addServletMappingDecoded("/api/productos/*", "ProductoController");
        ctx.addServletMappingDecoded("/productos/*", "ProductoController");

        Tomcat.addServlet(ctx, "PedidoController", new PedidoController());
        ctx.addServletMappingDecoded("/api/pedidos/*", "PedidoController");
        ctx.addServletMappingDecoded("/pedidos/*", "PedidoController");

        Tomcat.addServlet(ctx, "AuthController", new AuthController());
        ctx.addServletMappingDecoded("/api/auth/*", "AuthController");
        ctx.addServletMappingDecoded("/auth/*", "AuthController");

        Tomcat.addServlet(ctx, "MarcaController", new MarcaController());
        ctx.addServletMappingDecoded("/api/marcas/*", "MarcaController");
        ctx.addServletMappingDecoded("/marcas/*", "MarcaController");

        Tomcat.addServlet(ctx, "DashboardController", new DashboardController());
        ctx.addServletMappingDecoded("/api/dashboard/*", "DashboardController");
        ctx.addServletMappingDecoded("/dashboard/*", "DashboardController");

        String urlBase = "http://localhost:" + puerto + "/";

        System.out.println("==================================================================");
        System.out.println("🌾 SERVIDOR TOMCAT INICIADO - DISTRIBUIDORA DEL CAMPO (SENA CIMM)");
        System.out.println("📍 URL Portal Web:    " + urlBase);
        System.out.println("👨‍🌾 API Campesinos:   http://localhost:" + puerto + "/api/campesinos");
        System.out.println("🛒 API Productos:     http://localhost:" + puerto + "/api/productos");
        System.out.println("📦 API Pedidos:       http://localhost:" + puerto + "/api/pedidos");
        System.out.println("🏷️ API Marcas:        http://localhost:" + puerto + "/api/marcas");
        System.out.println("📊 API Dashboard:     http://localhost:" + puerto + "/api/dashboard");
        System.out.println("🔐 API Auth:          http://localhost:" + puerto + "/api/auth/usuarios");
        System.out.println("==================================================================");

        tomcat.start();

        // Abre automáticamente la aplicación en el navegador del usuario
        abrirNavegador(urlBase);

        tomcat.getServer().await();
    }

    /**
     * Busca un puerto libre para evitar el error 'Address already in use: bind'
     */
    private static int buscarPuertoDisponible(int puertoInicial) {
        for (int p = puertoInicial; p < puertoInicial + 50; p++) {
            try (ServerSocket socket = new ServerSocket(p)) {
                return p;
            } catch (Exception ignored) {
                // Puerto en uso, sigue buscando el siguiente
            }
        }
        return puertoInicial;
    }

    /**
     * Abre automáticamente el navegador web predeterminado del sistema operativo
     */
    private static void abrirNavegador(String url) {
        new Thread(() -> {
            try {
                Thread.sleep(800); // Pequeña pausa para asegurar que Tomcat ya recibe tráfico
                if (java.awt.Desktop.isDesktopSupported() && java.awt.Desktop.getDesktop().isSupported(java.awt.Desktop.Action.BROWSE)) {
                    java.awt.Desktop.getDesktop().browse(new java.net.URI(url));
                    return;
                }
            } catch (Throwable ignored) {
            }

            try {
                // Fallback para Windows en caso de que Desktop AWT esté restringido
                Runtime.getRuntime().exec(new String[]{"rundll32", "url.dll,FileProtocolHandler", url});
            } catch (Throwable t) {
                System.out.println("👉 Abre tu navegador en: " + url);
            }
        }).start();
    }
}
