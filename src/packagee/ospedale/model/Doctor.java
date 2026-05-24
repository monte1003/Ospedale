package packagee.ospedale.model;

import java.util.ArrayList;

/**
 * Modela al doctor y sus relaciones con citas y hospitalizaciones.
 */
public class Doctor extends User {

    private Specialty specialty;
    private String licenceNumber;
    private String assignedOffice;
    private final ArrayList<Appointment> appointments;
    private final ArrayList<Hospitalization> hospitalizations;

    public Doctor(long id, String username, String firstname, String lastname, String password, Specialty specialty, String licenceNumber, String assignedOffice) {
        super(id, username, firstname, lastname, password);
        appointments = new ArrayList<>();
        hospitalizations = new ArrayList<>();
        this.specialty = specialty;
        this.licenceNumber = licenceNumber;
        this.assignedOffice = assignedOffice;
    }

    public ArrayList<Appointment> getAppointments() {
        return new ArrayList<>(appointments);
    }

    public boolean addAppointment(Appointment appointment) {
        return appointments.add(appointment);
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public boolean addHospitalization(Hospitalization hosp) {
        return hospitalizations.add(hosp);
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public void setAssignedOffice(String assignedOffice) {
        this.assignedOffice = assignedOffice;
    }
    
    public String getLicenceNumber() { 
        return licenceNumber; 
    }
    
    public String getAssignedOffice() { 
        return assignedOffice; 
    }
}
