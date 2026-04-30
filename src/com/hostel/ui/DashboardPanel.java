package com.hostel.ui;

import com.hostel.service.HostelService;

import javax.swing.*;
import java.awt.*;

public class DashboardPanel extends JPanel {
    private final HostelService hostelService;
    private final JPanel cardsPanel;

    public DashboardPanel(HostelService hostelService) {
        this.hostelService = hostelService;
        setLayout(new BorderLayout(20, 20));
        setBackground(UITheme.BG);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel header = new JPanel();
        header.setOpaque(false);
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.add(UITheme.title("Overview"));
        header.add(Box.createVerticalStrut(6));
        header.add(UITheme.subtitle("Live summary of hostel operations"));

        cardsPanel = new JPanel(new GridLayout(1, 4, 18, 18));
        cardsPanel.setOpaque(false);

        JButton refreshButton = UITheme.button("Refresh Stats", UITheme.ACCENT);
        refreshButton.addActionListener(e -> refreshCards());

        JPanel south = new JPanel(new FlowLayout(FlowLayout.LEFT));
        south.setOpaque(false);
        south.add(refreshButton);

        add(header, BorderLayout.NORTH);
        add(cardsPanel, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);

        refreshCards();
    }

    private JPanel statCard(String title, String value, Color accent) {
        JPanel card = UITheme.cardLayout();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setForeground(UITheme.MUTED);
        titleLabel.setFont(new Font("SansSerif", Font.PLAIN, 15));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setForeground(UITheme.TEXT);
        valueLabel.setFont(new Font("SansSerif", Font.BOLD, 30));

        JPanel line = new JPanel();
        line.setMaximumSize(new Dimension(Integer.MAX_VALUE, 6));
        line.setBackground(accent);

        card.add(line);
        card.add(Box.createVerticalStrut(18));
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(16));
        card.add(valueLabel);
        return card;
    }

    public void refreshCards() {
        cardsPanel.removeAll();
        cardsPanel.add(statCard("Total Students", String.valueOf(hostelService.getTotalStudents()), UITheme.ACCENT));
        cardsPanel.add(statCard("Total Rooms", String.valueOf(hostelService.getTotalRooms()), UITheme.ACCENT_2));
        cardsPanel.add(statCard("Occupied Rooms", String.valueOf(hostelService.getOccupiedRooms()), new Color(253, 203, 110)));
        cardsPanel.add(statCard("Open Complaints", String.valueOf(hostelService.getOpenComplaints()), UITheme.DANGER));
        cardsPanel.revalidate();
        cardsPanel.repaint();
    }
}
