package com.skkujava;

import java.util.Scanner;

public class JavaProject {

    public static void main(String[] args) {

        boolean isRanking = false;
        showLogo();
        System.out.printf("%38s%-14s\n", "", "1. Single Play");
        System.out.printf("%38s%-14s\n", "", "2. Multi Play");
        System.out.printf("%38s%-14s\n", "", "3. Ranking");
        System.out.printf("%38s%-14s\n", "", "4. Exit Game");
        Scanner input = new Scanner(System.in);
        Game game;

        Ranking.loadRanking();

        String userInput = input.next();
        do {
            if (userInput.trim().equals("1")) {
                game = new SinglePlay();
                ((SinglePlay) game).Play();
            } else if (userInput.trim().equals("2")) {
                game = new DoublePlay();
                ((DoublePlay) game).Play();
            } else if (userInput.trim().equals("3")) {
                isRanking = true;
                showLogo();
                Ranking.showRanking();
            } else if (userInput.trim().equals("4")) {
                break;
            } else {
                System.out.println("Invalid input! Please re-input!");
            }
            if(!isRanking) {
                showLogo();
            }
            isRanking = false;
            System.out.printf("%38s%-14s\n", "", "1. Single Play");
            System.out.printf("%38s%-14s\n", "", "2. Multi Play");
            System.out.printf("%38s%-14s\n", "", "3. Ranking");
            System.out.printf("%38s%-14s\n", "", "4. Exit Game");
            userInput = input.next();
        }while(true);


    }

    public static void showLogo() {
        System.out.println("==========================================================================================");
        System.out.println("      ■■■■■■■■■■   ■                                                                ");
        System.out.println("      ■            ■                                                                ");
        System.out.println("      ■            ■                                                                ");
        System.out.println("      ■            ■                                                                ");
        System.out.println("      ■■■■■■■■■■   ■   ■■■■■■■   ■     ■                                            ");
        System.out.println("               ■   ■         ■   ■     ■                                            ");
        System.out.println("               ■   ■   ■■■■■■■   ■■■■■■■      ■  ■                                  ");
        System.out.println("               ■   ■   ■     ■         ■     ■■■ ■    ■■                            ");
        System.out.println("      ■■■■■■■■■■   ■   ■■■■■■■         ■      ■  ■■■ ■■■■                           ");
        System.out.println("                                 ■■■■■■■      ■■ ■ ■  ■■                            ");
        System.out.println();
        System.out.println("                                        ■■■■■■■■■■             ■                    ");
        System.out.println("                                        ■                     ■■■                   ");
        System.out.println("                                        ■                      ■                    ");
        System.out.println("                                        ■                                           ");
        System.out.println("                                        ■■■■■■■■■■   ■■■■■■■   ■   ■         ■■■■■■■");
        System.out.println("                                                 ■   ■     ■   ■   ■■■■■■■   ■     ■");
        System.out.println("                                                 ■   ■     ■   ■   ■         ■■■■■■■");
        System.out.println("                                                 ■   ■■■■■■■   ■   ■         ■      ");
        System.out.println("                                        ■■■■■■■■■■   ■         ■   ■         ■■■■■■■");
        System.out.println("                                                     ■                              ");
        System.out.println("                                                     ■                              ");
        System.out.println("                                                     ■                              ");
        System.out.println("==========================================================================================");
    }
}
