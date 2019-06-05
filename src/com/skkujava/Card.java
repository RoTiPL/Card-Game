package com.skkujava;
import java.util.Scanner;
import java.util.Random;

abstract public class Card implements Cloneable{
    Random random;
    Scanner scanner;

    String name; // 카드의 이름
    int cost; // 카드를 사용하기 위한 비용
    String card_type; // 카드의 타입 설정
    boolean reinforced = false; // 카드가 강화되었는지 확인
    abstract void action(Player player, HumanObject enemy); // 플레이어와 적 간의 상호작용을 나타내는 메소드
    abstract void reinforce(); // 카드를 강화시키기 위한 메소드
    abstract String cardDescription(); // 카드의 설명

    public Object clone() throws CloneNotSupportedException{
        return super.clone();
    }
}
// https://slay-the-spire.fandom.com/wiki/Ironclad_Cards 참조
// 31 Cards.
class Defend extends Card {
    private int block = 5;

    Defend() {
        this.name = "Defend";
        this.cost = 1;
        this.card_type = "Skill";
    }

    void action(Player player, HumanObject enemy){
        player.setArmor(player.getArmor() + block);
    }

    void reinforce() {
        block = 8;
        reinforced = true;
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
        this.name = "Strike";
        this.cost = 1;
        this.card_type = "Attack";
    }

    void action(Player player, HumanObject enemy) {
        enemy.TakeDamage(damage);
    }

    @Override
    void reinforce() {
        damage = 9;
        reinforced = true;
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
        this.name = "Anger";
        this.cost = 0;
        this.card_type = "Attack";
    }

    void action(Player player, HumanObject enemy){
        enemy.TakeDamage(damage);
        player.grave.add(this);
    }

    void reinforce() {
        damage = 8;
        reinforced = true;
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
        this.name = "Armaments";
        this.cost = 1;
        this.card_type = "Skill";
    }

    void action(Player player, HumanObject enemy){
        scanner = new Scanner( System.in );
        player.setArmor(block);
        if (reinforced){
            for(int i=0; i<player.hand.size(); i++){
                player.hand.get(i).reinforced = true;
            }
        }
        else {
            System.out.println("Input a index of card that will be reinforced.");
            int index = scanner.nextInt();
            player.hand.get(index).reinforced = true;
        }

    }

    void reinforce() {
        reinforced = true;
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
        if (reinforced) {
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
        this.name = "Body Slam";
        this.cost = 1;
        this.card_type = "Attack";
    }

    void action(Player player, HumanObject enemy){
        damage = player.getArmor();
        enemy.TakeDamage(damage);
    }

    void reinforce() {
        this.cost = 0;
        reinforced = true;
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
        this.name = "Clash";
        this.cost = 0;
        this.card_type = "Attack";
    }

    void action(Player player, HumanObject enemy){
        for(int i=0; i<player.hand.size(); i++){
            if (player.hand.get(i).card_type != "Attack"){
                return;
            }
        }

        enemy.TakeDamage(damage);
    }

    void reinforce() {
        damage = 18;
        reinforced = true;
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
        this.name = "Cleave";
        this.cost = 1;
        this.card_type = "Attack";
    }

    void action(Player player, HumanObject enemy){
        enemy.TakeDamage(damage);
    }

    void reinforce() {
        damage = 11;
        reinforced = true;
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
    int strength = 2;

    Flex() {
        this.name = "Flex";
        this.cost = 0;
        this.card_type = "Skill";
    }

    void action(Player player, HumanObject enemy){
        player.setStrength(player.getStrength() + strength);
    }

    void reinforce() {
        strength = 4;
        reinforced = true;
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
        return "Gain " + strength + " Strength. " + "At the end of your turn, lose " + strength + " Strength.";
    }
}

class Headbutt extends Card {
    int damage = 9;

    Headbutt() {
        this.name = "Headbutt";
        this.cost = 1;
        this.card_type = "Attack";
    }

    void action(Player player, HumanObject enemy){
        enemy.TakeDamage(damage);
        // 보류
    }

    void reinforce() {
        damage = 12;
        reinforced = true;
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
        return "Deal " + damage + " damage. " + "Place a card from your discard pile on top of your draw pile.";
    }
}

class Heavy_Blade extends Card {
    int damage = 14;
    int add_damage = 3;

    Heavy_Blade() {
        this.name = "Heavy Blade";
        this.cost = 2;
        this.card_type = "Attack";
    }

    void action(Player player, HumanObject enemy){
        damage += player.getStrength() * add_damage;
        enemy.TakeDamage(damage);
    }

    void reinforce() {
        add_damage = 5;
        reinforced = true;
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
        this.name = "Iron Wave";
        this.cost = 1;
        this.card_type = "Attack";
    }

    void action(Player player, HumanObject enemy){
        player.setArmor(player.getArmor() + block);
        enemy.TakeDamage(damage);
    }

    void reinforce() {
        damage = 7;
        block = 7;
        reinforced = true;
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
        this.name = "Perfected Strike";
        this.cost = 2;
        this.card_type = "Attack";
    }

    void action(Player player, HumanObject enemy){
        for(int i=0; i<player.deck.size(); i++){
            if ( player.deck.get(i).name.contains("Strike") ){
                damage += add_damage;
            }
        }
        for(int i=0; i<player.hand.size(); i++){
            if ( player.hand.get(i).name.contains("Strike") ){
                damage += add_damage;
            }
        }
        for (int i=0; i<player.grave.size(); i++){
            if ( player.grave.get(i).name.contains("Strike") ){
                damage += add_damage;
            }
        }

        enemy.TakeDamage(damage);
    }

    void reinforce() {
        add_damage = 3;
        reinforced = true;
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
        this.name = "Pommel Strike";
        this.cost = 1;
        this.card_type = "Attack";
    }

    void action(Player player, HumanObject enemy) {
        enemy.TakeDamage(damage);

        random = new Random();
        for(int i = 0; i < draw; i++){
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

    void reinforce() {
        damage = 10;
        draw = 2;
        reinforced = true;
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
        this.name = "Shrug_It_Off";
        this.cost = 1;
        this.card_type = "Skill";
    }

    void action(Player player, HumanObject enemy){
        player.setArmor( player.getArmor() + block );

        random = new Random();
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

    void reinforce() {
        block = 11;
        reinforced = true;
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
        this.name = "Twin Strike";
        this.cost = 1;
        this.card_type = "Attack";
    }

    void action(Player player, HumanObject enemy){
        for(int i=0; i<2; i++){
            enemy.TakeDamage(damage);
        }
    }

    void reinforce() {
        damage = 7;
        reinforced = true;
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
        this.name = "Warcry";
        this.cost = 0;
        this.card_type = "Skill";
    }

    void action(Player player, HumanObject enemy){
        random = new Random();
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

        scanner = new Scanner( System.in );
        int index = scanner.nextInt();
        player.hand.get(index);
    }

    void reinforce() {
        draw = 2;
        reinforced = true;
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
        this.name = "Bloodletting";
        this.cost = 0;
        this.card_type = "Skill";
    }

    void action(Player player, HumanObject enemy){
        player.setHp( player.getHp() - 3 );
        player.setMana( player.getMana() + energy );
    }

    void reinforce() {
        energy = 2;
        reinforced = true;
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
        this.name = "Burning Pact";
        this.cost = 1;
        this.card_type = "Skill";
    }

    void action(Player player, HumanObject enemy){
        System.out.println("Input a index which will be Exhaust.");
        scanner = new Scanner(System.in);
        int index = scanner.nextInt();
        // Exhaust

        random = new Random();
        for(int i = 0; i < draw; i++) {
            if (player.deck.size() == 0) {
                while (player.grave.size() != 0) {
                    int randInt = random.nextInt(player.grave.size());
                    Card card = player.grave.get(randInt);
                    player.grave.remove(randInt);
                    player.deck.add(card);
                }
            }
            if (player.deck.size() == 0) return;
            Card card = player.deck.get(0);
            player.deck.remove(0);
            player.hand.add(card);
        }
    }

    void reinforce() {
        draw = 2;
        reinforced = true;
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
        this.name = "Entrench";
        this.cost = 2;
        this.card_type = "Skill";
    }

    void action(Player player, HumanObject enemy){
        player.setArmor( player.getArmor() * 2 );
    }

    void reinforce() {
        this.cost = 1;
        reinforced = true;
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
        this.name = "Hemokinesis";
        this.cost = 1;
        this.card_type = "Attack";
    }

    void action(Player player, HumanObject enemy){
        player.setHp( player.getHp() - lossHP );
        enemy.TakeDamage(damage);
    }

    void reinforce() {
        lossHP = 2;
        damage = 18;
        reinforced = true;
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
        this.name = "Inflame";
        this.cost = 1;
        this.card_type = "Power";
    }

    void action(Player player, HumanObject enemy){
        player.setStrength( player.getStrength() + strength );
    }

    void reinforce() {
        strength = 3;
        reinforced = true;
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

class Metallicize extends Card {
    int block = 3;

    Metallicize() {
        this.name = "Metallicize";
        this.cost = 1;
        this.card_type = "Power";
    }

    void action(Player player, HumanObject enemy){
        // 보류
    }

    void reinforce() {
        block = 4;
        reinforced = true;
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
        return "At the end of your turn, gain " + block + " Block.";
    }
}

class Rampage extends Card {
    int damage = 8;
    int add_damage = 5;

    Rampage() {
        this.name = "Rampage";
        this.cost = 1;
        this.card_type = "Attack";
    }

    void action(Player player, HumanObject enemy){
        enemy.TakeDamage(damage);
        damage += add_damage;
    }

    void reinforce() {
        add_damage = 8;
        reinforced = true;
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
        this.name = "Seeing Red";
        this.cost = 1;
        this.card_type = "Skill";
    }

    void action(Player player, HumanObject enemy){
        player.setMana( player.getMana() + 2 );
        //소멸
    }

    void reinforce() {
        this.cost = 0;
        reinforced = true;
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

class Barricade extends Card {
    Barricade() {
        this.name = "Barricade";
        this.cost = 3;
        this.card_type = "Power";
    }

    void action(Player player, HumanObject enemy){
        //보류
    }

    void reinforce() {
        this.cost = 2;
        reinforced = true;
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
        return "Block no longer expires at the start of your turn.";
    }
}

class Bludgeon extends Card {
    int damage = 32;
    Bludgeon() {
        this.name = "Bludgeon";
        this.cost = 3;
        this.card_type = "Attack";
    }

    void action(Player player, HumanObject enemy){
        enemy.TakeDamage(damage);
    }

    void reinforce() {
        damage = 42;
        reinforced = true;
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

class Demon_Form extends Card {
    int add_strength = 2;
    Demon_Form() {
        this.name = "Demon Form";
        this.cost = 3;
        this.card_type = "Power";
    }

    void action(Player player, HumanObject enemy){

    }

    void reinforce() {
        add_strength = 3;
        reinforced = true;
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
        return "At the start of each turn, gain " + add_strength + " Strength.";
    }
}

class Double_Tap extends Card {
    Double_Tap() {
        this.name = "Double Tap";
        this.cost = 1;
        this.card_type = "Skill";
    }

    void action(Player player, HumanObject enemy){
        // 보류
    }

    void reinforce() {
        reinforced = true;
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
        if (reinforced) {
            return "This turn, your next 2 Attacks are played twice.";
        }
        else {
            return "This turn, your next Attack is played twice.";
        }
    }
}

class Impervious extends Card {
    int block = 30;
    Impervious() {
        this.name = "Impervious";
        this.cost = 2;
        this.card_type = "Skill";
    }

    void action(Player player, HumanObject enemy){
        player.setArmor( player.getArmor() + block );
        //소멸
    }

    void reinforce() {
        block = 40;
        reinforced = true;
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
        this.name = "Limit Break";
        this.cost = 1;
        this.card_type = "Skill";
    }

    void action(Player player, HumanObject enemy){
        player.setStrength( player.getStrength() * 2 );
        //소멸
    }

    void reinforce() {
        reinforced = true;
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
        if (reinforced) {
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
        this.name = "Offering";
        this.cost = 0;
        this.card_type = "Skill";
    }

    void action(Player player, HumanObject enemy){
        player.setHp( player.getHp() - 6 );
        player.setMana( player.getMana() + 2 );

        random = new Random();
        for(int i = 0; i < draw; i++) {
            if (player.deck.size() == 0) {
                while (player.grave.size() != 0) {
                    int randInt = random.nextInt(player.grave.size());
                    Card card = player.grave.get(randInt);
                    player.grave.remove(randInt);
                    player.deck.add(card);
                }
            }
            if (player.deck.size() == 0) return;
            Card card = player.deck.get(0);
            player.deck.remove(0);
            player.hand.add(card);
        }
    }

    void reinforce() {
        draw = 5;
        reinforced = true;
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

// https://slay-the-spire.fandom.com/wiki/Silent_Cards
// 35 Cards.
class Survivor extends Card {
    int block = 8;

    Survivor() {
        this.name = "Survivor";
        this.cost = 1;
        this.card_type = "Skill";
    }

    void action(Player player, HumanObject enemy){
        player.setArmor( player.getArmor() + block );

        System.out.println("Input a index number of card which will discard.");
        int index = scanner.nextInt();
        player.grave.add( player.hand.get(index) );
        player.hand.remove( index );
    }

    void reinforce() {
        block = 11;
        reinforced = true;
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
        this.name = "Acrobatics";
        this.cost = 1;
        this.card_type = "Skill";
    }

    void action(Player player, HumanObject enemy){
        random = new Random();
        for(int i = 0; i < draw; i++) {
            if (player.deck.size() == 0) {
                while (player.grave.size() != 0) {
                    int randInt = random.nextInt(player.grave.size());
                    Card card = player.grave.get(randInt);
                    player.grave.remove(randInt);
                    player.deck.add(card);
                }
            }
            if (player.deck.size() == 0) return;
            Card card = player.deck.get(0);
            player.deck.remove(0);
            player.hand.add(card);
        }

        System.out.println("Input a index number of card which will discard.");
        int index = scanner.nextInt();
        player.grave.add( player.hand.get(index) );
        player.hand.remove( index );
    }

    void reinforce() {
        draw = 4;
        reinforced = true;
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
        this.name = "Backfilp";
        this.cost = 1;
        this.card_type = "Skill";
    }

    void action(Player player, HumanObject enemy){
        player.setArmor( player.getArmor() + block );

        random = new Random();
        for(int i = 0; i < 2; i++) {
            if (player.deck.size() == 0) {
                while (player.grave.size() != 0) {
                    int randInt = random.nextInt(player.grave.size());
                    Card card = player.grave.get(randInt);
                    player.grave.remove(randInt);
                    player.deck.add(card);
                }
            }
            if (player.deck.size() == 0) return;
            Card card = player.deck.get(0);
            player.deck.remove(0);
            player.hand.add(card);
        }
    }

    void reinforce() {
        block = 8;
        reinforced = true;
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
        this.name = "Bane";
        this.cost = 1;
        this.card_type = "Attack";
    }

    void action(Player player, HumanObject enemy){
        enemy.TakeDamage(damage);
        if (enemy.getPoisonDamage() > 0){
            enemy.TakeDamage(damage);
        }
    }

    void reinforce() {
        damage = 10;
        reinforced = true;
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
        this.name = "Blade Dance";
        this.cost = 1;
        this.card_type = "Skill";
    }

    void action(Player player, HumanObject enemy){
        Card shiv = new Shiv();
        for (int i=0; i<add_shivs; i++) {
            player.hand.add(shiv);
        }
    }

    void reinforce() {
        add_shivs = 3;
        reinforced = true;
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
        this.name = "Cloak And Dagger";
        this.cost = 1;
        this.card_type = "Skill";
    }

    void action(Player player, HumanObject enemy){
        player.setArmor( player.getArmor() + 6 );

        Card shiv = new Shiv();
        for (int i=0; i<add_shivs; i++) {
            player.hand.add(shiv);
        }
    }

    void reinforce() {
        add_shivs = 2;
        reinforced = true;
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
        this.name = "Dagger Throw";
        this.cost = 1;
        this.card_type = "Attack";
    }

    void action(Player player, HumanObject enemy){
        enemy.TakeDamage(damage);

        random = new Random();
        for(int i = 0; i < 2; i++) {
            if (player.deck.size() == 0) {
                while (player.grave.size() != 0) {
                    int randInt = random.nextInt(player.grave.size());
                    Card card = player.grave.get(randInt);
                    player.grave.remove(randInt);
                    player.deck.add(card);
                }
            }
            if (player.deck.size() == 0) return;
            Card card = player.deck.get(0);
            player.deck.remove(0);
            player.hand.add(card);
        } // Card Draw

        System.out.println("Input a index number of card which will discard.");
        int index = scanner.nextInt();
        player.grave.add( player.hand.get(index) );
        player.hand.remove( index ); // Card Discard
    }

    void reinforce() {
        damage = 12;
        reinforced = true;
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
        this.name = "Deadly Poison";
        this.cost = 1;
        this.card_type = "Skill";
    }

    void action(Player player, HumanObject enemy){
        enemy.setPoisonDamage( enemy.getPoisonDamage() + poison );
        enemy.setPoisoned(true);
    }

    void reinforce() {
        poison = 7;
        reinforced = true;
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
        this.name = "Deflect";
        this.cost = 0;
        this.card_type = "Skill";
    }

    void action(Player player, HumanObject enemy){
        player.setArmor( player.getArmor() + block );
    }

    void reinforce() {
        block = 7;
        reinforced = true;
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
        this.name = "Flying Knee";
        this.cost = 1;
        this.card_type = "Attack";
    }

    void action(Player player, HumanObject enemy){

    }

    void reinforce() {
        damage = 11;
        reinforced = true;
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
        this.name = "Outmaneuver";
        this.cost = 1;
        this.card_type = "Skill";
    }

    void action(Player player, HumanObject enemy){

    }

    void reinforce() {
        add_energy = 3;
        reinforced = true;
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
        this.name = "Poisoned Stab";
        this.cost = 1;
        this.card_type = "Attack";
    }

    void action(Player player, HumanObject enemy){
        enemy.TakeDamage(damage);
        enemy.setPoisonDamage(enemy.getPoisonDamage() + poison);
        enemy.setPoisoned(true);
    }

    void reinforce() {
        damage = 8;
        poison = 4;
        reinforced = true;
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
        this.name = "Prepared";
        this.cost = 0;
        this.card_type = "Skill";
    }

    void action(Player player, HumanObject enemy){
        random = new Random();
        for(int i = 0; i < draw; i++) {
            if (player.deck.size() == 0) {
                while (player.grave.size() != 0) {
                    int randInt = random.nextInt(player.grave.size());
                    Card card = player.grave.get(randInt);
                    player.grave.remove(randInt);
                    player.deck.add(card);
                }
            }
            if (player.deck.size() == 0) return;
            Card card = player.deck.get(0);
            player.deck.remove(0);
            player.hand.add(card);
        } // Card Draw

        for(int i=0; i<draw; i++){
            System.out.println("Input a index number of card which will discard.");
            int index = scanner.nextInt();
            player.grave.add( player.hand.get(index) );
            player.hand.remove( index ); // Card Discard
        }
    }

    void reinforce() {
        draw = 2;
        reinforced = true;
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
        this.name = "Quick Slash";
        this.cost = 1;
        this.card_type = "Attack";
    }

    void action(Player player, HumanObject enemy){
        enemy.TakeDamage(damage);

        random = new Random();
        if (player.deck.size() == 0) {
            while (player.grave.size() != 0) {
                int randInt = random.nextInt(player.grave.size());
                Card card = player.grave.get(randInt);
                player.grave.remove(randInt);
                player.deck.add(card);
            }
        }
        if (player.deck.size() == 0) return;
        Card card = player.deck.get(0);
        player.deck.remove(0);
        player.hand.add(card);
    }

    void reinforce() {
        damage = 12;
        reinforced = true;
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

class Sneaky_Strike extends Card {
    int damage = 10;

    Sneaky_Strike() {
        this.name = "Sneaky Strike";
        this.cost = 2;
        this.card_type = "Attack";
    }

    void action(Player player, HumanObject enemy){
        enemy.TakeDamage(damage);
        // 보류
    }

    void reinforce() {
        damage = 14;
        reinforced = true;
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
        return "Deal " + damage + " damage. " + "If you have discarded a card this turn, gain 2 Energy.";
    }
}

class Accuracy extends Card {
    int add_damage = 3;

    Accuracy() {
        this.name = "Accuracy";
        this.cost = 1;
        this.card_type = "Power";
    }

    void action(Player player, HumanObject enemy){
        for(int i=0; i<player.deck.size(); i++){
            if (player.deck.get(i).name == "Shiv"){
                //
            }
        }
    }

    void reinforce() {
        add_damage = 5;
        reinforced = true;
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
        return "Shivs deal " + add_damage + " additional damage.";
    }
}

class Calculated_Gamble extends Card {
    Calculated_Gamble() {
        this.name = "Calculated Gamble";
        this.cost = 0;
        this.card_type = "Skill";
    }

    void action(Player player, HumanObject enemy){
        int numOfcards = player.hand.size() - 1;

        player.grave.addAll(player.hand);
        player.hand.clear();

        for(int i = 0; i < numOfcards; i++) {
            if (player.deck.size() == 0) {
                while (player.grave.size() != 0) {
                    int randInt = random.nextInt(player.grave.size());
                    Card card = player.grave.get(randInt);
                    player.grave.remove(randInt);
                    player.deck.add(card);
                }
            }
            if (player.deck.size() == 0) return;
            Card card = player.deck.get(0);
            player.deck.remove(0);
            player.hand.add(card);
        } // Card Draw
    }

    void reinforce() {
        reinforced = true;
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
        if (!reinforced) {
            return "Discard your hand, then draw that many cards. " + "Exhaust.";
        }
        else {
            return "Discard your hand, then draw that many cards.";
        }
    }
}

class Catalyst extends Card {

    Catalyst() {
        this.name = "Catalyst";
        this.cost = 1;
        this.card_type = "Skill";
    }

    void action(Player player, HumanObject enemy){
        if (reinforced) {
            enemy.setPoisonDamage( enemy.getPoisonDamage() * 3 );
        }
        else{
            enemy.setPoisonDamage( enemy.getPoisonDamage() * 2 );
        }
    }

    void reinforce() {
        reinforced = true;
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
        if (!reinforced) {
            return "Double an enemy's Poison. " + "Exhaust.";
        }
        else {
            return "Triple an enemy's Poison.";
        }
    }
}

class Choke extends Card {
    int damage = 12;
    int add_damage = 3;

    Choke() {
        this.name = "Choke";
        this.cost = 2;
        this.card_type = "Attack";
    }

    void action(Player player, HumanObject enemy){
        enemy.TakeDamage(damage);
        //
    }

    void reinforce() {
        add_damage = 5;
        reinforced = true;
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
        return "Deal 12 damage. " + "Whenever you play a card this turn, the enemy loses " + add_damage + " HP.";
    }
}

class Concentrate extends Card {
    int energy = 2;
    int dis_card = 3;

    Concentrate() {
        this.name = "Concentrate";
        this.cost = 0;
        this.card_type = "Skill";
    }

    void action(Player player, HumanObject enemy){
        for(int i=0; i<dis_card; i++){
            System.out.println("Input a index number of card which will discard.");
            int index = scanner.nextInt();
            player.grave.add( player.hand.get(index) );
            player.hand.remove( index ); // Card Discard
        }

        player.setMana( player.getMana() + energy );
    }

    void reinforce() {
        dis_card = 2;
        reinforced = true;
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
        this.name = "Dash";
        this.cost = 2;
        this.card_type = "Attack";
    }

    void action(Player player, HumanObject enemy){
        player.setArmor( player.getArmor() + block );
        enemy.TakeDamage(damage);
    }

    void reinforce() {
        damage = 13;
        block = 13;
        reinforced = true;
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
        this.name = "Escape Plan";
        this.cost = 0;
        this.card_type = "Skill";
    }

    void action(Player player, HumanObject enemy){
        //
    }

    void reinforce() {
        block = 5;
        reinforced = true;
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
        return "Draw 1 card. " + "If you darw a Skill, gain " + block + " Block.";
    }
}

class Footwork extends Card {
    int dexterity = 2;

    Footwork() {
        this.name = "Footwork";
        this.cost = 1;
        this.card_type = "Power";
    }

    void action(Player player, HumanObject enemy){
        player.setDexterity( player.getDexterity() + dexterity );
    }

    void reinforce() {
        dexterity = 3;
        reinforced = true;
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

class Infinite_Blades extends Card {
    Infinite_Blades() {
        this.name = "Infinite Blades";
        this.cost = 1;
        this.card_type = "Power";
    }

    void action(Player player, HumanObject enemy){
//
    }

    void reinforce() {
        reinforced = true;
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
        if (reinforced) {
            return "At the start of your turn, add a Shiv into your hand " + "Innate";
        }
        else {
            return "At the start of your turn, add a Shiv into your hand";
        }
    }
}

class Noxious_Fumes extends Card {
    int poison = 2;

    Noxious_Fumes() {
        this.name = "Noxious Fumes";
        this.cost = 1;
        this.card_type = "Power";
    }

    void action(Player player, HumanObject enemy){
//
    }

    void reinforce() {
        poison = 3;
        reinforced = true;
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
        return "At the start of your turn, apply " + poison + " Poison to enemy";
    }
}

class Skewer extends Card {
    int damage = 7;

    Skewer() {
        this.name = "Skewer";
        this.cost = -1;
        this.card_type = "Attack";
    }

    void action(Player player, HumanObject enemy){
        for (int i=0; i<player.getMana(); i++){
            enemy.TakeDamage(damage);
        }
    }

    void reinforce() {
        damage = 10;
        reinforced = true;
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
        this.name = "Adrenaline";
        this.cost = 0;
        this.card_type = "Skill";
    }

    void action(Player player, HumanObject enemy){
        player.setMana( player.getMana() + energy );

        for(int i = 0; i < 2; i++) {
            if (player.deck.size() == 0) {
                while (player.grave.size() != 0) {
                    int randInt = random.nextInt(player.grave.size());
                    Card card = player.grave.get(randInt);
                    player.grave.remove(randInt);
                    player.deck.add(card);
                }
            }
            if (player.deck.size() == 0) return;
            Card card = player.deck.get(0);
            player.deck.remove(0);
            player.hand.add(card);
        } // Card Draw
    }

    void reinforce() {
        energy = 2;
        reinforced = true;
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

class After_Image extends Card {
    After_Image() {
        this.name = "After Image";
        this.cost = 1;
        this.card_type = "Power";
    }

    void action(Player player, HumanObject enemy){

    }

    void reinforce() {
        reinforced = true;
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
        if (reinforced) {
            return "Whenever you play a card, gain 1 Block. " + "Innate.";
        }
        else {
            return "Whenever you play a card, gain 1 Block.";
        }

    }
}

class Bullet_Time extends Card {
    Bullet_Time() {
        this.name = "Bullet_Time";
        this.cost = 3;
        this.card_type = "Skill";
    }

    void action(Player player, HumanObject enemy){
//
    }

    void reinforce() {
        this.cost = 2;
        reinforced = true;
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
        return "You cannot draw additional cards this turn. Reduce the cost of cards in your hand to 0 this turn.";
    }
}

class Burst extends Card {
    int skill = 1;

    Burst() {
        this.name = "Burst";
        this.cost = 1;
        this.card_type = "Skill";
    }

    void action(Player player, HumanObject enemy){
//
    }

    void reinforce() {
        skill = 2;
        reinforced = true;
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
        if (reinforced) {
            return "This turn your next 1 Skill is played twice.";
        }
        else {
            return "This turn your next 2 Skills are played twice.";
        }
    }
}

class Doppelganger extends Card {
    Doppelganger() {
        this.name = "Doppelganger";
        this.cost = -1;
        this.card_type = "Skill";
    }

    void action(Player player, HumanObject enemy){

    }

    void reinforce() {
        reinforced = true;
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
        if (reinforced) {
            return "Next turn, draw X cards and gain X Energy.";
        }
        else {
            return "Next turn, draw X+1 cards and gain X+1 Energy.";
        }
    }
}

class Phantasmal_Killer extends Card {
    Phantasmal_Killer() {
        this.name = "Phantasmal Killer";
        this.cost = 2;
        this.card_type = "Skill";
    }

    void action(Player player, HumanObject enemy){

    }

    void reinforce() {
        this.cost = 1;
        reinforced = true;
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
        return "Next turn, your Attacks deal double damage.";
    }
}

class Tools_of_the_Trade extends Card {
    Tools_of_the_Trade() {
        this.name = "Tools of the Trade";
        this.cost = 1;
        this.card_type = "Power";
    }

    void action(Player player, HumanObject enemy){

    }

    void reinforce() {
        this.cost = 0;
        reinforced = true;
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
        return "At the start of your turn, draw 1 card and discard 1 card.";
    }
}

class Shiv extends Card {
    int damage = 4;

    Shiv() {
        this.name = "Shiv";
        this.cost = 0;
        this.card_type = "Attack";
    }

    void action(Player player, HumanObject enemy){
        enemy.TakeDamage(damage);
    }

    void reinforce() {
        damage = 6;
        reinforced = true;
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