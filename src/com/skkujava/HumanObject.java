package com.skkujava;

public abstract class HumanObject {

    private String name;
    private int maxHp;
    private int hp;
    private int armor;
    private int strength;
    private boolean isPoisoned;
    private int poisonDamage;

    private String[] asciiArt = new String[13];

    public int getMaxHp() {
        return maxHp;
    }

    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public boolean isPoisoned() {
        return isPoisoned;
    }

    public void setPoisoned(boolean poisoned) {
        isPoisoned = poisoned;
    }

    public int getPoisonDamage() {
        return poisonDamage;
    }

    public void setPoisonDamage(int poisonDamage) {
        this.poisonDamage = poisonDamage;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public void setAsciiArt(int i, String str) { asciiArt[i] = str; }

    public String getAsciiArt(int i) { return asciiArt[i]; }

    public void TakeDamage(int damage) {
        int temp = armor;
        armor -= damage;
        damage -= temp;
        if(armor < 0) {
            armor = 0;
            hp -= damage;
        }
        if(damage < 0)damage = 0;
        System.out.println("=======================================");
        System.out.println(getName() + " got " + damage + " damage.");
        System.out.println("=======================================");
    }

    // 각 턴이 끝날 때 isPoisoned 이면 이 함수 실행
    public void TakePoisonDamage() {
        int realDmg;
        if(armor >= poisonDamage) {
            armor -= poisonDamage;
            realDmg = 0;
        }
        else {
            realDmg = poisonDamage - armor;
            armor = 0;
        }

        System.out.println("=======================================");
        System.out.println(getName() + " got " + realDmg + " poison damage.");
        System.out.println("=======================================");
        hp -= realDmg;
        if(poisonDamage > 0)
            poisonDamage--;
        if(poisonDamage == 0)
            isPoisoned = false;
    }
}
