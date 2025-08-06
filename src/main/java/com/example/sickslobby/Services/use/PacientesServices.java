package com.example.sickslobby.Services.use;

import com.example.sickslobby.Config.FirebaseConfig;
import com.example.sickslobby.Dto.PacienteDTO;
import com.example.sickslobby.Services.ServicesInterface;
import com.example.sickslobby.Services.SharedMethods;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class PacientesServices implements ServicesInterface<PacienteDTO> {

    private final SharedMethods sharedMethods;

    private PacienteDTO pacienteDTO;


    public PacientesServices(SharedMethods sharedMethods) {
        this.sharedMethods = sharedMethods;
    }

    @Override
    public List<PacienteDTO> list() {
        List<PacienteDTO> pacientes= new ArrayList<>();
ApiFuture<QuerySnapshot> future = sharedMethods.getCollection("Pacientes").get();

try{
    for(DocumentSnapshot doc :   future.get().getDocuments()){
        pacienteDTO = doc.toObject(PacienteDTO.class);
        assert pacienteDTO != null;
        pacienteDTO.setId(doc.getId());
        pacientes.add(pacienteDTO);
    }
    return pacientes;
}
catch (Exception e){
    e.printStackTrace();
}
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


        CollectionReference posts = sharedMethods.getCollection("Pacientes");
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
    public Boolean edit(String id, PacienteDTO post)  {

        Map<String, Object> docData = new HashMap<>();

        if (post.getApellido() != null) docData.put("apellido", post.getApellido());
        if (post.getEdad() != null) docData.put("edad", post.getEdad());
        if (post.getEstadoCivil() != null) docData.put("estadoCivil", post.getEstadoCivil());
        if (post.getNombre() != null) docData.put("nombre", post.getNombre());
        if (post.getSexo() != null) docData.put("sexo", post.getSexo());
        if (post.getGrupoEdad() != null) docData.put("grupoEdad", post.getGrupoEdad());
        if (post.getTelefono() != null) docData.put("telefono", post.getTelefono());
        if (post.getOcupacion() != null) docData.put("ocupacion", post.getOcupacion());


        DocumentReference posts = sharedMethods.getCollection("Pacientes").document(id);


        sharedMethods.idExiste("Pacientes", posts);
        ApiFuture<WriteResult> writeResult = posts.set(docData, SetOptions.merge());

        try {
            if (null != writeResult.get()) {
                return Boolean.TRUE;
            }
            return Boolean.FALSE;
        } catch (Exception e) {
            e.printStackTrace(); // o usa un logger
            return Boolean.FALSE;

        }
    }

    @Override
    public Boolean remove(String id) {
        DocumentReference esprRef = sharedMethods.getCollection("Pacientes").document(id);
        sharedMethods.idExiste("Pacientes", esprRef);
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
