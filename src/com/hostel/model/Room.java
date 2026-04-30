package com.hostel.model;

public class Room {
    private String roomNumber;
    private String block;
    private String type;
    private int capacity;
    private int occupied;

    public Room(String roomNumber, String block, String type, int capacity) {
        this.roomNumber = roomNumber;
        this.block = block;
        this.type = type;
        this.capacity = capacity;
        this.occupied = 0;
    }

    public String getRoomNumber() { return roomNumber; }
    public String getBlock() { return block; }
    public String getType() { return type; }
    public int getCapacity() { return capacity; }
    public int getOccupied() { return occupied; }
    public int getAvailableBeds() { return capacity - occupied; }
    public boolean hasSpace() { return occupied < capacity; }
    public void occupyBed() { if (hasSpace()) occupied++; }
    public void vacateBed() { if (occupied > 0) occupied--; }
}
