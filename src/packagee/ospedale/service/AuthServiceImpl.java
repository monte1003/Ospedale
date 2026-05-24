/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.ospedale.service;

import java.util.HashMap;
import packagee.ospedale.controller.utils.Response;
import packagee.ospedale.controller.utils.Status;
import packagee.ospedale.model.User;
import packagee.ospedale.model.storage.Storage;
import packagee.ospedale.repository.PatientRepository;
import packagee.ospedale.validator.UserValidator;

/**
 * Implementacion del servicio de autenticacion.
 */
public class AuthServiceImpl implements IAuthService {

    private final PatientRepository patientRepository;

    public AuthServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public Response login(String username, String password) {
        try {
            Response validation = UserValidator.validateUsername(username);
            if (validation != null) {
                return validation;
            }

            if (password == null || password.trim().isEmpty()) {
                return new Response("Password must not be empty", Status.BAD_REQUEST);
            }

            User user = patientRepository.getUserByUsername(username.trim());

            if (user == null) {
                return new Response("User not found", Status.NOT_FOUND);
            }

            if (!user.getPassword().equals(password.trim())) {
                return new Response("Invalid credentials", Status.BAD_REQUEST);
            }

            HashMap<String, Object> data = Storage.getInstance().serializeUser(user);
            return new Response("Login successful", Status.OK, data);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
