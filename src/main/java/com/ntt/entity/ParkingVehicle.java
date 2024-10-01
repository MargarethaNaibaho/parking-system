package com.ntt.entity;

public class ParkingVehicle {
    private int slot;
    private String registrationNumber;
    private String type;
    private String color;

    public ParkingVehicle(int slot, String registrationNumber, String type, String color) {
        this.slot = slot;
        this.registrationNumber = registrationNumber;
        this.type = type;
        this.color = color;
    }

    public ParkingVehicle(){}

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
