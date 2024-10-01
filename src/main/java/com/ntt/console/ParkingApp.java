package com.ntt.console;

import com.ntt.entity.ParkingVehicle;
import com.ntt.service.ParkingService;
import com.ntt.utils.InputHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingApp {
    private ParkingService parkingService;
    private InputHandler inputHandler;
    private boolean isClose = false;
    private Integer slot = -1;
    private Integer slotAvailable = -1;
    private Map<Integer, Integer> parkingSlot = new HashMap<>();

    public ParkingApp(ParkingService parkingService, InputHandler inputHandler) {
        this.parkingService = parkingService;
        this.inputHandler = inputHandler;

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            parkingService.truncateTable();
        }));
    }

    public ParkingApp(){}

    public void run(){
        while(!this.isClose){
            showMenu();
            int choice = inputHandler.getInt("Enter your choice [eg: 1]: ");

            switch(choice){
                case 1:
                    createParking();
                    closeApp();
                    break;
                case 2:
                    parkingVehicle();
                    closeApp();
                    break;
                case 3:
                    leaveParkingSlot();
                    closeApp();
                    break;
                case 4:
                    listAllVehicles();
                    closeApp();
                    break;
                case 5:
                    countParkedCar();
                    closeApp();
                    break;
                case 6:
                    countParkedMotorCycle();
                    closeApp();
                    break;
                case 7:
                    countParkedVehiclesByColor();
                    closeApp();
                    break;
                case 8:
                    listOddPlate();
                    closeApp();
                    break;
                case 9:
                    listEvenPlate();
                    closeApp();
                    break;
                case 10:
                    findSlotNumberByColor();
                    closeApp();
                    break;
                case 11:
                    findVehicleByRegistrationNumber();
                    closeApp();
                    break;
                case 12:
                    System.out.println("Exit");
                    closeApp();
                    this.isClose = true;
                    break;
                default:
                    System.out.println("=".repeat(100));
                    System.out.println("Invalid Choice!");
                    System.out.println("=".repeat(100));
                    break;
            }
        }
    }

    private void showMenu(){
        System.out.println("=".repeat(100));
        System.out.println("Menu Parking System");
        System.out.println("=".repeat(100));
        System.out.println("1. Create Parking");
        System.out.println("2. Parking Vehicle");
        System.out.println("3. Leave A Parking Slot");
        System.out.println("4. List All Parked Vehicles");
        System.out.println("5. Count Parked Car");
        System.out.println("6. Count Parked Motor Cycle");
        System.out.println("7. Registration Numbers By Color");
        System.out.println("8. Registration Numbers By Odd Plate");
        System.out.println("9. Registration Numbers By Even Plate");
        System.out.println("10. Slot Numbers By Color");
        System.out.println("11. Slot Number By Registration Number");
        System.out.println("12. Exit");
        System.out.println("=".repeat(100));
    }

    private void closing(){
        System.out.println("=".repeat(100));
        System.out.println("Thank you for using this application! All of the data has been deleted everytime you exit! See you and have a good day!");
        System.out.println("=".repeat(100));
    }

    private boolean closeApp(){
        while(true){
            System.out.println("=".repeat(100));
            String choice = inputHandler.getString("Do you still want to use this application? [Y / N]: ");

            if(choice.equalsIgnoreCase("y")){
                this.isClose = false;
                return isClose;
            } else if(choice.equalsIgnoreCase("n")){
                parkingService.truncateTable();
                closing();
                this.isClose = true;
                return isClose;
            } else {
                System.out.println("=".repeat(100));
                System.out.println("Invalid input!");
                System.out.println("=".repeat(100));
            }

        }
    }

    private void createParking(){
        int slotInput;
        System.out.println("=".repeat(100));
        System.out.println("Menu Create Parking");
        System.out.println("=".repeat(100));

        if(slot < 0 && slotAvailable < 0) {
            slotInput = inputHandler.getInt("Input slots: ");
            this.slot = slotInput;
            this.slotAvailable = slotInput;

            for(int i = 1; i <= slotInput; i++){
                this.parkingSlot.put(i, 0);
            }

            System.out.println("Created a parking lot with " + this.slot + " slots");
        } else{
            System.out.println("Failed! You've already input the slots!");
        }
    }

    private void parkingVehicle(){
        if(slot < 0 && slotAvailable < 0){
            System.out.println("You still don't create parking slot. Go to menu 1 First");
            return;
        }

        if(slotAvailable == 0){
            System.out.println("Sorry, parking lot is full");
            return;
        }

        System.out.println("=".repeat(100));
        System.out.println("Menu Parking Vehicle");
        System.out.println("=".repeat(100));

        String userInput = inputHandler.getString("What's the vehicle detail? [e.g: B-9999-XYZ Putih Motor]: ");
        String[] userInputArray = userInput.split("[\\s]+");

        if(userInputArray[2].equalsIgnoreCase("motor") || userInputArray[2].equalsIgnoreCase("mobil")){
            int slotNumber = -1;
            for(int i = 1; i <= this.slot; i++){
                if(parkingSlot.get(i) == 0){
                    slotNumber = i;
                    break;
                }
            }

            ParkingVehicle parkingVehicle = new ParkingVehicle(slotNumber, userInputArray[0], userInputArray[2], userInputArray[1]);
            parkingService.create(parkingVehicle);
            slotAvailable--;
            parkingSlot.put(slotNumber, 1);
            System.out.println("Allocated slot number: " + slotNumber);
        } else{
            System.out.println("It seems that your vehicle is not a 'Motor' or a 'Mobil'");
        }
    }

    private void leaveParkingSlot(){
        if(slot < 0 && slotAvailable < 0){
            System.out.println("You still don't create parking slot. Go to menu 1 First");
            return;
        }
        if(slot == slotAvailable){
            System.out.println("Parking lot seems empty");
            return;
        }
        System.out.println("=".repeat(100));
        System.out.println("Menu Leaving Parking Lot");
        System.out.println("=".repeat(100));

        Integer lotWillLeave = inputHandler.getInt("Which parking lot will be empty? [e.g: 1]: ");

        if(!parkingSlot.containsKey(lotWillLeave)){
            System.out.println("Your lot number isn't available");
            return;
        }

        parkingService.leaveSlot(lotWillLeave);
        slotAvailable++;
        parkingSlot.put(lotWillLeave, 0);
        System.out.println("Slot number " + lotWillLeave + " is free");
    }

    private void listAllVehicles(){
        if(slot < 0 && slotAvailable < 0){
            System.out.println("You still don't create parking slot. Go to menu 1 First");
            return;
        }
        if(slot == slotAvailable){
            System.out.println("Parking lot seems empty");
            return;
        }

        System.out.println("=".repeat(100));
        System.out.println("Menu List All Vehicles");
        System.out.println("=".repeat(100));

        System.out.println("=".repeat(100));
        System.out.printf("%-5s %-15s %-10s %-10s%n", "Slot", "Registration No", "Type", "Color");
        System.out.println("=".repeat(100));

        for(ParkingVehicle parkingVehicle : parkingService.getAllVehicles()){
            System.out.printf("%-5d %-15s %-10s %-10s%n",
                    parkingVehicle.getSlot(),
                    parkingVehicle.getRegistrationNumber(),
                    parkingVehicle.getType(),
                    parkingVehicle.getColor());
        }
        System.out.println("=".repeat(100));

    }

    private void countParkedCar(){
        if(slot < 0 && slotAvailable < 0){
            System.out.println("You still don't create parking slot. Go to menu 1 First");
            return;
        }
        if(slot == slotAvailable){
            System.out.println("Parking lot seems empty");
            return;
        }

        System.out.println(parkingService.countParked("Mobil"));
    }

    private void countParkedMotorCycle(){
        if(slot < 0 && slotAvailable < 0){
            System.out.println("You still don't create parking slot. Go to menu 1 First");
            return;
        }
        if(slot == slotAvailable){
            System.out.println("Parking lot seems empty");
            return;
        }

        System.out.println(parkingService.countParked("Motor"));
    }

    private void countParkedVehiclesByColor(){
        if(slot < 0 && slotAvailable < 0){
            System.out.println("You still don't create parking slot. Go to menu 1 First");
            return;
        }
        if(slot == slotAvailable){
            System.out.println("Parking lot seems empty");
            return;
        }

        String color = inputHandler.getString("Input the color: ");
        List<String> registrationNumbers = parkingService.countParkedByColor(color);

        if (registrationNumbers.isEmpty()) {
            System.out.println("No vehicles found with color: " + color);
        } else {
            String formattedOutput = String.join(", ", registrationNumbers);
            System.out.println(formattedOutput);
        }
    }

    private void listOddPlate(){
        if(slot < 0 && slotAvailable < 0){
            System.out.println("You still don't create parking slot. Go to menu 1 First");
            return;
        }
        if(slot == slotAvailable){
            System.out.println("Parking lot seems empty");
            return;
        }

        List<ParkingVehicle> parkingVehicleList = parkingService.getAllVehicles();
        List<String> registrationNumbers = new ArrayList<>();
        for(ParkingVehicle parkingVehicle : parkingVehicleList){
            String[] registrationNumberArray = parkingVehicle.getRegistrationNumber().split("-");
            try{
                int num = Integer.parseInt(registrationNumberArray[1]);
                if (num % 2 == 1) {
                    registrationNumbers.add(parkingVehicle.getRegistrationNumber());
                }
            } catch (NumberFormatException e){
                System.out.println("Not an integer!");
            }
        }

        if (registrationNumbers.isEmpty()) {
            System.out.println("No vehicles found with odd plate");
        } else {
            String formattedOutput = String.join(", ", registrationNumbers.toString());
            System.out.println(formattedOutput);
        }
    }

    private void listEvenPlate(){
        if(slot < 0 && slotAvailable < 0){
            System.out.println("You still don't create parking slot. Go to menu 1 First");
            return;
        }
        if(slot == slotAvailable){
            System.out.println("Parking lot seems empty");
            return;
        }

        List<ParkingVehicle> parkingVehicleList = parkingService.getAllVehicles();
        List<String> registrationNumbers = new ArrayList<>();
        for(ParkingVehicle parkingVehicle : parkingVehicleList){
            String[] registrationNumberArray = parkingVehicle.getRegistrationNumber().split("-");
            try{
                int num = Integer.parseInt(registrationNumberArray[1]);
                if (num % 2 == 0) {
                    registrationNumbers.add(parkingVehicle.getRegistrationNumber());
                }
            } catch (NumberFormatException e){
                System.out.println("Not an integer!");
            }
        }

        if (registrationNumbers.isEmpty()) {
            System.out.println("No vehicles found with even plate");
        } else {
            String formattedOutput = String.join(", ", registrationNumbers.toString());
            System.out.println(formattedOutput);
        }
    }

    private void findVehicleByRegistrationNumber(){
        if(slot < 0 && slotAvailable < 0){
            System.out.println("You still don't create parking slot. Go to menu 1 First");
            return;
        }
        if(slot == slotAvailable){
            System.out.println("Parking lot seems empty");
            return;
        }

        String registrationNumber = inputHandler.getString("Registration Number: ");

        Integer slotNum =  parkingService.findSlotByRegistrationNumber(registrationNumber);
        if(slotNum == null || slotNum == 0){
            System.out.println("Not Found");
        } else{
            System.out.println(slotNum);
        }
    }

    private void findSlotNumberByColor(){
        if(slot < 0 && slotAvailable < 0){
            System.out.println("You still don't create parking slot. Go to menu 1 First");
            return;
        }
        if(slot == slotAvailable){
            System.out.println("Parking lot seems empty");
            return;
        }

        String color = inputHandler.getString("Color: ");
        List<Integer> slotNumbers = parkingService.findSlotNumberByColor(color);
        if (slotNumbers.isEmpty() || slotNumbers.contains(0)) {
            System.out.println("No vehicles found with that color");
        } else {
            String formattedOutput = String.join(", ", slotNumbers.toString());
            System.out.println(formattedOutput);
        }
    }

}
