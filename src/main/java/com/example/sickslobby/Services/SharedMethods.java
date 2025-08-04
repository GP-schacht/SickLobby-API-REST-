package com.example.sickslobby.Services;

import com.example.sickslobby.Config.FirebaseConfig;
import com.google.cloud.firestore.CollectionGroup;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SharedMethods {


    private final FirebaseConfig firebaseConfig;

    public SharedMethods(FirebaseConfig firebaseConfig) {
        this.firebaseConfig = firebaseConfig;
    }

    public CollectionReference getCollection(@NotNull String collectionName) {
        if (collectionName == null || collectionName.isEmpty()) {
            throw new IllegalArgumentException("Nombre del collection no puede ser nulo");

        }
        return firebaseConfig.getFirestore().collection(collectionName);
    }

    public CollectionGroup getCollectionGroup(@NotNull String collectionName) {
        if (collectionName == null || collectionName.isEmpty()) {
            throw new IllegalArgumentException("Nombre del collection no puede ser nulo");

        }
        return firebaseConfig.getFirestore().collectionGroup(collectionName);
    }

    public void idExiste(@NotNull String collectionName, @NotNull DocumentSnapshot documentSnapshot) {
        if (!documentSnapshot.exists()) {
            throw new IllegalArgumentException("Id no encontrado en:" + collectionName);
        }
    }

}