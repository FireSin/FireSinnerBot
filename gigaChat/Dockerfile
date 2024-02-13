FROM openjdk:17-jdk-alpine

COPY russiantrustedca.pem /usr/local/share/ca-certificates
RUN update-ca-certificates

ARG JAR_FILE=target/*.jar

WORKDIR /app

COPY ${JAR_FILE} /app/app.jar

CMD ["java", "-jar", "-Dspring.profiles.active=prod", "app.jar"]