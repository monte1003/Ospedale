package packagee.ospedale.controller;

import packagee.ospedale.controller.utils.Response;
import packagee.ospedale.service.IPatientService;

/**
 * Expone las operaciones de pacientes a la interfaz grafica.
 */
public class PatientController implements IPatientController {

    private final IPatientService patientService;

    public PatientController(IPatientService patientService) {
        this.patientService = patientService;
    }

    @Override
    public Response registerPatient(
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
        return patientService.registerPatient(
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

    @Override
    public Response updatePatient(
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
        return patientService.updatePatient(
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

    @Override
    public Response getPatientInfo(String idStr) {
        return patientService.getPatientInfo(idStr);
    }

    @Override
    public Response getAllPatients() {
        return patientService.getAllPatients();
    }
}
