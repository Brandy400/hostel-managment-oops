package com.hostel.ui;

import com.hostel.exception.HostelException;
import com.hostel.model.Student;
import com.hostel.service.HostelService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StudentsPanel extends JPanel {
    private final HostelService hostelService;
    private final DefaultTableModel tableModel;

    public StudentsPanel(HostelService hostelService) {
        this.hostelService = hostelService;
        setLayout(new BorderLayout(18, 18));
        setBackground(UITheme.BG);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.add(UITheme.title("Student Records"));
        text.add(Box.createVerticalStrut(6));
        text.add(UITheme.subtitle("Manage hostel student profiles and room status"));

        JButton addButton = UITheme.button("Add Student", UITheme.ACCENT_2);
        addButton.addActionListener(e -> openAddStudentDialog());
        header.add(text, BorderLayout.WEST);
        header.add(addButton, BorderLayout.EAST);

        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Age", "Course", "Phone", "Guardian", "Guardian Phone", "Room"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable table = new JTable(tableModel);
        table.setRowHeight(24);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));

        add(header, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        refreshTable();
    }

    private void openAddStudentDialog() {
        JTextField id = UITheme.textField();
        JTextField name = UITheme.textField();
        JTextField age = UITheme.textField();
        JTextField course = UITheme.textField();
        JTextField phone = UITheme.textField();
        JTextField guardian = UITheme.textField();
        JTextField guardianPhone = UITheme.textField();

        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.add(new JLabel("Student ID")); panel.add(id);
        panel.add(new JLabel("Name")); panel.add(name);
        panel.add(new JLabel("Age")); panel.add(age);
        panel.add(new JLabel("Course")); panel.add(course);
        panel.add(new JLabel("Phone")); panel.add(phone);
        panel.add(new JLabel("Guardian Name")); panel.add(guardian);
        panel.add(new JLabel("Guardian Phone")); panel.add(guardianPhone);

        int option = JOptionPane.showConfirmDialog(this, panel, "Add Student", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            try {
                Student student = new Student(id.getText().trim(), name.getText().trim(), Integer.parseInt(age.getText().trim()), course.getText().trim(), phone.getText().trim(), guardian.getText().trim(), guardianPhone.getText().trim());
                hostelService.addStudent(student);
                refreshTable();
                JOptionPane.showMessageDialog(this, "Student added successfully.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Age must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (HostelException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        for (Student s : hostelService.getAllStudents()) {
            tableModel.addRow(new Object[]{s.getStudentId(), s.getName(), s.getAge(), s.getCourse(), s.getPhone(), s.getGuardianName(), s.getGuardianPhone(), s.getRoomNumber()});
        }
    }
}
