package com.example.sickslobby.Services.use;

import com.example.sickslobby.Config.FirebaseConfig;
import com.example.sickslobby.Dto.PacienteDTO;
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
public class PacientesServices implements PacientesServicesI {


    @Autowired
    private FirebaseConfig firebaseConfig;

    @Override
    public List<PacienteDTO> list() {
        return List.of();
    }

    @Override
    public Boolean add(PacienteDTO post) {
        Map<String, Object> docData = new HashMap<>();
        docData.put("apellido", post.getApellido());
        docData.put("edad", post.getEdad());
        docData.put("estadoCivil", post.getEstadoCivil());
        docData.put("nombre", post.getNombre());
        docData.put("sexo", post.getSexo());
        docData.put("grupoEdad", post.getGrupoEdad());
        docData.put("telefono", post.getTelefono());
        docData.put("ocupacion", post.getOcupacion());


        CollectionReference posts = firebaseConfig.getFirestore().collection("Pacientes");
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
    public Boolean edit(String id, PacienteDTO post) {
        return null;
    }

    @Override
    public Boolean remove(String id) {
        return null;
    }
}
