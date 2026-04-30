package com.hostel.ui;

import com.hostel.model.Warden;
import com.hostel.service.AuthService;
import com.hostel.service.HostelService;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class DashboardFrame extends JFrame {
    public DashboardFrame(Warden warden, HostelService hostelService, AuthService authService) {
        setTitle("Hostel Management System - Warden Dashboard");
        setSize(1300, 760);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UITheme.BG);

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(UITheme.PANEL);
        topBar.setBorder(BorderFactory.createEmptyBorder(14, 18, 14, 18));

        JLabel heading = new JLabel("Warden Control Panel");
        heading.setForeground(UITheme.TEXT);
        heading.setFont(new Font("SansSerif", Font.BOLD, 24));

        JLabel userInfo = new JLabel("Logged in as: " + warden.getName());
        userInfo.setForeground(UITheme.MUTED);
        userInfo.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JPanel titleWrap = new JPanel();
        titleWrap.setOpaque(false);
        titleWrap.setLayout(new BoxLayout(titleWrap, BoxLayout.Y_AXIS));
        titleWrap.add(heading);
        titleWrap.add(userInfo);

        JButton logoutButton = UITheme.button("Logout", UITheme.DANGER);
        logoutButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> new LoginFrame(authService, hostelService).setVisible(true));
        });

        topBar.add(titleWrap, BorderLayout.WEST);
        topBar.add(logoutButton, BorderLayout.EAST);

        DashboardPanel dashboardPanel = new DashboardPanel(hostelService);
        StudentsPanel studentsPanel = new StudentsPanel(hostelService);
        RoomsPanel roomsPanel = new RoomsPanel(hostelService);
        ComplaintsPanel complaintsPanel = new ComplaintsPanel(hostelService);
        FeesPanel feesPanel = new FeesPanel(hostelService);

        JTabbedPane tabs = new JTabbedPane();
        tabs.setFont(new Font("SansSerif", Font.BOLD, 14));
        tabs.addTab("Dashboard", dashboardPanel);
        tabs.addTab("Students", studentsPanel);
        tabs.addTab("Rooms", roomsPanel);
        tabs.addTab("Complaints", complaintsPanel);
        tabs.addTab("Fees", feesPanel);

        ChangeListener refreshListener = e -> {
            dashboardPanel.refreshCards();
            studentsPanel.refreshTable();
            roomsPanel.refreshTable();
            complaintsPanel.refreshTable();
            feesPanel.refreshTable();
        };
        tabs.addChangeListener(refreshListener);

        root.add(topBar, BorderLayout.NORTH);
        root.add(tabs, BorderLayout.CENTER);
        setContentPane(root);
    }
}
