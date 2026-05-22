package packagee.ospedale.controller.utils;

import java.util.HashMap;

/**
 * Representa la respuesta estandar entre servicios, controladores y vistas.
 */
public class Response {

    private final String message;
    private final int status;
    private final HashMap<String, Object> data;

    public Response(String message, int status) {
        this.message = message;
        this.status = status;
        this.data = null;
    }

    public Response(String message, int status, HashMap<String, Object> data) {
        this.message = message;
        this.status = status;
        this.data = data;
    }

    public String getMessage() { return message; }

    public int getStatus()     { return status; }

    public HashMap<String, Object> getData() { return data; }
}
