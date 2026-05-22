package packagee.ospedale.controller;

import packagee.ospedale.controller.utils.Response;
import packagee.ospedale.service.IDoctorService;

/**
 * Coordina las operaciones relacionadas con doctores.
 */
public class DoctorController implements IDoctorController {

    private final IDoctorService doctorService;

    public DoctorController(IDoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @Override
    public Response registerDoctor(String id, String username, String password,
            String confirmPassword, String firstname, String lastname,
            String licence, String office, String specialty) {
        return doctorService.registerDoctor(id, username, password, confirmPassword,
                firstname, lastname, licence, office, specialty);
    }

    @Override
    public Response updateDoctor(String idStr, String username, String password,
            String confirmPassword, String firstname, String lastname,
            String licence, String office, String specialty) {
        return doctorService.updateDoctor(idStr, username, password, confirmPassword,
                firstname, lastname, licence, office, specialty);
    }

    @Override
    public Response getDoctorInfo(String idStr) {
        return doctorService.getDoctorInfo(idStr);
    }

    @Override
    public Response getAllDoctors() {
        return doctorService.getAllDoctors();
    }
}
