package packagee.ospedale.repository;

import java.util.HashMap;
import java.util.List;
import packagee.ospedale.model.Doctor;
import packagee.ospedale.model.User;
import packagee.ospedale.model.Specialty;

/**
 * Define las operaciones de acceso a datos para doctores.
 */
public interface DoctorRepository {

    Doctor getDoctorById(long id);

    User getUserByUsername(String username);

    boolean addDoctor(Doctor doctor);

    void updateDoctor(Doctor doctor);

    List<Doctor> getAllDoctors();

    HashMap<String, Object> serializeDoctor(Doctor doctor);

    Specialty getSpecialtyByName(String name);
}
