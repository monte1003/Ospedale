package packagee.ospedale.controller;

import packagee.ospedale.controller.utils.Response;
import packagee.ospedale.service.DoctorService;

/**
 * Coordina las operaciones relacionadas con doctores.
 */
public class DoctorController {

    public static Response registerDoctor(String id, String username, String password,
            String confirmPassword, String firstname, String lastname,
            String licence, String office, String specialty) {
        return DoctorService.registerDoctor(id, username, password, confirmPassword,
                firstname, lastname, licence, office, specialty);
    }

    public static Response updateDoctor(String idStr, String username, String password,
            String confirmPassword, String firstname, String lastname,
            String licence, String office, String specialty) {
        return DoctorService.updateDoctor(idStr, username, password, confirmPassword,
                firstname, lastname, licence, office, specialty);
    }

    public static Response getDoctorInfo(String idStr) {
        return DoctorService.getDoctorInfo(idStr);
    }

    public static Response getAllDoctors() {
        return DoctorService.getAllDoctors();
    }
}
