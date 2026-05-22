package packagee.ospedale.repository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import packagee.ospedale.model.Appointment;
import packagee.ospedale.model.Doctor;
import packagee.ospedale.model.Patient;
import packagee.ospedale.model.Specialty;
import packagee.ospedale.model.storage.Storage;
import packagee.ospedale.observer.StorageEventType;

/**
 * Implementacion concreta del repositorio de citas sobre el almacenamiento en memoria.
 */
public class AppointmentRepositoryImpl implements AppointmentRepository {

    private final Storage storage;

    public AppointmentRepositoryImpl() {
        storage = Storage.getInstance();
    }

    @Override
    public void addAppointment(Appointment appointment) {
        storage.addAppointment(appointment);
        storage.publishEvent(StorageEventType.APPOINTMENTS_CHANGED);
    }

    @Override
    public Appointment getAppointmentById(String id) {
        return storage.getAppointmentById(id);
    }

    @Override
    public void updateAppointment(Appointment appointment) {
        storage.publishEvent(StorageEventType.APPOINTMENTS_CHANGED);
    }

    @Override
    public boolean isDoctorAvailable(Doctor doctor, LocalDateTime datetime) {
        return storage.isDoctorAvailable(doctor, datetime);
    }

    @Override
    public boolean isDoctorAvailableExcluding(Doctor doctor, LocalDateTime datetime, String excludeAppointmentId) {
        return storage.isDoctorAvailableExcluding(doctor, datetime, excludeAppointmentId);
    }

    @Override
    public String generateAppointmentId(long patientId) {
        return storage.generateAppointmentId(patientId);
    }

    @Override
    public Patient getPatientById(long id) {
        return storage.getPatientById(id);
    }

    @Override
    public Doctor getDoctorById(long id) {
        return storage.getDoctorById(id);
    }

    @Override
    public Doctor findAvailableDoctor(
            Specialty specialty,
            LocalDateTime datetime
    ) {
        return storage.findAvailableDoctor(specialty, datetime);
    }

    @Override
    public List<HashMap<String, Object>> getAppointmentsByPatientSorted(long patientId) {
        return storage.getAppointmentsByPatientSorted(patientId);
    }

    @Override
    public List<HashMap<String, Object>> getAppointmentsByDoctorSorted(
            long doctorId,
            boolean pendingOnly
    ) {
        return storage.getAppointmentsByDoctorSorted(doctorId, pendingOnly);
    }

    @Override
    public List<HashMap<String, Object>> getAllAppointments() {
        List<HashMap<String, Object>> result = new java.util.ArrayList<>();

        for (Appointment appointment : storage.getAllAppointments()) {
            result.add(storage.serializeAppointment(appointment));
        }

        return result;
    }

    @Override
    public HashMap<String, Object> serializeAppointment(Appointment appointment) {
        return storage.serializeAppointment(appointment);
    }
}
