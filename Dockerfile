FROM openjdk:17 AS build
WORKDIR /app
COPY target/SpaceLab_4_2-1.0-SNAPSHOT.jar /app/MinionsDD.jar

FROM openjdk:17
WORKDIR /app
COPY --from=build /app/MinionsDD.jar .
EXPOSE 8489

ENTRYPOINT ["java", "-jar", "MinionsDD.jar"]
