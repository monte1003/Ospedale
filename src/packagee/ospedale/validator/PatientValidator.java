package packagee.ospedale.validator;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import packagee.ospedale.controller.utils.Response;
import packagee.ospedale.controller.utils.Status;

/**
 * Agrupa las reglas de validacion propias de pacientes.
 */
public final class PatientValidator {

    private PatientValidator() {
    }

    public static Response validatePhone(String phone) {
        if (!phone.trim().matches("\\d{10}")) {
            return new Response("Phone must have exactly 10 digits", Status.BAD_REQUEST);
        }

        return null;
    }

    public static Response validateEmail(String email) {
        if (!email.trim().matches("[^@]+@[^@]+\\.com")) {
            return new Response("Email must follow format XXXXX@XXXXX.com", Status.BAD_REQUEST);
        }

        return null;
    }

    public static Response validateBirthdate(String birthdate) {
        try {
            LocalDate.parse(birthdate.trim());
            return null;
        } catch (DateTimeParseException e) {
            return new Response("Birthdate must be valid and follow format YYYY-MM-DD", Status.BAD_REQUEST);
        }
    }
}
