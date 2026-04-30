package com.hostel.ui;

import com.hostel.model.Warden;
import com.hostel.service.AuthService;
import com.hostel.service.HostelService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LoginFrame extends JFrame {
    private final AuthService authService;
    private final HostelService hostelService;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginFrame(AuthService authService, HostelService hostelService) {
        this.authService = authService;
        this.hostelService = hostelService;
        initialize();
    }

    private void initialize() {
        setTitle("Hostel Management System - Warden Login");
        setSize(980, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 2));

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(UITheme.BG);
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(new EmptyBorder(60, 50, 60, 50));

        JLabel logo = UITheme.title("HOSTEL MANAGEMENT");
        JLabel logo2 = UITheme.title("SYSTEM");
        JLabel tag = UITheme.subtitle("Secure hostel control panel for wardens");
        JLabel small = UITheme.subtitle("Students • Rooms • Complaints • Fees");

        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(logo);
        leftPanel.add(Box.createVerticalStrut(8));
        leftPanel.add(logo2);
        leftPanel.add(Box.createVerticalStrut(18));
        leftPanel.add(tag);
        leftPanel.add(Box.createVerticalStrut(6));
        leftPanel.add(small);
        leftPanel.add(Box.createVerticalGlue());

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(new Color(236, 240, 241));

        JPanel formCard = new JPanel();
        formCard.setLayout(new BoxLayout(formCard, BoxLayout.Y_AXIS));
        formCard.setBackground(Color.WHITE);
        formCard.setBorder(new EmptyBorder(30, 30, 30, 30));
        formCard.setPreferredSize(new Dimension(360, 360));

        JLabel loginLabel = new JLabel("Warden Login");
        loginLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        loginLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel hint = new JLabel("Use the default credentials to access the dashboard");
        hint.setForeground(Color.GRAY);
        hint.setAlignmentX(Component.LEFT_ALIGNMENT);

        usernameField = UITheme.textField();
        passwordField = UITheme.passwordField();
        loginButton = UITheme.button("Login", UITheme.ACCENT_2);

        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        loginButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel defaultInfo = new JLabel("Default: warden / hostel123");
        defaultInfo.setForeground(new Color(99, 110, 114));
        defaultInfo.setAlignmentX(Component.LEFT_ALIGNMENT);

        loginButton.addActionListener(e -> performLogin());
        usernameField.addActionListener(e -> performLogin());
        passwordField.addActionListener(e -> performLogin());

        formCard.add(loginLabel);
        formCard.add(Box.createVerticalStrut(8));
        formCard.add(hint);
        formCard.add(Box.createVerticalStrut(25));
        formCard.add(new JLabel("Username"));
        formCard.add(Box.createVerticalStrut(6));
        formCard.add(usernameField);
        formCard.add(Box.createVerticalStrut(15));
        formCard.add(new JLabel("Password"));
        formCard.add(Box.createVerticalStrut(6));
        formCard.add(passwordField);
        formCard.add(Box.createVerticalStrut(22));
        formCard.add(loginButton);
        formCard.add(Box.createVerticalStrut(18));
        formCard.add(defaultInfo);

        rightPanel.add(formCard);
        add(leftPanel);
        add(rightPanel);

        getRootPane().setDefaultButton(loginButton);
        SwingUtilities.invokeLater(() -> usernameField.requestFocusInWindow());
    }

    private void performLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter both username and password.", "Missing Details", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Warden warden = authService.login(username, password);
        if (warden != null) {
            dispose();
            SwingUtilities.invokeLater(() -> new DashboardFrame(warden, hostelService, authService).setVisible(true));
        } else {
            passwordField.setText("");
            JOptionPane.showMessageDialog(this, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
            SwingUtilities.invokeLater(() -> usernameField.requestFocusInWindow());
        }
    }
}
