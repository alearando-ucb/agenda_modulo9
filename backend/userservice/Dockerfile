# Usamos la imagen base de Eclipse Temurin con JRE Alpine, que es ligera y adecuada para aplicaciones spring boot.
FROM eclipse-temurin:21-jre-alpine

# Definimos el directorio de trabajo dentro del contenedor
WORKDIR /app

# Agregamos información al contenedor
LABEL maintainer="Alejandro Marcelo Arando Espejo <alejandro.arando@ucb.edu.bo>"
LABEL description="Micro servicio que gestiona los usuarios."
LABEL version="1.0.2"

# Documentación de variables de entorno requeridas para la configuración del servicio
LABEL env.SERVER_PORT="Puerto del servicio (default: 8081)"
LABEL env.EUREKA_CLIENT_SERVICEURL_DEFAULTZONE="URL del servidor Eureka"
LABEL env.DB_SERVER="Host de la base de datos"
LABEL env.DB_NAME="Nombre de la base de datos"
LABEL env.DB_USERNAME="Usuario de la base de datos"
LABEL env.DB_PASSWORD="Contraseña de la base de datos"
LABEL env.RABBITMQ_HOST="Host de RabbitMQ"
LABEL env.RABBITMQ_PORT="Puerto de RabbitMQ"
LABEL env.RABBITMQ_USERNAME="Usuario de RabbitMQ"
LABEL env.RABBITMQ_PASSWORD="Contraseña de RabbitMQ"
LABEL volume.avatars.path="/app/avatars"
LABEL volume.avatars.description="Se requiere un volumen persistente para almacenar los avatares de los usuarios. Montar en /app/avatars."

# Copiamos el archivo JAR construido en el contenedor
COPY target/userservice-1.0.2-SNAPSHOT.jar app.jar


# Exponemos el puerto definido en la variable de entorno (para documentación y configuración de red)
EXPOSE 8081

# Comando para ejecutar la aplicación Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]
