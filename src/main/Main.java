package main;

import com.formdev.flatlaf.FlatDarkLaf;
import packagee.ospedale.view.Login;

/**
 * Punto de entrada de la aplicacion.
 */
public class Main {

    public static void main(String[] args) {
        System.setProperty("flatlaf.useNativeLibrary", "false");

        try {
            javax.swing.UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize LaF");
        }

        java.awt.EventQueue.invokeLater(() -> new Login().setVisible(true));
    }
}
