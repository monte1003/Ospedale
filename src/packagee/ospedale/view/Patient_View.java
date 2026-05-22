/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package packagee.ospedale.view;

import packagee.ospedale.controller.ControllerRegistry;
import packagee.ospedale.controller.utils.Response;
import packagee.ospedale.controller.utils.Status;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import packagee.ospedale.model.Specialty;
import packagee.ospedale.model.storage.Storage;
import packagee.ospedale.observer.StorageEventType;
import packagee.ospedale.observer.StorageObserver;

/**
 * Vista del paciente para consultar datos personales y solicitar servicios.
 */
public class Patient_View extends javax.swing.JFrame {
    private static final long serialVersionUID = 1L;

    private int x, y;
    private long patientId;
    private transient StorageObserver storageObserver;

    @SuppressWarnings("this-escape")
    public Patient_View(long patientId, boolean fromAdmin) {
        initComponents();
        this.patientId = patientId;
        this.setBackground(new Color(0, 0, 0, 0));
        this.setLocationRelativeTo(null);
        backButton.setVisible(fromAdmin);
        loadPatientInfo();
        loadAppointmentsTable();
        loadDoctorsComboBox();
        loadAppointmentsComboBox();
        loadRoomTypesComboBox();
        registerObserver();
    }

    // Escucha cambios en usuarios y citas para mantener la vista actualizada.
    private void registerObserver() {
        storageObserver = eventType -> SwingUtilities.invokeLater(() -> {
            if (eventType == StorageEventType.USERS_CHANGED) {
                loadPatientInfo();
                loadDoctorsComboBox();
            }

            if (eventType == StorageEventType.APPOINTMENTS_CHANGED) {
                loadAppointmentsTable();
                loadAppointmentsComboBox();
            }
        });

        Storage.getInstance().addObserver(storageObserver);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                Storage.getInstance().removeObserver(storageObserver);
            }
        });
    }
    
    private void loadPatientInfo() {
        Response response = ControllerRegistry.getInstance().getPatientController().getPatientInfo("" + patientId);
        if (response.getStatus() == Status.OK) {
            java.util.HashMap<String, Object> data = response.getData();
            firstname_input.setText((String) data.get("firstname"));
            lastname_input.setText((String) data.get("lastname"));
            birthdate_input.setText((String) data.get("birthdate"));
            email_input.setText((String) data.get("email"));
            phone_input.setText("" + data.get("phone"));
            address_input.setText((String) data.get("address"));
            username_input.setText((String) data.get("username"));
            password_input.setText((String) data.get("password"));
            password_confirmation_input.setText((String) data.get("password"));
            boolean gender = (boolean) data.get("gender");
            gender_combobox.setSelectedItem(gender ? "Male" : "Female");
        }
    }
    
    @SuppressWarnings("unchecked")
    private void loadAppointmentsTable() {
        Response response = ControllerRegistry.getInstance().getMedicalServiceController().getPatientAppointments("" + patientId);
        if (response.getStatus() == Status.OK) {
            ArrayList<java.util.HashMap<String, Object>> appointments =
                (ArrayList<java.util.HashMap<String, Object>>) response.getData().get("appointments");
            DefaultTableModel model = (DefaultTableModel) table_patient_view.getModel();
            model.setRowCount(0);
            for (java.util.HashMap<String, Object> a : appointments) {
                model.addRow(new Object[]{
                    a.get("id"),
                    a.get("datetime"),
                    a.get("doctorName"),
                    a.get("specialty"),
                    a.get("type"),
                    a.get("status")
                });
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    private void loadDoctorsComboBox() {
        Response response = ControllerRegistry.getInstance().getDoctorController().getAllDoctors();
        if (response.getStatus() == Status.OK) {
            select_attending_doctor.removeAllItems();
            select_sepecialty_or_doctor.removeAllItems();
            ArrayList<java.util.HashMap<String, Object>> doctors =
                (ArrayList<java.util.HashMap<String, Object>>) response.getData().get("doctors");
            for (java.util.HashMap<String, Object> d : doctors) {
                select_attending_doctor.addItem("" + d.get("id"));
                select_sepecialty_or_doctor.addItem("" + d.get("id"));
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    private void loadAppointmentsComboBox() {
        Response response = ControllerRegistry.getInstance().getMedicalServiceController().getPatientAppointments("" + patientId);
        if (response.getStatus() == Status.OK) {
            select_id_appointment.removeAllItems();
            ArrayList<java.util.HashMap<String, Object>> appointments =
                (ArrayList<java.util.HashMap<String, Object>>) response.getData().get("appointments");
            for (java.util.HashMap<String, Object> a : appointments) {
                select_id_appointment.addItem((String) a.get("id"));
            }
        }
    }
    
    private void loadRoomTypesComboBox() {
        select_desired_room_type.removeAllItems();
        for (packagee.ospedale.model.RoomType rt : packagee.ospedale.model.RoomType.values()) {
            select_desired_room_type.addItem(rt.name());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelRound1 = new packagee.ospedale.view.PanelRound();
        panelRound2 = new packagee.ospedale.view.PanelRound();
        exit_button = new javax.swing.JButton();
        lbl_patient_view = new javax.swing.JLabel();
        backButton = new javax.swing.JButton();
        Table_Patient_view = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        table_patient_view = new javax.swing.JTable();
        refresh_button = new javax.swing.JButton();
        logout_button = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lbl_firstname = new javax.swing.JLabel();
        firstname_input = new javax.swing.JTextField();
        lbl_lastname = new javax.swing.JLabel();
        lastname_input = new javax.swing.JTextField();
        lbl_birthdate = new javax.swing.JLabel();
        birthdate_input = new javax.swing.JTextField();
        lbl_gender = new javax.swing.JLabel();
        lbl_email = new javax.swing.JLabel();
        email_input = new javax.swing.JTextField();
        lbl_phone = new javax.swing.JLabel();
        phone_input = new javax.swing.JTextField();
        lbl_adress = new javax.swing.JLabel();
        address_input = new javax.swing.JTextField();
        password_input = new javax.swing.JTextField();
        lbl_password = new javax.swing.JLabel();
        lbl_password_confirmation = new javax.swing.JLabel();
        password_confirmation_input = new javax.swing.JTextField();
        saveModifyInfoPatient = new javax.swing.JButton();
        lbl_user = new javax.swing.JLabel();
        username_input = new javax.swing.JTextField();
        gender_combobox = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        lbl_request_medical_appointment = new javax.swing.JLabel();
        selectSpecialty = new javax.swing.JRadioButton();
        selectDoctor = new javax.swing.JRadioButton();
        jSeparator1 = new javax.swing.JSeparator();
        lbl_appointment_date = new javax.swing.JLabel();
        date_appointment_input = new javax.swing.JTextField();
        time_appointment_input = new javax.swing.JTextField();
        lbl_appointment_time = new javax.swing.JLabel();
        lbl_appointment_type = new javax.swing.JLabel();
        lbl_appointment_reason = new javax.swing.JLabel();
        select_appointment_type = new javax.swing.JComboBox<>();
        createRequestMedicalAppointment = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        lbl_request_hospitalization = new javax.swing.JLabel();
        lbl_hospitalization_reason = new javax.swing.JLabel();
        lbl_attending_doctor = new javax.swing.JLabel();
        select_attending_doctor = new javax.swing.JComboBox<>();
        date_hospitalization_input = new javax.swing.JTextField();
        lbl_estimated_date_of_admission = new javax.swing.JLabel();
        lbl_desired_room_type = new javax.swing.JLabel();
        select_desired_room_type = new javax.swing.JComboBox<>();
        lbl_observations = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        observations_hospitalization = new javax.swing.JTextArea();
        createRequestHospitalization = new javax.swing.JButton();
        lbl_cancel_appointment = new javax.swing.JLabel();
        lbl_id_appointment = new javax.swing.JLabel();
        lbl_observations_cancel_appointment = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        observations_cancel_appointment = new javax.swing.JTextArea();
        cancelAppointment_button = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        reason_hospitalization = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        reason_appointment = new javax.swing.JTextArea();
        select_id_appointment = new javax.swing.JComboBox<>();
        select_sepecialty_or_doctor = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        panelRound1.setRadius(50);

        panelRound2.setRadius(50);
        panelRound2.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                panelRound2MouseDragged(evt);
            }
        });
        panelRound2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelRound2MousePressed(evt);
            }
        });

        exit_button.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        exit_button.setText("X");
        exit_button.setBorderPainted(false);
        exit_button.setContentAreaFilled(false);
        exit_button.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        exit_button.setFocusable(false);
        exit_button.setRequestFocusEnabled(false);
        exit_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exit_buttonActionPerformed(evt);
            }
        });

        lbl_patient_view.setFont(new java.awt.Font("Yu Gothic UI", 0, 14)); // NOI18N
        lbl_patient_view.setText("PATIENT VIEW");

        backButton.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        backButton.setText("Back");
        backButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                backButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRound2Layout = new javax.swing.GroupLayout(panelRound2);
        panelRound2.setLayout(panelRound2Layout);
        panelRound2Layout.setHorizontalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound2Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lbl_patient_view)
                .addGap(29, 29, 29)
                .addComponent(backButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(exit_button)
                .addGap(19, 19, 19))
        );
        panelRound2Layout.setVerticalGroup(
            panelRound2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(exit_button))
            .addGroup(panelRound2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(backButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(lbl_patient_view, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        table_patient_view.setAutoCreateRowSorter(true);
        table_patient_view.setModel(new javax.swing.table.DefaultTableModel(
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
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(table_patient_view);

        refresh_button.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        refresh_button.setText("Refresh");
        refresh_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refresh_buttonActionPerformed(evt);
            }
        });

        logout_button.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        logout_button.setText("Logout");
        logout_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logout_buttonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 1167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(602, 602, 602)
                .addComponent(refresh_button)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(logout_button)
                .addGap(78, 78, 78))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(58, 58, 58)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(refresh_button)
                    .addComponent(logout_button))
                .addContainerGap(71, Short.MAX_VALUE))
        );

        Table_Patient_view.addTab("Appointment history", jPanel3);

        lbl_firstname.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lbl_firstname.setText("Firstname");

        firstname_input.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        lbl_lastname.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lbl_lastname.setText("Lastname");

        lastname_input.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        lbl_birthdate.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lbl_birthdate.setText("Birthdate");

        birthdate_input.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        lbl_gender.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lbl_gender.setText("Gender");

        lbl_email.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lbl_email.setText("Email");

        email_input.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        lbl_phone.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lbl_phone.setText("Phone");

        phone_input.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        lbl_adress.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lbl_adress.setText("Address");

        address_input.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        password_input.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        lbl_password.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lbl_password.setText("Password");

        lbl_password_confirmation.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lbl_password_confirmation.setText("Password confirmation");

        password_confirmation_input.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        saveModifyInfoPatient.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        saveModifyInfoPatient.setText("Save");
        saveModifyInfoPatient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveModifyInfoPatientActionPerformed(evt);
            }
        });

        lbl_user.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lbl_user.setText("User");

        username_input.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        gender_combobox.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        gender_combobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one", "Female", "Male" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addComponent(lbl_firstname)
                .addGap(18, 18, 18)
                .addComponent(firstname_input, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(lbl_lastname)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lbl_phone)
                        .addGap(18, 18, 18)
                        .addComponent(phone_input, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_adress)
                        .addGap(18, 18, 18)
                        .addComponent(address_input, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lastname_input, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_birthdate)
                        .addGap(18, 18, 18)
                        .addComponent(birthdate_input, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_gender)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(gender_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)
                        .addComponent(lbl_email)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addComponent(email_input, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(141, 141, 141))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(516, 516, 516)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(saveModifyInfoPatient))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(password_confirmation_input, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbl_password_confirmation)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(lbl_password))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(username_input, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(39, 39, 39)
                                    .addComponent(lbl_user)))
                            .addComponent(password_input, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_firstname)
                    .addComponent(firstname_input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_lastname)
                    .addComponent(lastname_input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_birthdate)
                    .addComponent(birthdate_input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_gender)
                    .addComponent(lbl_email)
                    .addComponent(email_input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(gender_combobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbl_phone)
                    .addComponent(phone_input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbl_adress)
                    .addComponent(address_input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(66, 66, 66)
                .addComponent(lbl_user)
                .addGap(18, 18, 18)
                .addComponent(username_input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl_password)
                .addGap(18, 18, 18)
                .addComponent(password_input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbl_password_confirmation)
                .addGap(18, 18, 18)
                .addComponent(password_confirmation_input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(saveModifyInfoPatient)
                .addContainerGap(68, Short.MAX_VALUE))
        );

        Table_Patient_view.addTab("Modify info", jPanel1);

        lbl_request_medical_appointment.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lbl_request_medical_appointment.setText("Request medical appointment");

        selectSpecialty.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        selectSpecialty.setText("Specialty");
        selectSpecialty.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectSpecialtyActionPerformed(evt);
            }
        });

        selectDoctor.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        selectDoctor.setText("Doctor");
        selectDoctor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectDoctorActionPerformed(evt);
            }
        });

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        lbl_appointment_date.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lbl_appointment_date.setText("Appointment date");

        date_appointment_input.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        time_appointment_input.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        lbl_appointment_time.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lbl_appointment_time.setText("Appointment time");

        lbl_appointment_type.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lbl_appointment_type.setText("Appointment type");

        lbl_appointment_reason.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lbl_appointment_reason.setText("Appointment reason");

        select_appointment_type.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        select_appointment_type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one", "Remote", "In-person" }));

        createRequestMedicalAppointment.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        createRequestMedicalAppointment.setText("Create");
        createRequestMedicalAppointment.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createRequestMedicalAppointmentActionPerformed(evt);
            }
        });

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        lbl_request_hospitalization.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lbl_request_hospitalization.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_request_hospitalization.setText("Request hospitalization");

        lbl_hospitalization_reason.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lbl_hospitalization_reason.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_hospitalization_reason.setText("Hospitalization reason");

        lbl_attending_doctor.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lbl_attending_doctor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_attending_doctor.setText("Attending doctor");

        select_attending_doctor.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        select_attending_doctor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one" }));

        date_hospitalization_input.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N

        lbl_estimated_date_of_admission.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lbl_estimated_date_of_admission.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_estimated_date_of_admission.setText("Estimated date of admission");
        lbl_estimated_date_of_admission.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        lbl_desired_room_type.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lbl_desired_room_type.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_desired_room_type.setText("Desired room type");

        select_desired_room_type.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        select_desired_room_type.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one" }));

        lbl_observations.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lbl_observations.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_observations.setText("Observations");

        observations_hospitalization.setColumns(20);
        observations_hospitalization.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        observations_hospitalization.setRows(5);
        jScrollPane1.setViewportView(observations_hospitalization);

        createRequestHospitalization.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        createRequestHospitalization.setText("Create");
        createRequestHospitalization.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createRequestHospitalizationActionPerformed(evt);
            }
        });

        lbl_cancel_appointment.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lbl_cancel_appointment.setText("Cancel appointment");

        lbl_id_appointment.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lbl_id_appointment.setText("ID appointment");

        lbl_observations_cancel_appointment.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        lbl_observations_cancel_appointment.setText("Observations");

        observations_cancel_appointment.setColumns(20);
        observations_cancel_appointment.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        observations_cancel_appointment.setRows(5);
        jScrollPane2.setViewportView(observations_cancel_appointment);

        cancelAppointment_button.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        cancelAppointment_button.setText("Cancel");
        cancelAppointment_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelAppointment_buttonActionPerformed(evt);
            }
        });

        reason_hospitalization.setColumns(20);
        reason_hospitalization.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        reason_hospitalization.setRows(5);
        jScrollPane4.setViewportView(reason_hospitalization);

        reason_appointment.setColumns(20);
        reason_appointment.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        reason_appointment.setRows(5);
        jScrollPane5.setViewportView(reason_appointment);

        select_id_appointment.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        select_id_appointment.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one" }));

        select_sepecialty_or_doctor.setFont(new java.awt.Font("Yu Gothic UI", 0, 18)); // NOI18N
        select_sepecialty_or_doctor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Select one" }));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(44, 44, 44)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(selectSpecialty)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(selectDoctor))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(63, 63, 63)
                                    .addComponent(date_appointment_input, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(47, 47, 47)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(lbl_appointment_time)
                                        .addComponent(lbl_appointment_date)
                                        .addComponent(select_sepecialty_or_doctor, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(63, 63, 63)
                                    .addComponent(time_appointment_input, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(38, 38, 38)
                                    .addComponent(lbl_appointment_reason))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(46, 46, 46)
                                    .addComponent(lbl_appointment_type))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(55, 55, 55)
                                    .addComponent(select_appointment_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(42, 42, 42)
                            .addComponent(lbl_request_medical_appointment)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(122, 122, 122)
                        .addComponent(createRequestMedicalAppointment)))
                .addGap(69, 69, 69)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(211, 211, 211)
                            .addComponent(createRequestHospitalization))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGap(127, 127, 127)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lbl_hospitalization_reason, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(lbl_request_hospitalization, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                                .addComponent(lbl_attending_doctor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                            .addGap(127, 127, 127)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lbl_observations, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbl_estimated_date_of_admission, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lbl_desired_room_type, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 246, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(190, 190, 190)
                        .addComponent(select_attending_doctor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(200, 200, 200)
                        .addComponent(date_hospitalization_input, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(191, 191, 191)
                        .addComponent(select_desired_room_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(63, 63, 63)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(lbl_cancel_appointment))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(77, 77, 77)
                                .addComponent(cancelAppointment_button))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(47, 47, 47)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(select_id_appointment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lbl_id_appointment)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(60, 60, 60)
                                .addComponent(lbl_observations_cancel_appointment)))
                        .addGap(49, 49, 49)))
                .addGap(81, 81, 81))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addComponent(jSeparator2)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lbl_request_hospitalization)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                        .addComponent(lbl_hospitalization_reason)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lbl_attending_doctor)
                        .addGap(18, 18, 18)
                        .addComponent(select_attending_doctor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_estimated_date_of_admission)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(date_hospitalization_input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(lbl_desired_room_type)
                        .addGap(18, 18, 18)
                        .addComponent(select_desired_room_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_observations)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(createRequestHospitalization)
                        .addGap(15, 15, 15))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lbl_request_medical_appointment)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(selectSpecialty)
                                    .addComponent(selectDoctor))
                                .addGap(18, 18, 18)
                                .addComponent(select_sepecialty_or_doctor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbl_appointment_date)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(date_appointment_input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(13, 13, 13)
                                .addComponent(lbl_appointment_time)
                                .addGap(18, 18, 18)
                                .addComponent(time_appointment_input, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lbl_appointment_reason)
                                .addGap(24, 24, 24)
                                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lbl_cancel_appointment)
                                .addGap(39, 39, 39)
                                .addComponent(lbl_id_appointment)
                                .addGap(18, 18, 18)
                                .addComponent(select_id_appointment, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(lbl_observations_cancel_appointment)
                                .addGap(18, 18, 18)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(56, 56, 56)
                                .addComponent(cancelAppointment_button)))
                        .addGap(18, 18, 18)
                        .addComponent(lbl_appointment_type)
                        .addGap(18, 18, 18)
                        .addComponent(select_appointment_type, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(createRequestMedicalAppointment)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        Table_Patient_view.addTab("Request/Cancel", jPanel2);

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelRound2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Table_Patient_view)
        );
        panelRound1Layout.setVerticalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addComponent(panelRound2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Table_Patient_view))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelRound1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelRound1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void panelRound2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound2MousePressed
        x = evt.getX();
        y = evt.getY();
        
    }//GEN-LAST:event_panelRound2MousePressed

    private void panelRound2MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound2MouseDragged
        this.setLocation(this.getLocation().x + evt.getX() - x, this.getLocation().y + evt.getY() - y);
    }//GEN-LAST:event_panelRound2MouseDragged

    private void exit_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exit_buttonActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exit_buttonActionPerformed

    private void cancelAppointment_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelAppointment_buttonActionPerformed
        String appointmentId = (String) select_id_appointment.getSelectedItem();
        Response response = ControllerRegistry.getInstance().getMedicalServiceController().cancelAppointment(appointmentId);
        if (response.getStatus() >= 500) {
            JOptionPane.showMessageDialog(null, response.getMessage(), "Error " + response.getStatus(), JOptionPane.ERROR_MESSAGE);
        } else if (response.getStatus() >= 400) {
            JOptionPane.showMessageDialog(null, response.getMessage(), "Error " + response.getStatus(), JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, response.getMessage(), "Response Message", JOptionPane.INFORMATION_MESSAGE);
            observations_cancel_appointment.setText("");
            loadAppointmentsTable();
            loadAppointmentsComboBox();
        }
    }//GEN-LAST:event_cancelAppointment_buttonActionPerformed

    private void saveModifyInfoPatientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveModifyInfoPatientActionPerformed
        String firstname = firstname_input.getText();
        String lastname = lastname_input.getText();
        String birthdate = birthdate_input.getText();
        String email = email_input.getText();
        String phone = phone_input.getText();
        String address = address_input.getText();
        String password = password_input.getText();
        String confirmPassword = password_confirmation_input.getText();
        String username = username_input.getText();
        String gender = (String) gender_combobox.getSelectedItem();

        Response response = ControllerRegistry.getInstance().getPatientController().updatePatient("" + patientId, username,
                password, confirmPassword, firstname, lastname, email, birthdate,
                gender, phone, address);

        if (response.getStatus() >= 500) {
            JOptionPane.showMessageDialog(null, response.getMessage(), "Error " + response.getStatus(), JOptionPane.ERROR_MESSAGE);
        } else if (response.getStatus() >= 400) {
            JOptionPane.showMessageDialog(null, response.getMessage(), "Error " + response.getStatus(), JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, response.getMessage(), "Response Message", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_saveModifyInfoPatientActionPerformed

    private void logout_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logout_buttonActionPerformed
        Login login = new Login();
        login.setVisible(true);
        dispose();
    }//GEN-LAST:event_logout_buttonActionPerformed

    private void backButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_backButtonActionPerformed
        Admin_View admin = new Admin_View();
        admin.setVisible(true);
        dispose();
    }//GEN-LAST:event_backButtonActionPerformed

    private void selectSpecialtyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectSpecialtyActionPerformed
        if (selectDoctor.isSelected()) {
            selectDoctor.setSelected(false);
        }
        select_sepecialty_or_doctor.removeAllItems();
        for (Specialty spec : Specialty.values()) {
            select_sepecialty_or_doctor.addItem(spec.name());
        }
    }//GEN-LAST:event_selectSpecialtyActionPerformed

    private void selectDoctorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectDoctorActionPerformed
        if (selectSpecialty.isSelected()) {
            selectSpecialty.setSelected(false);
        }
        loadDoctorsComboBox();
    }//GEN-LAST:event_selectDoctorActionPerformed

    private void createRequestMedicalAppointmentActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createRequestMedicalAppointmentActionPerformed
        String date = date_appointment_input.getText();
        String time = time_appointment_input.getText();
        String reason = reason_appointment.getText();
        String doctorOrSpecialty = (String) select_sepecialty_or_doctor.getSelectedItem();

        String doctorId = "";
        String specialty = "";

        if (selectDoctor.isSelected()) {
            doctorId = doctorOrSpecialty;
        } else {
            specialty = doctorOrSpecialty;
        }

        String appointmentType = (String) select_appointment_type.getSelectedItem();

        Response response = ControllerRegistry.getInstance().getMedicalServiceController().requestAppointment(
                "" + patientId, doctorId, specialty, date, time, reason, appointmentType);

        if (response.getStatus() >= 500) {
            JOptionPane.showMessageDialog(null, response.getMessage(), "Error " + response.getStatus(), JOptionPane.ERROR_MESSAGE);
        } else if (response.getStatus() >= 400) {
            JOptionPane.showMessageDialog(null, response.getMessage(), "Error " + response.getStatus(), JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, response.getMessage(), "Response Message", JOptionPane.INFORMATION_MESSAGE);
            date_appointment_input.setText("");
            time_appointment_input.setText("");
            reason_appointment.setText("");
            loadAppointmentsTable();    
            loadAppointmentsComboBox();
        }
    }//GEN-LAST:event_createRequestMedicalAppointmentActionPerformed


    private void refresh_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refresh_buttonActionPerformed

        loadAppointmentsTable();
        loadAppointmentsComboBox();
    }//GEN-LAST:event_refresh_buttonActionPerformed

    private void createRequestHospitalizationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createRequestHospitalizationActionPerformed
        String reason = reason_hospitalization.getText();
        String doctorId = (String) select_attending_doctor.getSelectedItem();
        String date = date_hospitalization_input.getText();
        String roomType = (String) select_desired_room_type.getSelectedItem();
        String observations = observations_hospitalization.getText();

        Response response = ControllerRegistry.getInstance().getMedicalServiceController().requestHospitalization(
                "" + patientId, doctorId, date, roomType, reason, observations);

        if (response.getStatus() >= 500) {
            JOptionPane.showMessageDialog(null, response.getMessage(), "Error " + response.getStatus(), JOptionPane.ERROR_MESSAGE);
        } else if (response.getStatus() >= 400) {
            JOptionPane.showMessageDialog(null, response.getMessage(), "Error " + response.getStatus(), JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, response.getMessage(), "Response Message", JOptionPane.INFORMATION_MESSAGE);
            reason_hospitalization.setText("");
            date_hospitalization_input.setText("");
            observations_hospitalization.setText("");
            select_desired_room_type.setSelectedIndex(0);
        }
    }//GEN-LAST:event_createRequestHospitalizationActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane Table_Patient_view;
    private javax.swing.JTextField address_input;
    private javax.swing.JButton backButton;
    private javax.swing.JTextField birthdate_input;
    private javax.swing.JButton cancelAppointment_button;
    private javax.swing.JButton createRequestHospitalization;
    private javax.swing.JButton createRequestMedicalAppointment;
    private javax.swing.JTextField date_appointment_input;
    private javax.swing.JTextField date_hospitalization_input;
    private javax.swing.JTextField email_input;
    private javax.swing.JButton exit_button;
    private javax.swing.JTextField firstname_input;
    private javax.swing.JComboBox<String> gender_combobox;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextField lastname_input;
    private javax.swing.JLabel lbl_adress;
    private javax.swing.JLabel lbl_appointment_date;
    private javax.swing.JLabel lbl_appointment_reason;
    private javax.swing.JLabel lbl_appointment_time;
    private javax.swing.JLabel lbl_appointment_type;
    private javax.swing.JLabel lbl_attending_doctor;
    private javax.swing.JLabel lbl_birthdate;
    private javax.swing.JLabel lbl_cancel_appointment;
    private javax.swing.JLabel lbl_desired_room_type;
    private javax.swing.JLabel lbl_email;
    private javax.swing.JLabel lbl_estimated_date_of_admission;
    private javax.swing.JLabel lbl_firstname;
    private javax.swing.JLabel lbl_gender;
    private javax.swing.JLabel lbl_hospitalization_reason;
    private javax.swing.JLabel lbl_id_appointment;
    private javax.swing.JLabel lbl_lastname;
    private javax.swing.JLabel lbl_observations;
    private javax.swing.JLabel lbl_observations_cancel_appointment;
    private javax.swing.JLabel lbl_password;
    private javax.swing.JLabel lbl_password_confirmation;
    private javax.swing.JLabel lbl_patient_view;
    private javax.swing.JLabel lbl_phone;
    private javax.swing.JLabel lbl_request_hospitalization;
    private javax.swing.JLabel lbl_request_medical_appointment;
    private javax.swing.JLabel lbl_user;
    private javax.swing.JButton logout_button;
    private javax.swing.JTextArea observations_cancel_appointment;
    private javax.swing.JTextArea observations_hospitalization;
    private packagee.ospedale.view.PanelRound panelRound1;
    private packagee.ospedale.view.PanelRound panelRound2;
    private javax.swing.JTextField password_confirmation_input;
    private javax.swing.JTextField password_input;
    private javax.swing.JTextField phone_input;
    private javax.swing.JTextArea reason_appointment;
    private javax.swing.JTextArea reason_hospitalization;
    private javax.swing.JButton refresh_button;
    private javax.swing.JButton saveModifyInfoPatient;
    private javax.swing.JRadioButton selectDoctor;
    private javax.swing.JRadioButton selectSpecialty;
    private javax.swing.JComboBox<String> select_appointment_type;
    private javax.swing.JComboBox<String> select_attending_doctor;
    private javax.swing.JComboBox<String> select_desired_room_type;
    private javax.swing.JComboBox<String> select_id_appointment;
    private javax.swing.JComboBox<String> select_sepecialty_or_doctor;
    private javax.swing.JTable table_patient_view;
    private javax.swing.JTextField time_appointment_input;
    private javax.swing.JTextField username_input;
    // End of variables declaration//GEN-END:variables
}
