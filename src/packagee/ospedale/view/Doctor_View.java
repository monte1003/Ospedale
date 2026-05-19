/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package packagee.ospedale.view;

import packagee.ospedale.controller.DoctorController;
import packagee.ospedale.controller.MedicalServiceController;
import packagee.ospedale.controller.PatientController;
import packagee.ospedale.controller.utils.Response;
import packagee.ospedale.controller.utils.Status;
import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author jjlora
 * @author edangulo
 */
public class Doctor_View extends javax.swing.JFrame {

    private int x, y;
    private long doctorId;
    private boolean fromAdmin;

    public Doctor_View(long doctorId, boolean fromAdmin) {
        initComponents();
        this.doctorId = doctorId;
        this.fromAdmin = fromAdmin;
        this.setBackground(new Color(0, 0, 0, 0));
        this.setLocationRelativeTo(null);
        back_button.setVisible(fromAdmin);
        loadDoctorInfo();
        loadAppointmentsComboBoxes();
        loadPatientsComboBox();
        loadHospitalizationsComboBox();
    }

    private void loadDoctorInfo() {
        Response response = DoctorController.getDoctorInfo("" + doctorId);
        if (response.getStatus() == Status.OK) {
            java.util.HashMap<String, Object> data = response.getData();
            fist_name_input.setText((String) data.get("firstname"));
            last_name_input.setText((String) data.get("lastname"));
            license_numer_input.setText((String) data.get("licenceNumber"));
            office_input.setText((String) data.get("assignedOffice"));
            select_specialty.setSelectedItem(data.get("specialty"));
        }
    }

    private void loadAppointmentsComboBoxes() {
        Response response = MedicalServiceController.getDoctorAppointments("" + doctorId, false);
        if (response.getStatus() == Status.OK) {
            select_appointment.removeAllItems();
            select_appointment_2.removeAllItems();
            select_appointment_complete_medical.removeAllItems();
            select_appointment_id.removeAllItems();
            ArrayList<java.util.HashMap<String, Object>> appointments =
                (ArrayList<java.util.HashMap<String, Object>>) response.getData().get("appointments");
            for (java.util.HashMap<String, Object> a : appointments) {
                String id = (String) a.get("id");
                select_appointment.addItem(id);
                select_appointment_2.addItem(id);
                select_appointment_complete_medical.addItem(id);
                select_appointment_id.addItem(id);
            }
        }
    }

    private void loadPatientsComboBox() {
        Response response = PatientController.getAllPatients();
        if (response.getStatus() == Status.OK) {
            select_patient.removeAllItems();
            ArrayList<java.util.HashMap<String, Object>> patients =
                (ArrayList<java.util.HashMap<String, Object>>) response.getData().get("patients");
            for (java.util.HashMap<String, Object> p : patients) {
                select_patient.addItem("" + p.get("id"));
            }
        }
    }

    private void loadHospitalizationsComboBox() {
        select_requests.removeAllItems();
    }

    private void loadAppointmentsTable(boolean pendingOnly) {
        Response response = MedicalServiceController.getDoctorAppointments("" + doctorId, pendingOnly);
        if (response.getStatus() == Status.OK) {
            ArrayList<java.util.HashMap<String, Object>> appointments =
                (ArrayList<java.util.HashMap<String, Object>>) response.getData().get("appointments");
            DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
            model.setRowCount(0);
            for (java.util.HashMap<String, Object> a : appointments) {
                model.addRow(new Object[]{
                    a.get("id"),
                    a.get("datetime"),
                    a.get("patientName"),
                    a.get("specialty"),
                    a.get("status")
                });
            }
        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel1 = new packagee.ospedale.view.PanelRound();
        panel2 = new packagee.ospedale.view.PanelRound();
        x_button = new javax.swing.JButton();
        doctor_view_label = new javax.swing.JLabel();
        back_button = new javax.swing.JButton();
        option_menu = new javax.swing.JTabbedPane();
        appointments_visualization = new javax.swing.JPanel();
        total_appointments_button = new javax.swing.JRadioButton();
        table = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        pending_appointments_button = new javax.swing.JRadioButton();
        logout_button = new javax.swing.JButton();
        history_patient = new javax.swing.JPanel();
        select_patient = new javax.swing.JComboBox<>();
        patient_label = new javax.swing.JLabel();
        table_option2 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        search_button = new javax.swing.JButton();
        modify_info = new javax.swing.JPanel();
        firstname_label = new javax.swing.JLabel();
        fist_name_input = new javax.swing.JTextField();
        lastname_label = new javax.swing.JLabel();
        last_name_input = new javax.swing.JTextField();
        specialty_label = new javax.swing.JLabel();
        licensenumer_label = new javax.swing.JLabel();
        license_numer_input = new javax.swing.JTextField();
        office_label = new javax.swing.JLabel();
        user_input = new javax.swing.JTextField();
        user_label = new javax.swing.JLabel();
        office_input = new javax.swing.JTextField();
        password_input = new javax.swing.JTextField();
        password_label = new javax.swing.JLabel();
        confirmation_label = new javax.swing.JLabel();
        confirmation_input = new javax.swing.JTextField();
        select_specialty = new javax.swing.JComboBox<>();
        save_button = new javax.swing.JButton();
        request = new javax.swing.JPanel();
        appointment_label = new javax.swing.JLabel();
        accept_medical_label = new javax.swing.JLabel();
        select_appointment = new javax.swing.JComboBox<>();
        separator1 = new javax.swing.JSeparator();
        accept_label = new javax.swing.JButton();
        reschedule_label = new javax.swing.JLabel();
        appointment_label2 = new javax.swing.JLabel();
        select_appointment_2 = new javax.swing.JComboBox<>();
        accept_button_reschedule_medical = new javax.swing.JButton();
        new_time_label = new javax.swing.JLabel();
        new_time_appointment_label = new javax.swing.JTextField();
        reason_label = new javax.swing.JLabel();
        reason_appointment_input = new javax.swing.JTextField();
        separator2 = new javax.swing.JSeparator();
        complete_label = new javax.swing.JLabel();
        appointment_label3 = new javax.swing.JLabel();
        select_appointment_complete_medical = new javax.swing.JComboBox<>();
        diagnosis_label = new javax.swing.JLabel();
        observations_label = new javax.swing.JLabel();
        recomended_label = new javax.swing.JLabel();
        follow_up_label = new javax.swing.JLabel();
        complete_button = new javax.swing.JButton();
        hospitalization_label = new javax.swing.JLabel();
        reasons_hospitalization_label = new javax.swing.JLabel();
        date_entry_label = new javax.swing.JLabel();
        date_entry_input = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        estimated_duration_input = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        observations_output = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        generate_button = new javax.swing.JButton();
        select_requests = new javax.swing.JComboBox<>();
        requests_button = new javax.swing.JRadioButton();
        patient_id_button = new javax.swing.JRadioButton();
        diagnosis_output = new javax.swing.JScrollPane();
        jTextArea5 = new javax.swing.JTextArea();
        observations_medical_appointment_output = new javax.swing.JScrollPane();
        jTextArea6 = new javax.swing.JTextArea();
        recommended_treatment_output = new javax.swing.JScrollPane();
        jTextArea7 = new javax.swing.JTextArea();
        follow_up_indication = new javax.swing.JScrollPane();
        jTextArea8 = new javax.swing.JTextArea();
        separator4 = new javax.swing.JSeparator();
        cancel_button = new javax.swing.JButton();
        select_patient_id = new javax.swing.JComboBox<>();
        reasons_for_hospitalazation_output = new javax.swing.JScrollPane();
        jTextArea9 = new javax.swing.JTextArea();
        medications = new javax.swing.JPanel();
        appointment_id_label = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        medication_name_input = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        dose_input = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        administration_route_label = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        frecuency_label = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        treatment_duration_label = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        additional_instructions_label = new javax.swing.JTextField();
        output1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        add_button = new javax.swing.JButton();
        prescribe_button = new javax.swing.JButton();
        select_appointment_id = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        panel1.setRadius(50);

        panel2.setRadius(50);
        panel2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                panel2MouseDragged(evt);
            }
        });
        panel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panel2MousePressed(evt);
            }
        });

        x_button.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        x_button.setText("X");
        x_button.setBorderPainted(false);
        x_button.setContentAreaFilled(false);
        x_button.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        x_button.setFocusable(false);
        x_button.setRequestFocusEnabled(false);
        x_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                x_buttonActionPerformed(evt);
            }
        });

        doctor_view_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        doctor_view_label.setText("DOCTOR VIEW");

        back_button.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        back_button.setText("Back");
        back_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                back_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(doctor_view_label)
                .addGap(32, 32, 32)
                .addComponent(back_button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(x_button)
                .addGap(19, 19, 19))
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(x_button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(doctor_view_label, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(back_button))
        );

        total_appointments_button.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        total_appointments_button.setText("Total appointments");
        total_appointments_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                total_appointments_buttonActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Date", "Patient", "Specialty", "Type", "Status"
            }
        ));
        table.setViewportView(jTable2);

        pending_appointments_button.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        pending_appointments_button.setText("Pending appointments");
        pending_appointments_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pending_appointments_buttonActionPerformed(evt);
            }
        });

        logout_button.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        logout_button.setText("Logout");
        logout_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logout_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout appointments_visualizationLayout = new javax.swing.GroupLayout(appointments_visualization);
        appointments_visualization.setLayout(appointments_visualizationLayout);
        appointments_visualizationLayout.setHorizontalGroup(
            appointments_visualizationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(appointments_visualizationLayout.createSequentialGroup()
                .addGroup(appointments_visualizationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(logout_button)
                    .addGroup(appointments_visualizationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(appointments_visualizationLayout.createSequentialGroup()
                            .addGap(16, 16, 16)
                            .addComponent(total_appointments_button)
                            .addGap(18, 18, 18)
                            .addComponent(pending_appointments_button))
                        .addGroup(appointments_visualizationLayout.createSequentialGroup()
                            .addGap(108, 108, 108)
                            .addComponent(table, javax.swing.GroupLayout.PREFERRED_SIZE, 1035, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(152, Short.MAX_VALUE))
        );
        appointments_visualizationLayout.setVerticalGroup(
            appointments_visualizationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(appointments_visualizationLayout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(appointments_visualizationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(total_appointments_button)
                    .addComponent(pending_appointments_button))
                .addGap(18, 18, 18)
                .addComponent(table, javax.swing.GroupLayout.PREFERRED_SIZE, 504, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addComponent(logout_button)
                .addGap(23, 23, 23))
        );

        option_menu.addTab("Appointments visualization", appointments_visualization);

        select_patient.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        select_patient.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one" }));

        patient_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        patient_label.setText("Patient");

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Date", "Doctor", "Specialty", "Type", "Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table_option2.setViewportView(jTable3);

        search_button.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        search_button.setText("Search");
        search_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                search_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout history_patientLayout = new javax.swing.GroupLayout(history_patient);
        history_patient.setLayout(history_patientLayout);
        history_patientLayout.setHorizontalGroup(
            history_patientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(history_patientLayout.createSequentialGroup()
                .addGroup(history_patientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(history_patientLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(patient_label)
                        .addGap(18, 18, 18)
                        .addComponent(select_patient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(history_patientLayout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addComponent(table_option2, javax.swing.GroupLayout.PREFERRED_SIZE, 1133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(99, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, history_patientLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(search_button)
                .addGap(601, 601, 601))
        );
        history_patientLayout.setVerticalGroup(
            history_patientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(history_patientLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(history_patientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(patient_label)
                    .addComponent(select_patient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(table_option2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(search_button)
                .addContainerGap(67, Short.MAX_VALUE))
        );

        option_menu.addTab("History Appointments of a patient", history_patient);

        firstname_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        firstname_label.setText("Firstname");

        fist_name_input.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        lastname_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lastname_label.setText("Lastname");

        last_name_input.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        specialty_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        specialty_label.setText("Specialty");

        licensenumer_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        licensenumer_label.setText("License Number");

        license_numer_input.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        office_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        office_label.setText("Assigned office");

        user_input.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        user_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        user_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        user_label.setText("User");

        office_input.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        password_input.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        password_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        password_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        password_label.setText("Password");

        confirmation_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        confirmation_label.setText("Password confirmation");

        confirmation_input.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        select_specialty.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        select_specialty.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one", "General Medicine", "Cardiology", "Pediatrics", "Neurology", "Traumatology & Orthopedics", "Gynecology & Obstetrics", "Dermatology", "Psychiatry", "Oncology", "Ophthalmology", "Internal Medicine" }));

        save_button.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        save_button.setText("Save");
        save_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                save_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout modify_infoLayout = new javax.swing.GroupLayout(modify_info);
        modify_info.setLayout(modify_infoLayout);
        modify_infoLayout.setHorizontalGroup(
            modify_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modify_infoLayout.createSequentialGroup()
                .addGroup(modify_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(modify_infoLayout.createSequentialGroup()
                        .addGap(211, 211, 211)
                        .addComponent(firstname_label)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(fist_name_input, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lastname_label)
                        .addGap(18, 18, 18)
                        .addComponent(last_name_input, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(specialty_label)
                        .addGap(18, 18, 18)
                        .addComponent(select_specialty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(modify_infoLayout.createSequentialGroup()
                        .addGap(351, 351, 351)
                        .addComponent(licensenumer_label)
                        .addGap(18, 18, 18)
                        .addComponent(license_numer_input, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(office_label)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(office_input, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(modify_infoLayout.createSequentialGroup()
                        .addGap(558, 558, 558)
                        .addGroup(modify_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(password_input, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(modify_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(user_input, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
                                .addComponent(user_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(password_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(modify_infoLayout.createSequentialGroup()
                        .addGap(521, 521, 521)
                        .addComponent(confirmation_label))
                    .addGroup(modify_infoLayout.createSequentialGroup()
                        .addGap(576, 576, 576)
                        .addComponent(save_button))
                    .addGroup(modify_infoLayout.createSequentialGroup()
                        .addGap(561, 561, 561)
                        .addComponent(confirmation_input, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(269, Short.MAX_VALUE))
        );
        modify_infoLayout.setVerticalGroup(
            modify_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(modify_infoLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addGroup(modify_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstname_label)
                    .addComponent(fist_name_input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lastname_label)
                    .addComponent(last_name_input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(specialty_label)
                    .addComponent(select_specialty, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(modify_infoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(licensenumer_label)
                    .addComponent(license_numer_input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(office_input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(office_label))
                .addGap(30, 30, 30)
                .addComponent(user_label)
                .addGap(18, 18, 18)
                .addComponent(user_input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(password_label)
                .addGap(27, 27, 27)
                .addComponent(password_input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(confirmation_label)
                .addGap(18, 18, 18)
                .addComponent(confirmation_input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(save_button)
                .addContainerGap(161, Short.MAX_VALUE))
        );

        option_menu.addTab("Modify info", modify_info);

        appointment_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        appointment_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        appointment_label.setText("Appointment ID");

        accept_medical_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        accept_medical_label.setText("Accept medical appointment");

        select_appointment.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        select_appointment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one" }));

        separator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        accept_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        accept_label.setText("Accept");
        accept_label.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accept_labelActionPerformed(evt);
            }
        });

        reschedule_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        reschedule_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        reschedule_label.setText("Reschedule medical appointment");

        appointment_label2.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        appointment_label2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        appointment_label2.setText("Appointment");

        select_appointment_2.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        select_appointment_2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one" }));

        accept_button_reschedule_medical.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        accept_button_reschedule_medical.setText("Accept");
        accept_button_reschedule_medical.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accept_button_reschedule_medicalActionPerformed(evt);
            }
        });

        new_time_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        new_time_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        new_time_label.setText("New time appointment");

        new_time_appointment_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        new_time_appointment_label.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                new_time_appointment_labelActionPerformed(evt);
            }
        });

        reason_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        reason_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        reason_label.setText("Reason for appointment");

        reason_appointment_input.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        separator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        complete_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        complete_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        complete_label.setText("Complete medical appointment");

        appointment_label3.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        appointment_label3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        appointment_label3.setText("Appointment");

        select_appointment_complete_medical.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        select_appointment_complete_medical.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one" }));

        diagnosis_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        diagnosis_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        diagnosis_label.setText("Diagnosis");

        observations_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        observations_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        observations_label.setText("Observations");

        recomended_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        recomended_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        recomended_label.setText("Recommended treatment");

        follow_up_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        follow_up_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        follow_up_label.setText("Follow-up indication");

        complete_button.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        complete_button.setText("Complete");
        complete_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                complete_buttonActionPerformed(evt);
            }
        });

        hospitalization_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        hospitalization_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hospitalization_label.setText("Hospitalization");

        reasons_hospitalization_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        reasons_hospitalization_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        reasons_hospitalization_label.setText("Reason for hospitalization");

        date_entry_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        date_entry_label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        date_entry_label.setText("Date of entry");

        date_entry_input.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel29.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("Estimated duration");

        estimated_duration_input.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel30.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("Observations");

        jTextArea1.setColumns(20);
        jTextArea1.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jTextArea1.setRows(5);
        observations_output.setViewportView(jTextArea1);

        generate_button.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        generate_button.setText("Generate");
        generate_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generate_buttonActionPerformed(evt);
            }
        });

        select_requests.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        select_requests.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one" }));

        requests_button.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        requests_button.setText("Requests");

        patient_id_button.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        patient_id_button.setText("Patient ID");
        patient_id_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                patient_id_buttonActionPerformed(evt);
            }
        });

        jTextArea5.setColumns(20);
        jTextArea5.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jTextArea5.setRows(5);
        diagnosis_output.setViewportView(jTextArea5);

        jTextArea6.setColumns(20);
        jTextArea6.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jTextArea6.setRows(5);
        observations_medical_appointment_output.setViewportView(jTextArea6);

        jTextArea7.setColumns(20);
        jTextArea7.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jTextArea7.setRows(5);
        recommended_treatment_output.setViewportView(jTextArea7);

        jTextArea8.setColumns(20);
        jTextArea8.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jTextArea8.setRows(5);
        follow_up_indication.setViewportView(jTextArea8);

        separator4.setOrientation(javax.swing.SwingConstants.VERTICAL);

        cancel_button.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        cancel_button.setText("Cancel");
        cancel_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancel_buttonActionPerformed(evt);
            }
        });

        select_patient_id.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        select_patient_id.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one" }));

        jTextArea9.setColumns(20);
        jTextArea9.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jTextArea9.setRows(5);
        reasons_for_hospitalazation_output.setViewportView(jTextArea9);

        javax.swing.GroupLayout requestLayout = new javax.swing.GroupLayout(request);
        request.setLayout(requestLayout);
        requestLayout.setHorizontalGroup(
            requestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(requestLayout.createSequentialGroup()
                .addGroup(requestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, requestLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(requestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, requestLayout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addGroup(requestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, requestLayout.createSequentialGroup()
                                        .addComponent(accept_label)
                                        .addGap(87, 87, 87))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, requestLayout.createSequentialGroup()
                                        .addComponent(select_appointment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(67, 67, 67))))
                            .addComponent(appointment_label, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(separator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1))
                    .addGroup(requestLayout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(accept_medical_label)
                        .addGap(22, 22, 22)))
                .addGroup(requestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(reschedule_label, javax.swing.GroupLayout.PREFERRED_SIZE, 306, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(requestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(appointment_label2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(new_time_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(reason_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(requestLayout.createSequentialGroup()
                            .addGroup(requestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(requestLayout.createSequentialGroup()
                                    .addGap(90, 90, 90)
                                    .addComponent(select_appointment_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(requestLayout.createSequentialGroup()
                                    .addGap(99, 99, 99)
                                    .addComponent(new_time_appointment_label, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(requestLayout.createSequentialGroup()
                                    .addGap(98, 98, 98)
                                    .addComponent(reason_appointment_input, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(requestLayout.createSequentialGroup()
                                    .addGap(112, 112, 112)
                                    .addComponent(accept_button_reschedule_medical)))
                            .addGap(91, 91, 91))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(separator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(requestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(requestLayout.createSequentialGroup()
                        .addGap(112, 112, 112)
                        .addComponent(complete_button)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(requestLayout.createSequentialGroup()
                        .addGroup(requestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(requestLayout.createSequentialGroup()
                                .addGroup(requestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, requestLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(requestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(appointment_label3, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(complete_label, javax.swing.GroupLayout.PREFERRED_SIZE, 307, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(requestLayout.createSequentialGroup()
                                        .addGap(99, 99, 99)
                                        .addComponent(select_appointment_complete_medical, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 25, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, requestLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(requestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(diagnosis_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(observations_label, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(requestLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(requestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(follow_up_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(recomended_label, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(requestLayout.createSequentialGroup()
                                .addGroup(requestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(requestLayout.createSequentialGroup()
                                        .addGap(42, 42, 42)
                                        .addComponent(diagnosis_output, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(requestLayout.createSequentialGroup()
                                        .addGap(41, 41, 41)
                                        .addComponent(observations_medical_appointment_output, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(requestLayout.createSequentialGroup()
                                .addGroup(requestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(requestLayout.createSequentialGroup()
                                        .addGap(42, 42, 42)
                                        .addComponent(recommended_treatment_output, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(requestLayout.createSequentialGroup()
                                        .addGap(43, 43, 43)
                                        .addComponent(follow_up_indication, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(separator4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(requestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(requestLayout.createSequentialGroup()
                        .addGroup(requestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(requestLayout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addGroup(requestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(hospitalization_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(date_entry_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel29, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel30, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(requestLayout.createSequentialGroup()
                                .addGap(121, 121, 121)
                                .addGroup(requestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(date_entry_input, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(estimated_duration_input, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                    .addGroup(requestLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addGroup(requestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(requestLayout.createSequentialGroup()
                                .addComponent(cancel_button)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(generate_button))
                            .addComponent(observations_output, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(56, Short.MAX_VALUE))
                    .addGroup(requestLayout.createSequentialGroup()
                        .addGroup(requestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(requestLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(select_requests, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(requestLayout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(requests_button)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(requestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, requestLayout.createSequentialGroup()
                                .addComponent(patient_id_button, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(19, 19, 19))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, requestLayout.createSequentialGroup()
                                .addComponent(select_patient_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(29, 29, 29))))
                    .addGroup(requestLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(reasons_hospitalization_label, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, requestLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(reasons_for_hospitalazation_output, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47))))
        );
        requestLayout.setVerticalGroup(
            requestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(separator1)
            .addGroup(requestLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(requestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(separator2)
                    .addGroup(requestLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(complete_label)
                        .addGap(10, 10, 10)
                        .addComponent(appointment_label3)
                        .addGap(18, 18, 18)
                        .addComponent(select_appointment_complete_medical, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(diagnosis_label)
                        .addGap(18, 18, 18)
                        .addComponent(diagnosis_output, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(observations_label)
                        .addGap(18, 18, 18)
                        .addComponent(observations_medical_appointment_output, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(recomended_label)
                        .addGap(18, 18, 18)
                        .addComponent(recommended_treatment_output, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(follow_up_label)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(follow_up_indication, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(complete_button)
                        .addGap(12, 12, 12))
                    .addGroup(requestLayout.createSequentialGroup()
                        .addGroup(requestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(requestLayout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(accept_medical_label)
                                .addGap(18, 18, 18)
                                .addComponent(appointment_label)
                                .addGap(18, 18, 18)
                                .addComponent(select_appointment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(31, 31, 31)
                                .addComponent(accept_label))
                            .addGroup(requestLayout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(reschedule_label)
                                .addGap(18, 18, 18)
                                .addComponent(appointment_label2)
                                .addGap(18, 18, 18)
                                .addComponent(select_appointment_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(new_time_label)
                                .addGap(18, 18, 18)
                                .addComponent(new_time_appointment_label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(reason_label)
                                .addGap(18, 18, 18)
                                .addComponent(reason_appointment_input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(30, 30, 30)
                                .addComponent(accept_button_reschedule_medical)))
                        .addGap(18, 18, Short.MAX_VALUE))))
            .addGroup(requestLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(hospitalization_label)
                .addGap(18, 18, 18)
                .addGroup(requestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(requests_button)
                    .addComponent(patient_id_button))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(requestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(select_requests, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(select_patient_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(reasons_hospitalization_label)
                .addGap(16, 16, 16)
                .addComponent(reasons_for_hospitalazation_output, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(date_entry_label)
                .addGap(18, 18, 18)
                .addComponent(date_entry_input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel29)
                .addGap(18, 18, 18)
                .addComponent(estimated_duration_input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel30)
                .addGap(18, 18, 18)
                .addComponent(observations_output, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(requestLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(generate_button)
                    .addComponent(cancel_button))
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(separator4, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        option_menu.addTab("Request/Appointments", request);

        appointment_id_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        appointment_id_label.setText("Appointment ID");

        jLabel32.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel32.setText("Medication name");

        medication_name_input.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel33.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel33.setText("Dose");

        dose_input.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel34.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel34.setText("Administration route");

        administration_route_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel35.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel35.setText("Frecuency");

        frecuency_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel36.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel36.setText("Treatment duration");

        treatment_duration_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jLabel37.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        jLabel37.setText("Additional instructions");

        additional_instructions_label.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Appointment ID", "Medication name", "Dose", "Administration route", "Treatment duration", "Additional instructions", "Frecuency"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        output1.setViewportView(jTable1);

        add_button.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        add_button.setText("Add");
        add_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_buttonActionPerformed(evt);
            }
        });

        prescribe_button.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        prescribe_button.setText("Prescribe");
        prescribe_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prescribe_buttonActionPerformed(evt);
            }
        });

        select_appointment_id.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        select_appointment_id.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one" }));

        javax.swing.GroupLayout medicationsLayout = new javax.swing.GroupLayout(medications);
        medications.setLayout(medicationsLayout);
        medicationsLayout.setHorizontalGroup(
            medicationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(medicationsLayout.createSequentialGroup()
                .addGroup(medicationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(medicationsLayout.createSequentialGroup()
                        .addGap(62, 62, 62)
                        .addGroup(medicationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(output1, javax.swing.GroupLayout.PREFERRED_SIZE, 1125, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(medicationsLayout.createSequentialGroup()
                                .addGroup(medicationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(medicationsLayout.createSequentialGroup()
                                        .addComponent(appointment_id_label)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(select_appointment_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(9, 9, 9)
                                        .addComponent(jLabel32))
                                    .addGroup(medicationsLayout.createSequentialGroup()
                                        .addComponent(jLabel36)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(treatment_duration_label, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(medicationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(medicationsLayout.createSequentialGroup()
                                        .addComponent(jLabel37)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(additional_instructions_label, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel35)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(frecuency_label, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(medicationsLayout.createSequentialGroup()
                                        .addComponent(medication_name_input, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel33)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(dose_input, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel34)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(administration_route_label, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(add_button))))
                    .addGroup(medicationsLayout.createSequentialGroup()
                        .addGap(583, 583, 583)
                        .addComponent(prescribe_button)))
                .addContainerGap(108, Short.MAX_VALUE))
        );
        medicationsLayout.setVerticalGroup(
            medicationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(medicationsLayout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(medicationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(appointment_id_label)
                    .addComponent(jLabel32)
                    .addComponent(medication_name_input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33)
                    .addComponent(dose_input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34)
                    .addComponent(administration_route_label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(add_button)
                    .addComponent(select_appointment_id, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(medicationsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel36)
                    .addComponent(treatment_duration_label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel37)
                    .addComponent(additional_instructions_label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35)
                    .addComponent(frecuency_label, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addComponent(output1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addComponent(prescribe_button)
                .addContainerGap(64, Short.MAX_VALUE))
        );

        option_menu.addTab("Prescribe medications", medications);

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(option_menu))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addComponent(panel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(option_menu))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void panel2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel2MousePressed
        x = evt.getX();
        y = evt.getY();
    }//GEN-LAST:event_panel2MousePressed

    private void panel2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panel2MouseDragged
        this.setLocation(this.getLocation().x + evt.getX() - x, this.getLocation().y + evt.getY() - y);
    }//GEN-LAST:event_panel2MouseDragged

    private void x_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_x_buttonActionPerformed
        System.exit(0);
    }//GEN-LAST:event_x_buttonActionPerformed

    private void pending_appointments_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pending_appointments_buttonActionPerformed
        total_appointments_button.setSelected(false);
        loadAppointmentsTable(true);
    }//GEN-LAST:event_pending_appointments_buttonActionPerformed

    private void save_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_save_buttonActionPerformed
        String firstname = fist_name_input.getText();
        String lastname = last_name_input.getText();
        String username = user_input.getText();
        String password = password_input.getText();
        String confirmPassword = confirmation_input.getText();
        String licence = license_numer_input.getText();
        String office = office_input.getText();
        String specialty = (String) select_specialty.getSelectedItem();

        Response response = DoctorController.updateDoctor("" + doctorId, username,
                password, confirmPassword, firstname, lastname, licence, office, specialty);

        if (response.getStatus() >= 500) {
            JOptionPane.showMessageDialog(null, response.getMessage(), "Error " + response.getStatus(), JOptionPane.ERROR_MESSAGE);
        } else if (response.getStatus() >= 400) {
            JOptionPane.showMessageDialog(null, response.getMessage(), "Error " + response.getStatus(), JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, response.getMessage(), "Response Message", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_save_buttonActionPerformed

    private void logout_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logout_buttonActionPerformed
        Login login = new Login();
        this.setVisible(false);
        login.setVisible(true);
    }//GEN-LAST:event_logout_buttonActionPerformed

    private void back_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_back_buttonActionPerformed
        Admin_View admin = new Admin_View();
        this.setVisible(false);
        admin.setVisible(true);
    }//GEN-LAST:event_back_buttonActionPerformed

    private void cancel_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_buttonActionPerformed
        String hospitalizationId = (String) select_requests.getSelectedItem();
        if (hospitalizationId == null) {
            JOptionPane.showMessageDialog(null, "Please select a hospitalization", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Response response = MedicalServiceController.cancelHospitalization(hospitalizationId);
        if (response.getStatus() >= 500) {
            JOptionPane.showMessageDialog(null, response.getMessage(), "Error " + response.getStatus(), JOptionPane.ERROR_MESSAGE);
        } else if (response.getStatus() >= 400) {
            JOptionPane.showMessageDialog(null, response.getMessage(), "Error " + response.getStatus(), JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, response.getMessage(), "Response Message", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_cancel_buttonActionPerformed

    private void generate_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generate_buttonActionPerformed
        String hospitalizationId = (String) select_requests.getSelectedItem();
        if (hospitalizationId == null) {
            JOptionPane.showMessageDialog(null, "Please select a hospitalization", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Response response = MedicalServiceController.acceptHospitalization(hospitalizationId);
        if (response.getStatus() >= 500) {
            JOptionPane.showMessageDialog(null, response.getMessage(), "Error " + response.getStatus(), JOptionPane.ERROR_MESSAGE);
        } else if (response.getStatus() >= 400) {
            JOptionPane.showMessageDialog(null, response.getMessage(), "Error " + response.getStatus(), JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, response.getMessage(), "Response Message", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_generate_buttonActionPerformed

    private void search_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_search_buttonActionPerformed
        String patientId = (String) select_patient.getSelectedItem();
        if (patientId == null) {
            JOptionPane.showMessageDialog(null, "Please select a patient", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Response response = MedicalServiceController.getPatientAppointments(patientId);
        if (response.getStatus() == Status.OK) {
            ArrayList<java.util.HashMap<String, Object>> appointments =
                (ArrayList<java.util.HashMap<String, Object>>) response.getData().get("appointments");
            DefaultTableModel model = (DefaultTableModel) jTable3.getModel();
            model.setRowCount(0);
            for (java.util.HashMap<String, Object> a : appointments) {
                model.addRow(new Object[]{
                    a.get("id"),
                    a.get("datetime"),
                    a.get("doctorName"),
                    a.get("specialty"),
                    a.get("status")
                });
            }
        } else {
            JOptionPane.showMessageDialog(null, response.getMessage(), "Error " + response.getStatus(), JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_search_buttonActionPerformed

    private void total_appointments_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_total_appointments_buttonActionPerformed
        pending_appointments_button.setSelected(false);
        loadAppointmentsTable(false);
    }//GEN-LAST:event_total_appointments_buttonActionPerformed

    private void accept_labelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accept_labelActionPerformed
        String appointmentId = (String) select_appointment_2.getSelectedItem();
        String newTime = new_time_appointment_label.getText();
        String reason = reason_appointment_input.getText();

        Response response = MedicalServiceController.rescheduleAppointment(appointmentId, newTime, reason);

        if (response.getStatus() >= 500) {
            JOptionPane.showMessageDialog(null, response.getMessage(), "Error " + response.getStatus(), JOptionPane.ERROR_MESSAGE);
        } else if (response.getStatus() >= 400) {
            JOptionPane.showMessageDialog(null, response.getMessage(), "Error " + response.getStatus(), JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, response.getMessage(), "Response Message", JOptionPane.INFORMATION_MESSAGE);
            new_time_appointment_label.setText("");
            reason_appointment_input.setText("");
            loadAppointmentsComboBoxes();
            loadAppointmentsTable(false);   
        }
    }//GEN-LAST:event_accept_labelActionPerformed

    private void complete_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_complete_buttonActionPerformed
        String appointmentId = (String) select_appointment_complete_medical.getSelectedItem();
        if (appointmentId == null) {
            JOptionPane.showMessageDialog(null, "Please select an appointment", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }
        Response response = MedicalServiceController.completeAppointment(appointmentId);
        if (response.getStatus() >= 500) {
            JOptionPane.showMessageDialog(null, response.getMessage(), "Error " + response.getStatus(), JOptionPane.ERROR_MESSAGE);
        } else if (response.getStatus() >= 400) {
            JOptionPane.showMessageDialog(null, response.getMessage(), "Error " + response.getStatus(), JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, response.getMessage(), "Response Message", JOptionPane.INFORMATION_MESSAGE);
            loadAppointmentsComboBoxes();
            loadAppointmentsTable(false);
        }
    }//GEN-LAST:event_complete_buttonActionPerformed

    private void prescribe_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prescribe_buttonActionPerformed
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(null, "No medications to prescribe", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean allOk = true;
        for (int i = 0; i < model.getRowCount(); i++) {
            String appointmentId = (String) model.getValueAt(i, 0);
            String medicationName = (String) model.getValueAt(i, 1);
            double dose = Double.parseDouble((String) model.getValueAt(i, 2));
            String route = (String) model.getValueAt(i, 3);
            int duration = Integer.parseInt((String) model.getValueAt(i, 4));
            String instructions = (String) model.getValueAt(i, 5);
            int frecuency = Integer.parseInt((String) model.getValueAt(i, 6));

            Response response = MedicalServiceController.prescribeMedication(
                    appointmentId, medicationName, dose, route, duration, instructions, frecuency);

            if (response.getStatus() >= 400) {
                JOptionPane.showMessageDialog(null, response.getMessage(), "Error row " + (i+1), JOptionPane.WARNING_MESSAGE);
                allOk = false;
            }
        }

        if (allOk) {
            JOptionPane.showMessageDialog(null, "All medications prescribed successfully", "Response Message", JOptionPane.INFORMATION_MESSAGE);
            model.setRowCount(0);
        }
    }//GEN-LAST:event_prescribe_buttonActionPerformed

    private void add_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_buttonActionPerformed
        String appointmentId = (String) select_appointment_id.getSelectedItem();
        String medicationName = medication_name_input.getText();
        String dose = dose_input.getText();
        String route = administration_route_label.getText();
        String duration = treatment_duration_label.getText();
        String instructions = additional_instructions_label.getText();
        String frecuency = frecuency_label.getText();

        if (appointmentId == null || medicationName.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all fields", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.addRow(new Object[]{
            appointmentId, medicationName, dose, route, duration, instructions, frecuency
        });

        medication_name_input.setText("");
        dose_input.setText("");
        administration_route_label.setText("");
        treatment_duration_label.setText("");
        additional_instructions_label.setText("");
        frecuency_label.setText("");
    }//GEN-LAST:event_add_buttonActionPerformed

    private void accept_button_reschedule_medicalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accept_button_reschedule_medicalActionPerformed
        String appointmentId = (String) select_appointment_2.getSelectedItem();
        String newTime = new_time_appointment_label.getText();
        String reason = reason_appointment_input.getText();

        Response response = MedicalServiceController.rescheduleAppointment(appointmentId, newTime, reason);

        if (response.getStatus() >= 500) {
            JOptionPane.showMessageDialog(null, response.getMessage(), "Error " + response.getStatus(), JOptionPane.ERROR_MESSAGE);
        } else if (response.getStatus() >= 400) {
            JOptionPane.showMessageDialog(null, response.getMessage(), "Error " + response.getStatus(), JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, response.getMessage(), "Response Message", JOptionPane.INFORMATION_MESSAGE);
            new_time_appointment_label.setText("");
            reason_appointment_input.setText("");
            loadAppointmentsComboBoxes();
            loadAppointmentsTable(false);
        }
    }//GEN-LAST:event_accept_button_reschedule_medicalActionPerformed

    private void new_time_appointment_labelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_new_time_appointment_labelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_new_time_appointment_labelActionPerformed

    private void patient_id_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_patient_id_buttonActionPerformed
        String patientId = (String) select_patient_id.getSelectedItem();
        if (patientId == null) return;
        Response response = MedicalServiceController.getPatientHospitalizations(patientId);
        if (response.getStatus() == Status.OK) {
            select_requests.removeAllItems();
            ArrayList<java.util.HashMap<String, Object>> hosps =
                (ArrayList<java.util.HashMap<String, Object>>) response.getData().get("hospitalizations");
            for (java.util.HashMap<String, Object> h : hosps) {
                select_requests.addItem((String) h.get("id"));
            }
        }
    }//GEN-LAST:event_patient_id_buttonActionPerformed




    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton accept_button_reschedule_medical;
    private javax.swing.JButton accept_label;
    private javax.swing.JLabel accept_medical_label;
    private javax.swing.JButton add_button;
    private javax.swing.JTextField additional_instructions_label;
    private javax.swing.JTextField administration_route_label;
    private javax.swing.JLabel appointment_id_label;
    private javax.swing.JLabel appointment_label;
    private javax.swing.JLabel appointment_label2;
    private javax.swing.JLabel appointment_label3;
    private javax.swing.JPanel appointments_visualization;
    private javax.swing.JButton back_button;
    private javax.swing.JButton cancel_button;
    private javax.swing.JButton complete_button;
    private javax.swing.JLabel complete_label;
    private javax.swing.JTextField confirmation_input;
    private javax.swing.JLabel confirmation_label;
    private javax.swing.JTextField date_entry_input;
    private javax.swing.JLabel date_entry_label;
    private javax.swing.JLabel diagnosis_label;
    private javax.swing.JScrollPane diagnosis_output;
    private javax.swing.JLabel doctor_view_label;
    private javax.swing.JTextField dose_input;
    private javax.swing.JTextField estimated_duration_input;
    private javax.swing.JLabel firstname_label;
    private javax.swing.JTextField fist_name_input;
    private javax.swing.JScrollPane follow_up_indication;
    private javax.swing.JLabel follow_up_label;
    private javax.swing.JTextField frecuency_label;
    private javax.swing.JButton generate_button;
    private javax.swing.JPanel history_patient;
    private javax.swing.JLabel hospitalization_label;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea5;
    private javax.swing.JTextArea jTextArea6;
    private javax.swing.JTextArea jTextArea7;
    private javax.swing.JTextArea jTextArea8;
    private javax.swing.JTextArea jTextArea9;
    private javax.swing.JTextField last_name_input;
    private javax.swing.JLabel lastname_label;
    private javax.swing.JTextField license_numer_input;
    private javax.swing.JLabel licensenumer_label;
    private javax.swing.JButton logout_button;
    private javax.swing.JTextField medication_name_input;
    private javax.swing.JPanel medications;
    private javax.swing.JPanel modify_info;
    private javax.swing.JTextField new_time_appointment_label;
    private javax.swing.JLabel new_time_label;
    private javax.swing.JLabel observations_label;
    private javax.swing.JScrollPane observations_medical_appointment_output;
    private javax.swing.JScrollPane observations_output;
    private javax.swing.JTextField office_input;
    private javax.swing.JLabel office_label;
    private javax.swing.JTabbedPane option_menu;
    private javax.swing.JScrollPane output1;
    private packagee.ospedale.view.PanelRound panel1;
    private packagee.ospedale.view.PanelRound panel2;
    private javax.swing.JTextField password_input;
    private javax.swing.JLabel password_label;
    private javax.swing.JRadioButton patient_id_button;
    private javax.swing.JLabel patient_label;
    private javax.swing.JRadioButton pending_appointments_button;
    private javax.swing.JButton prescribe_button;
    private javax.swing.JTextField reason_appointment_input;
    private javax.swing.JLabel reason_label;
    private javax.swing.JScrollPane reasons_for_hospitalazation_output;
    private javax.swing.JLabel reasons_hospitalization_label;
    private javax.swing.JLabel recomended_label;
    private javax.swing.JScrollPane recommended_treatment_output;
    private javax.swing.JPanel request;
    private javax.swing.JRadioButton requests_button;
    private javax.swing.JLabel reschedule_label;
    private javax.swing.JButton save_button;
    private javax.swing.JButton search_button;
    private javax.swing.JComboBox<String> select_appointment;
    private javax.swing.JComboBox<String> select_appointment_2;
    private javax.swing.JComboBox<String> select_appointment_complete_medical;
    private javax.swing.JComboBox<String> select_appointment_id;
    private javax.swing.JComboBox<String> select_patient;
    private javax.swing.JComboBox<String> select_patient_id;
    private javax.swing.JComboBox<String> select_requests;
    private javax.swing.JComboBox<String> select_specialty;
    private javax.swing.JSeparator separator1;
    private javax.swing.JSeparator separator2;
    private javax.swing.JSeparator separator4;
    private javax.swing.JLabel specialty_label;
    private javax.swing.JScrollPane table;
    private javax.swing.JScrollPane table_option2;
    private javax.swing.JRadioButton total_appointments_button;
    private javax.swing.JTextField treatment_duration_label;
    private javax.swing.JTextField user_input;
    private javax.swing.JLabel user_label;
    private javax.swing.JButton x_button;
    // End of variables declaration//GEN-END:variables
}
