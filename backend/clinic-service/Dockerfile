FROM openjdk:21-jdk

WORKDIR /app

COPY /target/clinic-service-0.0.1-SNAPSHOT.jar clinic-service.jar

EXPOSE 8071

ENTRYPOINT [ "java", "-jar", "clinic-service.jar" ]