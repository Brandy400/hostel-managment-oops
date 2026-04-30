package com.hostel.app;

import com.hostel.service.AuthService;
import com.hostel.service.HostelService;
import com.hostel.ui.LoginFrame;
import com.hostel.ui.UITheme;

import javax.swing.*;

public class HostelManagementSystemGUI {
    public static void main(String[] args) {
        UITheme.applyLookAndFeel();
        SwingUtilities.invokeLater(() -> {
            HostelService hostelService = new HostelService();
            AuthService authService = new AuthService();
            new LoginFrame(authService, hostelService).setVisible(true);
        });
    }
}
