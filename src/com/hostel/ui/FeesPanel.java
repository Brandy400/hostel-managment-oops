package com.hostel.ui;

import com.hostel.exception.HostelException;
import com.hostel.model.FeeRecord;
import com.hostel.service.HostelService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class FeesPanel extends JPanel {
    private final HostelService hostelService;
    private final DefaultTableModel tableModel;

    public FeesPanel(HostelService hostelService) {
        this.hostelService = hostelService;
        setLayout(new BorderLayout(18, 18));
        setBackground(UITheme.BG);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.add(UITheme.title("Fee Management"));
        text.add(Box.createVerticalStrut(6));
        text.add(UITheme.subtitle("Track payments and outstanding hostel fee balances"));

        JButton paymentButton = UITheme.button("Add Payment", UITheme.ACCENT_2);
        paymentButton.addActionListener(e -> openPaymentDialog());

        header.add(text, BorderLayout.WEST);
        header.add(paymentButton, BorderLayout.EAST);

        tableModel = new DefaultTableModel(new String[]{"Student ID", "Total Fee", "Paid Amount", "Due Amount"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable table = new JTable(tableModel);
        table.setRowHeight(24);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));

        add(header, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        refreshTable();
    }

    private void openPaymentDialog() {
        JTextField studentId = UITheme.textField();
        JTextField amount = UITheme.textField();
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.add(new JLabel("Student ID")); panel.add(studentId);
        panel.add(new JLabel("Payment Amount")); panel.add(amount);

        int option = JOptionPane.showConfirmDialog(this, panel, "Add Payment", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                hostelService.addPayment(studentId.getText().trim(), Double.parseDouble(amount.getText().trim()));
                refreshTable();
                JOptionPane.showMessageDialog(this, "Payment added successfully.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Payment amount must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (HostelException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        for (FeeRecord f : hostelService.getAllFeeRecords()) {
            tableModel.addRow(new Object[]{f.getStudentId(), f.getTotalFee(), f.getPaidAmount(), f.getDueAmount()});
        }
    }
}
