FROM openjdk:23-jdk

WORKDIR /app

COPY /target/discovery-server-0.0.1-SNAPSHOT.jar discovery-server.jar

EXPOSE 8761

ENTRYPOINT [ "java", "-jar", "discovery-server.jar" ]