package packagee.ospedale.repository;

import java.util.HashMap;
import java.util.List;
import packagee.ospedale.model.Hospitalization;

/**
 * Define las operaciones de acceso a datos para hospitalizaciones.
 */
public interface HospitalizationRepository {

    void addHospitalization(Hospitalization hospitalization);

    Hospitalization getHospitalizationById(String id);

    void updateHospitalization(Hospitalization hospitalization);

    List<HashMap<String, Object>> getHospitalizationsByPatient(long patientId);

    List<HashMap<String, Object>> getRequestedHospitalizations();

    String generateHospitalizationId(long patientId);

    HashMap<String, Object> serializeHospitalization(Hospitalization hospitalization);
}
