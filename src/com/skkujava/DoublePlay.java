package com.skkujava;

import java.util.ArrayList;

public class DoublePlay extends Game {
    private Player p1, p2;
    DoublePlay(){
        int temp;
        System.out.println("Player 1을 설정합니다.\n직업을 선택해 주세요\n1. Warrior\n2. Thief");
        do{
            temp = scanner.nextInt();
            if (temp == 1){
                p1 = new Warrior();
                break;
            }
            else if (temp == 2){
                p1 = new Thief();
                break;
            }
            else{
                System.out.println("Invalid Input");
            }
        }while(true);

        System.out.println("카드를 골라 주세요. 나오는 5장의 카드 중 한 장을 골라 덱에 넣습니다.");
        for(int i = 1; i <= 10; i++){
            System.out.printf("%d/10\n", i);
            AddRandomCardToPlayer(p1, 5, p1.grave);
            System.out.println();
        }
        clear();
        System.out.println("Player 2를 설정합니다.\n직업을 선택해 주세요\n1. Warrior\n2. Thief");
        do{
            temp = scanner.nextInt();
            if (temp == 1){
                p2 = new Warrior();
                break;
            }
            else if (temp == 2){
                p2 = new Thief();
                break;
            }
            else{
                System.out.println("Invalid Input");
            }
        }while(true);

        System.out.println("카드를 골라 주세요. 나오는 5장의 카드 중 한 장을 골라 덱에 넣습니다.");
        for(int i = 1; i <= 10; i++){
            System.out.printf("%d/10\n", i);
            AddRandomCardToPlayer(p2, 5, p2.grave);
            System.out.println();
        }
    }

    void Play(){
        boolean flag;
        int res;
        do{
            System.out.println("Player 1의 차례입니다.");
            DrawCard(p1);
            if(p1 instanceof Warrior)
                p1.setDrawCount(5);
            else p1.setDrawCount(7);
            do {
                System.out.printf("%-15s%s\n%-4s%-10s%-4s%s\n",
                        "Player 1", "Player 2", "HP : ", p1.getHp() + "/" + p1.getMaxHp(),
                        "HP : ", p2.getHp() + "/" + p2.getMaxHp());
                System.out.printf("%-8s%-7s%-8s%-7s\n",
                        "Armor :", p1.getArmor(), "Armor :", p2.getArmor());
                System.out.printf("%-7s%s\n",
                        "Mana :", p1.getMana() + "/" + p1.getMaxMana());

                String debuff;
                if (p1.isPoisoned())
                    debuff = String.format("%-15s", "Poison");
                else debuff = String.format("%-15s", "");
                if (p2.isPoisoned())
                    debuff += String.format("%-15s", "Poison");

                System.out.println(debuff);
                System.out.println("사용할 카드를 입력해 주세요, 0을 입력 시 턴을 종료합니다");
                for (int i = 0; i < p1.hand.size(); i++) {
                    System.out.printf("%d : Cost %d │ %-15s │ %s\n",
                            i + 1, p1.hand.get(i).cost, p1.hand.get(i).name, p1.hand.get(i).cardDescription());
                }
                int input;
                do {
                    input = scanner.nextInt();

                    if (input == 0) break;
                    else if (input < 0 || input > p1.hand.size()) {
                        System.out.println("Invalid input! Please enter again");
                    } else if (p1.getMana() < p1.hand.get(input - 1).cost) {
                        System.out.println("마나가 부족합니다.");
                    } else break;
                } while (true);

                if (input == 0) break;

                if(PlayCard(p1, p2, --input) == 1){
                    break;
                }
            }while(true);
            res = TurnEnd(p1, p2);
            if(res == 1){
                flag = true;
                break;
            }
            else if(res == 2){
                flag = false;
                break;
            }

            p2.setMana(p2.getMaxMana() + p2.getBonusMana());
            p2.setArmor(0);
            p2.setBonusMana(0);

            System.out.println("Player 2의 차례입니다.");
            DrawCard(p2);
            if(p2 instanceof Warrior)
                p2.setDrawCount(5);
            else p2.setDrawCount(7);
            do {
                System.out.printf("%-15s%s\n%-4s%-10s%-4s%s\n",
                        "Player 1", "Player 2", "HP : ", p1.getHp() + "/" + p1.getMaxHp(),
                        "HP : ", p2.getHp() + "/" + p2.getMaxHp());
                System.out.printf("%-8s%-7s%-8s%-7s\n",
                        "Armor :", p1.getArmor(), "Armor :", p2.getArmor());
                System.out.printf("%-7s%s\n",
                        "Mana :", p2.getMana() + "/" + p2.getMaxMana());

                String debuff;
                if (p1.isPoisoned())
                    debuff = String.format("%-15s", "Poison");
                else debuff = String.format("%-15s", "");
                if (p2.isPoisoned())
                    debuff += String.format("%-15s", "Poison");

                System.out.println(debuff);
                System.out.println("사용할 카드를 입력해 주세요, 0을 입력 시 턴을 종료합니다");
                for (int i = 0; i < p2.hand.size(); i++) {
                    System.out.printf("%d : Cost %d │ %-15s │ %s\n",
                            i + 1, p2.hand.get(i).cost, p2.hand.get(i).name, p2.hand.get(i).cardDescription());
                }
                int input;
                do {
                    input = scanner.nextInt();

                    if (input == 0) break;
                    else if (input < 0 || input > p2.hand.size()) {
                        System.out.println("Invalid input! Please enter again");
                    } else if (p2.getMana() < p2.hand.get(input - 1).cost) {
                        System.out.println("마나가 부족합니다.");
                    } else break;
                } while (true);

                if (input == 0) break;

                if(PlayCard(p2, p1, --input) == 1){
                    break;
                }
            }while(true);

            res = TurnEnd(p2, p1);
            if(res == 1){
                flag = false;
                break;
            }
            else if(res==2){
                flag = true;
                break;
            }

            p1.setMana(p1.getMaxMana() + p1.getBonusMana());
            p1.setBonusMana(0);
            p1.setArmor(0);
        }while(true);

        if(flag){
            System.out.println("Player 1 Win!");
        }
        else{
            System.out.println("Player 2 Win!");
        }
    }

}
