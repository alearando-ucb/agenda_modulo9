# Usamos la imagen base de Eclipse Temurin con JRE Alpine, que es ligera y adecuada para aplicaciones spring boot.
FROM eclipse-temurin:21-jre-alpine

# Definimos el directorio de trabajo dentro del contenedor
WORKDIR /app

# Documentación de variables de entorno requeridas para la configuración del servicio
LABEL env.SERVER_PORT="Puerto del servicio (default: 8761)"

# Copiamos el archivo JAR construido en el contenedor
COPY target/discoveryservice-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto definido en la variable de entorno
EXPOSE 8761

# Comando para ejecutar la aplicación Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
