package com.skkujava;
import java.util.Random;

abstract class Boss extends HumanObject{

    private Player player;
    private int floor;

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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }
}

class Poisoner extends Boss {

    public Poisoner(Player player, int floor) {
        setName("Poisoner");
        setAsciiArt(0, "        l:         \",!{    :+       ");
        setAsciiArt(1, "        `>ii_>>:!1]})i   [)         ");
        setAsciiArt(2, "           >>iI(}ijLJc1+}~.         ");
        setAsciiArt(3, "           ,!Ilw{YOYvczzLmuf        ");
        setAsciiArt(4, "           :!IX,XCvnnvvcLQLJ0- ..   ");
        setAsciiArt(5, "          _j;/i)LcvvzzcJO0Q00dC!    ");
        setAsciiArt(6, "         ;xf:l]QXczXzzUOOQ0JUZZOZi  ");
        setAsciiArt(7, "        |vJUUcuuunvzXYYJ0OZLZ/iZ+ {{");
        setAsciiArt(8, "       ~C~~`?JzxrncXYUXzJO-fI^~^    ");
        setAsciiArt(9, "     Iz/      (znnvcXJUUXf^     .   ");
        setAsciiArt(10, "    jU,         ?uccJ?~\\f|.    ..   ");
        setAsciiArt(11, " ^j0Y`            !Ii<  {\\{;__i-    ");
        setAsciiArt(12, "  \"+nuxf/\\1{}                       ");
        this.setPlayer(player);
        this.setFloor(floor);
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
                if(!getPlayer().isPoisoned()) PoisonBottle();
                else                     PoisonAmplification();
                break;
            case 2:
                Defend();
                break;
        }
    }

    public void PoisonBottle() {
        int damage = 5 + getFloor();
        getPlayer().setPoisoned(true);
        getPlayer().setPoisonDamage(getPlayer().getPoisonDamage() + damage);
        System.out.println("=======================================");
        System.out.println("Poisoner throws poison bottle!");
        System.out.println("You got " + damage + " poison.");
    }

    public void PoisonAmplification() {
        getPlayer().setPoisonDamage(getPlayer().getPoisonDamage() * 2);
        System.out.println("=======================================");
        System.out.println("Poisoner amplifies your poison!");
        System.out.println("Your poison is amplified by x2.");
        System.out.println("=======================================");
    }

    public void Defend() {
        int armor = 5 + getFloor();
        setArmor(getArmor() + armor);
        System.out.println("=======================================");
        System.out.println("Poisoner takes guard action!");
        System.out.println("Poisoner got " + armor + " armor.");
        System.out.println("=======================================");
    }
}

class Giant extends Boss {

    public Giant(Player player, int floor) {
        setName("Giant");
        setAsciiArt(0, " w0Z    f}|n                         ");
        setAsciiArt(1, " <m0L  QmZkj                         ");
        setAsciiArt(2, "  JzQLQxUmqpd                        ");
        setAsciiArt(3, " ^LwZmwdZmmZOCY                      ");
        setAsciiArt(4, "YWa0mqmqqm0QLCCJ                     ");
        setAsciiArt(5, "kkbOZZmmQL00O0LCU                    ");
        setAsciiArt(6, " co0zZZwppZZZOZqpU                   ");
        setAsciiArt(7, " |#JZZZZmwwwZZZZd~                   ");
        setAsciiArt(8, "  oOOmYn);ttrbmZZc                   ");
        setAsciiArt(9, "  zZw0<mwwww}rUQCC          I i+^    ");
        setAsciiArt(10, "  ;bw)        +adj\\)f{>\"-c{\\tC|uf{>[ ");
        setAsciiArt(11, "   ;mw`        Qd,     |{1}_}-{+\\<xt1");
        setAsciiArt(12, " >Cv[{}       U0Ox          <+I i+^  ");
        this.setPlayer(player);
        this.setFloor(floor);
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
        int damage = 10 + 2 * getFloor() + getStrength();
        System.out.println("=======================================");
        System.out.println("Giant swings his big bat!");
        getPlayer().TakeDamage(damage);
    }

    public void OverflowingStrength() {
        setStrength(getStrength() + 3);
        System.out.println("=======================================");
        System.out.println("Giant is shouting!");
        System.out.println("Giant got 3 strength.");
        System.out.println("=======================================");
    }

    public void DoubleSwing() {
        int damage = 5 + getFloor() + getStrength();
        System.out.println("=======================================");
        System.out.println("Giant swings his bat twice!");
        getPlayer().TakeDamage(damage);
        getPlayer().TakeDamage(damage);
    }
}

class Slime extends Boss {

    public Slime(Player player, int floor) {
        setName("Slime");
        setAsciiArt(0, "             ..::..                  ");
        setAsciiArt(1, "         .:========:::.              ");
        setAsciiArt(2, "        ::.:======::::=.             ");
        setAsciiArt(3, "      :==========:=======.           ");
        setAsciiArt(4, "     .====================:          ");
        setAsciiArt(5, "   .:=======================:        ");
        setAsciiArt(6, "  .:::======:==:::::========..       ");
        setAsciiArt(7, ".::..===========:::::===::::====:    ");
        setAsciiArt(8, "==:::=======:====::==:::::========+  ");
        setAsciiArt(9, "===:=======:===:::====:::===:==++++:.");
        setAsciiArt(10, " ==========:.:=:::========:::.:::++=.");
        setAsciiArt(11, "  .=++=======++++=:..::========:=++: ");
        setAsciiArt(12, "      ..::::::..    .=:====++===:.   ");
        this.setPlayer(player);
        this.setFloor(floor);
        setMaxHp(80 + 5 * floor);
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
        int damage = 7 + getFloor() + getStrength();
        System.out.println("=======================================");
        System.out.println("Slime is rushing to you!");
        getPlayer().TakeDamage(damage);
    }

    public void Heal() {
        int heal = 10 + getFloor();
        if(getHp() + heal > getMaxHp())
            setHp(getMaxHp());
        else
            setHp(getHp() + heal);
        System.out.println("=======================================");
        System.out.println("Slime heals itself!");
        System.out.println("Slime healed " + heal + " HP.");
        System.out.println("=======================================");
    }

    public void Defend() {
        int armor = 5 + getFloor();
        setArmor(getArmor() + armor);
        System.out.println("=======================================");
        System.out.println("Slime takes guard action!");
        System.out.println("Slime got " + armor + " armor.");
        System.out.println("=======================================");
    }
}