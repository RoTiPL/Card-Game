package com.skkujava;

import java.util.Scanner;

public class JavaProject {

    public static void main(String[] args) {
	// write your code here
        System.out.println("1. Single Play");
        System.out.println("2. Multi Play");
        System.out.println("3. Ranking");
        System.out.println("4. Exit Game");

        Scanner input = new Scanner(System.in);

        String userInput = input.next();
        do {
            if (userInput.trim().equals("1")) {

            } else if (userInput.trim().equals("2")) {

            } else if (userInput.trim().equals("3")) {

            } else if (userInput.trim().equals("4")) {
                break;
            } else {
                System.out.println("Invalid input! Please re-input!");
            }

            userInput = input.next();
        }while(true);
    }
}
