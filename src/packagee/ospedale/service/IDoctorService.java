/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.ospedale.service;

import packagee.ospedale.controller.utils.Response;

/**
 * Interface para el servicio de doctores.
 */
public interface IDoctorService {

    Response registerDoctor(
            String id,
            String username,
            String password,
            String confirmPassword,
            String firstname,
            String lastname,
            String licence,
            String office,
            String specialty
    );

    Response updateDoctor(
            String idStr,
            String username,
            String password,
            String confirmPassword,
            String firstname,
            String lastname,
            String licence,
            String office,
            String specialty
    );

    Response getDoctorInfo(String idStr);

    Response getAllDoctors();
}
