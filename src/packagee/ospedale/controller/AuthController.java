package packagee.ospedale.controller;

import packagee.ospedale.controller.utils.Response;
import packagee.ospedale.service.IAuthService;

/**
 * Expone las operaciones de autenticacion a la capa de vista.
 */
public class AuthController implements IAuthController {

    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @Override
    public Response login(String username, String password) {
        return authService.login(username, password);
    }
}
