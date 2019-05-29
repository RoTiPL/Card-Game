package com.skkujava;

import java.util.ArrayList;

public class Player extends HumanObject {
    ArrayList<Card> deck, hand, grave;

    private int maxMana;
    private int mana;
    private int drawCount;
    private int dexterity;

    public Player() {
        setMaxMana(3);
        setMana(getMaxMana());
        setArmor(0);
        setStrength(0);
        setDexterity(0);
        setPoisoned(false);
        setPoisonDamage(0);
    }

    public int getMaxMana() {
        return maxMana;
    }

    public void setMaxMana(int maxMana) {
        this.maxMana = maxMana;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getDrawCount() {
        return drawCount;
    }

    public void setDrawCount(int drawCount) {
        this.drawCount = drawCount;
    }

    public int getDexterity() {
        return dexterity;
    }

    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }

    public void completeFloor() {
        // Thief: none
        // Warrior: hp +7
    }
}