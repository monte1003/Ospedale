/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.ospedale.service;

import packagee.ospedale.controller.utils.Response;

/**
 * Interface para el servicio de pacientes.
 */
public interface IPatientService {

    Response registerPatient(
            String id,
            String username,
            String password,
            String confirmPassword,
            String firstname,
            String lastname,
            String email,
            String birthdate,
            String gender,
            String phone,
            String address
    );

    Response updatePatient(
            String idStr,
            String username,
            String password,
            String confirmPassword,
            String firstname,
            String lastname,
            String email,
            String birthdate,
            String gender,
            String phone,
            String address
    );

    Response getPatientInfo(String idStr);

    Response getAllPatients();
}
