package packagee.ospedale.controller;

import packagee.ospedale.controller.utils.Response;
import packagee.ospedale.service.AuthService;

/**
 * Expone las operaciones de autenticacion a la capa de vista.
 */
public class AuthController {

    public static Response login(String username, String password) {
        return AuthService.login(username, password);
    }
}
