#FROM openjdk:17-jdk
#WORKDIR /app
#COPY target/*.jar /app/MinionsDD.jar
#EXPOSE 8489
#CMD ["java", "-jar", "MinionsDD.jar"]
FROM openjdk:17.0.2-jdk-slim-buster
COPY target/*.jar MinionsDD.jar
ENTRYPOINT ["java", "-jar", "MinionsDD.jar"]