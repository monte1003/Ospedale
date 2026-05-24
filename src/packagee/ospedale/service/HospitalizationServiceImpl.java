package packagee.ospedale.service;

import java.time.LocalDate;
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
import packagee.ospedale.model.RoomType;
import packagee.ospedale.repository.HospitalizationRepository;
import packagee.ospedale.repository.PatientRepository;
import packagee.ospedale.repository.DoctorRepository;
import packagee.ospedale.repository.AppointmentRepository;
import packagee.ospedale.validator.UserValidator;

/**
 * Implementacion del servicio de hospitalizaciones.
 */
public class HospitalizationServiceImpl implements IHospitalizationService {

    private final HospitalizationRepository repository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;

    public HospitalizationServiceImpl(
            HospitalizationRepository repository,
            PatientRepository patientRepository,
            DoctorRepository doctorRepository,
            AppointmentRepository appointmentRepository
    ) {
        this.repository = repository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Response requestHospitalization(String patientIdStr, String doctorIdStr,
            String date, String roomType, String reason, String observations) {
        try {
            Response validation = validateDate(date);
            if (validation != null) {
                return validation;
            }

            if ((validation = UserValidator.validateUserId(patientIdStr)) != null) {
                return validation;
            }

            if ((validation = UserValidator.validateUserId(doctorIdStr)) != null) {
                return validation;
            }

            Patient patient = patientRepository.getPatientById(Long.parseLong(patientIdStr.trim()));
            if (patient == null) {
                return new Response("Patient not found", Status.NOT_FOUND);
            }

            Doctor doctor = doctorRepository.getDoctorById(Long.parseLong(doctorIdStr.trim()));
            if (doctor == null) {
                return new Response("Doctor not found", Status.NOT_FOUND);
            }

            RoomType parsedRoomType = parseRoomType(roomType);
            if (parsedRoomType == null) {
                return new Response("Invalid room type", Status.BAD_REQUEST);
            }

            String hospitalizationId = repository.generateHospitalizationId(patient.getId());
            Hospitalization hospitalization = new Hospitalization(hospitalizationId, patient, doctor,
                    LocalDate.parse(date.trim()), reason == null ? "" : reason.trim(),
                    parsedRoomType, observations == null ? "" : observations.trim());
            repository.addHospitalization(hospitalization);

            HashMap<String, Object> data = new HashMap<>();
            data.put("hospitalizationId", hospitalizationId);
            return new Response("Hospitalization requested", Status.CREATED, data);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response acceptHospitalization(String hospitalizationId) {
        try {
            Hospitalization hospitalization = repository.getHospitalizationById(hospitalizationId.trim());

            if (hospitalization == null) {
                return new Response("Hospitalization not found", Status.NOT_FOUND);
            }

            if (hospitalization.getStatus() != HospitalizationStatus.REQUESTED) {
                return new Response("Hospitalization is not in REQUESTED status", Status.BAD_REQUEST);
            }

            hospitalization.setStatus(HospitalizationStatus.ONGOING);
            repository.updateHospitalization(hospitalization);
            return new Response("Hospitalization approved", Status.OK);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response cancelHospitalization(String hospitalizationId) {
        try {
            Hospitalization hospitalization = repository.getHospitalizationById(hospitalizationId.trim());

            if (hospitalization == null) {
                return new Response("Hospitalization not found", Status.NOT_FOUND);
            }

            if (hospitalization.getStatus() != HospitalizationStatus.REQUESTED) {
                return new Response("Only REQUESTED hospitalizations can be denied", Status.BAD_REQUEST);
            }

            hospitalization.setStatus(HospitalizationStatus.CANCELED);
            repository.updateHospitalization(hospitalization);
            return new Response("Hospitalization denied", Status.OK);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response getPatientHospitalizations(String patientIdStr) {
        try {
            Response validation = UserValidator.validateUserId(patientIdStr);
            if (validation != null) {
                return validation;
            }

            long patientId = Long.parseLong(patientIdStr.trim());

            if (patientRepository.getPatientById(patientId) == null) {
                return new Response("Patient not found", Status.NOT_FOUND);
            }

            List<HashMap<String, Object>> list = repository.getHospitalizationsByPatient(patientId);
            HashMap<String, Object> data = new HashMap<>();
            data.put("hospitalizations", list);
            return new Response("Hospitalizations retrieved", Status.OK, data);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response getRequestedHospitalizations() {
        try {
            HashMap<String, Object> data = new HashMap<>();
            data.put("hospitalizations", repository.getRequestedHospitalizations());
            return new Response("Hospitalization requests retrieved", Status.OK, data);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response hospitalizeFromAppointment(String appointmentId, String date,
            String reason, String observations) {
        try {
            Appointment appointment = appointmentRepository.getAppointmentById(appointmentId.trim());

            if (appointment == null) {
                return new Response("Appointment not found", Status.NOT_FOUND);
            }

            if (appointment.getStatus() != AppointmentStatus.PENDING) {
                return new Response("Appointment must be in PENDING status", Status.BAD_REQUEST);
            }

            appointment.setStatus(AppointmentStatus.COMPLETED);
            appointmentRepository.updateAppointment(appointment);

            String hospitalizationId = repository.generateHospitalizationId(appointment.getPatient().getId());
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

            repository.addHospitalization(hospitalization);

            HashMap<String, Object> data = new HashMap<>();
            data.put("hospitalizationId", hospitalizationId);
            return new Response("Patient hospitalized and appointment completed", Status.CREATED, data);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    private RoomType parseRoomType(String roomType) {
        if (roomType == null || roomType.trim().isEmpty() || "Select one".equalsIgnoreCase(roomType.trim())) {
            return null;
        }

        try {
            return RoomType.valueOf(roomType.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    private Response validateDate(String date) {
        try {
            LocalDate.parse(date.trim());
            return null;
        } catch (Exception ex) {
            return new Response("Date must follow format YYYY-MM-DD", Status.BAD_REQUEST);
        }
    }
}
