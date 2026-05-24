package packagee.ospedale.factory;

import java.time.LocalDate;
import packagee.ospedale.model.Patient;

/**
 * Encapsula la construccion de objetos {@code Patient}.
 */
public class PatientFactory {

    public static Patient createPatient(
            long id,
            String username,
            String password,
            String firstname,
            String lastname,
            String email,
            String birthdate,
            String gender,
            String phone,
            String address
    ) {

        boolean genderBool =
                gender.equalsIgnoreCase("Male")
                || gender.equals("true");

        return new Patient(
                id,
                username.trim(),
                firstname.trim(),
                lastname.trim(),
                password,
                email.trim(),
                LocalDate.parse(birthdate.trim()),
                genderBool,
                Long.parseLong(phone.trim()),
                address.trim()
        );
    }
}
