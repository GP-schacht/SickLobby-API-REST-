package com.example.sickslobby.Services.use;

import com.example.sickslobby.Firebase.FirebaseConfig;
import com.example.sickslobby.PostDTO;
import com.example.sickslobby.Services.SicksLobbyServicesI;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class SicksLobbyServicesUse implements SicksLobbyServicesI {


    @Autowired
    private FirebaseConfig firebaseConfig;

    @Override
    public List<PostDTO> list() {
        return List.of();
    }

    @Override
    public Boolean add(PostDTO post) {
        Map<String, Object> docData = new HashMap<>();
        docData.put("apellido", post.getApellido());
        docData.put("edad", post.getEdad());
        docData.put("identificacion", post.getIdentificacion());
        docData.put("nombre", post.getNombre());

        CollectionReference posts = firebaseConfig.getFirestore().collection("pacientes");
        ApiFuture<WriteResult> writeResult = posts.document().create(docData);
        try {
            if (null != writeResult.get()) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            return Boolean.FALSE;

        }
    }

    @Override
    public Boolean edit(String id, PostDTO post) {
        return null;
    }

    @Override
    public Boolean remove(String id) {
        return null;
    }
}
