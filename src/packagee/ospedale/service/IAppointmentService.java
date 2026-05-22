package packagee.ospedale.service;

import packagee.ospedale.controller.utils.Response;

/**
 * Interface para el servicio de citas medicas.
 */
public interface IAppointmentService {

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

    Response hospitalizeFromAppointment(
            String appointmentId,
            String date,
            String reason,
            String observations
    );

    Response getAppointmentInfo(String id);

    Response getAllAppointments();

    Response getPatientAppointments(String patientIdStr);

    Response getDoctorAppointments(String doctorIdStr, boolean pendingOnly);
}
