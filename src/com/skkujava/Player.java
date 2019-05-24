package com.skkujava;

import java.util.ArrayList;

public class Player extends HumanObject {
    ArrayList<Card> deck, hand, grave;

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getMaxhp() {
        return maxhp;
    }

    public void setMaxhp(int maxhp) {
        this.maxhp = maxhp;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getMaxmana() {
        return maxmana;
    }

    public void setMaxmana(int maxmana) {
        this.maxmana = maxmana;
    }

    private int hp, maxhp, mana, maxmana;


}
