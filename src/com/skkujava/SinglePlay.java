package com.skkujava;

import java.util.ArrayList;
import java.util.Random;

class SinglePlay extends Game {
    private Player player;
    private Boss boss;

    private int floor;

    SinglePlay(){
        floor = 0;
        int temp;
        do {
            System.out.println("Select your class.\n1. Warrior\n2. Thief");
            temp = scanner.nextInt();
            if (temp == 1){
                player = new Warrior();
                break;
            }
            else if (temp == 2){
                player = new Thief();
                break;
            }
            else{
                System.out.println("Invalid Input");
            }
        }while(true);

        boss = Boss.CreateBoss(player, floor);
        random = new Random();

        for(int i=0; i<5; i++){
            player.deck.add(new Strike());
            player.deck.add(new Defend());
        }

    }

    void Play(){
        do {
            DrawCard(player);
            do {

                System.out.printf("Floor %d\n\n", floor);

                System.out.printf("%-15s%s\n%-4s%-10s%-4s%s\n",
                        "Player", boss.name, "HP : ", player.getHp() + "/" + player.getMaxHp(),
                        "HP : ", boss.getHp() + "/" + boss.getMaxHp());
                System.out.printf("%-8s%-7s%-8s%-7s\n",
                        "Armor :", player.getArmor(), "Armor :", boss.getArmor());
                System.out.printf("%-7s%s\n",
                        "Mana :", player.getMana() + "/" + player.getMaxMana());

                String debuff;
                if (player.isPoisoned())
                    debuff = String.format("Poison %-7d", player.getPoisonDamage());
                else debuff = String.format("%-15s", "");
                if (boss.isPoisoned())
                    debuff += String.format("Poison %-7d", boss.getPoisonDamage());

                System.out.println(debuff);

                if(boss.getStrength() > 0){
                    System.out.printf("%15s%s%d\n", "", "Strength +", boss.getStrength());
                }
                System.out.println("Input the card number to use. 0: Turn end");
                for(int i=0; i<player.hand.size(); i++){
                    System.out.printf("%d : Cost %d │ %-15s │ %s\n",
                            i+1, player.hand.get(i).cost, player.hand.get(i).name, player.hand.get(i).cardDescription());
                }
                int input;
                do {
                    input = scanner.nextInt();

                    if(input == 0)break;
                    else if (input < 0 || input > player.hand.size()){
                        System.out.println("Invalid input! Please enter again");
                    }
                    else if(player.getMana() < player.hand.get(input - 1).cost){
                        System.out.println("Not enough mana.");
                    }
                    else break;
                } while(true);

                if(input == 0)break;

                if(PlayCard(player, boss, --input) == 1){
                    break;
                }
            } while (true);
            if(TurnEnd() == 1) break;

        } while(true);
        //TODO: ranking

        System.out.print("Please input your nickname: ");
        scanner.nextLine();
        String nickname = scanner.nextLine();
        Ranking.uploadRanking(nickname, floor, player.name);
        Ranking.loadRanking();
    }

    private int TurnEnd(){
        if(player.getHp() <= 0){
            return 1;
        }
        boss.setArmor(0);
        int res = super.TurnEnd(player, boss, true);
        if(res == 1) {
            // Game Clear 시키기
            player.grave.addAll(player.deck);
            player.deck.clear();
            ++floor;
            boss = Boss.CreateBoss(player, floor);
            player.completeFloor();
            GetReward();
            player.setArmor(0);
            player.setPoisoned(false);
            player.setPoisonDamage(0);
            player.setMana(player.getMaxMana());
            return 2;
        }
        else if(res == 2){
            return 1;
        }
        else{
            boss.Action();
            if(player.getHp() <= 0){
                return 1;
            }
            player.setArmor(0);
            player.setMana(player.getMaxMana() + player.getBonusMana());
            player.setBonusMana(0);
            return 0;
        }
    }



    private void GetReward(){
        boolean flag = true;
        System.out.println("Congratulation! You cleared floor " + floor + "!");
        System.out.println("1: Add one random card to your deck.\n2: Remove one card from your deck.\n" +
                "3: Heal perfectly.(You will heal 1/3 of your HP unless you select this one.)\n4: Reinforce one card of your deck.");
        String userInput = scanner.next();
        do {
            if (userInput.trim().equals("1")) {                 //카드 덱에 추가
                AddRandomCardToPlayer(player, 3);
                break;
            } else if (userInput.trim().equals("2")) {              //카드 버리기
                Card card;
                do {
                    System.out.println("Select the card to remove.");
                    for (int i = 0; i < player.grave.size(); i++) {
                        card = player.grave.get(i);
                        System.out.printf("%d: %-7s│%s", i + 1, card.name, card.cardDescription());
                    }
                    int inp;
                    do {
                        inp = scanner.nextInt();
                        if (inp <= 0 || inp > player.deck.size()) {
                            System.out.println("Invalid input! Please re-input!");
                        } else break;
                    } while (true);

                    card = player.grave.get(inp);

                    System.out.println("Are you sure remove this card? (Y/N)");
                    System.out.printf("%-7s│%s\n", card.name, card.cardDescription());

                    do{
                        userInput = scanner.next();
                        if(userInput.equals("Y")){
                            player.grave.remove(inp);
                            flag = false;
                            break;
                        } else if(userInput.equals("N")){
                            break;
                        } else {
                            System.out.println("Invalid input! Please re-input!");
                        }
                    } while(true);

                }while(flag);

                break;
            } else if (userInput.trim().equals("3")) {                  //체력 회복
                player.setHp(player.getMaxHp());
                break;
            } else if (userInput.trim().equals("4")) {                  //카드 강화
                Card card;
                ArrayList<Card> temp = new ArrayList<>();
                for(int i=0; i<player.grave.size(); ++i){
                    card = player.grave.get(i);
                    if(card.reinforced){
                        player.grave.remove(i);
                        temp.add(card);
                        --i;
                    }
                }
                do {
                    System.out.println("Select the card to reinforce.");

                    for (int i = 0; i < player.grave.size(); ++i) {
                        card = player.grave.get(i);
                        System.out.printf("%d: %-7s│%s", i + 1, card.name, card.cardDescription());
                    }
                    int inp;
                    do {
                        inp = scanner.nextInt();
                        if (inp <= 0 || inp > player.deck.size()) {
                            System.out.println("Invalid input! Please re-input!");
                        } else break;
                    } while (true);

                    card = player.grave.get(inp);
                    Card clone;
                    try {
                        clone = (Card)card.clone();
                    } catch (CloneNotSupportedException e){
                        e.printStackTrace();
                        return;
                    }
                    clone.reinforce();
                    System.out.println("Are you sure reinforce this card? (Y/N)");
                    System.out.printf("%-7s│%s\n%-15s\n%-7s│%s\n", card.name, card.cardDescription(), "↓", clone.name, clone.cardDescription());

                    do{
                        userInput = scanner.next();
                        if(userInput.equals("Y")){
                            card.reinforce();
                            flag = false;
                            break;
                        } else if(userInput.equals("N")){
                            break;
                        } else {
                            System.out.println("Invalid input! Please re-input!");
                        }
                    } while(true);

                    player.grave.addAll(temp);
                }while(flag);
            } else {
                System.out.println("Invalid input! Please re-input!");
            }

            userInput = scanner.next();
        }while(true);

        player.setHp((player.getMaxHp()/3 + player.getHp()) > player.getMaxHp() ? player.getMaxHp() : (player.getMaxHp()/3 + player.getHp()));
    }
}