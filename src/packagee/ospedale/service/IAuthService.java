package packagee.ospedale.service;

import packagee.ospedale.controller.utils.Response;

/**
 * Interface para el servicio de autenticacion.
 */
public interface IAuthService {

    Response login(String username, String password);
}
