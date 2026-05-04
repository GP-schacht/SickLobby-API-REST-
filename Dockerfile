FROM openjdk:17
COPY build/libs/*.jar app.jar
ENV FIREBASE_CREDENTIALS_JSON=
ENTRYPOINT ["java", "-jar", "/app.jar"]
