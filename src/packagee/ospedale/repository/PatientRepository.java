/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.ospedale.repository;

import java.util.HashMap;
import java.util.List;
import packagee.ospedale.model.Patient;
import packagee.ospedale.model.User;

/**
 * Define las operaciones de acceso a datos para pacientes y usuarios.
 */
public interface PatientRepository {

    Patient getPatientById(long id);

    User getUserByUsername(String username);

    boolean addUser(User user);

    void updatePatient(Patient patient);

    HashMap<String, Object> serializePatient(Patient patient);

    List<User> getAllUsers();
}
