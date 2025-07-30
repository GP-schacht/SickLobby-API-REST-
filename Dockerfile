FROM openjdk:17
COPY build/libs/gestion-pacientes-*.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
