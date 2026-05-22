package packagee.ospedale.repository;

import java.util.HashMap;
import java.util.List;

import packagee.ospedale.model.Patient;
import packagee.ospedale.model.User;
import packagee.ospedale.model.storage.Storage;

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
        return storage.addUser(user);
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
