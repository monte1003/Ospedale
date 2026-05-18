/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.ospedale.controller;
import packagee.ospedale.controller.utils.Response;
import packagee.ospedale.controller.utils.Status;
import packagee.ospedale.model.*;
import packagee.ospedale.model.storage.Storage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 *
 * @author isaac
 */
public class DoctorController {
    private static Response validateLicence(String licence) {
        if (!licence.trim().matches("L-\\d{10} MTL"))
            return new Response("Licence must follow format L-XXXXXXXXXX MTL", Status.BAD_REQUEST);
        return null;
    }

    private static Response validateOffice(String office) {
        if (!office.trim().matches("O-\\d{3}"))
            return new Response("Office must follow format O-XXX", Status.BAD_REQUEST);
        return null;
    }

    public static Response registerDoctor(String id, String username, String password,
            String confirmPassword, String firstname, String lastname,
            String licence, String office, String specialty) {
        try {
            if (!id.trim().matches("\\d{12}") || Long.parseLong(id.trim()) <= 0)
                return new Response("ID must be exactly 12 digits and greater than 0", Status.BAD_REQUEST);
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

            Response v;
            if ((v = validateLicence(licence)) != null) return v;
            if ((v = validateOffice(office)) != null) return v;

            Storage storage = Storage.getInstance();
            long idLong = Long.parseLong(id.trim());

            if (storage.getDoctorById(idLong) != null)
                return new Response("A doctor with that ID already exists", Status.BAD_REQUEST);
            if (storage.getUserByUsername(username.trim()) != null)
                return new Response("Username already taken", Status.BAD_REQUEST);

            Specialty spec = storage.getSpecialtyByName(specialty.trim());
            if (spec == null)
                return new Response("Specialty not found", Status.NOT_FOUND);

            Doctor doctor = new Doctor(idLong, username.trim(), firstname.trim(), lastname.trim(),
                    password, spec, licence.trim(), office.trim());
            storage.addUser(doctor);

            return new Response("Doctor registered successfully", Status.CREATED);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response updateDoctor(String idStr, String username, String password,
            String confirmPassword, String firstname, String lastname,
            String licence, String office, String specialty) {
        try {
            long id = Long.parseLong(idStr.trim());
            Storage storage = Storage.getInstance();
            Doctor doctor = storage.getDoctorById(id);
            if (doctor == null)
                return new Response("Doctor not found", Status.NOT_FOUND);

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

            Response v;
            if ((v = validateLicence(licence)) != null) return v;
            if ((v = validateOffice(office)) != null) return v;

            User existing = storage.getUserByUsername(username.trim());
            if (existing != null && existing.getId() != id)
                return new Response("Username already taken", Status.BAD_REQUEST);

            Specialty spec = storage.getSpecialtyByName(specialty.trim());
            if (spec == null)
                return new Response("Specialty not found", Status.NOT_FOUND);

            doctor.setUsername(username.trim());
            doctor.setPassword(password);
            doctor.setFirstname(firstname.trim());
            doctor.setLastname(lastname.trim());
            doctor.setLicenceNumber(licence.trim());
            doctor.setAssignedOffice(office.trim());
            doctor.setSpecialty(spec);

            return new Response("Doctor updated successfully", Status.OK);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response getDoctorInfo(String idStr) {
        try {
            long id = Long.parseLong(idStr.trim());
            Storage storage = Storage.getInstance();
            Doctor doctor = storage.getDoctorById(id);
            if (doctor == null)
                return new Response("Doctor not found", Status.NOT_FOUND);
            return new Response("Doctor found", Status.OK, storage.serializeDoctor(doctor));
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }

    public static Response getAllDoctors() {
        try {
            Storage storage = Storage.getInstance();
            List<HashMap<String, Object>> list = new ArrayList<>();
            for (User u : storage.getAllUsers()) {
                if (u instanceof Doctor) list.add(storage.serializeDoctor((Doctor) u));
            }
            HashMap<String, Object> data = new HashMap<>();
            data.put("doctors", list);
            return new Response("Doctors retrieved", Status.OK, data);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
