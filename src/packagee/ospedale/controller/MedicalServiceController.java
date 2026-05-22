package packagee.ospedale.controller;

import packagee.ospedale.controller.utils.Response;
import packagee.ospedale.service.AppointmentService;
import packagee.ospedale.service.HospitalizationService;

/**
 * Centraliza acciones medicas que afectan citas y hospitalizaciones.
 */
public class MedicalServiceController {

    public static Response requestAppointment(String patientIdStr, String doctorIdStr,
            String specialty, String date, String time, String reason) {
        return AppointmentService.requestAppointment(patientIdStr, doctorIdStr, specialty,
                date, time, reason, "In-person");
    }

    public static Response requestAppointment(String patientIdStr, String doctorIdStr,
            String specialty, String date, String time, String reason, String appointmentType) {
        return AppointmentService.requestAppointment(patientIdStr, doctorIdStr, specialty,
                date, time, reason, appointmentType);
    }

    public static Response acceptAppointment(String appointmentId) {
        return AppointmentService.acceptAppointment(appointmentId);
    }

    public static Response completeAppointment(String appointmentId) {
        return AppointmentService.completeAppointment(appointmentId, "", "", "", "");
    }

    public static Response completeAppointment(String appointmentId, String diagnosis,
            String observations, String recommendedTreatment, String followUp) {
        return AppointmentService.completeAppointment(appointmentId, diagnosis, observations,
                recommendedTreatment, followUp);
    }

    public static Response cancelAppointment(String appointmentId) {
        return AppointmentService.cancelAppointment(appointmentId);
    }

    public static Response rescheduleAppointment(String appointmentId, String newTime, String rescheduleReason) {
        return AppointmentService.rescheduleAppointment(appointmentId, newTime, rescheduleReason);
    }

    public static Response prescribeMedication(String appointmentId, String medicationName,
            String dose, String administrationRoute, String treatmentDuration,
            String additionalInstructions, String frecuency) {
        return AppointmentService.prescribeMedication(appointmentId, medicationName, dose,
                administrationRoute, treatmentDuration, additionalInstructions, frecuency);
    }

    public static Response requestHospitalization(String patientIdStr, String doctorIdStr,
            String date, String roomType, String reason, String observations) {
        return HospitalizationService.requestHospitalization(patientIdStr, doctorIdStr,
                date, roomType, reason, observations);
    }

    public static Response acceptHospitalization(String hospitalizationId) {
        return HospitalizationService.acceptHospitalization(hospitalizationId);
    }

    public static Response cancelHospitalization(String hospitalizationId) {
        return HospitalizationService.cancelHospitalization(hospitalizationId);
    }

    public static Response hospitalizeFromAppointment(String appointmentId, String date,
            String reason, String observations) {
        return HospitalizationService.hospitalizeFromAppointment(
                appointmentId,
                date,
                reason,
                observations
        );
    }

    public static Response getPatientAppointments(String patientIdStr) {
        return AppointmentService.getPatientAppointments(patientIdStr);
    }

    public static Response getDoctorAppointments(String doctorIdStr, boolean pendingOnly) {
        return AppointmentService.getDoctorAppointments(doctorIdStr, pendingOnly);
    }

    public static Response getPatientHospitalizations(String patientIdStr) {
        return HospitalizationService.getPatientHospitalizations(patientIdStr);
    }

    public static Response getRequestedHospitalizations() {
        return HospitalizationService.getRequestedHospitalizations();
    }
}
