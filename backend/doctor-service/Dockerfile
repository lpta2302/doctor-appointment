FROM openjdk:23-jdk

WORKDIR /app

COPY /target/doctor-service-0.0.1-SNAPSHOT.jar doctor-service.jar

EXPOSE 8070

ENTRYPOINT [ "java", "-jar", "doctor-service.jar" ]