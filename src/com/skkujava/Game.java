package com.skkujava;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    Random random;
    Scanner scanner;
    Game(){
        random = new Random();
        scanner = new Scanner(System.in);
    }

    int PlayCard(Player player, HumanObject enemy, int index){
        Card card = player.hand.get(index);
        if(player.isSuperpower() || player.getMana() >= card.cost) {
            if(!player.isSuperpower())
                player.setMana(player.getMana() - card.cost);

            player.setSuperpower(player.getSuperpower() - 1);

            card.action(player, enemy, index);
            player.hand.remove(index);
            if(!card.be_exhaust) {
                player.grave.add(card);
            }
            if (enemy.getHp() <= 0) {
                return 1;
            }
        }
        else{
            System.out.println("Not enough mana.");
        }
        return 0;
    }

    int TurnEnd(Player player, HumanObject enemy){
        player.grave.addAll(player.hand);
        player.hand.clear();
        if(enemy.isPoisoned()) {
            enemy.TakePoisonDamage();
        }
        player.setSuperpower(0);
        if(enemy.getHp() <= 0)return 1;
        else if(player.getHp() <= 0)return 2;
        return 0;
    }

    void DrawCard(Player player){
        for(int i = 0; i < player.getDrawCount(); i++){
            if(player.deck.size() == 0){
                while(player.grave.size() != 0) {
                    int randInt = random.nextInt(player.grave.size());
                    Card card = player.grave.get(randInt);
                    player.grave.remove(randInt);
                    player.deck.add(card);
                }
            }
            if(player.deck.size() == 0)return;
            Card card = player.deck.get(0);
            player.deck.remove(0);
            player.hand.add(card);
        }
        player.setDrawCount(5);
    }

    void AddRandomCardToPlayer(Player player, int size, ArrayList<Card> store){
        ArrayList<Card> collection;
        if(player instanceof Warrior)collection = CardCollection.warriorCollection;
        else if(player instanceof Thief)collection = CardCollection.thiefCollection;
        else {
            System.out.println("Error: Unknown Class");
            System.exit(1);
            return;
        }
        int[] rand = new int[size];
        for(int i=0; i<rand.length; i++){
            boolean flag;
            do {
                flag = false;
                rand[i] = random.nextInt(collection.size());
                for(int j=0; j<i; j++)
                    if(rand[j] == rand[i])
                        flag = true;
            }while(flag);
            System.out.printf("%d: %-7s│%s\n", i + 1, collection.get(rand[i]).name, collection.get(rand[i]).cardDescription());

        }
        System.out.println("Select the card to add.");
        int inp;
        do {
            inp = scanner.nextInt();
            if (inp <= 0 || inp > rand.length) {
                System.out.println("Invalid input! Please re-input!");
            } else break;
        } while (true);


        try {
            store.add((Card)collection.get(rand[inp-1]).clone());
        }catch (CloneNotSupportedException e){
            e.printStackTrace();
        }
    }

    void clear()
    {
        try{
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else Runtime.getRuntime().exec("clear");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
