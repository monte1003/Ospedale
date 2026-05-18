/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.ospedale.model.storage;
import packagee.ospedale.model.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
/**
 *
 * @author isaac
 */
public class Storage {

    private static Storage instance;

    private ArrayList<User> users;
    private ArrayList<Appointment> appointments;
    private ArrayList<Hospitalization> hospitalizations;

    // Contadores por paciente para generar IDs
    private HashMap<Long, Integer> appointmentCounters;
    private HashMap<Long, Integer> hospitalizationCounters;

    private Storage() {
        users = new ArrayList<>();
        appointments = new ArrayList<>();
        hospitalizations = new ArrayList<>();
        appointmentCounters = new HashMap<>();
        hospitalizationCounters = new HashMap<>();
        loadUsersFromJson();
    }

    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }

    // ── Carga JSON ─────────────────────────────────────────────────────────

    private void loadUsersFromJson() {
        try {
            String content = new String(Files.readAllBytes(Paths.get("json/users.json")));
            JSONObject root = new JSONObject(content);
            JSONArray arr = root.getJSONArray("users");

            for (int i = 0; i < arr.length(); i++) {
                JSONObject obj = arr.getJSONObject(i);
                String type = obj.getString("type");
                long id = obj.getLong("id");
                String username = obj.getString("username");
                String firstname = obj.getString("firstname");
                String lastname = obj.getString("lastname");
                String password = obj.getString("password");

                switch (type) {
                    case "admin":
                        users.add(new Administrator(id, username, firstname, lastname, password));
                        break;
                    case "patient":
                        String email = obj.getString("email");
                        LocalDate birthdate = LocalDate.parse(obj.getString("birthdate"));
                        boolean gender = obj.getBoolean("gender");
                        long phone = obj.getLong("phone");
                        String address = obj.getString("address");
                        users.add(new Patient(id, username, firstname, lastname, password,
                                              email, birthdate, gender, phone, address));
                        break;
                    case "doctor":
                        Specialty specialty = parseSpecialty(obj.getString("specialty"));
                        String licence = obj.getString("licenceNumber");
                        String office = obj.getString("assignedOffice");
                        users.add(new Doctor(id, username, firstname, lastname, password,
                                             specialty, licence, office));
                        break;
                }
            }
        } catch (IOException e) {
            System.err.println("Error loading users.json: " + e.getMessage());
        }
    }

    // Mapea los strings del JSON a los valores del enum
    private Specialty parseSpecialty(String s) {
        switch (s.toUpperCase()) {
            case "CARDIOLOGY":           return Specialty.CARDIOLOGY;
            case "NEUROLOGY":            return Specialty.NEUROLOGY;
            case "PEDIATRICS":           return Specialty.PEDIATRICS;
            case "DERMATOLOGY":          return Specialty.DERMATOLOGY;
            case "ORTHOPEDICS":
            case "TRAUMATOLOGY_ORTHOPEDICS": return Specialty.TRAUMATOLOGY_ORTHOPEDICS;
            case "GYNECOLOGY":
            case "GYNECOLOGY_OBSTETRICS":   return Specialty.GYNECOLOGY_OBSTETRICS;
            case "PSYCHIATRY":           return Specialty.PSYCHIATRY;
            case "ONCOLOGY":             return Specialty.ONCOLOGY;
            case "OPHTHALMOLOGY":        return Specialty.OPHTHALMOLOGY;
            case "INTERNAL_MEDICINE":    return Specialty.INTERNAL_MEDICINE;
            default:                     return Specialty.GENERAL_MEDICINE;
        }
    }

    // ── Búsquedas de usuarios ──────────────────────────────────────────────

    public User getUserByUsername(String username) {
        for (User u : users) {
            if (u.getUsername().equals(username)) return u;
        }
        return null;
    }

    public Patient getPatientById(long id) {
        for (User u : users) {
            if (u instanceof Patient && u.getId() == id) return (Patient) u;
        }
        return null;
    }

    public Doctor getDoctorById(long id) {
        for (User u : users) {
            if (u instanceof Doctor && u.getId() == id) return (Doctor) u;
        }
        return null;
    }

    public ArrayList<User> getAllUsers() { return users; }

    public boolean addUser(User user) {
        for (User u : users) {
            if (u.getId() == user.getId()) return false;
        }
        users.add(user);
        return true;
    }

    // ── Especialidades ─────────────────────────────────────────────────────

    public Specialty getSpecialtyByName(String name) {
        try {
            return Specialty.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    // ── Doctores disponibles ───────────────────────────────────────────────

    public boolean isDoctorAvailable(Doctor doctor, LocalDateTime datetime) {
        for (Appointment a : appointments) {
            if (a.getDoctor().getId() == doctor.getId()
                    && a.getDatetime().equals(datetime)
                    && a.getStatus() != AppointmentStatus.CANCELED) {
                return false;
            }
        }
        return true;
    }

    public boolean isDoctorAvailableExcluding(Doctor doctor, LocalDateTime datetime, String excludeAppointmentId) {
        for (Appointment a : appointments) {
            if (a.getDoctor().getId() == doctor.getId()
                    && a.getDatetime().equals(datetime)
                    && a.getStatus() != AppointmentStatus.CANCELED
                    && !a.getId().equals(excludeAppointmentId)) {
                return false;
            }
        }
        return true;
    }

    public Doctor findAvailableDoctor(Specialty specialty, LocalDateTime datetime) {
        for (User u : users) {
            if (u instanceof Doctor) {
                Doctor d = (Doctor) u;
                if (d.getSpecialty() == specialty && isDoctorAvailable(d, datetime)) {
                    return d;
                }
            }
        }
        return null;
    }

    // ── Citas ──────────────────────────────────────────────────────────────

    public String generateAppointmentId(long patientId) {
        int count = appointmentCounters.getOrDefault(patientId, 0);
        appointmentCounters.put(patientId, count + 1);
        return String.format("A-%d-%04d", patientId, count);
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
    }

    public Appointment getAppointmentById(String id) {
        for (Appointment a : appointments) {
            if (a.getId().equals(id)) return a;
        }
        return null;
    }

    // Ordenadas descendentemente por datetime
    public List<HashMap<String, Object>> getAppointmentsByPatientSorted(long patientId) {
        return appointments.stream()
                .filter(a -> a.getPatient().getId() == patientId)
                .sorted(Comparator.comparing(Appointment::getDatetime).reversed())
                .map(a -> serializeAppointment(a))
                .collect(Collectors.toList());
    }

    public List<HashMap<String, Object>> getAppointmentsByDoctorSorted(long doctorId, boolean pendingOnly) {
        return appointments.stream()
                .filter(a -> a.getDoctor().getId() == doctorId)
                .filter(a -> !pendingOnly || a.getStatus() == AppointmentStatus.PENDING)
                .sorted(Comparator.comparing(Appointment::getDatetime).reversed())
                .map(a -> serializeAppointment(a))
                .collect(Collectors.toList());
    }

    // ── Hospitalizaciones ──────────────────────────────────────────────────

    public String generateHospitalizationId(long patientId) {
        int count = hospitalizationCounters.getOrDefault(patientId, 0);
        hospitalizationCounters.put(patientId, count + 1);
        return String.format("H-%d-%04d", patientId, count);
    }

    public void addHospitalization(Hospitalization h) {
        hospitalizations.add(h);
    }

    public Hospitalization getHospitalizationById(String id) {
        for (Hospitalization h : hospitalizations) {
            if (h.getId().equals(id)) return h;
        }
        return null;
    }

    public List<HashMap<String, Object>> getHospitalizationsByPatient(long patientId) {
        List<HashMap<String, Object>> result = new ArrayList<>();
        for (Hospitalization h : hospitalizations) {
            if (h.getPatient().getId() == patientId) {
                result.add(serializeHospitalization(h));
            }
        }
        return result;
    }

    // ── Serialización ──────────────────────────────────────────────────────

    public HashMap<String, Object> serializeUser(User u) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", u.getId());
        map.put("username", u.getUsername());
        map.put("firstname", u.getFirstname());
        map.put("lastname", u.getLastname());
        if (u instanceof Administrator) map.put("role", "admin");
        else if (u instanceof Doctor)   map.put("role", "doctor");
        else if (u instanceof Patient)  map.put("role", "patient");
        return map;
    }

    public HashMap<String, Object> serializePatient(Patient p) {
        HashMap<String, Object> map = serializeUser(p);
        map.put("email", p.getEmail());
        map.put("phone", p.getPhone());
        map.put("address", p.getAddress());
        map.put("birthdate", p.getBirthdate().toString());
        map.put("gender", p.isGender());
        return map;
    }

    public HashMap<String, Object> serializeDoctor(Doctor d) {
        HashMap<String, Object> map = serializeUser(d);
        map.put("specialty", d.getSpecialty().name());
        map.put("licenceNumber", d.getLicenceNumber());
        map.put("assignedOffice", d.getAssignedOffice());
        return map;
    }

    public HashMap<String, Object> serializeAppointment(Appointment a) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", a.getId());
        map.put("patientId", a.getPatient().getId());
        map.put("patientName", a.getPatient().getFirstname() + " " + a.getPatient().getLastname());
        map.put("doctorId", a.getDoctor().getId());
        map.put("doctorName", a.getDoctor().getFirstname() + " " + a.getDoctor().getLastname());
        map.put("specialty", a.getSpecialty().name());
        map.put("datetime", a.getDatetime().toString());
        map.put("status", a.getStatus().name());
        return map;
    }

    public HashMap<String, Object> serializeHospitalization(Hospitalization h) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", h.getId());
        map.put("patientId", h.getPatient().getId());
        map.put("date", h.getDate().toString());
        map.put("status", h.getStatus().name());
        map.put("roomType", h.getRoomType().name());
        return map;
    }
}