package com.hostel.ui;

import com.hostel.exception.HostelException;
import com.hostel.model.Room;
import com.hostel.service.HostelService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class RoomsPanel extends JPanel {
    private final HostelService hostelService;
    private final DefaultTableModel tableModel;

    public RoomsPanel(HostelService hostelService) {
        this.hostelService = hostelService;
        setLayout(new BorderLayout(18, 18));
        setBackground(UITheme.BG);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel header = new JPanel(new BorderLayout());
        header.setOpaque(false);
        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new BoxLayout(text, BoxLayout.Y_AXIS));
        text.add(UITheme.title("Room Management"));
        text.add(Box.createVerticalStrut(6));
        text.add(UITheme.subtitle("Add rooms and allocate or transfer room assignments"));

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        actions.setOpaque(false);
        JButton addRoom = UITheme.button("Add Room", UITheme.ACCENT_2);
        JButton allocateRoom = UITheme.button("Allocate Room", UITheme.ACCENT);
        JButton transferRoom = UITheme.button("Transfer Room", new Color(108, 92, 231));
        addRoom.addActionListener(e -> openAddRoomDialog());
        allocateRoom.addActionListener(e -> openAllocateDialog());
        transferRoom.addActionListener(e -> openTransferDialog());
        actions.add(addRoom);
        actions.add(allocateRoom);
        actions.add(transferRoom);

        header.add(text, BorderLayout.WEST);
        header.add(actions, BorderLayout.EAST);

        tableModel = new DefaultTableModel(new String[]{"Room", "Block", "Type", "Capacity", "Occupied", "Available Beds"}, 0) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable table = new JTable(tableModel);
        table.setRowHeight(24);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));

        add(header, BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        refreshTable();
    }

    private void openAddRoomDialog() {
        JTextField room = UITheme.textField();
        JTextField block = UITheme.textField();
        JTextField type = UITheme.textField();
        JTextField capacity = UITheme.textField();
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.add(new JLabel("Room Number")); panel.add(room);
        panel.add(new JLabel("Block")); panel.add(block);
        panel.add(new JLabel("Type")); panel.add(type);
        panel.add(new JLabel("Capacity")); panel.add(capacity);

        int option = JOptionPane.showConfirmDialog(this, panel, "Add Room", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                hostelService.addRoom(new Room(room.getText().trim(), block.getText().trim(), type.getText().trim(), Integer.parseInt(capacity.getText().trim())));
                refreshTable();
                JOptionPane.showMessageDialog(this, "Room added successfully.");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Capacity must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (HostelException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void openAllocateDialog() {
        JTextField studentId = UITheme.textField();
        JTextField roomNumber = UITheme.textField();
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.add(new JLabel("Student ID")); panel.add(studentId);
        panel.add(new JLabel("Room Number")); panel.add(roomNumber);

        int option = JOptionPane.showConfirmDialog(this, panel, "Allocate Room", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                hostelService.allocateRoom(studentId.getText().trim(), roomNumber.getText().trim());
                refreshTable();
                JOptionPane.showMessageDialog(this, "Room allocated successfully.");
            } catch (HostelException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void openTransferDialog() {
        JTextField studentId = UITheme.textField();
        JTextField roomNumber = UITheme.textField();
        JPanel panel = new JPanel(new GridLayout(0, 2, 10, 10));
        panel.add(new JLabel("Student ID")); panel.add(studentId);
        panel.add(new JLabel("New Room Number")); panel.add(roomNumber);

        int option = JOptionPane.showConfirmDialog(this, panel, "Transfer Room", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                hostelService.transferRoom(studentId.getText().trim(), roomNumber.getText().trim());
                refreshTable();
                JOptionPane.showMessageDialog(this, "Room transferred successfully.");
            } catch (HostelException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void refreshTable() {
        tableModel.setRowCount(0);
        for (Room r : hostelService.getAllRooms()) {
            tableModel.addRow(new Object[]{r.getRoomNumber(), r.getBlock(), r.getType(), r.getCapacity(), r.getOccupied(), r.getAvailableBeds()});
        }
    }
}
