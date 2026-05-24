package packagee.ospedale.controller;

import packagee.ospedale.controller.utils.Response;

/**
 * Interface para el controlador de doctores.
 */
public interface IDoctorController {

    Response registerDoctor(
            String id,
            String username,
            String password,
            String confirmPassword,
            String firstname,
            String lastname,
            String licence,
            String office,
            String specialty
    );

    Response updateDoctor(
            String idStr,
            String username,
            String password,
            String confirmPassword,
            String firstname,
            String lastname,
            String licence,
            String office,
            String specialty
    );

    Response getDoctorInfo(String idStr);

    Response getAllDoctors();
}
