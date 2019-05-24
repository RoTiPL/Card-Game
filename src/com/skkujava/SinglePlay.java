package com.skkujava;

import java.util.Random;

public class SinglePlay extends Game {
    Random random;
    Player player;
    Boss boss;

    int floor;

    SinglePlay(){
        floor = 0;
        p1 = new Player();
        player = (Player)p1;
        p2 = Boss.CreateBoss(floor);
        boss = (Boss)p2;
        random = new Random();
    }

    void Play(){

    }

    void PlayCard(int index){
        Card card = player.hand.get(index);
        card.action(player, p2);
        player.hand.remove(index);
        player.grave.add(card);
    }

    void TurnEnd(){
        player.grave.addAll(player.hand);
        player.hand.clear();
    }

    void DrawCard(){
        for(int i = 0; i<5; i++){
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
    }
}
