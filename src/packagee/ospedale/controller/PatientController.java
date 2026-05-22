package packagee.ospedale.controller;

import packagee.ospedale.controller.utils.Response;
import packagee.ospedale.service.PatientService;

/**
 * Expone las operaciones de pacientes a la interfaz grafica.
 */
public class PatientController {

    public static Response registerPatient(
            String id,
            String username,
            String password,
            String confirmPassword,
            String firstname,
            String lastname,
            String email,
            String birthdate,
            String gender,
            String phone,
            String address
    ) {
        return PatientService.registerPatient(
                id,
                username,
                password,
                confirmPassword,
                firstname,
                lastname,
                email,
                birthdate,
                gender,
                phone,
                address
        );
    }

    public static Response updatePatient(
            String idStr,
            String username,
            String password,
            String confirmPassword,
            String firstname,
            String lastname,
            String email,
            String birthdate,
            String gender,
            String phone,
            String address
    ) {
        return PatientService.updatePatient(
                idStr,
                username,
                password,
                confirmPassword,
                firstname,
                lastname,
                email,
                birthdate,
                gender,
                phone,
                address
        );
    }

    public static Response getPatientInfo(String idStr) {
        return PatientService.getPatientInfo(idStr);
    }

    public static Response getAllPatients() {
        return PatientService.getAllPatients();
    }
}
