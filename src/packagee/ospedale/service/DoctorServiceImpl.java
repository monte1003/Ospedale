package packagee.ospedale.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import packagee.ospedale.controller.utils.Response;
import packagee.ospedale.controller.utils.Status;
import packagee.ospedale.model.Doctor;
import packagee.ospedale.model.Specialty;
import packagee.ospedale.model.User;
import packagee.ospedale.repository.DoctorRepository;
import packagee.ospedale.validator.DoctorValidator;
import packagee.ospedale.validator.UserValidator;

/**
 * Implementacion del servicio de doctores.
 */
public class DoctorServiceImpl implements IDoctorService {

    private final DoctorRepository repository;

    public DoctorServiceImpl(DoctorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Response registerDoctor(String id, String username, String password,
            String confirmPassword, String firstname, String lastname,
            String licence, String office, String specialty) {
        try {
            Response validation = validateDoctorData(id, username, password, confirmPassword,
                    firstname, lastname, licence, office, specialty, true);
            if (validation != null) {
                return validation;
            }

            long doctorId = Long.parseLong(id.trim());

            if (repository.getDoctorById(doctorId) != null) {
                return new Response("A doctor with that ID already exists", Status.BAD_REQUEST);
            }

            if (repository.getUserByUsername(username.trim()) != null) {
                return new Response("Username already taken", Status.BAD_REQUEST);
            }

            Specialty parsedSpecialty = repository.getSpecialtyByName(specialty.trim());
            Doctor doctor = new Doctor(doctorId, username.trim(), firstname.trim(), lastname.trim(),
                    password, parsedSpecialty, licence.trim(), office.trim());

            if (!repository.addDoctor(doctor)) {
                return new Response("A user with that ID already exists", Status.BAD_REQUEST);
            }

            return new Response("Doctor registered successfully", Status.CREATED);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response updateDoctor(String idStr, String username, String password,
            String confirmPassword, String firstname, String lastname,
            String licence, String office, String specialty) {
        try {
            Response idValidation = UserValidator.validateUserId(idStr);
            if (idValidation != null) {
                return idValidation;
            }

            long doctorId = Long.parseLong(idStr.trim());
            Doctor doctor = repository.getDoctorById(doctorId);

            if (doctor == null) {
                return new Response("Doctor not found", Status.NOT_FOUND);
            }

            Response validation = validateDoctorData(idStr, username, password, confirmPassword,
                    firstname, lastname, licence, office, specialty, false);
            if (validation != null) {
                return validation;
            }

            User existing = repository.getUserByUsername(username.trim());
            if (existing != null && existing.getId() != doctorId) {
                return new Response("Username already taken", Status.BAD_REQUEST);
            }

            Specialty parsedSpecialty = repository.getSpecialtyByName(specialty.trim());
            doctor.setUsername(username.trim());
            doctor.setPassword(password);
            doctor.setFirstname(firstname.trim());
            doctor.setLastname(lastname.trim());
            doctor.setLicenceNumber(licence.trim());
            doctor.setAssignedOffice(office.trim());
            doctor.setSpecialty(parsedSpecialty);
            
            repository.updateDoctor(doctor);

            return new Response("Doctor updated successfully", Status.OK);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response getDoctorInfo(String idStr) {
        try {
            Response idValidation = UserValidator.validateUserId(idStr);
            if (idValidation != null) {
                return idValidation;
            }

            Doctor doctor = repository.getDoctorById(Long.parseLong(idStr.trim()));

            if (doctor == null) {
                return new Response("Doctor not found", Status.NOT_FOUND);
            }

            return new Response("Doctor found", Status.OK, repository.serializeDoctor(doctor));
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Response getAllDoctors() {
        try {
            List<HashMap<String, Object>> doctorsList = new ArrayList<>();

            for (Doctor doctor : repository.getAllDoctors()) {
                doctorsList.add(repository.serializeDoctor(doctor));
            }

            HashMap<String, Object> data = new HashMap<>();
            data.put("doctors", doctorsList);
            return new Response("Doctors retrieved", Status.OK, data);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    private Response validateDoctorData(String id, String username, String password,
            String confirmPassword, String firstname, String lastname,
            String licence, String office, String specialty, boolean validateId) {
        Response validation;

        if (validateId && (validation = UserValidator.validateUserId(id)) != null) {
            return validation;
        }

        if ((validation = UserValidator.validateUsername(username)) != null) {
            return validation;
        }

        if ((validation = UserValidator.validatePassword(password, confirmPassword)) != null) {
            return validation;
        }

        if ((validation = UserValidator.validateName("Firstname", firstname)) != null) {
            return validation;
        }

        if ((validation = UserValidator.validateName("Lastname", lastname)) != null) {
            return validation;
        }

        if ((validation = DoctorValidator.validateLicence(licence)) != null) {
            return validation;
        }

        if ((validation = DoctorValidator.validateOffice(office)) != null) {
            return validation;
        }

        if (specialty == null || specialty.trim().isEmpty() || "Select one".equalsIgnoreCase(specialty.trim())) {
            return new Response("Specialty must be selected", Status.BAD_REQUEST);
        }

        if (repository.getSpecialtyByName(specialty.trim()) == null) {
            return new Response("Specialty not found", Status.NOT_FOUND);
        }

        return null;
    }
}
