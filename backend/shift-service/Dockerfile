FROM openjdk:21-jdk

WORKDIR /app

COPY /target/shift-service-0.0.1-SNAPSHOT.jar shift-service.jar

EXPOSE 8071

ENTRYPOINT [ "java", "-jar", "shift-service.jar" ]