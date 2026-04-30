package com.hostel.model;

public class Student {
    private String studentId;
    private String name;
    private int age;
    private String course;
    private String phone;
    private String guardianName;
    private String guardianPhone;
    private String roomNumber;

    public Student(String studentId, String name, int age, String course, String phone, String guardianName, String guardianPhone) {
        this.studentId = studentId;
        this.name = name;
        this.age = age;
        this.course = course;
        this.phone = phone;
        this.guardianName = guardianName;
        this.guardianPhone = guardianPhone;
        this.roomNumber = "Not Assigned";
    }

    public String getStudentId() { return studentId; }
    public String getName() { return name; }
    public int getAge() { return age; }
    public String getCourse() { return course; }
    public String getPhone() { return phone; }
    public String getGuardianName() { return guardianName; }
    public String getGuardianPhone() { return guardianPhone; }
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
}
