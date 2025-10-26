# Usamos la imagen base de Eclipse Temurin con JRE Alpine, que es ligera y adecuada para aplicaciones spring boot.
FROM eclipse-temurin:21-jre-alpine

# Definimos el directorio de trabajo dentro del contenedor
WORKDIR /app

# Agregamos información al contenedor
LABEL maintainer="Alejandro Marcelo Arando Espejo <alejandro.arando@ucb.edu.bo>"
LABEL description="Micro servicio que gestiona el API Gateway."
LABEL version="1.0"

# Documentación de variables de entorno requeridas para la configuración del servicio
LABEL env.SERVER_PORT="Puerto del servicio (default: 8080)"
LABEL env.EUREKA_CLIENT_SERVICEURL_DEFAULTZONE="URL del servidor Eureka"

# Copiamos el archivo JAR construido en el contenedor
COPY target/gatewayservice-0.0.1-SNAPSHOT.jar app.jar

# Exponemos el puerto definido en la variable de entorno (para documentación y configuración de red)
EXPOSE 8080

# Comando para ejecutar la aplicación Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
