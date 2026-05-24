package packagee.ospedale.controller.utils;

/**
 * Constantes de codigos de estado usadas por la aplicacion.
 */
public final class Status {

    private Status() {
    }

    public static final int OK = 200;
    public static final int CREATED = 201;
    public static final int NO_CONTENT = 204;
    // Client error
    public static final int BAD_REQUEST = 400;
    public static final int NOT_FOUND = 404;
    // Server error
    public static final int INTERNAL_SERVER_ERROR = 500;
    public static final int NOT_IMPLEMENTED = 501;
}
