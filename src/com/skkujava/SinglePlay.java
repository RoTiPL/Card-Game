package com.skkujava;

import java.util.ArrayList;
import java.util.Random;

class SinglePlay extends Game {
    private Random random;
    private Player player;
    private Boss boss;

    private int floor;

    SinglePlay(){
        floor = 0;
        p1 = new Player();
        player = (Player)p1;
        p2 = Boss.CreateBoss(player, floor);
        boss = (Boss)p2;
        random = new Random();

        //TODO: 기본 덱 initialize
    }

    void Play(){
        System.out.printf("Floor %d\n\n", floor);
        do {
            DrawCard();
            do {
                System.out.printf("%-15s%s\n%-4s%-11s%-4s%s\n",
                        "Player", "Boss", "HP : ", player.getHp() + "/" + player.getMaxHp(),
                        "HP : ", boss.getHp() + "/" + boss.getMaxHp());
                System.out.printf("%-8s%-7s%-8s%-7s\n",
                        "Armor :", player.getArmor(), "Armor :", boss.getArmor());
                System.out.printf("%-7s%s\n",
                        "Mana :", player.getMana() + "/" + player.getMaxMana());

                String debuff;
                if (player.isPoisoned())
                    debuff = String.format("%-15s", "Poison");
                else debuff = String.format("%-15s", "");
                if (boss.isPoisoned())
                    debuff += String.format("%-15s", "Poison");

                System.out.println(debuff);
                System.out.println("사용할 카드를 입력해 주세요, 0을 입력 시 턴을 종료합니다");
                for(int i=0; i<player.hand.size(); i++){
                    System.out.printf("%d : Cost %d │%-15s%s",
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
                        System.out.println("마나가 부족합니다.");
                    }
                    else break;
                } while(true);

                if(input == 0)break;

                PlayCard(--input);
            } while (true);
            if(TurnEnd() == 1) break;
        } while(true);
        //TODO
    }

    private void PlayCard(int index){
        Card card = player.hand.get(index);
        if(player.getMana() >= card.cost) {
            card.action(player, p2);
            player.setMana(player.getMana() - card.cost);
            player.hand.remove(index);
            player.grave.add(card);

            if (boss.getHp() <= 0) {
                TurnEnd();
            }
        }
        else{
            System.out.println("마나가 부족합니다");
        }
    }

    private int TurnEnd(){
        player.grave.addAll(player.hand);
        player.hand.clear();

        // 턴이 끝날 때 보스에게 독이 걸려 있으면 독데미지 주기
        if(boss.isPoisoned()) {
            boss.TakePoisonDamage();
        }
        if(player.isPoisoned()){
            player.TakePoisonDamage();
        }
        if(boss.getHp() <= 0) {
            // Game Clear 시키기
            ++floor;
            p2 = Boss.CreateBoss(player, floor);
            boss = (Boss)p2;
            player.completeFloor();
            GetReward();
            return 2;
        }

        boss.Action();
        if(player.getHp() <= 0){
            return 1;
        }
        return 0;
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
        boolean flag = true;
        System.out.println("축하합니다! 현재 층을 클리어하셨습니다.");
        System.out.println("1: 카드를 한 장 덱에 추가합니다\n2: 덱에서 카드를 한 장 뺍니다\n3: 체력을 모두 회복합니다.(이 보상을 고르지 않아도 1/3의 체력이 회복됩니다.)\n4: 카드를 한 장 강화합니다.");
        String userInput = scanner.next();
        do {
            if (userInput.trim().equals("1")) {                 //카드 덱에 추가

                ArrayList<Card> collection;
                if(player instanceof Warrior)collection = CardCollection.warriorCollection;
                else if(player instanceof Thief)collection = CardCollection.thiefCollection;
                else {
                    System.out.println("Error: Unknown Class");
                    System.exit(1);
                    return;
                }
                int rand[] = new int[3];
                for(int i=0; i<rand.length; i++){
                    rand[i] = random.nextInt(collection.size());

                    System.out.printf("%d: %-7s│%s", i + 1, collection.get(rand[i]).name, collection.get(rand[i]).cardDescription());

                }
                System.out.println("추가할 카드를 선택하세요");
                //TODO: 카드 추가하기
                int inp;
                do {
                    inp = scanner.nextInt();
                    if (inp <= 0 || inp > rand.length) {
                        System.out.println("Invalid input! Please re-input!");
                    } else break;
                } while (true);

                Card card = collection.get(rand[inp-1]);
                try {
                    Card newCard = (Card)card.clone();
                    collection.remove(rand[inp-1]);
                    collection.add(rand[inp-1], newCard);
                }catch (CloneNotSupportedException e){}

                player.grave.add(card);

                break;
            } else if (userInput.trim().equals("2")) {              //카드 버리기
                Card card;
                do {
                    System.out.println("제거할 카드를 선택하세요");
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

                    System.out.println("다음 카드를 제거하시겠습니까? (Y/N)");
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

                    System.out.println("강화할 카드를 선택하세요");

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
                    //TODO: make clone
                    System.out.println("다음 카드를 강화하시겠습니까? (Y/N)");
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
                }while(flag);
            } else {
                System.out.println("Invalid input! Please re-input!");
            }

            userInput = scanner.next();
        }while(true);

        player.setHp(player.getMaxHp()/3 + player.getHp());
    }
}
