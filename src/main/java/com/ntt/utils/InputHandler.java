package com.ntt.utils;

import java.util.Scanner;

public class InputHandler {
    private Scanner scanner;

    public InputHandler(){
        this.scanner = new Scanner(System.in);
    }

    public int getInt(String prompt){
        System.out.print(prompt);

        while(!scanner.hasNextInt()){
            System.out.println("Input must be integer!");
            scanner.next();
            System.out.print(prompt);
        }

        int result = scanner.nextInt();
        scanner.nextLine();

        return result;
    }

    public String getString(String prompt){
        System.out.print(prompt);
        return scanner.nextLine();
    }
}
