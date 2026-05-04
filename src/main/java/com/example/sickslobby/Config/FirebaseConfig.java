package com.example.sickslobby.Config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.FirestoreClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    private static final Logger logger = LoggerFactory.getLogger(FirebaseConfig.class);

    @Value("${firebase.credentials.path:src/main/resources/firebase/serviceAccountKey.json}")
    private String credentialsPath;

    @Value("${firebase.credentials.json:}")
    private String credentialsJson;

    @Bean
    public Firestore firebaseAppInitializer() {

        try {
            InputStream serviceAccount;

            if (!credentialsJson.isEmpty()) {
                serviceAccount = new ByteArrayInputStream(credentialsJson.getBytes());
                logger.info("Cargando credenciales de Firebase desde variable de entorno...");
            } else {
                serviceAccount = new FileInputStream(credentialsPath);
                logger.info("Cargando credenciales de Firebase desde archivo: {}", credentialsPath);
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp app;

            if (FirebaseApp.getApps().isEmpty()) {
                app = FirebaseApp.initializeApp(options);
            } else {
                app = FirebaseApp.getApps().get(0);
            }
            logger.info("FirebaseApp inicializado correctamente.");
            return FirestoreClient.getFirestore();

        } catch (IOException e) {
            logger.error("Error al cargar el archivo de credenciales de Firebase o al inicializar FirebaseApp: {}", e.getMessage(), e);

            throw new RuntimeException("Error during FirebaseApp initialization: " + e.getMessage(), e);
        } catch (Exception e) {
            logger.error("Error inesperado durante la inicialización de FirebaseApp: {}", e.getMessage(), e);
            throw new RuntimeException("Unexpected error during FirebaseApp initialization: " + e.getMessage(), e);
        }
    }

    // Mantén tus beans de Firestore y FirebaseAuth, asegurándote de que dependan del FirebaseApp inicializado
    @Bean
    public Firestore getFirestore() {
        return FirestoreClient.getFirestore();
    }

    @Bean
    public FirebaseAuth getFirebaseAuth() {
        return FirebaseAuth.getInstance();
    }
}





