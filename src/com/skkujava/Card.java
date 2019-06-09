package com.skkujava;
import java.util.Scanner;
import java.util.Random;

abstract public class Card implements Cloneable{
    private Random random;
    private Scanner scanner;

    private String name;
    private int cost;
    private String card_type;
    private boolean reinforced = false;
    private boolean be_exhaust = false;
    abstract int action(Player player, HumanObject enemy);
    abstract void reinforce();
    abstract String cardDescription();
    static void PrintHand(Player player){
        for(int i=0; i<player.hand.size(); i++){
            System.out.printf("%d : Cost %d │ %-18s │ %s\n",
                    i+1, player.hand.get(i).getCost(), player.hand.get(i).getName(), player.hand.get(i).cardDescription());
        }
    }

    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }

    public Random getRandom() {
        return random;
    }

    public void setRandom(Random random) {
        this.random = random;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public boolean isReinforced() {
        return reinforced;
    }

    public void setReinforced(boolean reinforced) {
        this.reinforced = reinforced;
    }

    public boolean isBe_exhaust() {
        return be_exhaust;
    }

    public void setBe_exhaust(boolean be_exhaust) {
        this.be_exhaust = be_exhaust;
    }
}

class Defend extends Card {
    private int block = 5;

    Defend() {
        this.setName("Defend");
        this.setCost(1);
        this.setCard_type("Skill");
    }

    int action(Player player, HumanObject enemy){
        player.setArmor(player.getArmor() + block);
        return 0;
    }

    void reinforce() {
        block = 8;
        setReinforced(true);
    }

    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Gain " + block + " block.";
    }
}

class Strike extends Card {
    private int damage = 6;

    Strike(){
        this.setName("Strike");
        this.setCost(1);
        this.setCard_type("Attack");
    }

    int action(Player player, HumanObject enemy) {
        enemy.TakeDamage(damage + player.getStrength());
        return 0;
    }

    @Override
    void reinforce() {
        damage = 9;
        setReinforced(true);
    }

    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }
    @Override
    String cardDescription() {
        return "Deal " + damage + " damage.";
    }
}

class Anger extends Card {
    int damage = 6;

    Anger() {
        this.setName("Anger");
        this.setCost(0);
        this.setCard_type("Attack");
    }

    int action(Player player, HumanObject enemy){
        enemy.TakeDamage(damage + player.getStrength());
        player.grave.add(this);
        return 0;
    }

    void reinforce() {
        damage = 8;
        setReinforced(true);
    }

    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }
    String cardDescription() {
        return "Deal " + damage + " damage. " + "Add a copy of this card to your discard pile.";
    }
}

class Armaments extends Card {
    int block = 5;

    Armaments() {
        this.setName("Armaments");
        this.setCost(1);
        this.setCard_type("Skill");
    }

    int action(Player player, HumanObject enemy){
        setScanner(new Scanner( System.in ));
        if (isReinforced()){
            for(Card card : player.hand){
                card.reinforce();
            }
        }
        else {
            System.out.println("Input a index of card which will be reinforced.");
            PrintHand(player);
            int index;

            do {
                index = getScanner().nextInt() - 1;
                if (index < 0 || index >= player.hand.size()){
                    System.out.println("Unable Input!");
                    return 1;
                }
                else{
                    break;
                }
            } while (true);

            player.hand.get(index).reinforce();
        }
        player.setArmor(block + player.getArmor());
        return 0;
    }

    void reinforce() {
        setReinforced(true);
    }

    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }
    String cardDescription() {
        if (!isReinforced()) {
            return "Gain " + block + " Block. " + " Upgrade a card in your hand for the rest of combat.";
        }
        else {
            return "Gain " + block + " Block. " + " Upgrade ALL cards in your hand for the rest of combat.";
        }
    }
}

class Body_Slam extends Card {
    int damage;

    Body_Slam() {
        this.setName("Body Slam");
        this.setCost(1);
        this.setCard_type("Attack");
    }

    int action(Player player, HumanObject enemy){
        damage = player.getArmor();
        enemy.TakeDamage(damage + player.getStrength());
        return 0;
    }

    void reinforce() {
        this.setCost(0);
        setReinforced(true);
    }

    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }
    String cardDescription() {
        return "Deal damage equal to your Block.";
    }
}

class Clash extends Card {
    int damage = 14;

    Clash() {
        this.setName("Clash");
        this.setCost(0);
        this.setCard_type("Attack");
    }

    int action(Player player, HumanObject enemy){
        for(int i=0; i<player.hand.size(); i++){
            if (!player.hand.get(i).getCard_type().equals("Attack")){
                System.out.println("Can only be played if every card in your hand is an Attack!!");
                return 1;
            }
        }

        enemy.TakeDamage(damage + player.getStrength());
        return 0;
    }

    void reinforce() {
        damage = 18;
        setReinforced(true);
    }

    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }
    String cardDescription() {
        return "Can only be played if every card in your hand is an Attack. " + "Deal " + damage + " damage.";
    }
}

class Cleave extends Card {
    int damage = 8;

    Cleave() {
        this.setName("Cleave");
        this.setCost(1);
        this.setCard_type("Attack");
    }

    int action(Player player, HumanObject enemy){
        enemy.TakeDamage(damage + player.getStrength());
        return 0;
    }

    void reinforce() {
        damage = 11;
        setReinforced(true);
    }

    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }
    String cardDescription() {
        return "Deal " + damage + " damage to enemy.";
    }
}

class Flex extends Card {
    int strength = 1;

    Flex() {
        this.setName("Flex");
        this.setCost(0);
        this.setCard_type("Skill");
    }

    int action(Player player, HumanObject enemy){
        player.setStrength(player.getStrength() + strength);
        return 0;
    }

    void reinforce() {
        strength = 2;
        setReinforced(true);
    }

    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Gain " + strength + " Strength. ";
    }
}

class Heavy_Blade extends Card {
    int damage = 14;
    int add_damage = 3;

    Heavy_Blade() {
        this.setName("Heavy Blade");
        this.setCost(2);
        this.setCard_type("Attack");
    }

    int action(Player player, HumanObject enemy){
        int temp = damage;
        temp += player.getStrength() * add_damage;
        enemy.TakeDamage(temp);
        return 0;
    }

    void reinforce() {
        add_damage = 5;
        setReinforced(true);
    }

    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Deal " + damage + " damage. " + "Strength affects this card " + add_damage + " times.";
    }
}

class Iron_Wave extends Card {
    int damage = 5;
    int block = 5;
    Iron_Wave() {
        this.setName("Iron Wave");
        this.setCost(1);
        this.setCard_type("Attack");
    }

    int action(Player player, HumanObject enemy){
        player.setArmor(player.getArmor() + block);
        enemy.TakeDamage(damage + player.getStrength());
        return 0;
    }

    void reinforce() {
        damage = 7;
        block = 7;
        setReinforced(true);
    }

    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Gain " + block + " Block. " + "Deal " + damage + " damage.";
    }
}

class Perfected_Strike extends Card {
    int damage = 6;
    int add_damage = 2;
    Perfected_Strike() {
        this.setName("Perfected Strike");
        this.setCost(2);
        this.setCard_type("Attack");
    }

    int action(Player player, HumanObject enemy){
        int temp = damage;
        for(int i=0; i<player.deck.size(); i++){
            if ( player.deck.get(i).getName().contains("Strike") ){
                temp += add_damage;
            }
        }
        for(int i=0; i<player.hand.size(); i++){
            if ( player.hand.get(i).getName().contains("Strike") ){
                temp += add_damage;
            }
        }
        for (int i=0; i<player.grave.size(); i++){
            if ( player.grave.get(i).getName().contains("Strike") ){
                temp += add_damage;
            }
        }

        enemy.TakeDamage(temp + player.getStrength());
        return 0;
    }

    void reinforce() {
        add_damage = 3;
        setReinforced(true);
    }

    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Deal " + damage + " damage. " + "Deals an additional " + add_damage + " damage for ALL of your cards containing \"Strike\"";
    }
}

class Pommel_Strike extends Card {
    int damage = 9;
    int draw = 1;
    Pommel_Strike() {
        this.setName("Pommel Strike");
        this.setCost(1);
        this.setCard_type("Attack");
        setRandom(new Random());
    }

    int action(Player player, HumanObject enemy) {
        enemy.TakeDamage(damage + player.getStrength());

        for(int i = 0; i < draw; i++){
            if(player.deck.size() == 0){
                while(player.grave.size() != 0) {
                    int randInt = getRandom().nextInt(player.grave.size());
                    Card card = player.grave.get(randInt);
                    player.grave.remove(randInt);
                    player.deck.add(card);
                }
            }
            if(player.deck.size() == 0)break;
            Card card = player.deck.get(0);
            player.deck.remove(0);
            player.hand.add(card);
        }
        return 0;
    }

    void reinforce() {
        damage = 10;
        draw = 2;
        setReinforced(true);
    }

    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Deal " + damage + " damage. " + "Draw " + draw + " card(s)";
    }
}

class Shrug_It_Off extends Card {
    int block = 8;

    Shrug_It_Off() {
        this.setName("Shrug_It_Off");
        this.setCost(1);
        this.setCard_type("Skill");
        setRandom(new Random());
    }

    int action(Player player, HumanObject enemy){
        player.setArmor( player.getArmor() + block );

        if(player.deck.size() == 0){
            while(player.grave.size() != 0) {
                int randInt = getRandom().nextInt(player.grave.size());
                Card card = player.grave.get(randInt);
                player.grave.remove(randInt);
                player.deck.add(card);
            }
        }
        if(player.deck.size() != 0) {
            Card card = player.deck.get(0);
            player.deck.remove(0);
            player.hand.add(card);
        }
        return 0;
    }

    void reinforce() {
        block = 11;
        setReinforced(true);
    }

    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Gain " + block + " block. " + "Draw 1 card.";
    }
}

class Twin_Strike extends Card {
    int damage = 5;

    Twin_Strike() {
        this.setName("Twin Strike");
        this.setCost(1);
        this.setCard_type("Attack");
    }

    int action(Player player, HumanObject enemy){
        for(int i=0; i<2; i++){
            enemy.TakeDamage(damage + player.getStrength());
        }
        return 0;
    }

    void reinforce() {
        damage = 7;
        setReinforced(true);
    }

    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Deal " + damage + " damage twice. ";
    }
}

class Warcry extends Card {
    int draw = 1;

    Warcry() {
        this.setName("Warcry");
        this.setCost(0);
        this.setCard_type("Skill");
        this.setBe_exhaust(true);
        setRandom(new Random());
    }

    int action(Player player, HumanObject enemy){

        System.out.println("Insert an index of card which replace on the top of draw pile.");
        PrintHand(player);
        setScanner(new Scanner( System.in ));
        int index;

        do {
            index = getScanner().nextInt() - 1;
            if (index < 0 || index >= player.hand.size()){
                System.out.println("Unable Input!");
                return 1;
            }
            else{
                break;
            }
        } while (true);


        if(player.deck.size() == 0){
            while(player.grave.size() != 0) {
                int randInt = getRandom().nextInt(player.grave.size());
                Card card = player.grave.get(randInt);
                player.grave.remove(randInt);
                player.deck.add(card);
            }
        }
        if(player.deck.size() != 0) {
            Card card = player.deck.get(0);
            player.deck.remove(0);
            player.hand.add(card);
        }

        player.deck.add(0, player.hand.get(index));
        player.hand.remove(index);

        return 0;
    }

    void reinforce() {
        draw = 2;
        setReinforced(true);
    }

    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Draw " + draw + " card(s). " + "Place a card from your hand on top of your draw pile. " + "Exhaust.";
    }
}

class Bloodletting extends Card {
    int energy = 1;

    Bloodletting() {
        this.setName("Bloodletting");
        this.setCost(0);
        this.setCard_type("Skill");
    }

    int action(Player player, HumanObject enemy){
        player.TakeDamage(3);
        player.setMana( player.getMana() + energy );
        return 0;
    }

    void reinforce() {
        energy = 2;
        setReinforced(true);
    }

    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Lose 3 HP. " + "Gain " + energy + " Energy.";
    }
}

class Burning_Pact extends Card {
    int draw = 2;

    Burning_Pact() {
        this.setName("Burning Pact");
        this.setCost(1);
        this.setCard_type("Skill");
        setRandom(new Random());
    }

    int action(Player player, HumanObject enemy){
        System.out.println("Input a index which will be Exhaust.");
        PrintHand(player);
        setScanner(new Scanner(System.in));
        int index;

        do {
            index = getScanner().nextInt() - 1;
            if (index < 0 || index >= player.hand.size()){
                System.out.println("Unable Input!");
                return 1;
            }
            else{
                break;
            }
        } while (true);

        player.hand.remove(index);

        for(int i = 0; i < draw; i++) {
            if (player.deck.size() == 0) {
                while (player.grave.size() != 0) {
                    int randInt = getRandom().nextInt(player.grave.size());
                    Card card = player.grave.get(randInt);
                    player.grave.remove(randInt);
                    player.deck.add(card);
                }
            }
            if (player.deck.size() == 0) break;
            Card card = player.deck.get(0);
            player.deck.remove(0);
            player.hand.add(card);
        }
        return 0;
    }

    void reinforce() {
        draw = 2;
        setReinforced(true);
    }

    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Exhaust 1 card. " + "Draw " + draw + " cards.";
    }
}

class Entrench extends Card {
    Entrench() {
        this.setName("Entrench");
        this.setCost(2);
        this.setCard_type("Skill");
    }

    int action(Player player, HumanObject enemy){
        player.setArmor( player.getArmor() * 2 );
        return 0;
    }

    void reinforce() {
        this.setCost(1);
        setReinforced(true);
    }

    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Double your current Block.";
    }
}

class Hemokinesis extends Card {
    int lossHP = 3;
    int damage = 14;

    Hemokinesis() {
        this.setName("Hemokinesis");
        this.setCost(1);
        this.setCard_type("Attack");
    }

    int action(Player player, HumanObject enemy){
        player.setHp( player.getHp() - lossHP );
        enemy.TakeDamage(damage + player.getStrength());
        return 0;
    }

    void reinforce() {
        lossHP = 2;
        damage = 18;
        setReinforced(true);
    }

    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Lose " + lossHP + " HP. " + "Deal " + damage + " damage.";
    }
}

class Inflame extends Card {
    int strength = 2;

    Inflame() {
        this.setName("Inflame");
        this.setCost(1);
        this.setCard_type("Power");
    }

    int action(Player player, HumanObject enemy){
        player.setStrength( player.getStrength() + strength );
        return 0;
    }

    void reinforce() {
        strength = 3;
        setReinforced(true);
    }


    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }
    String cardDescription() {
        return "Gain " + strength + " Strength.";
    }
}

class Rampage extends Card {
    int damage = 8;
    int add_damage = 5;

    Rampage() {
        this.setName("Rampage");
        this.setCost(1);
        this.setCard_type("Attack");
    }

    int action(Player player, HumanObject enemy){
        enemy.TakeDamage(damage + player.getStrength());
        damage += add_damage;
        return 0;
    }

    void reinforce() {
        add_damage = 8;
        setReinforced(true);
    }


    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Deal " + damage + " damage. " + "Every time this card is played, increase its damage by " + add_damage + " for this combat.";
    }
}

class Seeing_Red extends Card {
    Seeing_Red() {
        this.setName("Seeing Red");
        this.setCost(1);
        this.setCard_type("Skill");
        this.setBe_exhaust(true);
    }

    int action(Player player, HumanObject enemy){
        player.setMana( player.getMana() + 2 );
        return 0;
    }

    void reinforce() {
        this.setCost(0);
        setReinforced(true);
    }


    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Gain 2 energy. " + "Exhaust.";
    }
}

class Bludgeon extends Card {
    int damage = 32;
    Bludgeon() {
        this.setName("Bludgeon");
        this.setCost(3);
        this.setCard_type("Attack");
    }

    int action(Player player, HumanObject enemy){
        enemy.TakeDamage(damage + player.getStrength());
        return 0;
    }

    void reinforce() {
        damage = 42;
        setReinforced(true);
    }


    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Deal " + damage + " damage.";
    }
}

class Impervious extends Card {
    int block = 30;
    Impervious() {
        this.setName("Impervious");
        this.setCost(2);
        this.setCard_type("Skill");
        this.setBe_exhaust(true);
    }

    int action(Player player, HumanObject enemy){
        player.setArmor( player.getArmor() + block );
        return 0;
    }

    void reinforce() {
        block = 40;
        setReinforced(true);
    }


    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Gain " + block + " Block. " + "Exhaust";
    }
}

class Limit_Break extends Card {
    Limit_Break() {
        this.setName("Limit Break");
        this.setCost(1);
        this.setCard_type("Skill");
        this.setBe_exhaust(true);
    }

    int action(Player player, HumanObject enemy){
        player.setStrength( player.getStrength() * 2 );
        return 0;
    }

    void reinforce() {
        this.setReinforced(true);
        this.setBe_exhaust(false);
    }


    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        if (isReinforced()) {
            return "Double your Strength.";
        }
        else {
            return "Double your Strength. " + "Exhaust.";
        }
    }
}

class Offering extends Card {
    int draw = 3;
    Offering() {
        this.setName("Offering");
        this.setCost(0);
        this.setCard_type("Skill");
        this.setBe_exhaust(true);
        setRandom(new Random());
    }

    int action(Player player, HumanObject enemy){
        player.setHp( player.getHp() - 6 );
        player.setMana( player.getMana() + 2 );

        for(int i = 0; i < draw; i++) {
            if (player.deck.size() == 0) {
                while (player.grave.size() != 0) {
                    int randInt = getRandom().nextInt(player.grave.size());
                    Card card = player.grave.get(randInt);
                    player.grave.remove(randInt);
                    player.deck.add(card);
                }
            }
            if (player.deck.size() == 0) break;
            Card card = player.deck.get(0);
            player.deck.remove(0);
            player.hand.add(card);
        }
        return 0;
    }

    void reinforce() {
        draw = 5;
        setReinforced(true);
    }


    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Lose 6 HP. " + "Gain 2 energy. " + "Draw " + draw + " cards. " + "Exhaust.";
    }
}

class Survivor extends Card {
    int block = 8;

    Survivor() {
        this.setName("Survivor");
        this.setCost(1);
        this.setCard_type("Skill");
    }

    int action(Player player, HumanObject enemy){
        System.out.println("Input a index number of card which will discard.");
        PrintHand(player);
        setScanner(new Scanner( System.in ));
        int index;

        do {
            index = getScanner().nextInt() - 1;
            if (index < 0 || index >= player.hand.size()){
                System.out.println("Unable Input!");
                return 1;
            }
            else{
                break;
            }
        } while (true);

        player.grave.add( player.hand.get(index) );
        player.hand.remove( index );
        player.setArmor( player.getArmor() + block );
        return index;
    }

    void reinforce() {
        block = 11;
        setReinforced(true);
    }


    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Gain " + block + " Block. " + "Discard 1 card.";
    }
}

class Acrobatics extends Card {
    int draw = 3;

    Acrobatics() {
        this.setName("Acrobatics");
        this.setCost(1);
        this.setCard_type("Skill");
        setRandom(new Random());
    }

    int action(Player player, HumanObject enemy){

        System.out.println("Input a index number of card which will discard.");
        PrintHand(player);
        setScanner(new Scanner( System.in ));
        int index;

        do {
            index = getScanner().nextInt() - 1;
            if (index < 0 || index >= player.hand.size()){
                System.out.println("Unable Input!");
                return 1;
            }
            else{
                break;
            }
        } while (true);

        player.grave.add( player.hand.get(index) );
        player.hand.remove( index );

        for(int i = 0; i < draw; i++) {
            if (player.deck.size() == 0) {
                while (player.grave.size() != 0) {
                    int randInt = getRandom().nextInt(player.grave.size());
                    Card card = player.grave.get(randInt);
                    player.grave.remove(randInt);
                    player.deck.add(card);
                }
            }
            if (player.deck.size() == 0) break;
            Card card = player.deck.get(0);
            player.deck.remove(0);
            player.hand.add(card);
        }
        return index;
    }

    void reinforce() {
        draw = 4;
        setReinforced(true);
    }


    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Draw " + draw + " cards. " + "Discard 1 card.";
    }
}

class Backflip extends Card {
    int block = 5;

    Backflip() {
        this.setName("Backfilp");
        this.setCost(1);
        this.setCard_type("Skill");
        setRandom(new Random());
    }

    int action(Player player, HumanObject enemy){
        player.setArmor( player.getArmor() + block );

        for(int i = 0; i < 2; i++) {
            if (player.deck.size() == 0) {
                while (player.grave.size() != 0) {
                    int randInt = getRandom().nextInt(player.grave.size());
                    Card card = player.grave.get(randInt);
                    player.grave.remove(randInt);
                    player.deck.add(card);
                }
            }
            if (player.deck.size() == 0) break;
            Card card = player.deck.get(0);
            player.deck.remove(0);
            player.hand.add(card);
        }
        return 0;
    }

    void reinforce() {
        block = 8;
        setReinforced(true);
    }


    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Gain " + block + "  Block. " + "Draw 2 cards.";
    }
}

class Bane extends Card {
    int damage = 7;

    Bane() {
        this.setName("Bane");
        this.setCost(1);
        this.setCard_type("Attack");
    }

    int action(Player player, HumanObject enemy){
        enemy.TakeDamage(damage + player.getStrength());
        if (enemy.isPoisoned()){
            enemy.TakeDamage(damage + player.getStrength());
        }
        return 0;
    }

    void reinforce() {
        damage = 10;
        setReinforced(true);
    }


    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Deal " + damage + " damage. " + "If the enemy is Poisoned, deal " + damage + " damage again.";
    }
}

class Blade_Dance extends Card {
    int add_shivs = 2;

    Blade_Dance() {
        this.setName("Blade Dance");
        this.setCost(1);
        this.setCard_type("Skill");
    }

    int action(Player player, HumanObject enemy){
        Card shiv = new Shiv();
        for (int i=0; i<add_shivs; i++) {
            player.hand.add(shiv);
        }
        return 0;
    }

    void reinforce() {
        add_shivs = 3;
        setReinforced(true);
    }


    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Add " + add_shivs + " Shivs into your hand.";
    }
}

class Cloak_And_Dagger extends Card {
    int add_shivs = 1;

    Cloak_And_Dagger() {
        this.setName("Cloak And Dagger");
        this.setCost(1);
        this.setCard_type("Skill");
    }

    int action(Player player, HumanObject enemy){
        player.setArmor( player.getArmor() + 6 );

        Card shiv = new Shiv();
        for (int i=0; i<add_shivs; i++) {
            player.hand.add(shiv);
        }
        return 0;
    }

    void reinforce() {
        add_shivs = 2;
        setReinforced(true);
    }


    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Gain 6 Block. " + "Add " + add_shivs + " Shiv(s) into your hand.";
    }
}

class Dagger_Throw extends Card {
    int damage = 9;

    Dagger_Throw() {
        this.setName("Dagger Throw");
        this.setCost(1);
        this.setCard_type("Attack");
        setRandom(new Random());
    }

    int action(Player player, HumanObject enemy){
        System.out.println("Input a index number of card which will discard.");
        PrintHand(player);
        setScanner(new Scanner( System.in ));
        int index;

        do {
            index = getScanner().nextInt() - 1;
            if (index < 0 || index >= player.hand.size()){
                System.out.println("Unable Input!");
                return 1;
            }
            else{
                break;
            }
        } while (true);

        player.grave.add( player.hand.get(index) );
        player.hand.remove( index );
        enemy.TakeDamage(damage + player.getStrength());

        for(int i = 0; i < 2; i++) {
            if (player.deck.size() == 0) {
                while (player.grave.size() != 0) {
                    int randInt = getRandom().nextInt(player.grave.size());
                    Card card = player.grave.get(randInt);
                    player.grave.remove(randInt);
                    player.deck.add(card);
                }
            }
            if (player.deck.size() == 0) break;
            Card card = player.deck.get(0);
            player.deck.remove(0);
            player.hand.add(card);
        }
        return index;
    }

    void reinforce() {
        damage = 12;
        setReinforced(true);
    }


    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Deal " + damage + " damage. " + "Draw 1 card. " + "Discard 1 card.";
    }
}

class Deadly_Poison extends Card {
    int poison = 5;

    Deadly_Poison() {
        this.setName("Deadly Poison");
        this.setCost(1);
        this.setCard_type("Skill");
    }

    int action(Player player, HumanObject enemy){
        enemy.setPoisonDamage( enemy.getPoisonDamage() + poison );
        enemy.setPoisoned(true);
        return 0;
    }

    void reinforce() {
        poison = 7;
        setReinforced(true);
    }


    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Apply " + poison + " Poison.";
    }
}

class Deflect extends Card {
    int block = 4;

    Deflect() {
        this.setName("Deflect");
        this.setCost(0);
        this.setCard_type("Skill");
    }

    int action(Player player, HumanObject enemy){
        player.setArmor( player.getArmor() + block );
        return 0;
    }

    void reinforce() {
        block = 7;
        setReinforced(true);
    }


    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Gain " + block + " Block.";
    }
}

class Flying_Knee extends Card {
    int damage = 8;

    Flying_Knee() {
        this.setName("Flying Knee");
        this.setCost(1);
        this.setCard_type("Attack");
    }

    int action(Player player, HumanObject enemy){
        enemy.TakeDamage(damage + player.getStrength());
        player.setBonusMana( player.getBonusMana() + 1 );
        return 0;
    }

    void reinforce() {
        damage = 11;
        setReinforced(true);
    }


    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Deal " + damage + " damage. " + "Next turn gain 1 Energy.";
    }
}

class Outmaneuver extends Card {
    int add_energy = 2;

    Outmaneuver() {
        this.setName("Outmaneuver");
        this.setCost(1);
        this.setCard_type("Skill");
    }

    int action(Player player, HumanObject enemy){
        player.setBonusMana( player.getBonusMana() + add_energy );
        return 0;
    }

    void reinforce() {
        add_energy = 3;
        setReinforced(true);
    }


    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Next turn gain " + add_energy + " Energy.";
    }
}

class Poisoned_Stab extends Card {
    int damage = 6;
    int poison = 3;

    Poisoned_Stab() {
        this.setName("Poisoned Stab");
        this.setCost(1);
        this.setCard_type("Attack");
    }

    int action(Player player, HumanObject enemy){
        enemy.TakeDamage(damage + player.getStrength());
        enemy.setPoisonDamage(enemy.getPoisonDamage() + poison);
        enemy.setPoisoned(true);
        return 0;
    }

    void reinforce() {
        damage = 8;
        poison = 4;
        setReinforced(true);
    }


    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Deal " + damage + " damage. " + "Apply " + poison + " Poison.";
    }
}

class Prepared extends Card {
    int draw = 1;

    Prepared() {
        this.setName("Prepared");
        this.setCost(0);
        this.setCard_type("Skill");
        setRandom(new Random());
    }

    int action(Player player, HumanObject enemy){
        for(int i = 0; i < draw; i++) {
            if (player.deck.size() == 0) {
                while (player.grave.size() != 0) {
                    int randInt = getRandom().nextInt(player.grave.size());
                    Card card = player.grave.get(randInt);
                    player.grave.remove(randInt);
                    player.deck.add(card);
                }
            }
            if (player.deck.size() == 0) break;
            Card card = player.deck.get(0);
            player.deck.remove(0);
            player.hand.add(card);
        }

        for(int i=0; i<draw; i++){
            System.out.println("Input a index number of card which will discard.");
            PrintHand(player);
            setScanner(new Scanner( System.in ));
            int index;

            do {
                index = getScanner().nextInt() - 1;
                if (index < 0 || index >= player.hand.size()){
                    System.out.println("Unable Input! Please re-input.");
                }
                else{
                    break;
                }
            } while (true);

            player.grave.add( player.hand.get(index) );
            player.hand.remove( index );
        }

        return 0;
    }

    void reinforce() {
        draw = 2;
        setReinforced(true);
    }


    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Draw " + draw + " card(s). " + "Discard " + draw + " card(s).";
    }
}

class Quick_Slash extends Card {
    int damage = 8;

    Quick_Slash() {
        this.setName("Quick Slash");
        this.setCost(1);
        this.setCard_type("Attack");
        setRandom(new Random());
    }

    int action(Player player, HumanObject enemy){
        enemy.TakeDamage(damage + player.getStrength());

        if (player.deck.size() == 0) {
            while (player.grave.size() != 0) {
                int randInt = getRandom().nextInt(player.grave.size());
                Card card = player.grave.get(randInt);
                player.grave.remove(randInt);
                player.deck.add(card);
            }
        }
        if (player.deck.size() != 0) {
            Card card = player.deck.get(0);
            player.deck.remove(0);
            player.hand.add(card);
        }
        return 0;
    }

    void reinforce() {
        damage = 12;
        setReinforced(true);
    }


    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Deal " + damage + " damage. " + "Draw 1 card.";
    }
}

class Calculated_Gamble extends Card {
    Calculated_Gamble() {
        this.setName("Calculated Gamble");
        this.setCost(0);
        this.setCard_type("Skill");
        this.setBe_exhaust(true);
        setRandom(new Random());
    }

    int action(Player player, HumanObject enemy){
        int numOfcards = player.hand.size() - 1;

        player.grave.addAll(player.hand);
        player.hand.clear();

        for(int i = 0; i < numOfcards; i++) {
            if (player.deck.size() == 0) {
                while (player.grave.size() != 0) {
                    int randInt = getRandom().nextInt(player.grave.size());
                    Card card = player.grave.get(randInt);
                    player.grave.remove(randInt);
                    player.deck.add(card);
                }
            }
            if (player.deck.size() == 0) break;
            Card card = player.deck.get(0);
            player.deck.remove(0);
            player.hand.add(card);
        }
        return numOfcards;
    }

    void reinforce() {
        this.setReinforced(true);
        this.setBe_exhaust(false);
    }


    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        if (!isReinforced()) {
            return "Discard your hand, then draw that many cards. " + "Exhaust.";
        }
        else {
            return "Discard your hand, then draw that many cards.";
        }
    }
}

class Catalyst extends Card {

    Catalyst() {
        this.setName("Catalyst");
        this.setCost(1);
        this.setCard_type("Skill");
        this.setBe_exhaust(true);
    }

    int action(Player player, HumanObject enemy){
        if (isReinforced()) {
            enemy.setPoisonDamage( enemy.getPoisonDamage() * 3 );
        }
        else{
            enemy.setPoisonDamage( enemy.getPoisonDamage() * 2 );
        }
        return 0;
    }

    void reinforce() {
        this.setReinforced(true);
        this.setBe_exhaust(false);
    }


    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        if (!isReinforced()) {
            return "Double an enemy's Poison. " + "Exhaust.";
        }
        else {
            return "Triple an enemy's Poison.";
        }
    }
}

class Concentrate extends Card {
    int energy = 2;
    int dis_card = 3;

    Concentrate() {
        this.setName("Concentrate");
        this.setCost(0);
        this.setCard_type("Skill");
    }

    int action(Player player, HumanObject enemy){
        if(player.hand.size() >= dis_card) {
            for (int i = 0; i < dis_card; i++) {
                System.out.println("Input a index number of card which will discard.");
                PrintHand(player);
                setScanner(new Scanner(System.in));
                int index;

                do {
                    index = getScanner().nextInt() - 1;
                    if (index < 0 || index >= player.hand.size()) {
                        System.out.println("Unable Input! Please re-input.");
                    } else {
                        break;
                    }
                } while (true);

                player.grave.add(player.hand.get(index));
                player.hand.remove(index);
            }
        }
        else {
            System.out.println("You don't have " + dis_card + " or more cards");
            return 1;
        }

        player.setMana( player.getMana() + energy );
        return 0;
    }

    void reinforce() {
        dis_card = 2;
        setReinforced(true);
    }


    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Discard " + dis_card + " cards. " + "Gain 2 Energy.";
    }
}

class Dash extends Card {
    int damage = 10;
    int block = 10;

    Dash() {
        this.setName("Dash");
        this.setCost(2);
        this.setCard_type("Attack");
    }

    int action(Player player, HumanObject enemy){
        player.setArmor( player.getArmor() + block );
        enemy.TakeDamage(damage + player.getStrength());
        return 0;
    }

    void reinforce() {
        damage = 13;
        block = 13;
        setReinforced(true);
    }


    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Gain " + block + " Block. " + "Deal " + damage + " damage.";
    }
}

class Escape_Plan extends Card {
    int block = 3;

    Escape_Plan() {
        this.setName("Escape Plan");
        this.setCost(0);
        this.setCard_type("Skill");
        setRandom(new Random());
    }

    int action(Player player, HumanObject enemy){
        if (player.deck.size() == 0) {
            while (player.grave.size() != 0) {
                int randInt = getRandom().nextInt(player.grave.size());
                Card card = player.grave.get(randInt);
                player.grave.remove(randInt);
                player.deck.add(card);
            }
        }
        if (player.deck.size() != 0) {
            Card card = player.deck.get(0);
            player.deck.remove(0);
            player.hand.add(card);

            if (card.getCard_type().equals("Skill")){
                player.setArmor( player.getArmor() + block );
            }
        }
        return 0;
    }

    void reinforce() {
        block = 5;
        setReinforced(true);
    }


    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Draw 1 card. " + "If you draw a Skill, gain " + block + " Block.";
    }
}

class Footwork extends Card {
    int dexterity = 2;

    Footwork() {
        this.setName("Footwork");
        this.setCost(1);
        this.setCard_type("Power");
    }

    int action(Player player, HumanObject enemy){
        player.setDexterity( player.getDexterity() + dexterity );
        return 0;
    }

    void reinforce() {
        dexterity = 3;
        setReinforced(true);
    }


    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Gain " + dexterity + " Dexterity.";
    }
}

class Skewer extends Card {
    int damage = 7;

    Skewer() {
        this.setName("Skewer");
        this.setCost(0);
        this.setCard_type("Attack");
    }

    int action(Player player, HumanObject enemy){
        for (int i=0; i<player.getMana(); i++){
            enemy.TakeDamage(damage + player.getStrength());
        }
        player.setMana(0);
        return 0;
    }

    void reinforce() {
        damage = 10;
        setReinforced(true);
    }


    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Deal " + damage + " damage X times.";
    }
}

class Adrenaline extends Card {
    int energy = 1;

    Adrenaline() {
        this.setName("Adrenaline");
        this.setCost(0);
        this.setCard_type("Skill");
        this.setBe_exhaust(true);
        setRandom(new Random());
    }

    int action(Player player, HumanObject enemy){
        player.setMana( player.getMana() + energy );

        for(int i = 0; i < 2; i++) {
            if (player.deck.size() == 0) {
                while (player.grave.size() != 0) {
                    int randInt = getRandom().nextInt(player.grave.size());
                    Card card = player.grave.get(randInt);
                    player.grave.remove(randInt);
                    player.deck.add(card);
                }
            }
            if (player.deck.size() == 0) break;
            Card card = player.deck.get(0);
            player.deck.remove(0);
            player.hand.add(card);
        }
        return 0;
    }

    void reinforce() {
        energy = 2;
        setReinforced(true);
    }


    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Gain " + energy + " Energy " + "Draw 2 cards. " + "Exhaust.";
    }
}

class Bullet_Time extends Card {
    Bullet_Time() {
        this.setName("Bullet_Time");
        this.setCost(3);
        this.setCard_type("Skill");
    }

    int action(Player player, HumanObject enemy){
        player.setSuperpower(5);
        return 0;
    }

    void reinforce() {
        this.setCost(2);
        setReinforced(true);
    }


    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "You can play 5 cards without consuming energy at this turn";
    }
}

class Doppelganger extends Card {
    Doppelganger() {
        this.setName("Doppelganger");
        this.setCost(0);
        this.setCard_type("Skill");
    }

    int action(Player player, HumanObject enemy){
        int energy;
        if (isReinforced()){
            energy = player.getMana() + 1;
        }
        else {
            energy = player.getMana();
        }
        player.setDrawCount( player.getDrawCount() + energy);
        player.setBonusMana( player.getBonusMana() + energy);
        player.setMana(0);
        return energy;
    }

    void reinforce() {
        setReinforced(true);
    }


    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        if (!isReinforced()) {
            return "Next turn, draw X cards and gain X Energy.";
        }
        else {
            return "Next turn, draw X+1 cards and gain X+1 Energy.";
        }
    }
}

class Shiv extends Card {
    int damage = 4;

    Shiv() {
        this.setName("Shiv");
        this.setCost(0);
        this.setCard_type("Attack");
        this.setBe_exhaust(true);
    }

    int action(Player player, HumanObject enemy){
        enemy.TakeDamage(damage + player.getStrength());
        return 0;
    }

    void reinforce() {
        damage = 6;
        setReinforced(true);
    }


    public Object clone(){
        Card object;
        try{
            object = (Card)super.clone();
        }
        catch (CloneNotSupportedException e){
            throw new InternalError();
        }
        return object;
    }

    String cardDescription() {
        return "Deal " + damage + " damage." + " Exhaust.";
    }
}