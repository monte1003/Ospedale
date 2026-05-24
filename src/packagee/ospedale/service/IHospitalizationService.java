/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.ospedale.service;

import packagee.ospedale.controller.utils.Response;

/**
 * Interface para el servicio de hospitalizaciones.
 */
public interface IHospitalizationService {

    Response requestHospitalization(
            String patientIdStr,
            String doctorIdStr,
            String date,
            String roomType,
            String reason,
            String observations
    );

    Response acceptHospitalization(String hospitalizationId);

    Response cancelHospitalization(String hospitalizationId);

    Response getPatientHospitalizations(String patientIdStr);

    Response getRequestedHospitalizations();

    Response hospitalizeFromAppointment(
            String appointmentId,
            String date,
            String reason,
            String observations
    );
}
