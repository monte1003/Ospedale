package packagee.ospedale.model;

import java.time.LocalDate;

/**
 * Representa una solicitud o proceso de hospitalizacion de un paciente.
 */
public class Hospitalization {

    private final String id;
    private final Patient patient;
    private final Doctor doctor;
    private final LocalDate date;
    private String reason;
    private final RoomType roomType;
    private String observations;
    private HospitalizationStatus status;

    public void setStatus(HospitalizationStatus status) {
        this.status = status;
    }

    public Hospitalization(String id, Patient patient, Doctor doctor, LocalDate date, String reason, RoomType roomType, String observations) {
        this.id = id;
        this.patient = patient;
        patient.addHospitalization(this);
        this.doctor = doctor;
        doctor.addHospitalization(this);
        this.date = date;
        this.reason = reason;
        this.roomType = roomType;
        this.observations = observations;
        this.status = HospitalizationStatus.REQUESTED;
    }

    public Hospitalization(String id, Patient patient, Doctor doctor, LocalDate date, String reason, RoomType roomType, String observations, HospitalizationStatus hopsS) {
        this.id = id;
        this.patient = patient;
        patient.addHospitalization(this);
        this.doctor = doctor;
        doctor.addHospitalization(this);
        this.date = date;
        this.reason = reason;
        this.roomType = roomType;
        this.observations = observations;
        this.status = hopsS;
    }

    public String getId() {
        return id;
    }

    public Patient getPatient() { 
        return patient; 
    }
    
    public Doctor getDoctor() { 
        return doctor; 
    }
    
    public LocalDate getDate() { 
        return date; 
    }
    
    public String getReason() { 
        return reason; 
    }
    
    public RoomType getRoomType() { 
        return roomType; 
    }
    
    public HospitalizationStatus getStatus() { 
        return status; 
    }

    public String getObservations() {
        return observations;
    }
}
