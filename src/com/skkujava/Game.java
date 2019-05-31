package com.skkujava;

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
        if(player.getMana() >= card.cost) {
            card.action(player, enemy);
            player.setMana(player.getMana() - card.cost);
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

    int TurnEnd(Player player, HumanObject enemy){
        player.grave.addAll(player.hand);
        player.hand.clear();
        if(enemy.isPoisoned()) {
            enemy.TakePoisonDamage();
        }
        if(player.isPoisoned()){
            player.TakePoisonDamage();
        }
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
}
