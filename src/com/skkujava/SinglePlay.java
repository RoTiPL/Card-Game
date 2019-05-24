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

    public void Play(){
        System.out.printf("Floor %d\n", floor);
    }

    private void PlayCard(int index){
        Card card = player.hand.get(index);
        card.action(player, p2);
        player.hand.remove(index);
        player.grave.add(card);

        //boss gethp == 0이면 다음 floor로 옮기기
        if(false){
            TurnEnd();
            ++floor;
            p2 = Boss.CreateBoss(floor);
            boss = (Boss)p2;
            GetReward();
        }

    }

    private void TurnEnd(){
        player.grave.addAll(player.hand);
        player.hand.clear();
    }

    private void DrawCard(){
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

    private void GetReward(){
        System.out.println("1: 카드를 한 장 덱에 추가합니다\n2: 덱에서 카드를 한 장 뺍니다\n3: 체력을 모두 회복합니다.(이 보상을 고르지 않아도 1/3의 체력이 회복됩니다.)");
        String userInput = scanner.next();
        do {
            if (userInput.trim().equals("1")) {
                break;
            } else if (userInput.trim().equals("2")) {
                break;
            } else if (userInput.trim().equals("3")) {
                player.setHp(player.getMaxhp());
                break;
            } else {
                System.out.println("Invalid input! Please re-input!");
            }

            userInput = scanner.next();
        }while(true);

        player.setHp(player.getMaxhp()/3 + player.getHp());
    }
}
