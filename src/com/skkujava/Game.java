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
            player.setMana(player.getMana() - card.cost);
            card.action(player, enemy);
            player.hand.remove(index);
            player.grave.add(card);

            if (enemy.getHp() <= 0) {
                return 1;
            }
        }
        else{
            System.out.println("마나가 부족합니다");
        }
        return 0;
    }

    int TurnEnd(Player player, HumanObject enemy, boolean poisonFlag){
        player.grave.addAll(player.hand);
        player.hand.clear();
        if(enemy.isPoisoned() && poisonFlag) {
            enemy.TakePoisonDamage();
        }
        if(player.isPoisoned() && poisonFlag){
            player.TakePoisonDamage();
        }
        player.setSuperpower(false);
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

    void AddRandomCardToPlayer(Player player, int size){
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
        System.out.println("추가할 카드를 선택하세요");
        int inp;
        do {
            inp = scanner.nextInt();
            if (inp <= 0 || inp > rand.length) {
                System.out.println("Invalid input! Please re-input!");
            } else break;
        } while (true);


        try {
            player.grave.add((Card)collection.get(rand[inp-1]).clone());
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
