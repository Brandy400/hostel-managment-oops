package com.hostel.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class UITheme {
    public static final Color BG = new Color(14, 20, 33);
    public static final Color PANEL = new Color(24, 32, 49);
    public static final Color CARD = new Color(32, 42, 63);
    public static final Color ACCENT = new Color(0, 184, 148);
    public static final Color ACCENT_2 = new Color(9, 132, 227);
    public static final Color TEXT = new Color(245, 246, 250);
    public static final Color MUTED = new Color(178, 190, 195);
    public static final Color DANGER = new Color(214, 48, 49);

    public static void applyLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
    }

    public static JLabel title(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(TEXT);
        label.setFont(new Font("SansSerif", Font.BOLD, 28));
        return label;
    }

    public static JLabel subtitle(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(MUTED);
        label.setFont(new Font("SansSerif", Font.PLAIN, 14));
        return label;
    }

    public static JButton button(String text, Color color) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(new EmptyBorder(10, 18, 10, 18));
        return button;
    }

    public static JTextField textField() {
        JTextField field = new JTextField();
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(220, 36));
        return field;
    }

    public static JPasswordField passwordField() {
        JPasswordField field = new JPasswordField();
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setPreferredSize(new Dimension(220, 36));
        return field;
    }

    public static JPanel panelLayout() {
        JPanel panel = new JPanel();
        panel.setBackground(PANEL);
        panel.setBorder(new EmptyBorder(16, 16, 16, 16));
        return panel;
    }

    public static JPanel cardLayout() {
        JPanel panel = new JPanel();
        panel.setBackground(CARD);
        panel.setBorder(new EmptyBorder(16, 16, 16, 16));
        return panel;
    }
}
