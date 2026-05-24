package packagee.ospedale.controller;

import packagee.ospedale.controller.utils.Response;

/**
 * Interface para el controlador de servicios medicos.
 */
public interface IMedicalServiceController {

    Response requestAppointment(
            String patientIdStr,
            String doctorIdStr,
            String specialty,
            String date,
            String time,
            String reason
    );

    Response requestAppointment(
            String patientIdStr,
            String doctorIdStr,
            String specialty,
            String date,
            String time,
            String reason,
            String appointmentType
    );

    Response acceptAppointment(String appointmentId);

    Response completeAppointment(String appointmentId);

    Response completeAppointment(
            String appointmentId,
            String diagnosis,
            String observations,
            String recommendedTreatment,
            String followUp
    );

    Response cancelAppointment(String appointmentId);

    Response rescheduleAppointment(
            String appointmentId,
            String newTime,
            String rescheduleReason
    );

    Response prescribeMedication(
            String appointmentId,
            String medicationName,
            String dose,
            String administrationRoute,
            String treatmentDuration,
            String additionalInstructions,
            String frequency
    );

    Response requestHospitalization(
            String patientIdStr,
            String doctorIdStr,
            String date,
            String roomType,
            String reason,
            String observations
    );

    Response acceptHospitalization(String hospitalizationId);

    Response cancelHospitalization(String hospitalizationId);

    Response hospitalizeFromAppointment(
            String appointmentId,
            String date,
            String reason,
            String observations
    );

    Response getPatientAppointments(String patientIdStr);

    Response getDoctorAppointments(String doctorIdStr, boolean pendingOnly);

    Response getPatientHospitalizations(String patientIdStr);

    Response getRequestedHospitalizations();
}
