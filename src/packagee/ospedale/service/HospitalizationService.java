package packagee.ospedale.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import packagee.ospedale.controller.utils.Response;
import packagee.ospedale.controller.utils.Status;
import packagee.ospedale.model.Doctor;
import packagee.ospedale.model.Hospitalization;
import packagee.ospedale.model.HospitalizationStatus;
import packagee.ospedale.model.Patient;
import packagee.ospedale.model.RoomType;
import packagee.ospedale.model.storage.Storage;
import packagee.ospedale.observer.StorageEventType;
import packagee.ospedale.validator.UserValidator;

/**
 * Gestiona solicitudes y estados de hospitalizacion.
 */
public final class HospitalizationService {

    private HospitalizationService() {
    }

    public static Response requestHospitalization(String patientIdStr, String doctorIdStr,
            String date, String roomType, String reason, String observations) {
        try {
            Response validation = AppointmentService.validateDate(date);
            if (validation != null) {
                return validation;
            }

            if ((validation = UserValidator.validateUserId(patientIdStr)) != null) {
                return validation;
            }

            if ((validation = UserValidator.validateUserId(doctorIdStr)) != null) {
                return validation;
            }

            Storage storage = Storage.getInstance();
            Patient patient = storage.getPatientById(Long.parseLong(patientIdStr.trim()));
            if (patient == null) {
                return new Response("Patient not found", Status.NOT_FOUND);
            }

            Doctor doctor = storage.getDoctorById(Long.parseLong(doctorIdStr.trim()));
            if (doctor == null) {
                return new Response("Doctor not found", Status.NOT_FOUND);
            }

            RoomType parsedRoomType = parseRoomType(roomType);
            if (parsedRoomType == null) {
                return new Response("Invalid room type", Status.BAD_REQUEST);
            }

            String hospitalizationId = storage.generateHospitalizationId(patient.getId());
            Hospitalization hospitalization = new Hospitalization(hospitalizationId, patient, doctor,
                    LocalDate.parse(date.trim()), reason == null ? "" : reason.trim(),
                    parsedRoomType, observations == null ? "" : observations.trim());
            storage.addHospitalization(hospitalization);
            storage.publishEvent(StorageEventType.HOSPITALIZATIONS_CHANGED);

            HashMap<String, Object> data = new HashMap<>();
            data.put("hospitalizationId", hospitalizationId);
            return new Response("Hospitalization requested", Status.CREATED, data);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response acceptHospitalization(String hospitalizationId) {
        try {
            Storage storage = Storage.getInstance();
            Hospitalization hospitalization = storage.getHospitalizationById(hospitalizationId.trim());

            if (hospitalization == null) {
                return new Response("Hospitalization not found", Status.NOT_FOUND);
            }

            if (hospitalization.getStatus() != HospitalizationStatus.REQUESTED) {
                return new Response("Hospitalization is not in REQUESTED status", Status.BAD_REQUEST);
            }

            hospitalization.setStatus(HospitalizationStatus.ONGOING);
            storage.publishEvent(StorageEventType.HOSPITALIZATIONS_CHANGED);
            return new Response("Hospitalization approved", Status.OK);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response cancelHospitalization(String hospitalizationId) {
        try {
            Storage storage = Storage.getInstance();
            Hospitalization hospitalization = storage.getHospitalizationById(hospitalizationId.trim());

            if (hospitalization == null) {
                return new Response("Hospitalization not found", Status.NOT_FOUND);
            }

            if (hospitalization.getStatus() != HospitalizationStatus.REQUESTED) {
                return new Response("Only REQUESTED hospitalizations can be denied", Status.BAD_REQUEST);
            }

            hospitalization.setStatus(HospitalizationStatus.CANCELED);
            storage.publishEvent(StorageEventType.HOSPITALIZATIONS_CHANGED);
            return new Response("Hospitalization denied", Status.OK);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response getPatientHospitalizations(String patientIdStr) {
        try {
            Response validation = UserValidator.validateUserId(patientIdStr);
            if (validation != null) {
                return validation;
            }

            Storage storage = Storage.getInstance();
            long patientId = Long.parseLong(patientIdStr.trim());

            if (storage.getPatientById(patientId) == null) {
                return new Response("Patient not found", Status.NOT_FOUND);
            }

            List<HashMap<String, Object>> list = storage.getHospitalizationsByPatient(patientId);
            HashMap<String, Object> data = new HashMap<>();
            data.put("hospitalizations", list);
            return new Response("Hospitalizations retrieved", Status.OK, data);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response getRequestedHospitalizations() {
        try {
            Storage storage = Storage.getInstance();
            HashMap<String, Object> data = new HashMap<>();
            data.put("hospitalizations", storage.getRequestedHospitalizations());
            return new Response("Hospitalization requests retrieved", Status.OK, data);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response hospitalizeFromAppointment(String appointmentId, String date,
            String reason, String observations) {
        return AppointmentService.hospitalizeFromAppointment(
                appointmentId, date, reason, observations
        );
    }

    private static RoomType parseRoomType(String roomType) {
        if (roomType == null || roomType.trim().isEmpty() || "Select one".equalsIgnoreCase(roomType.trim())) {
            return null;
        }

        try {
            return RoomType.valueOf(roomType.trim().toUpperCase());
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }
}
