/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.ospedale.controller;

import packagee.ospedale.controller.utils.Response;
import packagee.ospedale.controller.utils.Status;
import packagee.ospedale.model.*;
import packagee.ospedale.model.storage.Storage;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;

public class MedicalServiceController {

    private static Response validateDate(String date) {
        try { LocalDate.parse(date.trim()); return null; }
        catch (DateTimeParseException e) {
            return new Response("Date must follow format YYYY-MM-DD", Status.BAD_REQUEST);
        }
    }

    private static Response validateTime(String time) {
        if (!time.trim().matches("([01]\\d|2[0-3]):[0-5]\\d"))
            return new Response("Time must follow 24h format hh:mm", Status.BAD_REQUEST);
        int minutes = Integer.parseInt(time.trim().split(":")[1]);
        if (minutes != 0 && minutes != 15 && minutes != 30 && minutes != 45)
            return new Response("Minutes must be 00, 15, 30 or 45", Status.BAD_REQUEST);
        return null;
    }

    // ── CITAS ──────────────────────────────────────────────────────────────

    /**
     * type=true: solicita por doctor específico (doctorId no vacío)
     * type=false: solicita por especialidad (doctorId vacío, specialty no vacío)
     */
    public static Response requestAppointment(String patientIdStr, String doctorIdStr,
            String specialty, String date, String time, String reason) {
        try {
            Response v;
            if ((v = validateDate(date)) != null) return v;
            if ((v = validateTime(time)) != null) return v;

            Storage storage = Storage.getInstance();
            long patientId = Long.parseLong(patientIdStr.trim());
            Patient patient = storage.getPatientById(patientId);
            if (patient == null)
                return new Response("Patient not found", Status.NOT_FOUND);

            LocalDateTime datetime = LocalDateTime.of(
                    LocalDate.parse(date.trim()),
                    LocalTime.parse(time.trim()));

            Doctor assignedDoctor;
            boolean byDoctor = !doctorIdStr.trim().isEmpty();

            if (byDoctor) {
                long doctorId = Long.parseLong(doctorIdStr.trim());
                assignedDoctor = storage.getDoctorById(doctorId);
                if (assignedDoctor == null)
                    return new Response("Doctor not found", Status.NOT_FOUND);
                if (!storage.isDoctorAvailable(assignedDoctor, datetime))
                    return new Response("Doctor not available at that time", Status.BAD_REQUEST);
            } else {
                Specialty spec = storage.getSpecialtyByName(specialty.trim());
                if (spec == null)
                    return new Response("Specialty not found", Status.NOT_FOUND);
                assignedDoctor = storage.findAvailableDoctor(spec, datetime);
                if (assignedDoctor == null)
                    return new Response("No doctor available for that specialty and time", Status.BAD_REQUEST);
            }

            String appointmentId = storage.generateAppointmentId(patientId);
            Appointment appointment = new Appointment(appointmentId, patient, assignedDoctor,
                    assignedDoctor.getSpecialty(), datetime, reason.trim(), byDoctor);
            storage.addAppointment(appointment);

            HashMap<String, Object> data = new HashMap<>();
            data.put("appointmentId", appointmentId);
            return new Response("Appointment requested successfully", Status.CREATED, data);

        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response acceptAppointment(String appointmentId) {
        try {
            Storage storage = Storage.getInstance();
            Appointment a = storage.getAppointmentById(appointmentId.trim());
            if (a == null)
                return new Response("Appointment not found", Status.NOT_FOUND);
            if (a.getStatus() != AppointmentStatus.REQUESTED)
                return new Response("Appointment must be in REQUESTED status", Status.BAD_REQUEST);
            a.setStatus(AppointmentStatus.PENDING);
            return new Response("Appointment accepted", Status.OK);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response completeAppointment(String appointmentId) {
        try {
            Storage storage = Storage.getInstance();
            Appointment a = storage.getAppointmentById(appointmentId.trim());
            if (a == null)
                return new Response("Appointment not found", Status.NOT_FOUND);
            if (a.getStatus() != AppointmentStatus.PENDING)
                return new Response("Appointment must be in PENDING status to complete", Status.BAD_REQUEST);
            a.setStatus(AppointmentStatus.COMPLETED);
            return new Response("Appointment completed", Status.OK);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response cancelAppointment(String appointmentId) {
        try {
            Storage storage = Storage.getInstance();
            Appointment a = storage.getAppointmentById(appointmentId.trim());
            if (a == null)
                return new Response("Appointment not found", Status.NOT_FOUND);
            if (a.getStatus() == AppointmentStatus.COMPLETED)
                return new Response("Cannot cancel a completed appointment", Status.BAD_REQUEST);
            a.setStatus(AppointmentStatus.CANCELED);
            return new Response("Appointment canceled", Status.OK);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response rescheduleAppointment(String appointmentId, String newTime, String rescheduleReason) {
        try {
            Response v;
            if ((v = validateTime(newTime)) != null) return v;

            Storage storage = Storage.getInstance();
            Appointment a = storage.getAppointmentById(appointmentId.trim());
            if (a == null)
                return new Response("Appointment not found", Status.NOT_FOUND);

            // Misma fecha, nueva hora
            LocalDateTime newDatetime = LocalDateTime.of(a.getDatetime().toLocalDate(),
                    LocalTime.parse(newTime.trim()));

            if (!storage.isDoctorAvailableExcluding(a.getDoctor(), newDatetime, appointmentId.trim()))
                return new Response("Doctor not available at new time", Status.BAD_REQUEST);

            // Appointment no expone setDatetime → deberás agregarlo al modelo
            a.setDatetime(newDatetime);
            a.setReason(a.getReason() + " | Rescheduled: " + rescheduleReason.trim());

            return new Response("Appointment rescheduled", Status.OK);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response prescribeMedication(String appointmentId, String medicationName,
            double dose, String administrationRoute, int treatmentDuration,
            String additionalInstructions, int frecuency) {
        try {
            Storage storage = Storage.getInstance();
            Appointment a = storage.getAppointmentById(appointmentId.trim());
            if (a == null)
                return new Response("Appointment not found", Status.NOT_FOUND);
            if (a.getStatus() != AppointmentStatus.PENDING)
                return new Response("Can only prescribe during a PENDING appointment", Status.BAD_REQUEST);

            // El constructor de Prescription ya llama appointment.addPrescription(this)
            new Prescription(a, medicationName, dose, administrationRoute,
                    treatmentDuration, additionalInstructions, frecuency);

            return new Response("Medication prescribed", Status.OK);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    // ── HOSPITALIZACIONES ─────────────────────────────────────────────────

    public static Response requestHospitalization(String patientIdStr, String doctorIdStr,
            String date, String roomType, String reason, String observations) {
        try {
            Response v;
            if ((v = validateDate(date)) != null) return v;

            Storage storage = Storage.getInstance();
            long patientId = Long.parseLong(patientIdStr.trim());
            Patient patient = storage.getPatientById(patientId);
            if (patient == null)
                return new Response("Patient not found", Status.NOT_FOUND);

            long doctorId = Long.parseLong(doctorIdStr.trim());
            Doctor doctor = storage.getDoctorById(doctorId);
            if (doctor == null)
                return new Response("Doctor not found", Status.NOT_FOUND);

            RoomType rt;
            try { rt = RoomType.valueOf(roomType.trim().toUpperCase()); }
            catch (IllegalArgumentException e) {
                return new Response("Invalid room type", Status.BAD_REQUEST);
            }

            String hospId = storage.generateHospitalizationId(patientId);
            // Constructor 1: empieza en REQUESTED automáticamente
            Hospitalization hosp = new Hospitalization(hospId, patient, doctor,
                    LocalDate.parse(date.trim()), reason.trim(), rt, observations.trim());
            storage.addHospitalization(hosp);

            HashMap<String, Object> data = new HashMap<>();
            data.put("hospitalizationId", hospId);
            return new Response("Hospitalization requested", Status.CREATED, data);

        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response acceptHospitalization(String hospitalizationId) {
        try {
            Storage storage = Storage.getInstance();
            Hospitalization h = storage.getHospitalizationById(hospitalizationId.trim());
            if (h == null)
                return new Response("Hospitalization not found", Status.NOT_FOUND);
            if (h.getStatus() != HospitalizationStatus.REQUESTED)
                return new Response("Hospitalization is not in REQUESTED status", Status.BAD_REQUEST);
            h.setStatus(HospitalizationStatus.ONGOING);
            return new Response("Hospitalization approved", Status.OK);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response cancelHospitalization(String hospitalizationId) {
        try {
            Storage storage = Storage.getInstance();
            Hospitalization h = storage.getHospitalizationById(hospitalizationId.trim());
            if (h == null)
                return new Response("Hospitalization not found", Status.NOT_FOUND);
            h.setStatus(HospitalizationStatus.CANCELED);
            return new Response("Hospitalization denied", Status.OK);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response hospitalizeFromAppointment(String appointmentId, String roomType,
            String reason, String observations) {
        try {
            Storage storage = Storage.getInstance();
            Appointment a = storage.getAppointmentById(appointmentId.trim());
            if (a == null)
                return new Response("Appointment not found", Status.NOT_FOUND);

            RoomType rt;
            try { rt = RoomType.valueOf(roomType.trim().toUpperCase()); }
            catch (IllegalArgumentException e) {
                return new Response("Invalid room type", Status.BAD_REQUEST);
            }

            a.setStatus(AppointmentStatus.COMPLETED);

            long patientId = a.getPatient().getId();
            String hospId = storage.generateHospitalizationId(patientId);
            // Constructor 2: status ONGOING explícito
            Hospitalization hosp = new Hospitalization(hospId, a.getPatient(), a.getDoctor(),
                    a.getDatetime().toLocalDate(), reason.trim(), rt, observations.trim(),
                    HospitalizationStatus.ONGOING);
            storage.addHospitalization(hosp);

            HashMap<String, Object> data = new HashMap<>();
            data.put("hospitalizationId", hospId);
            return new Response("Patient hospitalized and appointment completed", Status.CREATED, data);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    // ── CONSULTAS PARA TABLAS ─────────────────────────────────────────────

    public static Response getPatientAppointments(String patientIdStr) {
        try {
            long patientId = Long.parseLong(patientIdStr.trim());
            Storage storage = Storage.getInstance();
            if (storage.getPatientById(patientId) == null)
                return new Response("Patient not found", Status.NOT_FOUND);
            List<HashMap<String, Object>> list = storage.getAppointmentsByPatientSorted(patientId);
            HashMap<String, Object> data = new HashMap<>();
            data.put("appointments", list);
            return new Response("Appointments retrieved", Status.OK, data);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response getDoctorAppointments(String doctorIdStr, boolean pendingOnly) {
        try {
            long doctorId = Long.parseLong(doctorIdStr.trim());
            Storage storage = Storage.getInstance();
            if (storage.getDoctorById(doctorId) == null)
                return new Response("Doctor not found", Status.NOT_FOUND);
            List<HashMap<String, Object>> list = storage.getAppointmentsByDoctorSorted(doctorId, pendingOnly);
            HashMap<String, Object> data = new HashMap<>();
            data.put("appointments", list);
            return new Response("Appointments retrieved", Status.OK, data);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response getPatientHospitalizations(String patientIdStr) {
        try {
            long patientId = Long.parseLong(patientIdStr.trim());
            Storage storage = Storage.getInstance();
            List<HashMap<String, Object>> list = storage.getHospitalizationsByPatient(patientId);
            HashMap<String, Object> data = new HashMap<>();
            data.put("hospitalizations", list);
            return new Response("Hospitalizations retrieved", Status.OK, data);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}