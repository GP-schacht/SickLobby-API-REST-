package com.example.sickslobby.Services.use;

import com.example.sickslobby.Dto.CitasDTO;
import com.example.sickslobby.Dto.CrearCitaDTO;
import com.example.sickslobby.Services.CitasServicesI;
import com.example.sickslobby.Services.SharedMethods;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import com.google.cloud.firestore.SetOptions;

@Service
public class CitasServices implements CitasServicesI {


    private final SharedMethods shared;


    public CitasServices(SharedMethods shared) {
        this.shared = shared;
    }

    @Override
    public CrearCitaDTO getById(String id, String especialistaId) {
        try {
            // Construir la referencia al documento de la cita
            DocumentReference citaRef = shared.getCollection("Especialistas")
                    .document(especialistaId)
                    .collection("Citas")
                    .document(id);

            // Obtener el snapshot del documento
            DocumentSnapshot snapshot = citaRef.get().get();

            // Validar que el documento exista
            shared.idExiste("Citas", citaRef);

            // Convertir el documento a CitasDTO
            CitasDTO cita = snapshot.toObject(CitasDTO.class);

            // Construir el DTO de respuesta
            CrearCitaDTO citaDTO = new CrearCitaDTO();
            citaDTO.setCita(cita);
            citaDTO.setEspecialistaId(especialistaId);
            citaDTO.setPacienteId(snapshot.getString("pacienteId")); // Validar si es null si es necesario

            return citaDTO;

        } catch (Exception e) {
            e.printStackTrace();
            // Podrías lanzar una excepción personalizada aquí si lo prefieres
            return null;
        }
    }



    @Override
    public List<CitasDTO> list() {
        List<CitasDTO> citasList = new ArrayList<>();
        try {
            ApiFuture<QuerySnapshot> future =
                    shared.getCollectionGroup("Citas").get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();


            for (QueryDocumentSnapshot document : documents) {
                CitasDTO citas = new CitasDTO();
                citas.setId(document.getId());
                citas.setFechaCita(document.getString("fechaCita"));
                citas.setEstado(document.getString("estado"));
                citasList.add(citas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return citasList;
    }


    @Override
    public Boolean add(CitasDTO cita, String especialistaId, String pacienteId) {

        DocumentReference especialistaRef = shared.getCollection("Especialistas").document(especialistaId);
        CollectionReference citasRef = especialistaRef.collection("Citas");
        DocumentReference pacienteRef = shared.getCollection("Pacientes").document(pacienteId);

        try {
            //Existe doc paciente?
            shared.idExiste("Pacientes", pacienteRef);
            //Existe doc especialista?
            shared.idExiste("Especialistas", especialistaRef);

            Map<String, Object> citaData = new HashMap<>();
            citaData.put("fechaCita", cita.getFechaCita());
            citaData.put("estado", cita.getEstado());
            //hacer spap a la instancia especifica en una referencia
            DocumentSnapshot pacienteSnap = pacienteRef.get().get();
            citaData.put("nombrePaciente", pacienteSnap.getString("nombre"));
            citaData.put("pacienteId", pacienteId);

            // Generar ID automáticamente
            ApiFuture<DocumentReference> writeResult = citasRef.add(citaData);

            return writeResult.get() != null;

        } catch (ExecutionException | InterruptedException e) {
            return Boolean.FALSE;
        }
    }


    @Override
    public Boolean edit(String id, CitasDTO post) {
        try {
            DocumentReference citaRef = shared.getCollection("Especialistas")
                    .document(post.getEspecialistaId())
                    .collection("Citas")
                    .document(id);

            shared.idExiste("Citas", citaRef);

            Map<String, Object> citaData = new HashMap<>();
            if (post.getFechaCita() != null) citaData.put("fechaCita", post.getFechaCita());
            if (post.getEstado() != null) citaData.put("estado", post.getEstado());

            ApiFuture<WriteResult> writeResult = citaRef.set(citaData, SetOptions.merge());
            return writeResult.get() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }

    @Override
    public Boolean remove(String id) {
        return remove(id, null);
    }

    public Boolean remove(String id, String especialistaId) {
        try {
            DocumentReference citaRef = shared.getCollection("Especialistas")
                    .document(especialistaId)
                    .collection("Citas")
                    .document(id);

            shared.idExiste("Citas", citaRef);

            ApiFuture<WriteResult> writeResult = citaRef.delete();
            return writeResult.get() != null;
        } catch (Exception e) {
            e.printStackTrace();
            return Boolean.FALSE;
        }
    }


}
