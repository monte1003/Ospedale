package packagee.ospedale.repository;

import java.util.HashMap;
import java.util.List;

import packagee.ospedale.model.Appointment;
import packagee.ospedale.model.Doctor;
import packagee.ospedale.model.Patient;
import packagee.ospedale.model.Specialty;

/**
 * Define las operaciones de acceso a datos para citas.
 */
public interface AppointmentRepository {

    void addAppointment(Appointment appointment);

    Appointment getAppointmentById(String id);
    
    List<HashMap<String, Object>> getAllAppointments();

    Patient getPatientById(long id);

    Doctor getDoctorById(long id);

    Doctor findAvailableDoctor(
            Specialty specialty,
            java.time.LocalDateTime datetime
    );

    List<HashMap<String, Object>> getAppointmentsByPatientSorted(long patientId);

    List<HashMap<String, Object>> getAppointmentsByDoctorSorted(
            long doctorId,
            boolean pendingOnly
    );

    HashMap<String, Object> serializeAppointment(Appointment appointment);
}
