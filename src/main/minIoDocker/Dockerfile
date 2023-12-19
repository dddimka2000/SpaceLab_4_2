FROM openjdk:17-jdk
WORKDIR /app
COPY target/*.jar /app/MinionsDD.jar
EXPOSE 8489
CMD ["java", "-jar", "MinionsDD.jar"]