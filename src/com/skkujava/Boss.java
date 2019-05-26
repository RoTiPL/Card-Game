package com.skkujava;
import java.util.Random;

abstract class Boss extends HumanObject{

    Player player;
    int floor;

    public static Boss CreateBoss(Player player, int floor) {

        Random random = new Random();
        Boss boss = null;
        int num = random.nextInt(3);
        switch(num) {
            case 0:
                boss = new Poisoner(player, floor);
                break;
            case 1:
                boss = new Giant(player, floor);
                break;
            case 2:
                boss = new Slime(player, floor);
                break;
        }
        return boss;
    }

    abstract void Action();
}

class Poisoner extends Boss {

    public Poisoner(Player player, int floor) {
        this.player = player;
        this.floor = floor;
        setMaxHp(50 + 5 * floor);
        setHp(getMaxHp());
        setArmor(0);
        setPoisoned(false);
        setPoisonDamage(0);
        setStrength(0);
    }

    public void Action() {
        Random random = new Random();
        int act = random.nextInt(3);

        switch(act) {
            case 0:
                PoisonBottle();
                break;
            case 1:
                PoisonAmplification();
                break;
            case 2:
                Defend();
                break;
        }

        if(player.isPoisoned()) {
            player.TakePoisonDamage();
        }

        if(player.getHp() <= 0) {
            // Game Over
        }
    }

    public void PoisonBottle() {
        int damage = 5 + floor;
        player.setPoisoned(true);
        player.setPoisonDamage(player.getPoisonDamage() + damage);
    }

    public void PoisonAmplification() {
        player.setPoisonDamage(player.getPoisonDamage() * 2);
    }

    public void Defend() {
        setArmor(getArmor() + 5 + floor);
    }
}

class Giant extends Boss {

    public Giant(Player player, int floor) {
        this.player = player;
        this.floor = floor;
        setMaxHp(100 + 5 * floor);
        setHp(getMaxHp());
        setArmor(0);
        setPoisoned(false);
        setPoisonDamage(0);
        setStrength(0);
    }

    public void Action() {
        Random random = new Random();
        int act = random.nextInt(3);

        switch(act) {
            case 0:
                BigSwing();
                break;
            case 1:
                OverflowingStrength();
                break;
            case 2:
                DoubleSwing();
                break;
        }

        if(player.getHp() <= 0) {
            // Game Over
        }
    }

    public void BigSwing() {
        int damage = 10 + 2 * floor + getStrength();
        player.TakeDamage(damage);
    }

    public void OverflowingStrength() {
        setStrength(getStrength() + 3);
    }

    public void DoubleSwing() {
        int damage = 5 + floor + getStrength();
        player.TakeDamage(damage);
        player.TakeDamage(damage);
    }
}

class Slime extends Boss {

    public Slime(Player player, int floor) {
        this.player = player;
        this.floor = floor;
        setMaxHp(80);
        setHp(getMaxHp());
        setArmor(0);
        setPoisoned(false);
        setPoisonDamage(0);
        setStrength(0);
    }

    public void Action() {
        Random random = new Random();
        int act = random.nextInt(3);

        switch(act) {
            case 0:
                Attack();
                break;
            case 1:
                Heal();
                break;
            case 2:
                Defend();
                break;
        }

        if(player.getHp() <= 0) {
            // Game Over
        }
    }

    public void Attack() {
        int damage = 7 + floor + getStrength();
        player.TakeDamage(damage);
    }

    public void Heal() {
        int heal = 20 + 2 * floor;
        if(getHp() + heal > getMaxHp())
            setHp(getMaxHp());
        else
            setHp(getHp() + heal);
    }

    public void Defend() {
        setArmor(getArmor() + 5 + floor);
    }
}