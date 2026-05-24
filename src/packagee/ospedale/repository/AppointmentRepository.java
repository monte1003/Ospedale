/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.ospedale.repository;

import java.util.HashMap;
import java.util.List;
import java.time.LocalDateTime;

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
    
    void updateAppointment(Appointment appointment);
    
    boolean isDoctorAvailable(Doctor doctor, LocalDateTime datetime);
    
    boolean isDoctorAvailableExcluding(Doctor doctor, LocalDateTime datetime, String excludeAppointmentId);
    
    String generateAppointmentId(long patientId);
    
    List<HashMap<String, Object>> getAllAppointments();

    Patient getPatientById(long id);

    Doctor getDoctorById(long id);

    Doctor findAvailableDoctor(
            Specialty specialty,
            LocalDateTime datetime
    );

    List<HashMap<String, Object>> getAppointmentsByPatientSorted(long patientId);

    List<HashMap<String, Object>> getAppointmentsByDoctorSorted(
            long doctorId,
            boolean pendingOnly
    );

    HashMap<String, Object> serializeAppointment(Appointment appointment);
}
