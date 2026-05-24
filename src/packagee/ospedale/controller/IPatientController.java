package packagee.ospedale.controller;

import packagee.ospedale.controller.utils.Response;

/**
 * Interface para el controlador de pacientes.
 */
public interface IPatientController {

    Response registerPatient(
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
    );

    Response updatePatient(
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
    );

    Response getPatientInfo(String idStr);

    Response getAllPatients();
}
