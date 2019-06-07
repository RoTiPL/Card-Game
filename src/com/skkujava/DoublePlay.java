package com.skkujava;

public class DoublePlay extends Game {
    private Player p1, p2;
    DoublePlay(){
        int temp;
        System.out.println("Set Player 1.\nPlease Select Player 1's Class.\n1. Warrior\n2. Thief");
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

        System.out.println("Select one card to use among 5 cards. That card will be added to your deck.");
        for(int i = 1; i <= 10; i++){
            System.out.printf("%d/10\n", i);
            AddRandomCardToPlayer(p1, 5, p1.grave);
            System.out.println();
        }
        System.out.println("Set Player 2.\nPlease Select Player 2's Class.\n1. Warrior\n2. Thief");
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

        System.out.println("Select one card to use among 5 cards. That card will be added to your deck.");
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
            DrawCard(p1);
            if(p1 instanceof Warrior)
                p1.setDrawCount(5);
            else p1.setDrawCount(7);
            do {
                System.out.println("┌────────────────────────────────────────────────────────────────────────────────────────────────────┐");
                System.out.printf("%c%42s%16s%42s%c\n", '│', "", "Player 1's Turn", "", '│');
                System.out.printf("%c%24s%-8s%37s%-8s%24c\n", '│', "", "Player 1", "", "Player 2", '│');
                System.out.printf("%c%24s%-5s%-7s%33s%-5s%-7s%20c\n",
                        '│', "", "HP : ", p1.getHp() + "/" + p1.getMaxHp(), "", "HP : ", p2.getHp() + "/" + p2.getMaxHp(), '│');

                System.out.printf("%c%24s%-8s%-2d%35s%-8s%-2d%22c\n",
                        '│', "", "Armor : ", p1.getArmor(), "", "Armor : ", p2.getArmor(), '│');

                System.out.printf("%c%101c\n", '│', '│');

                for(int i = 0 ; i < 13; i++) {
                    System.out.printf("%c%5s%-38s%14s%38s%6c\n", '│', "", p1.getAsciiArt(i), "", new StringBuffer(p2.getAsciiArt(i)).reverse(), '│');
                }

                System.out.printf("%c%101c\n", '│', '│');

                String debuff = "│";
                if (p1.isPoisoned())
                    debuff += String.format("%33s%-7d", "Poison : ", p1.getPoisonDamage());
                else debuff += String.format("%40s", "");
                if (p2.isPoisoned())
                    debuff += String.format("%49s%-7d%5c", "Poison : ", p2.getPoisonDamage(), '│');
                else debuff += String.format("%61c", '│');
                System.out.println(debuff);

                String strength = "│";
                if (p1.getStrength() > 0)
                    strength += String.format("%35s%-5d", "Strength : ", p1.getStrength());
                else strength += String.format("%40s", "");
                if (p2.getStrength() > 0)
                    strength += String.format("%51s%-5d%5c", "Strength : ", p2.getStrength(), '│');
                else strength += String.format("%61c", '│');
                System.out.println(strength);

                String dexterity = "│";
                if (p1.getDexterity() > 0)
                    dexterity += String.format("%36s%-4d", "Dexterity : ", p2.getStrength());
                else dexterity += String.format("%40s", "");
                if (p2.getDexterity() > 0)
                    dexterity += String.format("%51s%-5d%5c", "Dexterity : ", p2.getDexterity(), '│');
                else dexterity += String.format("%61c", '│');
                System.out.println(dexterity);
                System.out.println("├─────────┬──────────────────────────────────────────────────────────────────────────────────────────┘");
                System.out.println("│ Mana: " + p1.getMana() + " │");
                System.out.println("└─────────┘");
                System.out.println("Input the card number to use.\n0 : Turn end");
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
                        System.out.println("Not enough mana.");
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

            DrawCard(p2);
            if(p2 instanceof Warrior)
                p2.setDrawCount(5);
            else p2.setDrawCount(7);
            do {
                System.out.println("┌────────────────────────────────────────────────────────────────────────────────────────────────────┐");
                System.out.printf("%c%42s%16s%42s%c\n", '│', "", "Player 2's Turn", "", '│');
                System.out.printf("%c%24s%-8s%37s%-8s%24c\n", '│', "", "Player 1", "", "Player 2", '│');
                System.out.printf("%c%24s%-5s%-7s%33s%-5s%-7s%20c\n",
                        '│', "", "HP : ", p1.getHp() + "/" + p1.getMaxHp(), "", "HP : ", p2.getHp() + "/" + p2.getMaxHp(), '│');

                System.out.printf("%c%24s%-8s%-2d%35s%-8s%-2d%22c\n",
                        '│', "", "Armor : ", p1.getArmor(), "", "Armor : ", p2.getArmor(), '│');

                System.out.printf("%c%101c\n", '│', '│');

                for(int i = 0 ; i < 13; i++) {
                    System.out.printf("%c%5s%-38s%14s%38s%6c\n", '│', "", p1.getAsciiArt(i), "", new StringBuffer(p2.getAsciiArt(i)).reverse(), '│');
                }

                System.out.printf("%c%101c\n", '│', '│');

                String debuff = "│";
                if (p1.isPoisoned())
                    debuff += String.format("%33s%-7d", "Poison : ", p1.getPoisonDamage());
                else debuff += String.format("%40s", "");
                if (p2.isPoisoned())
                    debuff += String.format("%49s%-7d%5c", "Poison : ", p2.getPoisonDamage(), '│');
                else debuff += String.format("%61c", '│');
                System.out.println(debuff);

                String strength = "│";
                if (p1.getStrength() > 0)
                    strength += String.format("%35s%-5d", "Strength : ", p1.getStrength());
                else strength += String.format("%40s", "");
                if (p2.getStrength() > 0)
                    strength += String.format("%51s%-5d%5c", "Strength : ", p2.getStrength(), '│');
                else strength += String.format("%61c", '│');
                System.out.println(strength);

                String dexterity = "│";
                if (p1.getDexterity() > 0)
                    dexterity += String.format("%36s%-4d", "Dexterity : ", p2.getStrength());
                else dexterity += String.format("%40s", "");
                if (p2.getDexterity() > 0)
                    dexterity += String.format("%51s%-5d%5c", "Dexterity : ", p2.getDexterity(), '│');
                else dexterity += String.format("%61c", '│');
                System.out.println(dexterity);
                System.out.println("├─────────┬──────────────────────────────────────────────────────────────────────────────────────────┘");
                System.out.println("│ Mana: " + p2.getMana() + " │");
                System.out.println("└─────────┘");
                System.out.println("Input the card number to use.\n0 : Turn end");
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
                        System.out.println("Not enough mana.");
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
