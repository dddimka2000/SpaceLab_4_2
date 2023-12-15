FROM openjdk:17-jdk
WORKDIR /app
COPY target/SpaceLab_4_2-1.0-SNAPSHOT.jar /app/MinionsDD.jar
EXPOSE 8080
CMD ["java", "-jar", "MinionsDD.jar"]