package packagee.ospedale.repository;

import java.util.HashMap;
import java.util.List;
import packagee.ospedale.model.Patient;
import packagee.ospedale.model.User;

/**
 * Define las operaciones de acceso a datos para pacientes y usuarios.
 */
public interface PatientRepository {

    Patient getPatientById(long id);

    User getUserByUsername(String username);

    boolean addUser(User user);

    HashMap<String, Object> serializePatient(Patient patient);

    List<User> getAllUsers();
}
