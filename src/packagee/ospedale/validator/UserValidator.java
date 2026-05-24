/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.ospedale.validator;

import packagee.ospedale.controller.utils.Response;
import packagee.ospedale.controller.utils.Status;

/**
 * Reune validaciones comunes para cualquier tipo de usuario.
 */
public final class UserValidator {

    private UserValidator() {
    }

    public static Response validateUserId(String id) {
        if (id == null || !id.trim().matches("^[1-9]\\d{11}$")) {
            return new Response("ID must be exactly 12 digits and greater than 0", Status.BAD_REQUEST);
        }

        return null;
    }

    public static Response validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            return new Response("Username must not be empty", Status.BAD_REQUEST);
        }

        return null;
    }

    public static Response validatePassword(String password, String confirmPassword) {
        if (password == null || password.isBlank()) {
            return new Response("Password must not be empty", Status.BAD_REQUEST);
        }

        if (!password.equals(confirmPassword)) {
            return new Response("Passwords do not match", Status.BAD_REQUEST);
        }

        return null;
    }

    public static Response validateName(String label, String value) {
        if (value == null || value.trim().isEmpty()) {
            return new Response(label + " must not be empty", Status.BAD_REQUEST);
        }

        return null;
    }
}
