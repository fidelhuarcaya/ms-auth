# Usa una imagen base de OpenJDK
FROM openjdk:17-jdk-alpine

# Establece el mantenedor de la imagen
LABEL maintainer="tu-email@dominio.com"

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR generado por Maven al contenedor
COPY target/tu-aplicacion.jar app.jar

# Expone el puerto en el que la aplicación se ejecutará
EXPOSE 8080

# Define el comando de entrada para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
