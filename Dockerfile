FROM openjdk:17.0.2-jdk-slim-buster
COPY target/*.jar MinionsDD.jar
ENTRYPOINT ["java", "-jar", "MinionsDD.jar"]