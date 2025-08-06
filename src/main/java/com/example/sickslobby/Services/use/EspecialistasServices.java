package com.example.sickslobby.Services.use;

import com.example.sickslobby.Dto.EspecialistaDTO;
import com.example.sickslobby.Services.ServicesInterface;
import com.example.sickslobby.Services.SharedMethods;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class EspecialistasServices implements ServicesInterface<EspecialistaDTO> {

    private final SharedMethods sharedMethods;
    EspecialistaDTO especialistaDTO;

    public EspecialistasServices(SharedMethods sharedMethods) {
        this.sharedMethods = sharedMethods;
    }

    @Override
    public List<EspecialistaDTO> list() {
        List<EspecialistaDTO> especialistas = new ArrayList<>();

        ApiFuture<QuerySnapshot> future = sharedMethods.getCollection("Especialistas").get();
        try {
            for (DocumentSnapshot doc : future.get().getDocuments()) {
                especialistaDTO = doc.toObject(EspecialistaDTO.class);
                assert especialistaDTO != null;
                especialistaDTO.setId(doc.getId());
                especialistas.add(especialistaDTO);
            }
            return especialistas;

        } catch (Exception e) {
            return null;
        }

    }

    @Override
    public Boolean add(EspecialistaDTO post) {
        Map<String, Object> docData = new HashMap<>();
        docData.put("apellido", post.getApellido());
        docData.put("edad", post.getEdad());
        docData.put("especialidad", post.getEspecialidad());
        docData.put("nombre", post.getNombre());

        CollectionReference posts = sharedMethods.getCollection("Especialistas");
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
    public Boolean edit(String id, EspecialistaDTO post) {

        Map<String, Object> docData = new HashMap<>();
        if (post.getApellido() != null) docData.put("apellido", post.getApellido());
        if (post.getEdad() != null) docData.put("edad", post.getEdad());
        if (post.getEspecialidad() != null) docData.put("especialidad", post.getEspecialidad());
        if (post.getNombre() != null) docData.put("nombre", post.getNombre());

        DocumentReference esprRef = sharedMethods.getCollection("Especialistas").document(id);
        sharedMethods.idExiste("Especialistas", esprRef);
        ApiFuture<WriteResult> writeResult = esprRef.set(docData, SetOptions.merge());

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
    public Boolean remove(String id) {
        DocumentReference esprRef = sharedMethods.getCollection("Especialistas").document(id);
        sharedMethods.idExiste("Especialistas", esprRef);
        ApiFuture<WriteResult> writeResult = esprRef.delete();
        try {
            if (null != writeResult) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            return Boolean.FALSE;
        }
    }


}



