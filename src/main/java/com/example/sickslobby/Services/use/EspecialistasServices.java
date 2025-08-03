package com.example.sickslobby.Services.use;

import com.example.sickslobby.Config.FirebaseConfig;
import com.example.sickslobby.Dto.EspecialistaDTO;
import com.example.sickslobby.Dto.PacienteDTO;
import com.example.sickslobby.Services.EspecialistaServicesI;
import com.example.sickslobby.Services.PacientesServicesI;
import com.example.sickslobby.Services.SharedMethods;
import com.google.api.core.ApiFuture;
import com.google.api.core.ApiFutures;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;


@Service
public class EspecialistasServices implements EspecialistaServicesI {

private final SharedMethods sharedMethods;

    public EspecialistasServices(SharedMethods sharedMethods) {
        this.sharedMethods = sharedMethods;
    }


    @Override
    public List<EspecialistaDTO> list() {
        List<EspecialistaDTO> especialistas = new ArrayList<>();
        EspecialistaDTO especialistaDTO;
        ApiFuture<QuerySnapshot> future = sharedMethods.getCollection("Especialistas").get();
        try {
            for(DocumentSnapshot doc :   future.get().getDocuments()){
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
        return null;
    }

    @Override
    public Boolean remove(String id) {
        return null;
    }


}



