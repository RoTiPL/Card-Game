package com.skkujava;

import java.util.ArrayList;

public class Player extends HumanObject {
    ArrayList<Card> deck, hand, grave;

    String name;
    private int maxMana;
    private int mana;
    private int bonusMana;
    private int drawCount;
    private int dexterity;
    private boolean superpower;

    public Player() {
        setMaxMana(3);
        setMana(getMaxMana());
        setBonusMana(0);
        setArmor(0);
        setStrength(0);
        setDexterity(0);
        setPoisoned(false);
        setPoisonDamage(0);
        setSuperpower(false);
        drawCount = 5;
        deck = new ArrayList<>();
        hand = new ArrayList<>();
        grave = new ArrayList<>();
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

    public int getBonusMana() { return bonusMana; }

    public void setBonusMana(int bonusMana) { this.bonusMana = bonusMana; }

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

    public boolean isSuperpower() {
        return superpower;
    }

    public void setSuperpower(boolean superpower) {
        this.superpower = superpower;
    }

    public void completeFloor() {
        // Thief: none
        // Warrior: hp +7
    }
}