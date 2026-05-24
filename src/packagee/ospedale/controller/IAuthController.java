package packagee.ospedale.controller;

import packagee.ospedale.controller.utils.Response;

/**
 * Interface para el controlador de autenticacion.
 */
public interface IAuthController {

    Response login(String username, String password);
}
