package packagee.ospedale.validator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import packagee.ospedale.controller.utils.Response;
import packagee.ospedale.controller.utils.Status;

/**
 * Agrupa las validaciones utilizadas durante el ciclo de vida de una cita.
 */
public final class AppointmentValidator {

    private AppointmentValidator() {
    }

    public static Response validateId(String id) {
        if (id == null || !id.trim().matches("A-\\d{12}-\\d{4}")) {
            return new Response(
                    "Appointment ID must follow format A-XXXXXXXXXXXX-NNNN",
                    Status.BAD_REQUEST
            );
        }

        return null;
    }

    public static Response validateDate(String date) {
        try {
            LocalDate appointmentDate = LocalDate.parse(date);

            if (appointmentDate.isBefore(LocalDate.now())) {
                return new Response(
                        "Appointment date cannot be in the past",
                        Status.BAD_REQUEST
                );
            }

            return null;
        } catch (DateTimeParseException ex) {
            return new Response(
                    "Date must follow format YYYY-MM-DD",
                    Status.BAD_REQUEST
            );
        }
    }

    public static Response validateReason(String reason) {
        if (reason.trim().isEmpty()) {
            return new Response(
                    "Reason must not be empty",
                    Status.BAD_REQUEST
            );
        }

        return null;
    }
}
