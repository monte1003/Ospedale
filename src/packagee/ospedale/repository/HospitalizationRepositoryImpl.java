/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.ospedale.repository;

import java.util.HashMap;
import java.util.List;
import packagee.ospedale.model.Hospitalization;
import packagee.ospedale.model.storage.Storage;
import packagee.ospedale.observer.StorageEventType;

/**
 * Implementacion del repositorio de hospitalizaciones usando el almacenamiento compartido.
 */
public class HospitalizationRepositoryImpl implements HospitalizationRepository {

    private final Storage storage;

    public HospitalizationRepositoryImpl() {
        this.storage = Storage.getInstance();
    }

    @Override
    public void addHospitalization(Hospitalization hospitalization) {
        storage.addHospitalization(hospitalization);
        storage.publishEvent(StorageEventType.HOSPITALIZATIONS_CHANGED);
    }

    @Override
    public Hospitalization getHospitalizationById(String id) {
        return storage.getHospitalizationById(id);
    }

    @Override
    public void updateHospitalization(Hospitalization hospitalization) {
        storage.publishEvent(StorageEventType.HOSPITALIZATIONS_CHANGED);
    }

    @Override
    public List<HashMap<String, Object>> getHospitalizationsByPatient(long patientId) {
        return storage.getHospitalizationsByPatient(patientId);
    }

    @Override
    public List<HashMap<String, Object>> getRequestedHospitalizations() {
        return storage.getRequestedHospitalizations();
    }

    @Override
    public String generateHospitalizationId(long patientId) {
        return storage.generateHospitalizationId(patientId);
    }

    @Override
    public HashMap<String, Object> serializeHospitalization(Hospitalization hospitalization) {
        return storage.serializeHospitalization(hospitalization);
    }
}
