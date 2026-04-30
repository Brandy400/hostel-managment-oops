package com.hostel.model;

public class FeeRecord {
    private String studentId;
    private double totalFee;
    private double paidAmount;

    public FeeRecord(String studentId, double totalFee, double paidAmount) {
        this.studentId = studentId;
        this.totalFee = totalFee;
        this.paidAmount = paidAmount;
    }

    public String getStudentId() { return studentId; }
    public double getTotalFee() { return totalFee; }
    public double getPaidAmount() { return paidAmount; }
    public double getDueAmount() { return totalFee - paidAmount; }
    public void addPayment(double amount) { this.paidAmount += amount; }
}
