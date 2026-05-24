package packagee.ospedale.controller;

import packagee.ospedale.controller.utils.Response;
import packagee.ospedale.service.IAppointmentService;
import packagee.ospedale.service.IHospitalizationService;

/**
 * Centraliza acciones medicas que afectan citas y hospitalizaciones.
 */
public class MedicalServiceController implements IMedicalServiceController {

    private final IAppointmentService appointmentService;
    private final IHospitalizationService hospitalizationService;

    public MedicalServiceController(IAppointmentService appointmentService, IHospitalizationService hospitalizationService) {
        this.appointmentService = appointmentService;
        this.hospitalizationService = hospitalizationService;
    }

    @Override
    public Response requestAppointment(String patientIdStr, String doctorIdStr,
            String specialty, String date, String time, String reason) {
        return appointmentService.requestAppointment(patientIdStr, doctorIdStr, specialty,
                date, time, reason, "In-person");
    }

    @Override
    public Response requestAppointment(String patientIdStr, String doctorIdStr,
            String specialty, String date, String time, String reason, String appointmentType) {
        return appointmentService.requestAppointment(patientIdStr, doctorIdStr, specialty,
                date, time, reason, appointmentType);
    }

    @Override
    public Response acceptAppointment(String appointmentId) {
        return appointmentService.acceptAppointment(appointmentId);
    }

    @Override
    public Response completeAppointment(String appointmentId) {
        return appointmentService.completeAppointment(appointmentId, "", "", "", "");
    }

    @Override
    public Response completeAppointment(String appointmentId, String diagnosis,
            String observations, String recommendedTreatment, String followUp) {
        return appointmentService.completeAppointment(appointmentId, diagnosis, observations,
                recommendedTreatment, followUp);
    }

    @Override
    public Response cancelAppointment(String appointmentId) {
        return appointmentService.cancelAppointment(appointmentId);
    }

    @Override
    public Response rescheduleAppointment(String appointmentId, String newTime, String rescheduleReason) {
        return appointmentService.rescheduleAppointment(appointmentId, newTime, rescheduleReason);
    }

    @Override
    public Response prescribeMedication(String appointmentId, String medicationName,
            String dose, String administrationRoute, String treatmentDuration,
            String additionalInstructions, String frequency) {
        return appointmentService.prescribeMedication(appointmentId, medicationName, dose,
                administrationRoute, treatmentDuration, additionalInstructions, frequency);
    }

    @Override
    public Response requestHospitalization(String patientIdStr, String doctorIdStr,
            String date, String roomType, String reason, String observations) {
        return hospitalizationService.requestHospitalization(patientIdStr, doctorIdStr,
                date, roomType, reason, observations);
    }

    @Override
    public Response acceptHospitalization(String hospitalizationId) {
        return hospitalizationService.acceptHospitalization(hospitalizationId);
    }

    @Override
    public Response cancelHospitalization(String hospitalizationId) {
        return hospitalizationService.cancelHospitalization(hospitalizationId);
    }

    @Override
    public Response hospitalizeFromAppointment(String appointmentId, String date,
            String reason, String observations) {
        return hospitalizationService.hospitalizeFromAppointment(
                appointmentId,
                date,
                reason,
                observations
        );
    }

    @Override
    public Response getPatientAppointments(String patientIdStr) {
        return appointmentService.getPatientAppointments(patientIdStr);
    }

    @Override
    public Response getDoctorAppointments(String doctorIdStr, boolean pendingOnly) {
        return appointmentService.getDoctorAppointments(doctorIdStr, pendingOnly);
    }

    @Override
    public Response getPatientHospitalizations(String patientIdStr) {
        return hospitalizationService.getPatientHospitalizations(patientIdStr);
    }

    @Override
    public Response getRequestedHospitalizations() {
        return hospitalizationService.getRequestedHospitalizations();
    }
}
