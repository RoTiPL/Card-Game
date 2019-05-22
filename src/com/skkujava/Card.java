package com.skkujava;

abstract public class Card {
    String name;
    int cost;
    abstract void action(Player player, HumanObject enemy);
    abstract void reinforce();
    abstract String cardDescription();
}

class Attack extends Card{
    int damage = 3;
    @Override
    void action(Player player, HumanObject enemy) {

    }

    @Override
    void reinforce() {
        damage =5;
    }

    @Override
    String cardDescription() {
        return "asdfasdf";
    }
}
