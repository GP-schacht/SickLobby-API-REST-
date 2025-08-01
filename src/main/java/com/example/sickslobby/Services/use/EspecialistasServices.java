package com.example.sickslobby.Services.use;

import com.example.sickslobby.Config.FirebaseConfig;
import com.example.sickslobby.Dto.EspecialistaDTO;
import com.example.sickslobby.Dto.PacienteDTO;
import com.example.sickslobby.Services.EspecialistaServicesI;
import com.example.sickslobby.Services.PacientesServicesI;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.WriteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class EspecialistasServices implements EspecialistaServicesI {

    @Autowired
    private FirebaseConfig firebaseConfig;

    @Override
    public List<EspecialistaDTO> list() {
        return List.of();
    }

    @Override
    public Boolean add(EspecialistaDTO post) {
        Map<String, Object> docData = new HashMap<>();
        docData.put("Apellido", post.getApellido());
        docData.put("Edad", post.getEdad());
        docData.put("Especialidad", post.getEspecialidad());
        docData.put("Nombre", post.getNombre());

        CollectionReference posts = firebaseConfig.getFirestore().collection("Especialistas");
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



