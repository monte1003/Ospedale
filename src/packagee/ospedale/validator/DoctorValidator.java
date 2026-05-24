/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packagee.ospedale.validator;

import packagee.ospedale.controller.utils.Response;
import packagee.ospedale.controller.utils.Status;

/**
 * Centraliza las reglas de validacion especificas para doctores.
 */
public final class DoctorValidator {

    private DoctorValidator() {
    }

    public static Response validateLicence(String licence) {
        if (licence == null || !licence.trim().matches("L-\\d{10} MTL")) {
            return new Response("Licence must follow format L-XXXXXXXXXX MTL", Status.BAD_REQUEST);
        }

        return null;
    }

    public static Response validateOffice(String office) {
        if (office == null || !office.trim().matches("O-\\d{3}")) {
            return new Response("Office must follow format O-XXX", Status.BAD_REQUEST);
        }

        return null;
    }
}
