package com.hostel.model;

public class Complaint {
    private int complaintId;
    private String studentId;
    private String category;
    private String description;
    private String status;

    public Complaint(int complaintId, String studentId, String category, String description) {
        this.complaintId = complaintId;
        this.studentId = studentId;
        this.category = category;
        this.description = description;
        this.status = "Open";
    }

    public int getComplaintId() { return complaintId; }
    public String getStudentId() { return studentId; }
    public String getCategory() { return category; }
    public String getDescription() { return description; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
