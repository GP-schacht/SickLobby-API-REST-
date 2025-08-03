package com.example.sickslobby.Services.use;

import com.example.sickslobby.Dto.CitasDTO;
import com.example.sickslobby.Services.CitasServicesI;
import com.example.sickslobby.Services.SharedMethods;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.WriteResult;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class CitasServices implements CitasServicesI {


    private final SharedMethods shared;



    public CitasServices(SharedMethods shared) {
        this.shared = shared;
    }

    public List<CitasDTO> list() {
        return List.of();
    }
    @Override
    public Boolean add(CitasDTO cita, String especialistaId, String pacienteId) {

        DocumentReference especialistaRef = shared.getCollection("Especialistas").document(especialistaId);
        CollectionReference citasRef = especialistaRef.collection("Citas");
        DocumentReference pacienteRef = shared.getCollection("Pacientes").document(pacienteId);

        try {
            DocumentSnapshot pacienteSnap = pacienteRef.get().get();
            shared.idExiste("Pacientes",pacienteSnap);

            DocumentSnapshot especialistaSnap = especialistaRef.get().get();
            shared.idExiste("Especialistas",especialistaSnap);

            Map<String, Object> citaData = new HashMap<>();
            citaData.put("fechaCita", cita.getFechaCita());
            citaData.put("estado", cita.getEstado());
            citaData.put("nombrePaciente", pacienteSnap.getString("nombre"));
            citaData.put("pacienteId", pacienteId);

            // Generar ID automáticamente
            ApiFuture<DocumentReference> writeResult = citasRef.add(citaData);

            return writeResult.get() != null;

        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }



    @Override
    public Boolean edit(String id, CitasDTO post) {
        return null;
    }

    @Override
    public Boolean remove(String id) {
        return null;
    }


}
