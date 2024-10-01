package com.ntt.service;

import com.ntt.entity.ParkingVehicle;
import com.ntt.repository.ParkingRepository;

import java.util.List;

public class ParkingService {
    protected ParkingRepository repository;

    public ParkingService(ParkingRepository repository) {
        this.repository = repository;
    }

    public ParkingService(){}

    public void create(ParkingVehicle parkingVehicle){
        this.repository.createParkingVehicle(parkingVehicle);
    }

    public void truncateTable(){
        this.repository.truncateTable();
    }

    public void leaveSlot(int slotNumber){
        this.repository.deleteSlot(slotNumber);
    }

    public List<ParkingVehicle> getAllVehicles(){
        return this.repository.getAllVehicles();
    }

    public int countParked(String type){
        return this.repository.countParked(type);
    }

    public List<String> countParkedByColor(String color){
        return this.repository.countParkedByColor(color);
    }

    public Integer findSlotByRegistrationNumber(String registrationNumber){
        return this.repository.findSlotByRegistrationNumber(registrationNumber);
    }

    public List<Integer> findSlotNumberByColor(String color){
        return this.repository.findSlotNumberByColor(color);
    }
}
