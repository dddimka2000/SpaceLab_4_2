FROM openjdk:17
WORKDIR /app
COPY target/SpaceLab_4_2-1.0-SNAPSHOT.jar /app/MinionsDD.jar
EXPOSE 8080
CMD["java","-jar","SpaceLab_4_2-1.0-SNAPSHOT.jar"]