FROM openjdk:17-jdk
COPY target/SpaceLab_4_2-1.0-SNAPSHOT.jar MinionsDD.jar
EXPOSE 8489
CMD ["java", "-jar", "MinionsDD.jar"]