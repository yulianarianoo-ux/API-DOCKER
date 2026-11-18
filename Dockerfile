# ==============================================================================
# ETAPA 1: COMPILACIÓN Y EMPAQUETADO (BUILDER)
# ==============================================================================
FROM maven:3.9-eclipse-temurin-21-alpine AS builder

WORKDIR /build

# 1. Copiar configuración de Maven y código fuente
COPY pom.xml .
COPY src ./src

# 2. Compilar y empaquetar el archivo WAR (omitiendo tests)
RUN mvn clean package -DskipTests

# ==============================================================================
# ETAPA 2: SERVIDOR WEB OFICIAL TOMCAT (RUNNER)
# ==============================================================================
FROM tomcat:10.1-jdk21-temurin

WORKDIR /usr/local/tomcat

# 3. Limpiar las aplicaciones de bienvenida por defecto de Tomcat
RUN rm -rf webapps/*

# 4. Copiar el archivo .war generado en la Etapa 1 como ROOT.war
COPY --from=builder /build/target/distribuidora-delcampo-api.war webapps/ROOT.war

# 5. Exponer el puerto estándar de Tomcat
EXPOSE 8080

# 6. Iniciar el servidor Apache Tomcat
CMD ["catalina.sh", "run"]
