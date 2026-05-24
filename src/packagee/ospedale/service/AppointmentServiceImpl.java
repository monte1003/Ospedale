/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.ospedale.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;

import packagee.ospedale.controller.utils.Response;
import packagee.ospedale.controller.utils.Status;
import packagee.ospedale.model.Appointment;
import packagee.ospedale.model.AppointmentStatus;
import packagee.ospedale.model.Doctor;
import packagee.ospedale.model.Hospitalization;
import packagee.ospedale.model.HospitalizationStatus;
import packagee.ospedale.model.Patient;
import packagee.ospedale.model.Prescription;
import packagee.ospedale.model.RoomType;
import packagee.ospedale.model.Specialty;
import packagee.ospedale.repository.AppointmentRepository;
import packagee.ospedale.repository.PatientRepository;
import packagee.ospedale.repository.DoctorRepository;
import packagee.ospedale.repository.HospitalizationRepository;
import packagee.ospedale.validator.AppointmentValidator;
import packagee.ospedale.validator.UserValidator;

/**
 * Implementacion concreta del servicio de citas medicas.
 */
public class AppointmentServiceImpl implements IAppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final HospitalizationRepository hospitalizationRepository;

    public AppointmentServiceImpl(
            AppointmentRepository appointmentRepository,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository,
            HospitalizationRepository hospitalizationRepository
    ) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.hospitalizationRepository = hospitalizationRepository;
    }

    private Response validateAppointmentIdInput(String appointmentId) {
        return AppointmentValidator.validateId(appointmentId);
    }

    private Response validateAppointmentParticipants(String patientIdStr, String doctorIdStr) {
        Response validation = UserValidator.validateUserId(patientIdStr);
        if (validation != null) {
            return validation;
        }

        return UserValidator.validateUserId(doctorIdStr);
    }

    private Response validatePrescriptionData(String medicationName, String dose,
            String administrationRoute, String treatmentDuration, String frequency) {
        if (medicationName == null || medicationName.trim().isEmpty()) {
            return new Response("Medication name must not be empty", Status.BAD_REQUEST);
        }

        if (administrationRoute == null || administrationRoute.trim().isEmpty()) {
            return new Response("Administration route must not be empty", Status.BAD_REQUEST);
        }

        try {
            if (Double.parseDouble(dose.trim()) <= 0) {
                return new Response("Dose must be greater than 0", Status.BAD_REQUEST);
            }
        } catch (Exception ex) {
            return new Response("Dose must be a valid number", Status.BAD_REQUEST);
        }

        try {
            if (Integer.parseInt(treatmentDuration.trim()) <= 0) {
                return new Response("Treatment duration must be greater than 0", Status.BAD_REQUEST);
            }
        } catch (Exception ex) {
            return new Response("Treatment duration must be a valid integer", Status.BAD_REQUEST);
        }

        try {
            if (Integer.parseInt(frequency.trim()) <= 0) {
                return new Response("Frequency must be greater than 0", Status.BAD_REQUEST);
            }
        } catch (Exception ex) {
            return new Response("Frequency must be a valid integer", Status.BAD_REQUEST);
        }

        return null;
    }

    @Override
    public Response requestAppointment(String patientIdStr, String doctorIdStr,
            String specialty, String date, String time, String reason, String appointmentType) {
        try {
            Response validation = validateDate(date);
            if (validation != null) {
                return validation;
            }

            validation = validateTime(time);
            if (validation != null) {
                return validation;
            }

            validation = AppointmentValidator.validateReason(reason);
            if (validation != null) {
                return validation;
            }

            if (doctorIdStr != null && !doctorIdStr.trim().isEmpty()
                    && (validation = validateAppointmentParticipants(patientIdStr, doctorIdStr)) != null) {
                return validation;
            }

            if ((doctorIdStr == null || doctorIdStr.trim().isEmpty())
                    && (validation = UserValidator.validateUserId(patientIdStr)) != null) {
                return validation;
            }

            long patientId = Long.parseLong(patientIdStr.trim());
            Patient patient = patientRepository.getPatientById(patientId);
            if (patient == null) {
                return new Response("Patient not found", Status.NOT_FOUND);
            }

            LocalDateTime appointmentDateTime = LocalDateTime.of(
                    LocalDate.parse(date.trim()),
                    LocalTime.parse(time.trim())
            );

            boolean byDoctor = doctorIdStr != null && !doctorIdStr.trim().isEmpty();
            boolean isRemote = "Remote".equalsIgnoreCase(appointmentType);
            Doctor assignedDoctor;

            if (byDoctor) {
                assignedDoctor = doctorRepository.getDoctorById(Long.parseLong(doctorIdStr.trim()));
                if (assignedDoctor == null) {
                    return new Response("Doctor not found", Status.NOT_FOUND);
                }

                if (!appointmentRepository.isDoctorAvailable(assignedDoctor, appointmentDateTime)) {
                    return new Response("Doctor not available at that time", Status.BAD_REQUEST);
                }

                if (specialty != null && !specialty.trim().isEmpty()
                        && doctorRepository.getSpecialtyByName(specialty.trim()) != assignedDoctor.getSpecialty()) {
                    return new Response("Appointment specialty must match the doctor's specialty", Status.BAD_REQUEST);
                }
            } else {
                Specialty parsedSpecialty = doctorRepository.getSpecialtyByName(specialty == null ? "" : specialty.trim());
                if (parsedSpecialty == null) {
                    return new Response("Specialty not found", Status.NOT_FOUND);
                }

                assignedDoctor = appointmentRepository.findAvailableDoctor(parsedSpecialty, appointmentDateTime);
                if (assignedDoctor == null) {
                    return new Response("No doctor available for that specialty and time", Status.BAD_REQUEST);
                }
            }

            String appointmentId = appointmentRepository.generateAppointmentId(patientId);
            Appointment appointment = new Appointment(appointmentId, patient, assignedDoctor,
                    assignedDoctor.getSpecialty(), appointmentDateTime, reason.trim(), isRemote);
            appointmentRepository.addAppointment(appointment);

            HashMap<String, Object> data = new HashMap<>();
            data.put("appointmentId", appointmentId);
            return new Response("Appointment requested successfully", Status.CREATED, data);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response acceptAppointment(String appointmentId) {
        try {
            Response validation = validateAppointmentIdInput(appointmentId);
            if (validation != null) {
                return validation;
            }

            Appointment appointment = appointmentRepository.getAppointmentById(appointmentId.trim());

            if (appointment == null) {
                return new Response("Appointment not found", Status.NOT_FOUND);
            }

            if (appointment.getStatus() != AppointmentStatus.REQUESTED) {
                return new Response("Appointment must be in REQUESTED status", Status.BAD_REQUEST);
            }

            appointment.setStatus(AppointmentStatus.PENDING);
            appointmentRepository.updateAppointment(appointment);
            return new Response("Appointment accepted", Status.OK);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response completeAppointment(String appointmentId, String diagnosis,
            String observations, String recommendedTreatment, String followUp) {
        try {
            Response validation = validateAppointmentIdInput(appointmentId);
            if (validation != null) {
                return validation;
            }

            Appointment appointment = appointmentRepository.getAppointmentById(appointmentId.trim());

            if (appointment == null) {
                return new Response("Appointment not found", Status.NOT_FOUND);
            }

            if (appointment.getStatus() != AppointmentStatus.PENDING) {
                return new Response("Appointment must be in PENDING status to complete", Status.BAD_REQUEST);
            }

            appointment.setDiagnosis(diagnosis == null ? "" : diagnosis.trim());
            appointment.setObservations(observations == null ? "" : observations.trim());
            appointment.setRecommendedTreatment(recommendedTreatment == null ? "" : recommendedTreatment.trim());
            appointment.setFollowUp(followUp == null ? "" : followUp.trim());
            appointment.setStatus(AppointmentStatus.COMPLETED);
            appointmentRepository.updateAppointment(appointment);
            return new Response("Appointment completed", Status.OK);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response cancelAppointment(String appointmentId) {
        try {
            Response validation = validateAppointmentIdInput(appointmentId);
            if (validation != null) {
                return validation;
            }

            Appointment appointment = appointmentRepository.getAppointmentById(appointmentId.trim());

            if (appointment == null) {
                return new Response("Appointment not found", Status.NOT_FOUND);
            }

            if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
                return new Response("Cannot cancel a completed appointment", Status.BAD_REQUEST);
            }

            appointment.setStatus(AppointmentStatus.CANCELED);
            appointmentRepository.updateAppointment(appointment);
            return new Response("Appointment canceled", Status.OK);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response rescheduleAppointment(String appointmentId, String newTime, String rescheduleReason) {
        try {
            Response validation = validateAppointmentIdInput(appointmentId);
            if (validation != null) {
                return validation;
            }

            validation = validateTime(newTime);
            if (validation != null) {
                return validation;
            }

            Appointment appointment = appointmentRepository.getAppointmentById(appointmentId.trim());

            if (appointment == null) {
                return new Response("Appointment not found", Status.NOT_FOUND);
            }

            if (appointment.getStatus() == AppointmentStatus.COMPLETED
                    || appointment.getStatus() == AppointmentStatus.CANCELED) {
                return new Response("Only active appointments can be rescheduled", Status.BAD_REQUEST);
            }

            LocalDateTime newDateTime = LocalDateTime.of(
                    appointment.getDatetime().toLocalDate(),
                    LocalTime.parse(newTime.trim())
            );

            if (!appointmentRepository.isDoctorAvailableExcluding(appointment.getDoctor(), newDateTime, appointmentId.trim())) {
                return new Response("Doctor not available at new time", Status.BAD_REQUEST);
            }

            appointment.setDatetime(newDateTime);
            String previousReason = appointment.getReason() == null ? "" : appointment.getReason();
            String extraReason = rescheduleReason == null ? "" : rescheduleReason.trim();
            appointment.setReason(previousReason + (extraReason.isEmpty() ? "" : " | Rescheduled: " + extraReason));
            appointmentRepository.updateAppointment(appointment);
            return new Response("Appointment rescheduled", Status.OK);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response prescribeMedication(String appointmentId, String medicationName,
            String dose, String administrationRoute, String treatmentDuration,
            String additionalInstructions, String frequency) {
        try {
            Response validation = validateAppointmentIdInput(appointmentId);
            if (validation != null) {
                return validation;
            }

            validation = validatePrescriptionData(
                    medicationName,
                    dose,
                    administrationRoute,
                    treatmentDuration,
                    frequency
            );
            if (validation != null) {
                return validation;
            }

            Appointment appointment = appointmentRepository.getAppointmentById(appointmentId.trim());

            if (appointment == null) {
                return new Response("Appointment not found", Status.NOT_FOUND);
            }

            if (appointment.getStatus() != AppointmentStatus.PENDING) {
                return new Response("Can only prescribe during a PENDING appointment", Status.BAD_REQUEST);
            }

            new Prescription(
                    appointment,
                    medicationName.trim(),
                    Double.parseDouble(dose.trim()),
                    administrationRoute.trim(),
                    Integer.parseInt(treatmentDuration.trim()),
                    additionalInstructions == null ? "" : additionalInstructions.trim(),
                    Integer.parseInt(frequency.trim())
            );
            appointmentRepository.updateAppointment(appointment);
            return new Response("Medication prescribed", Status.OK);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response hospitalizeFromAppointment(String appointmentId, String date,
            String reason, String observations) {
        try {
            Response validation = validateAppointmentIdInput(appointmentId);
            if (validation != null) {
                return validation;
            }

            validation = validateDate(date);
            if (validation != null) {
                return validation;
            }

            Appointment appointment = appointmentRepository.getAppointmentById(appointmentId.trim());

            if (appointment == null) {
                return new Response("Appointment not found", Status.NOT_FOUND);
            }

            if (appointment.getStatus() != AppointmentStatus.PENDING) {
                return new Response("Appointment must be in PENDING status", Status.BAD_REQUEST);
            }

            appointment.setStatus(AppointmentStatus.COMPLETED);
            appointmentRepository.updateAppointment(appointment);

            String hospitalizationId = hospitalizationRepository.generateHospitalizationId(appointment.getPatient().getId());
            Hospitalization hospitalization = new Hospitalization(
                    hospitalizationId,
                    appointment.getPatient(),
                    appointment.getDoctor(),
                    LocalDate.parse(date.trim()),
                    reason == null ? "" : reason.trim(),
                    RoomType.STANDARD,
                    observations == null ? "" : observations.trim(),
                    HospitalizationStatus.ONGOING
            );

            hospitalizationRepository.addHospitalization(hospitalization);

            HashMap<String, Object> data = new HashMap<>();
            data.put("hospitalizationId", hospitalizationId);
            return new Response("Patient hospitalized and appointment completed", Status.CREATED, data);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response getAppointmentInfo(String id) {
        try {
            Response validation = validateAppointmentIdInput(id);
            if (validation != null) {
                return validation;
            }

            Appointment appointment = appointmentRepository.getAppointmentById(id);
            if (appointment == null) {
                return new Response("Appointment not found", Status.NOT_FOUND);
            }

            return new Response("Appointment found", Status.OK,
                    appointmentRepository.serializeAppointment(appointment));
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response getAllAppointments() {
        try {
            HashMap<String, Object> data = new HashMap<>();
            List<HashMap<String, Object>> appointmentsList = appointmentRepository.getAllAppointments();
            data.put("appointments", appointmentsList);
            return new Response("Appointments retrieved", Status.OK, data);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response getPatientAppointments(String patientIdStr) {
        try {
            Response validation = UserValidator.validateUserId(patientIdStr);
            if (validation != null) {
                return validation;
            }

            long patientId = Long.parseLong(patientIdStr.trim());

            if (patientRepository.getPatientById(patientId) == null) {
                return new Response("Patient not found", Status.NOT_FOUND);
            }

            HashMap<String, Object> data = new HashMap<>();
            data.put("appointments", appointmentRepository.getAppointmentsByPatientSorted(patientId));
            return new Response("Appointments retrieved", Status.OK, data);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response getDoctorAppointments(String doctorIdStr, boolean pendingOnly) {
        try {
            Response validation = UserValidator.validateUserId(doctorIdStr);
            if (validation != null) {
                return validation;
            }

            long doctorId = Long.parseLong(doctorIdStr.trim());

            if (doctorRepository.getDoctorById(doctorId) == null) {
                return new Response("Doctor not found", Status.NOT_FOUND);
            }

            HashMap<String, Object> data = new HashMap<>();
            data.put("appointments", appointmentRepository.getAppointmentsByDoctorSorted(doctorId, pendingOnly));
            return new Response("Appointments retrieved", Status.OK, data);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public Response validateDate(String date) {
        try {
            LocalDate.parse(date.trim());
            return null;
        } catch (DateTimeParseException ex) {
            return new Response("Date must follow format YYYY-MM-DD", Status.BAD_REQUEST);
        }
    }

    public Response validateTime(String time) {
        if (time == null || !time.trim().matches("([01]\\d|2[0-3]):[0-5]\\d")) {
            return new Response("Time must follow 24h format hh:mm", Status.BAD_REQUEST);
        }

        int minutes = Integer.parseInt(time.trim().split(":")[1]);
        if (minutes != 0 && minutes != 15 && minutes != 30 && minutes != 45) {
            return new Response("Minutes must be 00, 15, 30 or 45", Status.BAD_REQUEST);
        }

        return null;
    }
}
