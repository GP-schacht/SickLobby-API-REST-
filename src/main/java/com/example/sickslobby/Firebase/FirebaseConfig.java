package com.example.sickslobby.Firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.cloud.FirestoreClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    // Añade un logger para ver mensajes detallados
    private static final Logger logger = LoggerFactory.getLogger(FirebaseConfig.class);

    @Bean
    public Firestore firebaseAppInitializer() { // Removí 'throws IOException' para manejarla aquí
        InputStream serviceAccount = null;
        try {
            // Intenta cargar el archivo de credenciales
            ClassPathResource resource = new ClassPathResource("firebase/serviceAccountKey.json");
            if (!resource.exists()) {
                logger.error("¡ERROR FATAL! El archivo serviceAccountKey.json NO fue encontrado en src/main/resources. " +
                        "Por favor, verifica la ruta y el nombre.");
                throw new IOException("serviceAccountKey.json not found!");
            }
            serviceAccount = resource.getInputStream();

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            FirebaseApp  app = null;

            if (FirebaseApp.getApps().isEmpty()) {
                app = FirebaseApp.initializeApp(options);
            }
            else {
                app = FirebaseApp.getApps().get(0);
            }
            logger.info("FirebaseApp inicializando...");
            return FirestoreClient.getFirestore();

        }
        catch (IOException e) {
            logger.error("Error al cargar el archivo de credenciales de Firebase o al inicializar FirebaseApp: " + e.getMessage(), e);

            throw new RuntimeException("Error during FirebaseApp initialization: " + e.getMessage(), e);}
        catch (Exception e) {
            logger.error("Error inesperado durante la inicialización de FirebaseApp: " + e.getMessage(), e);
            throw new RuntimeException("Unexpected error during FirebaseApp initialization: " + e.getMessage(), e);
        }
        finally {
            // Asegúrate de que el stream se cierre incluso si hay una excepción
            if (serviceAccount != null) {
                try {
                    serviceAccount.close();
                } catch (IOException e) {
                    logger.error("Error al cerrar el InputStream de la cuenta de servicio: " + e.getMessage(), e);
                }
            }
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





