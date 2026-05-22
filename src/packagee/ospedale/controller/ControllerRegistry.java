package packagee.ospedale.controller;

import packagee.ospedale.repository.AppointmentRepository;
import packagee.ospedale.repository.AppointmentRepositoryImpl;
import packagee.ospedale.repository.DoctorRepository;
import packagee.ospedale.repository.DoctorRepositoryImpl;
import packagee.ospedale.repository.HospitalizationRepository;
import packagee.ospedale.repository.HospitalizationRepositoryImpl;
import packagee.ospedale.repository.PatientRepository;
import packagee.ospedale.repository.PatientRepositoryImpl;
import packagee.ospedale.service.AppointmentServiceImpl;
import packagee.ospedale.service.AuthServiceImpl;
import packagee.ospedale.service.DoctorServiceImpl;
import packagee.ospedale.service.HospitalizationServiceImpl;
import packagee.ospedale.service.IAppointmentService;
import packagee.ospedale.service.IAuthService;
import packagee.ospedale.service.IDoctorService;
import packagee.ospedale.service.IHospitalizationService;
import packagee.ospedale.service.IPatientService;
import packagee.ospedale.service.PatientServiceImpl;

/**
 * Registro central de controladores (Contenedor DI).
 */
public class ControllerRegistry {

    private static ControllerRegistry instance;

    private final IAuthController authController;
    private final IPatientController patientController;
    private final IDoctorController doctorController;
    private final IMedicalServiceController medicalServiceController;

    private ControllerRegistry() {
        // 1. Instanciar repositorios
        PatientRepository patientRepository = new PatientRepositoryImpl();
        DoctorRepository doctorRepository = new DoctorRepositoryImpl();
        AppointmentRepository appointmentRepository = new AppointmentRepositoryImpl();
        HospitalizationRepository hospitalizationRepository = new HospitalizationRepositoryImpl();

        // 2. Instanciar servicios
        IAuthService authService = new AuthServiceImpl(patientRepository);
        IPatientService patientService = new PatientServiceImpl(patientRepository);
        IDoctorService doctorService = new DoctorServiceImpl(doctorRepository);
        IHospitalizationService hospitalizationService = new HospitalizationServiceImpl(
                hospitalizationRepository, patientRepository, doctorRepository, appointmentRepository);
        IAppointmentService appointmentService = new AppointmentServiceImpl(
                appointmentRepository, patientRepository, doctorRepository, hospitalizationRepository);

        // 3. Instanciar controladores
        this.authController = new AuthController(authService);
        this.patientController = new PatientController(patientService);
        this.doctorController = new DoctorController(doctorService);
        this.medicalServiceController = new MedicalServiceController(appointmentService, hospitalizationService);
    }

    public static synchronized ControllerRegistry getInstance() {
        if (instance == null) {
            instance = new ControllerRegistry();
        }
        return instance;
    }

    public IAuthController getAuthController() {
        return authController;
    }

    public IPatientController getPatientController() {
        return patientController;
    }

    public IDoctorController getDoctorController() {
        return doctorController;
    }

    public IMedicalServiceController getMedicalServiceController() {
        return medicalServiceController;
    }
}
