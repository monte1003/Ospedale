package packagee.ospedale.model.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import org.json.JSONArray;
import org.json.JSONObject;
import packagee.ospedale.model.Administrator;
import packagee.ospedale.model.Appointment;
import packagee.ospedale.model.AppointmentStatus;
import packagee.ospedale.model.Doctor;
import packagee.ospedale.model.Hospitalization;
import packagee.ospedale.model.HospitalizationStatus;
import packagee.ospedale.model.Patient;
import packagee.ospedale.model.Specialty;
import packagee.ospedale.model.User;
import packagee.ospedale.observer.StorageEventType;
import packagee.ospedale.observer.StorageObserver;

/**
 * Almacenamiento central en memoria para usuarios, citas y hospitalizaciones.
 */
public class Storage {

    private static Storage instance;

    private final ArrayList<User> users;
    private final ArrayList<Appointment> appointments;
    private final ArrayList<Hospitalization> hospitalizations;
    private final HashMap<Long, Integer> appointmentCounters;
    private final HashMap<Long, Integer> hospitalizationCounters;
    private final List<StorageObserver> observers;

    private Storage() {
        users = new ArrayList<>();
        appointments = new ArrayList<>();
        hospitalizations = new ArrayList<>();
        appointmentCounters = new HashMap<>();
        hospitalizationCounters = new HashMap<>();
        observers = new CopyOnWriteArrayList<>();
        loadUsersFromJson();
    }

    public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }

        return instance;
    }

    // Las vistas se suscriben para refrescarse cuando cambia la informacion compartida.
    public void addObserver(StorageObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void removeObserver(StorageObserver observer) {
        observers.remove(observer);
    }

    public void publishEvent(StorageEventType eventType) {
        for (StorageObserver observer : observers) {
            observer.onStorageChanged(eventType);
        }
    }

    private void loadUsersFromJson() {
        try {
            String content = new String(Files.readAllBytes(Paths.get("json/users.json")));
            JSONObject root = new JSONObject(content);
            JSONArray array = root.getJSONArray("users");

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                String type = object.getString("type");
                long id = object.getLong("id");
                String username = object.getString("username");
                String firstname = object.getString("firstname");
                String lastname = object.getString("lastname");
                String password = object.getString("password");

                switch (type) {
                    case "admin" -> addUserFromJson(
                            new Administrator(id, username, firstname, lastname, password),
                            username
                    );
                    case "patient" -> {
                        addUserFromJson(new Patient(
                                id,
                                username,
                                firstname,
                                lastname,
                                password,
                                object.getString("email"),
                                LocalDate.parse(object.getString("birthdate")),
                                object.getBoolean("gender"),
                                object.getLong("phone"),
                                object.getString("address")
                        ), username);
                    }
                    case "doctor" -> {
                        Specialty specialty = parseSpecialty(object.getString("specialty"));
                        if (specialty == null) {
                            System.err.println("Skipping doctor with invalid specialty: " + username);
                            continue;
                        }

                        addUserFromJson(new Doctor(
                                id,
                                username,
                                firstname,
                                lastname,
                                password,
                                specialty,
                                object.getString("licenceNumber"),
                                object.getString("assignedOffice")
                        ), username);
                    }
                    default -> {
                    }
                }
            }
        } catch (IOException | RuntimeException ex) {
            System.err.println("Error loading users.json: " + ex.getMessage());
        }
    }

    private void addUserFromJson(User user, String username) {
        if (!isValidUserId(user.getId())) {
            System.err.println("Skipping user with invalid ID: " + username);
            return;
        }

        if (getUserByUsername(username) != null) {
            System.err.println("Skipping duplicated username in JSON: " + username);
            return;
        }

        if (!addUser(user)) {
            System.err.println("Skipping duplicated user ID in JSON: " + user.getId());
        }
    }

    private boolean isValidUserId(long id) {
        return String.valueOf(id).matches("^[1-9]\\d{11}$");
    }

    private Specialty parseSpecialty(String value) {
        String normalized = value.toUpperCase();
        return switch (normalized) {
            case "CARDIOLOGY" -> Specialty.CARDIOLOGY;
            case "NEUROLOGY" -> Specialty.NEUROLOGY;
            case "PEDIATRICS" -> Specialty.PEDIATRICS;
            case "DERMATOLOGY" -> Specialty.DERMATOLOGY;
            case "ORTHOPEDICS", "TRAUMATOLOGY_ORTHOPEDICS" -> Specialty.TRAUMATOLOGY_ORTHOPEDICS;
            case "GYNECOLOGY", "GYNECOLOGY_OBSTETRICS" -> Specialty.GYNECOLOGY_OBSTETRICS;
            case "PSYCHIATRY" -> Specialty.PSYCHIATRY;
            case "ONCOLOGY" -> Specialty.ONCOLOGY;
            case "OPHTHALMOLOGY" -> Specialty.OPHTHALMOLOGY;
            case "INTERNAL_MEDICINE" -> Specialty.INTERNAL_MEDICINE;
            case "GENERAL_MEDICINE" -> Specialty.GENERAL_MEDICINE;
            default -> null;
        };
    }

    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }

        return null;
    }

    public Patient getPatientById(long id) {
        for (User user : users) {
            if (user instanceof Patient patient && user.getId() == id) {
                return patient;
            }
        }

        return null;
    }

    public Doctor getDoctorById(long id) {
        for (User user : users) {
            if (user instanceof Doctor doctor && user.getId() == id) {
                return doctor;
            }
        }

        return null;
    }

    public ArrayList<User> getAllUsers() {
        return new ArrayList<>(users);
    }

    public boolean addUser(User user) {
        for (User current : users) {
            if (current.getId() == user.getId()) {
                return false;
            }
        }

        users.add(user);
        return true;
    }

    public Specialty getSpecialtyByName(String name) {
        if (name == null) {
            return null;
        }

        String normalized = name.trim().toUpperCase()
                .replace(" ", "_")
                .replace("&", "")
                .replace("-", "_");

        if ("TRAUMATOLOGY___ORTHOPEDICS".equals(normalized) || "TRAUMATOLOGY__ORTHOPEDICS".equals(normalized)) {
            normalized = "TRAUMATOLOGY_ORTHOPEDICS";
        }

        if ("GYNECOLOGY___OBSTETRICS".equals(normalized) || "GYNECOLOGY__OBSTETRICS".equals(normalized)) {
            normalized = "GYNECOLOGY_OBSTETRICS";
        }

        try {
            return Specialty.valueOf(normalized);
        } catch (IllegalArgumentException ex) {
            return null;
        }
    }

    public boolean isDoctorAvailable(Doctor doctor, LocalDateTime datetime) {
        for (Appointment appointment : appointments) {
            if (appointment.getDoctor().getId() == doctor.getId()
                    && appointment.getDatetime().equals(datetime)
                    && appointment.getStatus() != AppointmentStatus.CANCELED) {
                return false;
            }
        }

        return true;
    }

    public boolean isDoctorAvailableExcluding(Doctor doctor, LocalDateTime datetime, String excludeAppointmentId) {
        for (Appointment appointment : appointments) {
            if (appointment.getDoctor().getId() == doctor.getId()
                    && appointment.getDatetime().equals(datetime)
                    && appointment.getStatus() != AppointmentStatus.CANCELED
                    && !appointment.getId().equals(excludeAppointmentId)) {
                return false;
            }
        }

        return true;
    }

    public Doctor findAvailableDoctor(Specialty specialty, LocalDateTime datetime) {
        for (User user : users) {
            if (user instanceof Doctor doctor
                    && doctor.getSpecialty() == specialty
                    && isDoctorAvailable(doctor, datetime)) {
                return doctor;
            }
        }

        return null;
    }

    public String generateAppointmentId(long patientId) {
        int count = appointmentCounters.getOrDefault(patientId, 0);
        appointmentCounters.put(patientId, count + 1);
        return String.format("A-%d-%04d", patientId, count);
    }

    public void addAppointment(Appointment appointment) {
        appointments.add(appointment);
        appointment.getPatient().addAppointment(appointment);
        appointment.getDoctor().addAppointment(appointment);
    }

    public Appointment getAppointmentById(String id) {
        for (Appointment appointment : appointments) {
            if (appointment.getId().equals(id)) {
                return appointment;
            }
        }

        return null;
    }

    public ArrayList<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments);
    }

    public List<HashMap<String, Object>> getAppointmentsByPatientSorted(long patientId) {
        return appointments.stream()
                .filter(appointment -> appointment.getPatient().getId() == patientId)
                .sorted(Comparator.comparing(Appointment::getDatetime).reversed())
                .map(this::serializeAppointment)
                .collect(Collectors.toList());
    }

    public List<HashMap<String, Object>> getAppointmentsByDoctorSorted(long doctorId, boolean pendingOnly) {
        return appointments.stream()
                .filter(appointment -> appointment.getDoctor().getId() == doctorId)
                .filter(appointment -> !pendingOnly || appointment.getStatus() == AppointmentStatus.PENDING)
                .sorted(Comparator.comparing(Appointment::getDatetime).reversed())
                .map(this::serializeAppointment)
                .collect(Collectors.toList());
    }

    public String generateHospitalizationId(long patientId) {
        int count = hospitalizationCounters.getOrDefault(patientId, 0);
        hospitalizationCounters.put(patientId, count + 1);
        return String.format("H-%d-%04d", patientId, count);
    }

    public void addHospitalization(Hospitalization hospitalization) {
        hospitalizations.add(hospitalization);
    }

    public Hospitalization getHospitalizationById(String id) {
        for (Hospitalization hospitalization : hospitalizations) {
            if (hospitalization.getId().equals(id)) {
                return hospitalization;
            }
        }

        return null;
    }

    public List<HashMap<String, Object>> getHospitalizationsByPatient(long patientId) {
        return hospitalizations.stream()
                .filter(hospitalization -> hospitalization.getPatient().getId() == patientId)
                .map(this::serializeHospitalization)
                .collect(Collectors.toList());
    }

    public List<HashMap<String, Object>> getRequestedHospitalizations() {
        return hospitalizations.stream()
                .filter(hospitalization -> hospitalization.getStatus() == HospitalizationStatus.REQUESTED)
                .map(this::serializeHospitalization)
                .collect(Collectors.toList());
    }

    public HashMap<String, Object> serializeUser(User user) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("username", user.getUsername());
        map.put("firstname", user.getFirstname());
        map.put("lastname", user.getLastname());

        if (user instanceof Administrator) {
            map.put("role", "admin");
        } else if (user instanceof Doctor) {
            map.put("role", "doctor");
        } else if (user instanceof Patient) {
            map.put("role", "patient");
        }

        return map;
    }

    public HashMap<String, Object> serializePatient(Patient patient) {
        HashMap<String, Object> map = serializeUser(patient);
        map.put("email", patient.getEmail());
        map.put("phone", patient.getPhone());
        map.put("address", patient.getAddress());
        map.put("birthdate", patient.getBirthdate().toString());
        map.put("gender", patient.isGender());
        map.put("password", patient.getPassword());
        return map;
    }

    public HashMap<String, Object> serializeDoctor(Doctor doctor) {
        HashMap<String, Object> map = serializeUser(doctor);
        map.put("specialty", doctor.getSpecialty().name());
        map.put("licenceNumber", doctor.getLicenceNumber());
        map.put("assignedOffice", doctor.getAssignedOffice());
        map.put("password", doctor.getPassword());
        return map;
    }

    public HashMap<String, Object> serializeAppointment(Appointment appointment) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", appointment.getId());
        map.put("patientId", appointment.getPatient().getId());
        map.put("patientName", appointment.getPatient().getFirstname() + " " + appointment.getPatient().getLastname());
        map.put("doctorId", appointment.getDoctor().getId());
        map.put("doctorName", appointment.getDoctor().getFirstname() + " " + appointment.getDoctor().getLastname());
        map.put("specialty", appointment.getSpecialty().name());
        map.put("datetime", appointment.getDatetime().toString());
        map.put("status", appointment.getStatus().name());
        map.put("type", appointment.isType() ? "Remote" : "In-person");
        map.put("reason", appointment.getReason());
        return map;
    }

    public HashMap<String, Object> serializeHospitalization(Hospitalization hospitalization) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", hospitalization.getId());
        map.put("patientId", hospitalization.getPatient().getId());
        map.put("patientName", hospitalization.getPatient().getFirstname() + " " + hospitalization.getPatient().getLastname());
        map.put("doctorId", hospitalization.getDoctor().getId());
        map.put("doctorName", hospitalization.getDoctor().getFirstname() + " " + hospitalization.getDoctor().getLastname());
        map.put("date", hospitalization.getDate().toString());
        map.put("status", hospitalization.getStatus().name());
        map.put("roomType", hospitalization.getRoomType().name());
        map.put("reason", hospitalization.getReason());
        map.put("observations", hospitalization.getObservations());
        return map;
    }
}
