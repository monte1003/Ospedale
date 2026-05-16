/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.ospedale.controller;
import packagee.ospedale.controller.utils.Response;
import packagee.ospedale.controller.utils.Status;
import packagee.ospedale.model.*;
import packagee.ospedale.model.storage.Storage;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
/**
 *
 * @author isaac
 */
public class PatientController {

    private static Response validateId(String id) {
        if (!id.trim().matches("\\d{12}"))
            return new Response("ID must be exactly 12 digits", Status.BAD_REQUEST);
        if (Long.parseLong(id.trim()) <= 0)
            return new Response("ID must be greater than 0", Status.BAD_REQUEST);
        return null;
    }

    private static Response validatePhone(String phone) {
        if (!phone.trim().matches("\\d{10}"))
            return new Response("Phone must have exactly 10 digits", Status.BAD_REQUEST);
        return null;
    }

    private static Response validateEmail(String email) {
        if (!email.trim().matches("[^@]+@[^@]+\\.com"))
            return new Response("Email must follow format XXXXX@XXXXX.com", Status.BAD_REQUEST);
        return null;
    }

    private static Response validateBirthdate(String birthdate) {
        try {
            LocalDate.parse(birthdate.trim());
            return null;
        } catch (DateTimeParseException e) {
            return new Response("Birthdate must be valid and follow format YYYY-MM-DD", Status.BAD_REQUEST);
        }
    }

    public static Response registerPatient(String id, String username, String password,
            String confirmPassword, String firstname, String lastname,
            String email, String birthdate, String gender, String phone, String address) {
        try {
            Response v;
            if ((v = validateId(id)) != null) return v;
            if (username.trim().isEmpty())
                return new Response("Username must not be empty", Status.BAD_REQUEST);
            if (password.isEmpty())
                return new Response("Password must not be empty", Status.BAD_REQUEST);
            if (!password.equals(confirmPassword))
                return new Response("Passwords do not match", Status.BAD_REQUEST);
            if (firstname.trim().isEmpty())
                return new Response("Firstname must not be empty", Status.BAD_REQUEST);
            if (lastname.trim().isEmpty())
                return new Response("Lastname must not be empty", Status.BAD_REQUEST);
            if ((v = validateEmail(email)) != null) return v;
            if ((v = validateBirthdate(birthdate)) != null) return v;
            if ((v = validatePhone(phone)) != null) return v;

            Storage storage = Storage.getInstance();
            long idLong = Long.parseLong(id.trim());

            if (storage.getPatientById(idLong) != null)
                return new Response("A patient with that ID already exists", Status.BAD_REQUEST);
            if (storage.getUserByUsername(username.trim()) != null)
                return new Response("Username already taken", Status.BAD_REQUEST);

            boolean genderBool = gender.equalsIgnoreCase("Male") || gender.equals("true");
            Patient patient = new Patient(idLong, username.trim(), firstname.trim(), lastname.trim(),
                    password, email.trim(), LocalDate.parse(birthdate.trim()),
                    genderBool, Long.parseLong(phone.trim()), address.trim());
            storage.addUser(patient);

            return new Response("Patient registered successfully", Status.CREATED);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response updatePatient(String idStr, String username, String password,
            String confirmPassword, String firstname, String lastname,
            String email, String birthdate, String gender, String phone, String address) {
        try {
            long id = Long.parseLong(idStr.trim());
            Storage storage = Storage.getInstance();
            Patient patient = storage.getPatientById(id);
            if (patient == null)
                return new Response("Patient not found", Status.NOT_FOUND);

            Response v;
            if (username.trim().isEmpty())
                return new Response("Username must not be empty", Status.BAD_REQUEST);
            if (password.isEmpty())
                return new Response("Password must not be empty", Status.BAD_REQUEST);
            if (!password.equals(confirmPassword))
                return new Response("Passwords do not match", Status.BAD_REQUEST);
            if (firstname.trim().isEmpty())
                return new Response("Firstname must not be empty", Status.BAD_REQUEST);
            if (lastname.trim().isEmpty())
                return new Response("Lastname must not be empty", Status.BAD_REQUEST);
            if ((v = validateEmail(email)) != null) return v;
            if ((v = validateBirthdate(birthdate)) != null) return v;
            if ((v = validatePhone(phone)) != null) return v;

            User existing = storage.getUserByUsername(username.trim());
            if (existing != null && existing.getId() != id)
                return new Response("Username already taken", Status.BAD_REQUEST);

            patient.setUsername(username.trim());
            patient.setPassword(password);
            patient.setFirstname(firstname.trim());
            patient.setLastname(lastname.trim());
            patient.setEmail(email.trim());
            patient.setBirthdate(LocalDate.parse(birthdate.trim()));
            patient.setGender(gender.equalsIgnoreCase("Male") || gender.equals("true"));
            patient.setPhone(Long.parseLong(phone.trim()));
            patient.setAddress(address.trim());

            return new Response("Patient updated successfully", Status.OK);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response getPatientInfo(String idStr) {
        try {
            long id = Long.parseLong(idStr.trim());
            Storage storage = Storage.getInstance();
            Patient patient = storage.getPatientById(id);
            if (patient == null)
                return new Response("Patient not found", Status.NOT_FOUND);
            return new Response("Patient found", Status.OK, storage.serializePatient(patient));
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response getAllPatients() {
        try {
            Storage storage = Storage.getInstance();
            List<HashMap<String, Object>> list = new java.util.ArrayList<>();
            for (User u : storage.getAllUsers()) {
                if (u instanceof Patient) list.add(storage.serializePatient((Patient) u));
            }
            HashMap<String, Object> data = new HashMap<>();
            data.put("patients", list);
            return new Response("Patients retrieved", Status.OK, data);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
