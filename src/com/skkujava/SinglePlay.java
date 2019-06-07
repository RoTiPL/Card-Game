package com.skkujava;

import java.util.ArrayList;
import java.util.Random;

class SinglePlay extends Game {
    private Player player;
    private Boss boss;

    private int floor;
    private ArrayList<Card> store;

    SinglePlay(){
        floor = 0;
        int temp;
        store = new ArrayList<>();
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
            store.add(new Strike());
            store.add(new Defend());
        }
        try {
            for (Card card : store) {
                player.grave.add((Card)card.clone());
            }
        }
        catch(CloneNotSupportedException e){
            e.printStackTrace();
        }
    }

    void Play(){


        do {
            DrawCard(player);
            do {
                // 가로 102(100)
                // | 공백(5) 캐릭터(38) 공백(14) 캐릭터(38) 공백(5) |
                System.out.println("┌────────────────────────────────────────────────────────────────────────────────────────────────────┐");
                System.out.printf("%c%51s%2d%48c\n", '│', "Floor", floor + 1, '│');
                System.out.printf("%c%24s%-6s%39s%-8s%24c\n", '│', "", "Player", "", boss.name, '│');
                System.out.printf("%c%24s%-5s%-7s%33s%-5s%-7s%20c\n",
                        '│', "", "HP : ", player.getHp() + "/" + player.getMaxHp(), "", "HP : ", boss.getHp() + "/" + boss.getMaxHp(), '│');

                System.out.printf("%c%24s%-8s%-2d%35s%-8s%-2d%22c\n",
                        '│', "", "Armor : ", player.getArmor(), "", "Armor : ", boss.getArmor(), '│');

                System.out.printf("%c%101c\n", '│', '│');

                for(int i = 0 ; i < 13; i++) {
                    System.out.printf("%c%5s%-38s%14s%-38s%6c\n", '│', "", player.getAsciiArt(i), "", boss.getAsciiArt(i), '│');
                }

                System.out.printf("%c%101c\n", '│', '│');

                String debuff = "│";
                if (player.isPoisoned())
                    debuff += String.format("%33s%-7d", "Poison : ", player.getPoisonDamage());
                else debuff += String.format("%40s", "");
                if (boss.isPoisoned())
                    debuff += String.format("%49s%-7d%5c", "Poison : ", boss.getPoisonDamage(), '│');
                else debuff += String.format("%61c", '│');
                System.out.println(debuff);

                String strength = "│";
                if (player.getStrength() > 0)
                    strength += String.format("%35s%-5d", "Strength : ", player.getStrength());
                else strength += String.format("%40s", "");
                if (boss.getStrength() > 0)
                    strength += String.format("%51s%-5d%5c", "Strength : ", boss.getStrength(), '│');
                else strength += String.format("%61c", '│');
                System.out.println(strength);

                String dexterity = "│";
                if (player.getDexterity() > 0)
                    dexterity += String.format("%36s%-4d", "Dexterity : ", player.getStrength());
                else dexterity += String.format("%40s", "");
                dexterity += String.format("%61c", '│');
                System.out.println(dexterity);
                System.out.println("├─────────┬──────────────────────────────────────────────────────────────────────────────────────────┘");
                System.out.println("│ Mana: " + player.getMana() + " │");
                System.out.println("└─────────┘");

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
        Ranking.uploadRanking(nickname, floor + 1, player.name);
        Ranking.loadRanking();
    }

    private int TurnEnd(){
        if(player.getHp() <= 0){
            return 1;
        }
        boss.setArmor(0);
        int res = super.TurnEnd(player, boss);
        if(res == 1) {
            // Game Clear 시키기
            boss = Boss.CreateBoss(player, floor);
            player.completeFloor();
            GetReward();
            ++floor;
            player.setArmor(0);
            player.setPoisoned(false);
            player.setPoisonDamage(0);
            player.setMana(player.getMaxMana());

            player.hand.clear();
            player.deck.clear();
            player.grave.clear();
            try {
                for (Card card : store) {
                    player.grave.add((Card) card.clone());
                }
            }
            catch(CloneNotSupportedException e){e.printStackTrace();}
            return 2;
        }
        else if(res == 2){
            return 1;
        }
        else{
            boss.Action();
            if(player.isPoisoned())
                player.TakePoisonDamage();
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
        System.out.println("Congratulation! You cleared floor " + (floor + 1) + "!");
        System.out.println("1: Add one random card to your deck.\n2: Remove one card from your deck.\n" +
                "3: Heal perfectly. (You will heal 1/3 of your HP unless you select this one.)\n4: Reinforce one card of your deck.");
        String userInput = scanner.next();
        do {
            if (userInput.trim().equals("1")) {                 //카드 덱에 추가
                AddRandomCardToPlayer(player, 3, store);
                break;
            } else if (userInput.trim().equals("2")) {              //카드 버리기
                Card card;
                do {
                    System.out.println("Select the card to remove.");
                    for (int i = 0; i < store.size(); i++) {
                        card = store.get(i);
                        System.out.printf("%d: %-7s│%s\n", i + 1, card.name, card.cardDescription());
                    }
                    int inp;
                    do {
                        inp = scanner.nextInt();
                        if (inp <= 0 || inp > store.size()) {
                            System.out.println("Invalid input! Please re-input!");
                        } else break;
                    } while (true);

                    card = store.get(--inp);

                    System.out.println("Are you sure remove this card? (Y/N)");
                    System.out.printf("%-7s│%s\n", card.name, card.cardDescription());

                    do{
                        userInput = scanner.next();
                        if(userInput.equals("Y")){
                            store.remove(inp);
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
                for(int i=0; i<store.size(); ++i){
                    card = store.get(i);
                    if(card.reinforced){
                        store.remove(i);
                        temp.add(card);
                        --i;
                    }
                }
                do {
                    System.out.println("Select the card to reinforce.");

                    for (int i = 0; i < store.size(); ++i) {
                        card = store.get(i);
                        System.out.printf("%d: %-7s│%s\n", i + 1, card.name, card.cardDescription());
                    }
                    int inp;
                    do {
                        inp = scanner.nextInt();
                        if (inp <= 0 || inp > store.size()) {
                            System.out.println("Invalid input! Please re-input!");
                        } else break;
                    } while (true);

                    card = store.get(--inp);
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

                    store.addAll(temp);
                }while(flag);
            } else {
                System.out.println("Invalid input! Please re-input!");
            }

            userInput = scanner.next();
        }while(true);

        player.setHp((player.getMaxHp()/3 + player.getHp()) > player.getMaxHp() ? player.getMaxHp() : (player.getMaxHp()/3 + player.getHp()));
    }
}