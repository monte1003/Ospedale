package packagee.ospedale.model;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Modela al paciente y su informacion clinica basica.
 */
public class Patient extends User {

    private String email;
    private LocalDate birthdate;
    private boolean gender;
    private long phone;
    private String address;
    private final ArrayList<Appointment> appointments;
    private final ArrayList<Hospitalization> hospitalizations;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addHospitalization(Hospitalization hospitalization) {
        this.hospitalizations.add(hospitalization);
    }

    public ArrayList<Appointment> getAppointments() {
        return new ArrayList<>(appointments);
    }

    public ArrayList<Hospitalization> getHospitalizations() {
        return new ArrayList<>(hospitalizations);
    }
    
    public void addAppointment(Appointment a) {
        this.appointments.add(a);
    }
    
    public String getEmail() { 
        return email; 
    }
    
    public long getPhone() { 
        return phone; 
    }
    
    public String getAddress() { 
        return address; 
    }
    
    public LocalDate getBirthdate() { 
        return birthdate; 
    }
    
    public boolean isGender() { 
        return gender; 
    }

    public Patient(long id, String username, String firstname, String lastname, String password, String email, LocalDate birthdate, boolean gender, long phone, String address) {
        super(id, username, firstname, lastname, password);
        this.email = email;
        this.birthdate = birthdate;
        this.gender = gender;
        this.phone = phone;
        this.address = address;
        this.appointments = new ArrayList<>();
        this.hospitalizations = new ArrayList<>();
    }
}
