package com.skkujava;
import java.util.Random;

abstract class Boss extends HumanObject{

    Player player;
    int floor;
    String name;

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
        this.name = "Poisoner";
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
                if(!player.isPoisoned()) PoisonBottle();
                else                     PoisonAmplification();
                break;
            case 2:
                Defend();
                break;
        }

        if(player.isPoisoned()) {
            player.TakePoisonDamage();
        }
    }

    public void PoisonBottle() {
        int damage = 5 + floor;
        player.setPoisoned(true);
        player.setPoisonDamage(player.getPoisonDamage() + damage);
        System.out.println("Poisoner throws poison bottle!");
        System.out.println("You got " + damage + " poison.");
    }

    public void PoisonAmplification() {
        player.setPoisonDamage(player.getPoisonDamage() * 2);
        System.out.println("Poisoner amplifies your poison!");
        System.out.println("Your poison is amplified by x2.");
    }

    public void Defend() {
        int armor = 5 + floor;
        setArmor(getArmor() + armor);
        System.out.println("Poisoner takes guard action!");
        System.out.println("Poisoner got " + armor + " armor.");
    }
}

class Giant extends Boss {

    public Giant(Player player, int floor) {
        this.name = "Giant";
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
    }

    public void BigSwing() {
        int damage = 10 + 2 * floor + getStrength();
        player.TakeDamage(damage);
        System.out.println("Giant swings his big bat!");
        System.out.println("You got " + damage + " damage.");
    }

    public void OverflowingStrength() {
        setStrength(getStrength() + 3);
        System.out.println("Giant is shouting!");
        System.out.println("Giant got 3 strength.");
    }

    public void DoubleSwing() {
        int damage = 5 + floor + getStrength();
        player.TakeDamage(damage);
        player.TakeDamage(damage);
        System.out.println("Giant swings his bat twice!");
        System.out.println("You got " + damage + " damage.");
        System.out.println("You got " + damage + " damage.");
    }
}

class Slime extends Boss {

    public Slime(Player player, int floor) {
        this.name = "Slime";
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
                if(getHp() == getMaxHp()) Defend();
                else                      Heal();
                break;
            case 2:
                Defend();
                break;
        }
    }

    public void Attack() {
        int damage = 7 + floor + getStrength();
        player.TakeDamage(damage);
        System.out.println("Slime is rushing to you!");
        System.out.println("You got " + damage + " damage.");
    }

    public void Heal() {
        int heal = 20 + 2 * floor;
        if(getHp() + heal > getMaxHp())
            setHp(getMaxHp());
        else
            setHp(getHp() + heal);
        System.out.println("Slime heals itself!");
        System.out.println("Slime healed " + heal + " HP.");
    }

    public void Defend() {
        int armor = 5 + floor;
        setArmor(getArmor() + armor);
        System.out.println("Slime takes guard action!");
        System.out.println("Slime got " + armor + " armor.");
    }
}