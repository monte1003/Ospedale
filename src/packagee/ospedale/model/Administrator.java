package packagee.ospedale.model;

/**
 * Representa a un usuario administrador del sistema.
 */
public class Administrator extends User {

    public Administrator(long id, String username, String firstname, String lastname, String password) {
        super(id, username, firstname, lastname, password);
    }
}
