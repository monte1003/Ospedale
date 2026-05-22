package packagee.ospedale.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import packagee.ospedale.controller.utils.Response;
import packagee.ospedale.controller.utils.Status;
import packagee.ospedale.model.Doctor;
import packagee.ospedale.model.Specialty;
import packagee.ospedale.model.User;
import packagee.ospedale.model.storage.Storage;
import packagee.ospedale.observer.StorageEventType;
import packagee.ospedale.validator.DoctorValidator;
import packagee.ospedale.validator.UserValidator;

/**
 * Gestiona el registro, consulta y actualizacion de doctores.
 */
public final class DoctorService {

    private DoctorService() {
    }

    public static Response registerDoctor(String id, String username, String password,
            String confirmPassword, String firstname, String lastname,
            String licence, String office, String specialty) {
        try {
            Response validation = validateDoctorData(id, username, password, confirmPassword,
                    firstname, lastname, licence, office, specialty, true);
            if (validation != null) {
                return validation;
            }

            Storage storage = Storage.getInstance();
            long doctorId = Long.parseLong(id.trim());

            if (storage.getDoctorById(doctorId) != null) {
                return new Response("A doctor with that ID already exists", Status.BAD_REQUEST);
            }

            if (storage.getUserByUsername(username.trim()) != null) {
                return new Response("Username already taken", Status.BAD_REQUEST);
            }

            Specialty parsedSpecialty = storage.getSpecialtyByName(specialty.trim());
            Doctor doctor = new Doctor(doctorId, username.trim(), firstname.trim(), lastname.trim(),
                    password, parsedSpecialty, licence.trim(), office.trim());

            if (!storage.addUser(doctor)) {
                return new Response("A user with that ID already exists", Status.BAD_REQUEST);
            }

            storage.publishEvent(StorageEventType.USERS_CHANGED);

            return new Response("Doctor registered successfully", Status.CREATED);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response updateDoctor(String idStr, String username, String password,
            String confirmPassword, String firstname, String lastname,
            String licence, String office, String specialty) {
        try {
            Response idValidation = UserValidator.validateUserId(idStr);
            if (idValidation != null) {
                return idValidation;
            }

            Storage storage = Storage.getInstance();
            long doctorId = Long.parseLong(idStr.trim());
            Doctor doctor = storage.getDoctorById(doctorId);

            if (doctor == null) {
                return new Response("Doctor not found", Status.NOT_FOUND);
            }

            Response validation = validateDoctorData(idStr, username, password, confirmPassword,
                    firstname, lastname, licence, office, specialty, false);
            if (validation != null) {
                return validation;
            }

            User existing = storage.getUserByUsername(username.trim());
            if (existing != null && existing.getId() != doctorId) {
                return new Response("Username already taken", Status.BAD_REQUEST);
            }

            Specialty parsedSpecialty = storage.getSpecialtyByName(specialty.trim());
            doctor.setUsername(username.trim());
            doctor.setPassword(password);
            doctor.setFirstname(firstname.trim());
            doctor.setLastname(lastname.trim());
            doctor.setLicenceNumber(licence.trim());
            doctor.setAssignedOffice(office.trim());
            doctor.setSpecialty(parsedSpecialty);
            storage.publishEvent(StorageEventType.USERS_CHANGED);

            return new Response("Doctor updated successfully", Status.OK);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response getDoctorInfo(String idStr) {
        try {
            Response idValidation = UserValidator.validateUserId(idStr);
            if (idValidation != null) {
                return idValidation;
            }

            Storage storage = Storage.getInstance();
            Doctor doctor = storage.getDoctorById(Long.parseLong(idStr.trim()));

            if (doctor == null) {
                return new Response("Doctor not found", Status.NOT_FOUND);
            }

            return new Response("Doctor found", Status.OK, storage.serializeDoctor(doctor));
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response getAllDoctors() {
        try {
            Storage storage = Storage.getInstance();
            List<HashMap<String, Object>> doctors = new ArrayList<>();

            for (User user : storage.getAllUsers()) {
                if (user instanceof Doctor doctor) {
                    doctors.add(storage.serializeDoctor(doctor));
                }
            }

            HashMap<String, Object> data = new HashMap<>();
            data.put("doctors", doctors);
            return new Response("Doctors retrieved", Status.OK, data);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    private static Response validateDoctorData(String id, String username, String password,
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

        Storage storage = Storage.getInstance();
        if (storage.getSpecialtyByName(specialty.trim()) == null) {
            return new Response("Specialty not found", Status.NOT_FOUND);
        }

        return null;
    }
}
