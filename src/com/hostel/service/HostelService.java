package com.hostel.service;

import com.hostel.exception.HostelException;
import com.hostel.model.Complaint;
import com.hostel.model.FeeRecord;
import com.hostel.model.Room;
import com.hostel.model.Student;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HostelService {
    private final Map<String, Student> students = new LinkedHashMap<>();
    private final Map<String, Room> rooms = new LinkedHashMap<>();
    private final List<Complaint> complaints = new ArrayList<>();
    private final Map<String, FeeRecord> feeRecords = new LinkedHashMap<>();
    private int complaintCounter = 1;

    public Collection<Student> getAllStudents() { return students.values(); }
    public Collection<Room> getAllRooms() { return rooms.values(); }
    public List<Complaint> getAllComplaints() { return complaints; }
    public Collection<FeeRecord> getAllFeeRecords() { return feeRecords.values(); }

    public void addStudent(Student student) throws HostelException {
        validateStudent(student);
        if (students.containsKey(student.getStudentId())) {
            throw new HostelException("Student ID already exists.");
        }
        students.put(student.getStudentId(), student);
        feeRecords.put(student.getStudentId(), new FeeRecord(student.getStudentId(), 120000.0, 0.0));
    }

    public void addRoom(Room room) throws HostelException {
        validateRoom(room);
        if (rooms.containsKey(room.getRoomNumber())) {
            throw new HostelException("Room already exists.");
        }
        rooms.put(room.getRoomNumber(), room);
    }

    public void allocateRoom(String studentId, String roomNumber) throws HostelException {
        studentId = normalize(studentId);
        roomNumber = normalize(roomNumber);

        Student student = students.get(studentId);
        Room room = rooms.get(roomNumber);

        if (student == null) throw new HostelException("Student not found.");
        if (room == null) throw new HostelException("Room not found.");
        if (!"Not Assigned".equals(student.getRoomNumber())) {
            throw new HostelException("Student already has a room. Use transfer room instead.");
        }
        if (!room.hasSpace()) throw new HostelException("Room is full.");

        room.occupyBed();
        student.setRoomNumber(roomNumber);
    }

    public void transferRoom(String studentId, String newRoomNumber) throws HostelException {
        studentId = normalize(studentId);
        newRoomNumber = normalize(newRoomNumber);

        Student student = students.get(studentId);
        Room newRoom = rooms.get(newRoomNumber);

        if (student == null) throw new HostelException("Student not found.");
        if (newRoom == null) throw new HostelException("New room not found.");
        if ("Not Assigned".equals(student.getRoomNumber())) {
            throw new HostelException("Student has no current room. Use allocate room first.");
        }
        if (student.getRoomNumber().equals(newRoomNumber)) {
            throw new HostelException("Student is already assigned to this room.");
        }
        if (!newRoom.hasSpace()) throw new HostelException("Selected room is full.");

        String oldRoomNumber = student.getRoomNumber();
        Room oldRoom = rooms.get(oldRoomNumber);
        if (oldRoom != null) oldRoom.vacateBed();

        newRoom.occupyBed();
        student.setRoomNumber(newRoomNumber);
    }

    public void addComplaint(String studentId, String category, String description) throws HostelException {
        studentId = normalize(studentId);
        category = normalize(category);
        description = normalize(description);

        if (studentId.isEmpty() || category.isEmpty() || description.isEmpty()) {
            throw new HostelException("All complaint fields are required.");
        }
        if (!students.containsKey(studentId)) throw new HostelException("Student not found.");
        complaints.add(new Complaint(complaintCounter++, studentId, category, description));
    }

    public void updateComplaintStatus(int complaintId, String status) throws HostelException {
        status = normalize(status);
        for (Complaint complaint : complaints) {
            if (complaint.getComplaintId() == complaintId) {
                complaint.setStatus(status);
                return;
            }
        }
        throw new HostelException("Complaint not found.");
    }

    public void addPayment(String studentId, double amount) throws HostelException {
        studentId = normalize(studentId);
        FeeRecord record = feeRecords.get(studentId);
        if (record == null) throw new HostelException("Fee record not found.");
        if (amount <= 0) throw new HostelException("Payment must be greater than zero.");
        if (amount > record.getDueAmount()) throw new HostelException("Payment cannot exceed due amount.");
        record.addPayment(amount);
    }

    public int getTotalStudents() { return students.size(); }
    public int getTotalRooms() { return rooms.size(); }
    public int getOccupiedRooms() {
        int count = 0;
        for (Room room : rooms.values()) {
            if (room.getOccupied() > 0) count++;
        }
        return count;
    }
    public int getOpenComplaints() {
        int count = 0;
        for (Complaint complaint : complaints) {
            if ("Open".equalsIgnoreCase(complaint.getStatus())) count++;
        }
        return count;
    }

    private void validateStudent(Student student) throws HostelException {
        if (student == null) throw new HostelException("Student details are missing.");
        if (normalize(student.getStudentId()).isEmpty() || normalize(student.getName()).isEmpty() || normalize(student.getCourse()).isEmpty()
                || normalize(student.getPhone()).isEmpty() || normalize(student.getGuardianName()).isEmpty() || normalize(student.getGuardianPhone()).isEmpty()) {
            throw new HostelException("All student fields are required.");
        }
        if (student.getAge() <= 0 || student.getAge() > 100) {
            throw new HostelException("Enter a valid age.");
        }
    }

    private void validateRoom(Room room) throws HostelException {
        if (room == null) throw new HostelException("Room details are missing.");
        if (normalize(room.getRoomNumber()).isEmpty() || normalize(room.getBlock()).isEmpty() || normalize(room.getType()).isEmpty()) {
            throw new HostelException("All room fields are required.");
        }
        if (room.getCapacity() <= 0) {
            throw new HostelException("Room capacity must be greater than zero.");
        }
    }

    private String normalize(String value) {
        return value == null ? "" : value.trim();
    }
}
