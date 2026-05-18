/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.ospedale.controller;
import packagee.ospedale.controller.utils.Response;
import packagee.ospedale.controller.utils.Status;
import packagee.ospedale.model.*;
import packagee.ospedale.model.storage.Storage;
import java.util.HashMap;
/**
 *
 * @author isaac
 */
public class AuthController {
    public static Response login(String username, String password) {
        try {
            if (username.trim().isEmpty())
                return new Response("Username must not be empty", Status.BAD_REQUEST);
            if (password.trim().isEmpty())
                return new Response("Password must not be empty", Status.BAD_REQUEST);

            Storage storage = Storage.getInstance();
            User user = storage.getUserByUsername(username.trim());

            if (user == null)
                return new Response("User not found", Status.NOT_FOUND);
            if (!user.getPassword().equals(password.trim()))
                return new Response("Invalid credentials", Status.BAD_REQUEST);

            HashMap<String, Object> data = storage.serializeUser(user);
            return new Response("Login successful", Status.OK, data);

        } catch (Exception ex) {
            return new Response("Unexpected error", Status.INTERNAL_SERVER_ERROR);
        }
    }
}
