package com.skkujava;

public abstract class HumanObject {
    private int maxHp;
    private int hp;
    private int armor;
    private int strength;
    private boolean isPoisoned;
    private int poisonDamage;

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

    public void TakeDamage(int damage) {
        int temp = armor;
        armor -= damage;
        damage -= temp;
        if(armor < 0) {
            armor = 0;
            hp -= damage;
        }
    }

    // 각 턴이 끝날 때 isPoisoned 이면 이 함수 실행
    public void TakePoisonDamage() {
        hp -= poisonDamage;
        if(poisonDamage > 0)
            poisonDamage--;
        if(poisonDamage == 0)
            isPoisoned = false;
    }
}
