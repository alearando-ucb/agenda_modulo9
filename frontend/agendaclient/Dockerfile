# Primera fase: Construcción de la aplicación React
# Esta imagen contiene Node.js y npm/yarn, necesarios para compilar la aplicación React.
FROM node:20-alpine as builder

# Establecemos el directorio de trabajo dentro de esta etapa del contenedor.
WORKDIR /app

# Copiamos los archivos de manifiesto de paquetes (package.json y yarn.lock)
COPY package*.json ./
# Instalamos las dependencias.
RUN npm ci --only=production

# Copiamos el resto del código fuente de la aplicación.
COPY . .

# Variables de entorno para React
ARG REACT_APP_API_BASE_URL=http://localhost:8080/api/v1
ENV REACT_APP_API_BASE_URL=$REACT_APP_API_BASE_URL

# Compilamos la aplicación React. Esto genera los archivos estáticos optimizados en el directorio 'build'.
RUN npm run build

# Segunda fase: Servir la aplicación con Nginx
# Imagen base ligera de Nginx para servir contenido estático.
FROM nginx:alpine as nginx-server

# Agregamos información al contenedor
LABEL maintainer="Alejandro Marcelo Arando Espejo <alejandro.arando@ucb.edu.bo>"
LABEL description="Micro servicio que gestiona el API Gateway."
LABEL version="1.0"

# Copiamos solo los archivos estáticos compilados de la etapa 'builder' al directorio raíz de Nginx.
COPY --from=builder /app/build /usr/share/nginx/html

# Copiamos un archivo de configuración de Nginx personalizado.
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Exponemos el puerto 80, que es el puerto estándar para servidores web HTTP.
EXPOSE 80

# Inicia Nginx en primer plano.
CMD ["nginx", "-g", "daemon off;"]
