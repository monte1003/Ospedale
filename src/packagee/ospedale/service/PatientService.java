package packagee.ospedale.service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import packagee.ospedale.controller.utils.Response;
import packagee.ospedale.controller.utils.Status;
import packagee.ospedale.factory.PatientFactory;
import packagee.ospedale.model.Patient;
import packagee.ospedale.model.User;
import packagee.ospedale.model.storage.Storage;
import packagee.ospedale.observer.StorageEventType;
import packagee.ospedale.repository.PatientRepository;
import packagee.ospedale.repository.PatientRepositoryImpl;
import packagee.ospedale.validator.PatientValidator;
import packagee.ospedale.validator.UserValidator;

/**
 * Gestiona las operaciones de negocio relacionadas con pacientes.
 */
public class PatientService {

    private static PatientRepository repository = new PatientRepositoryImpl();

    public static void setRepository(PatientRepository repo) {
        repository = repo;
    }

    private static Response validatePatientData(
            String id,
            String username,
            String password,
            String confirmPassword,
            String firstname,
            String lastname,
            String email,
            String birthdate,
            String phone
    ) {
        Response validation;

        // Se valida en un unico punto para reutilizar reglas tanto en registro como en edicion.
        if ((validation = UserValidator.validateUserId(id)) != null) {
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

        if ((validation = PatientValidator.validateEmail(email)) != null) {
            return validation;
        }

        if ((validation = PatientValidator.validateBirthdate(birthdate)) != null) {
            return validation;
        }

        if ((validation = PatientValidator.validatePhone(phone)) != null) {
            return validation;
        }

        return null;
    }

    private static Response validateExistingPatientId(String idStr) {
        return UserValidator.validateUserId(idStr);
    }

    public static Response registerPatient(String id, String username, String password,
            String confirmPassword, String firstname, String lastname,
            String email, String birthdate, String gender, String phone, String address) {
        try {
            Response validation = validatePatientData(id, username, password, confirmPassword,
                    firstname, lastname, email, birthdate, phone);
            if (validation != null) {
                return validation;
            }

            long patientId = Long.parseLong(id.trim());

            if (repository.getPatientById(patientId) != null) {
                return new Response("A patient with that ID already exists", Status.BAD_REQUEST);
            }

            if (repository.getUserByUsername(username.trim()) != null) {
                return new Response("Username already taken", Status.BAD_REQUEST);
            }

            Patient patient = PatientFactory.createPatient(
                    patientId,
                    username,
                    password,
                    firstname,
                    lastname,
                    email,
                    birthdate,
                    gender,
                    phone,
                    address
            );

            if (!repository.addUser(patient)) {
                return new Response("A user with that ID already exists", Status.BAD_REQUEST);
            }

            Storage.getInstance().publishEvent(StorageEventType.USERS_CHANGED);
            return new Response("Patient registered successfully", Status.CREATED);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response updatePatient(String idStr, String username, String password,
            String confirmPassword, String firstname, String lastname,
            String email, String birthdate, String gender, String phone, String address) {
        try {
            Response idValidation = validateExistingPatientId(idStr);
            if (idValidation != null) {
                return idValidation;
            }

            long patientId = Long.parseLong(idStr.trim());
            Patient patient = repository.getPatientById(patientId);

            if (patient == null) {
                return new Response("Patient not found", Status.NOT_FOUND);
            }

            Response validation = validatePatientData(idStr, username, password, confirmPassword,
                    firstname, lastname, email, birthdate, phone);
            if (validation != null) {
                return validation;
            }

            User existing = repository.getUserByUsername(username.trim());
            if (existing != null && existing.getId() != patientId) {
                return new Response("Username already taken", Status.BAD_REQUEST);
            }

            patient.setUsername(username.trim());
            patient.setPassword(password);
            patient.setFirstname(firstname.trim());
            patient.setLastname(lastname.trim());
            patient.setEmail(email.trim());
            patient.setBirthdate(LocalDate.parse(birthdate.trim()));
            patient.setGender(gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("true"));
            patient.setPhone(Long.parseLong(phone.trim()));
            patient.setAddress(address.trim());
            Storage.getInstance().publishEvent(StorageEventType.USERS_CHANGED);

            return new Response("Patient updated successfully", Status.OK);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response getPatientInfo(String idStr) {
        try {
            Response idValidation = validateExistingPatientId(idStr);
            if (idValidation != null) {
                return idValidation;
            }

            long id = Long.parseLong(idStr.trim());
            Patient patient = repository.getPatientById(id);

            if (patient == null) {
                return new Response("Patient not found", Status.NOT_FOUND);
            }

            return new Response("Patient found", Status.OK, repository.serializePatient(patient));
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response getAllPatients() {
        try {
            List<HashMap<String, Object>> list = new java.util.ArrayList<>();

            for (User user : repository.getAllUsers()) {
                if (user instanceof Patient patient) {
                    list.add(repository.serializePatient(patient));
                }
            }

            HashMap<String, Object> data = new HashMap<>();
            data.put("patients", list);
            return new Response("Patients retrieved", Status.OK, data);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
