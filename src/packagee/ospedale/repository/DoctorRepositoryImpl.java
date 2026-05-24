package packagee.ospedale.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import packagee.ospedale.model.Doctor;
import packagee.ospedale.model.User;
import packagee.ospedale.model.Specialty;
import packagee.ospedale.model.storage.Storage;
import packagee.ospedale.observer.StorageEventType;

/**
 * Implementacion del repositorio de doctores usando el almacenamiento compartido.
 */
public class DoctorRepositoryImpl implements DoctorRepository {

    private final Storage storage;

    public DoctorRepositoryImpl() {
        this.storage = Storage.getInstance();
    }

    @Override
    public Doctor getDoctorById(long id) {
        return storage.getDoctorById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return storage.getUserByUsername(username);
    }

    @Override
    public boolean addDoctor(Doctor doctor) {
        boolean result = storage.addUser(doctor);
        if (result) {
            storage.publishEvent(StorageEventType.USERS_CHANGED);
        }
        return result;
    }

    @Override
    public void updateDoctor(Doctor doctor) {
        storage.publishEvent(StorageEventType.USERS_CHANGED);
    }

    @Override
    public List<Doctor> getAllDoctors() {
        List<Doctor> doctors = new ArrayList<>();
        for (User user : storage.getAllUsers()) {
            if (user instanceof Doctor doc) {
                doctors.add(doc);
            }
        }
        return doctors;
    }

    @Override
    public HashMap<String, Object> serializeDoctor(Doctor doctor) {
        return storage.serializeDoctor(doctor);
    }

    @Override
    public Specialty getSpecialtyByName(String name) {
        return storage.getSpecialtyByName(name);
    }
}
