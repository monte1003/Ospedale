package packagee.ospedale.model;

/**
 * Representa una prescripcion asociada a una cita.
 */
public class Prescription {

    private final Appointment appointment;
    private final String medicationName;
    private final double dose;
    private final String administrationRoute;
    private final int treatmentDuration;
    private final String additionalInstructions;
    private final int frecuency;

    public Prescription(Appointment appointment, String medicationName, double dose, String administrationRoute, int treatmentDuration, String additionalInstructions, int frecuency) {
        this.appointment = appointment;
        appointment.addPrescription(this);
        this.medicationName = medicationName;
        this.dose = dose;
        this.administrationRoute = administrationRoute;
        this.treatmentDuration = treatmentDuration;
        this.additionalInstructions = additionalInstructions;
        this.frecuency = frecuency;
    }
}
