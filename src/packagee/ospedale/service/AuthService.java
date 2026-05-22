package packagee.ospedale.service;

import java.util.HashMap;
import packagee.ospedale.controller.utils.Response;
import packagee.ospedale.controller.utils.Status;
import packagee.ospedale.model.User;
import packagee.ospedale.model.storage.Storage;
import packagee.ospedale.validator.UserValidator;

/**
 * Contiene la logica de autenticacion del sistema.
 */
public final class AuthService {

    private AuthService() {
    }

    public static Response login(String username, String password) {
        try {
            Response validation = UserValidator.validateUsername(username);
            if (validation != null) {
                return validation;
            }

            if (password == null || password.trim().isEmpty()) {
                return new Response("Password must not be empty", Status.BAD_REQUEST);
            }

            Storage storage = Storage.getInstance();
            User user = storage.getUserByUsername(username.trim());

            if (user == null) {
                return new Response("User not found", Status.NOT_FOUND);
            }

            if (!user.getPassword().equals(password.trim())) {
                return new Response("Invalid credentials", Status.BAD_REQUEST);
            }

            HashMap<String, Object> data = storage.serializeUser(user);
            return new Response("Login successful", Status.OK, data);
        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
