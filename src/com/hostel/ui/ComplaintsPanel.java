package com.hostel.ui;

import com.hostel.exception.HostelException;
import com.hostel.model.Complaint;
import com.hostel.service.HostelService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ComplaintsPanel extends JPanel {
    private final HostelService hostelService;
    private final DefaultTableModel tableModel;

    public ComplaintsPanel(HostelService hostelService) {
        this.hostelService = hostelService;
        setLayout(new BorderLayout(18, 18));
        setBackground(UITheme.BG);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.add(UITheme.title("Complaint Desk"));
        text.add(Box.createVerticalStrut(6));
        text.add(UITheme.subtitle("Register student complaints and update their status"));

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.setOpaque(false);
        JButton addComplaint = UITheme.button("Add Complaint", UITheme.ACCENT_2);
        JButton updateStatus = UITheme.button("Update Status", UITheme.ACCENT);
        addComplaint.addActionListener(e -> openAddComplaintDialog());
        updateStatus.addActionListener(e -> openStatusDialog());
        actions.add(addComplaint);
        actions.add(updateStatus);

        header.add(text, BorderLayout.WEST);
        header.add(actions, BorderLayout.EAST);

        tableModel = new DefaultTableModel(new String[]{"Complaint ID", "Student ID", "Category", "Description", "Status"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable table = new JTable(tableModel);
        table.setRowHeight(24);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));

        add(header, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        refreshTable();
    }

    private void openAddComplaintDialog() {
        JTextField studentId = UITheme.textField();
        JTextField category = UITheme.textField();
        JTextArea description = new JTextArea(4, 20);
        description.setLineWrap(true);
        description.setWrapStyleWord(true);
        JScrollPane descScroll = new JScrollPane(description);
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.add(new JLabel("Student ID")); panel.add(studentId);
        panel.add(new JLabel("Category")); panel.add(category);
        panel.add(new JLabel("Description")); panel.add(descScroll);

        int option = JOptionPane.showConfirmDialog(this, panel, "Add Complaint", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                hostelService.addComplaint(studentId.getText().trim(), category.getText().trim(), description.getText().trim());
                refreshTable();
                JOptionPane.showMessageDialog(this, "Complaint added successfully.");
            } catch (HostelException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void openStatusDialog() {
        JTextField complaintId = UITheme.textField();
        String[] statusOptions = {"Open", "In Progress", "Resolved"};
        JComboBox<String> statusBox = new JComboBox<>(statusOptions);
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.add(new JLabel("Complaint ID")); panel.add(complaintId);
        panel.add(new JLabel("New Status")); panel.add(statusBox);

        int option = JOptionPane.showConfirmDialog(this, panel, "Update Complaint Status", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                hostelService.updateComplaintStatus(Integer.parseInt(complaintId.getText().trim()), statusBox.getSelectedItem().toString());
                refreshTable();
                JOptionPane.showMessageDialog(this, "Complaint status updated.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Complaint ID must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (HostelException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        for (Complaint c : hostelService.getAllComplaints()) {
            tableModel.addRow(new Object[]{c.getComplaintId(), c.getStudentId(), c.getCategory(), c.getDescription(), c.getStatus()});
        }
    }
}
