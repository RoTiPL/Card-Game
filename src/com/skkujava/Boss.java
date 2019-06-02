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
        System.out.println("독술사가 독병을 던집니다.");
        System.out.println(damage + "만큼의 독에 중독되었습니다.");
    }

    public void PoisonAmplification() {
        player.setPoisonDamage(player.getPoisonDamage() * 2);
        System.out.println("독술사가 당신의 독을 증폭시킵니다. 독이 두배로 증폭됩니다.");
    }

    public void Defend() {
        int armor = 5 + floor;
        setArmor(getArmor() + armor);
        System.out.println("독술사가 방어 자세를 취합니다.");
        System.out.println("독술사가 " + armor + "의 방어도를 얻었습니다.");
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
        System.out.println("거인이 큰 방망이를 휘두릅니다.");
        System.out.println(damage + "의 피해를 입었습니다.");
    }

    public void OverflowingStrength() {
        setStrength(getStrength() + 3);
        System.out.println("거인이 기합을 외칩니다.");
        System.out.println("거인의 힘이 3만큼 증가합니다.");
    }

    public void DoubleSwing() {
        int damage = 5 + floor + getStrength();
        player.TakeDamage(damage);
        player.TakeDamage(damage);
        System.out.println("거인이 방망이를 두 번 휘두릅니다.");
        System.out.println(damage + "의 피해를 입었습니다.");
        System.out.println(damage + "의 피해를 입었습니다.");
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
        System.out.println("슬라임이 몸통박치기를 시전합니다.");
        System.out.println(damage + "만큼의 피해를 입었습니다.");
    }

    public void Heal() {
        int heal = 20 + 2 * floor;
        if(getHp() + heal > getMaxHp())
            setHp(getMaxHp());
        else
            setHp(getHp() + heal);
        System.out.println("슬라임이 자가 회복을 했습니다.");
        System.out.println("슬라임이 " + heal + "만큼의 체력을 회복했습니다.");
    }

    public void Defend() {
        int armor = 5 + floor;
        setArmor(getArmor() + armor);
        System.out.println("슬라임이 방어 자세를 취합니다.");
        System.out.println("슬라임이 " + armor + "의 방어도를 얻었습니다.");
    }
}