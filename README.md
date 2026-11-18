# 🌾 Distribuidora del Campo B2B — Java Web Application (SENA CIMM)

Proyecto desarrollado como **Java Web Application en Maven** (`<packaging>war</packaging>`) siguiendo la **Arquitectura en Capas SENA** (idéntica a la estructura de **FitCIMM**):
- **`controller`**: Controladores Servlets (`@WebServlet`)
- **`dao`**: Objetos de Acceso a Datos con **datos quemados en memoria** (sin necesidad de consultas SQL ni configuración de base de datos externa)
- **`servicio`**: Capa de Lógica del Negocio
- **`modelo`**: Clases Entidad del Dominio
- **`dto`**: Transferencia de datos JSON
- **`util`**: Utilidades JSON (Gson) y respuestas HTTP

Se mantiene **el diseño original de la distribuidora** con su interfaz web completa en `src/main/webapp/`.

---

## 🏗️ Estructura del Proyecto (Estilo FitCIMM)

```text
distribuidora-delcampo-api/
├── pom.xml                                  # Maven WAR con Jakarta EE 10, Gson y Tomcat embebido
├── nb-configuration.xml                     # Configuración NetBeans Jakarta 10-web
└── src/
    └── main/
        ├── java/co/edu/sena/cimm/distribuidora/
        │   ├── ServidorPrincipal.java       # Arranque inmediato con 1 clic (Run File)
        │   │
        │   ├── controller/                  # CAPA CONTROLADORES (Servlets HTTP)
        │   │   ├── ProductoController.java  # /api/productos
        │   │   ├── CampesinoController.java # /api/campesinos
        │   │   ├── PedidoController.java    # /api/pedidos
        │   │   ├── AuthController.java      # /api/auth
        │   │   ├── MarcaController.java     # /api/marcas
        │   │   └── DashboardController.java # /api/dashboard
        │   │
        │   ├── dao/                         # CAPA DAO: Acceso a datos quemados en memoria
        │   │   ├── ProductoDAO.java         # insertar, listar, obtenerPorId, actualizar, eliminar
        │   │   ├── CampesinoDAO.java        # datos semilla de productores campesinos
        │   │   ├── PedidoDAO.java           # pedidos y cambio de estados
        │   │   ├── UsuarioDAO.java          # usuarios y autenticación
        │   │   └── MarcaCampesinaDAO.java   # marcas y cooperativas
        │   │
        │   ├── servicio/                    # CAPA SERVICIOS: Lógica del negocio
        │   │   ├── ProductoService.java
        │   │   ├── CampesinoService.java
        │   │   ├── PedidoService.java
        │   │   ├── UsuarioService.java
        │   │   ├── MarcaCampesinaService.java
        │   │   └── DashboardService.java
        │   │
        │   ├── modelo/                      # CAPA MODELO: Clases de dominio (POJOs)
        │   │   ├── Producto.java
        │   │   ├── Campesino.java
        │   │   ├── Pedido.java
        │   │   ├── ItemPedido.java
        │   │   ├── Usuario.java
        │   │   └── MarcaCampesina.java
        │   │
        │   ├── dto/                         # CAPA DTO: Objetos de transferencia JSON
        │   │   ├── LoginRequestDTO.java
        │   │   ├── LoginResponseDTO.java
        │   │   ├── RegistroRequestDTO.java
        │   │   ├── CrearProductoDTO.java
        │   │   ├── CrearPedidoDTO.java
        │   │   ├── ItemPedidoRequestDTO.java
        │   │   ├── ActualizarEstadoDTO.java
        │   │   ├── DashboardResumenDTO.java
        │   │   └── RespuestaDTO.java
        │   │
        │   └── util/                        # UTILIDADES
        │       ├── JsonUtil.java            # Google Gson con LocalDate y LocalDateTime
        │       └── RespuestaUtil.java       # Manejo de CORS y respuestas HTTP
        │
        └── webapp/                          # DISEÑO WEB DE LA DISTRIBUIDORA
            ├── index.html                   # Portal interactivo completo con roles
            ├── css/
            │   └── style.css                # Estilos visuales del campo
            ├── js/
            │   └── app.js                   # Lógica frontend interactiva
            ├── images/                      # Fotografías de cosechas y campesinos
            └── WEB-INF/
                └── web.xml                  # Descriptor web estándar Jakarta 6.0
```

---

## 🚀 Cómo Abrir y Ejecutar en Apache NetBeans

### Opción 1: Ejecución con 1 Clic (Recomendada)
1. Abre **Apache NetBeans**.
2. Ve a `File` -> `Open Project...` y abre la carpeta del proyecto.
3. Despliega el paquete `co.edu.sena.cimm.distribuidora`.
4. Haz clic derecho sobre **`ServidorPrincipal.java`** y selecciona **`Run File`** (o presiona `Shift + F6`).
5. Abre en tu navegador:
   👉 **http://localhost:8080/**

### Opción 2: Despliegue en Apache Tomcat desde NetBeans
1. Haz clic derecho sobre el proyecto `distribuidora-delcampo-api` en la vista de proyectos.
2. Selecciona **`Run`** o **`Deploy`** hacia tu servidor Tomcat configurado.

### Opción 3: Ejecución con Docker
1. Construye la imagen Docker:
   ```bash
   docker build -t distribuidora-delcampo .
   ```
2. Ejecuta el contenedor en el puerto 8080:
   ```bash
   docker run -d -p 8080:8080 --name distribuidora-api distribuidora-delcampo
   ```
3. Abre en tu navegador:
   👉 **http://localhost:8080/**
