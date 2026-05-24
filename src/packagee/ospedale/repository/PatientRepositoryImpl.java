/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.ospedale.repository;

import java.util.HashMap;
import java.util.List;
import packagee.ospedale.model.Patient;
import packagee.ospedale.model.User;
import packagee.ospedale.model.storage.Storage;
import packagee.ospedale.observer.StorageEventType;

/**
 * Implementacion del repositorio de pacientes usando el almacenamiento compartido.
 */
public class PatientRepositoryImpl implements PatientRepository {

    private final Storage storage;

    public PatientRepositoryImpl() {
        storage = Storage.getInstance();
    }

    @Override
    public Patient getPatientById(long id) {
        return storage.getPatientById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return storage.getUserByUsername(username);
    }

    @Override
    public boolean addUser(User user) {
        boolean result = storage.addUser(user);
        if (result) {
            storage.publishEvent(StorageEventType.USERS_CHANGED);
        }
        return result;
    }

    @Override
    public void updatePatient(Patient patient) {
        storage.publishEvent(StorageEventType.USERS_CHANGED);
    }

    @Override
    public HashMap<String, Object> serializePatient(Patient patient) {
        return storage.serializePatient(patient);
    }

    @Override
    public List<User> getAllUsers() {
        return storage.getAllUsers();
    }
}
