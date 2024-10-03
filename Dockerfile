FROM maven:3.8.1-openjdk-17-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -DskipTests

FROM openjdk:17-jdk-alpine
COPY --from=build /home/app/target/ms-auth-0.0.1-SNAPSHOT.jar ms-auth-0.0.1.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/ms-auth-0.0.1.jar"]
