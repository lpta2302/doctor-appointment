FROM openjdk:21-jdk
ENV TZ=Asia/Ho_Chi_Minh

WORKDIR /app

COPY /target/appointment-service-0.0.1-SNAPSHOT.jar appointment-service.jar

EXPOSE 8073

ENTRYPOINT [ "java", "-jar", "appointment-service.jar" ]