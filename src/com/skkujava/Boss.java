package com.skkujava;
import java.util.Random;

abstract class Boss extends HumanObject{

    Player player;
    int floor;
    String name;

    public static Boss CreateBoss(Player player, int floor) {

        Random random = new Random();
        Boss boss = null;
        // 3으로 고치기
        int num = random.nextInt(1);
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
        asciiArt[0]  = "        l:         \",!{    :+       ";
        asciiArt[1]  = "        `>ii_>>:!1]})i   [)         ";
        asciiArt[2]  = "           >>iI(}ijLJc1+}~.         ";
        asciiArt[3]  = "           ,!Ilw{YOYvczzLmuf        ";
        asciiArt[4]  = "           :!IX,XCvnnvvcLQLJ0- ..   ";
        asciiArt[5]  = "          _j;/i)LcvvzzcJO0Q00dC!    ";
        asciiArt[6]  = "         ;xf:l]QXczXzzUOOQ0JUZZOZi  ";
        asciiArt[7]  = "        |vJUUcuuunvzXYYJ0OZLZ/iZ+ {{";
        asciiArt[8]  = "       ~C~~`?JzxrncXYUXzJO-fI^~^    ";
        asciiArt[9]  = "     Iz/      (znnvcXJUUXf^     .   ";
        asciiArt[10] = "    jU,         ?uccJ?~\\f|.    ..   ";
        asciiArt[11] = " ^j0Y`            !Ii<  {\\{;__i-    ";
        asciiArt[12] = "  \"+nuxf/\\1{}                       ";
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

    public String getAsciiArt(int i) {
        return asciiArt[i];
    }
}

class Giant extends Boss {

    public Giant(Player player, int floor) {
        this.name = "Giant";
        asciiArt[0]  = " w0Z    f}|n                         ";
        asciiArt[1]  = " <m0L  QmZkj                         ";
        asciiArt[2]  = "  JzQLQxUmqpd                        ";
        asciiArt[3]  = " ^LwZmwdZmmZOCY                      ";
        asciiArt[4]  = "YWa0mqmqqm0QLCCJ                     ";
        asciiArt[5]  = "kkbOZZmmQL00O0LCU                    ";
        asciiArt[6]  = " co0zZZwppZZZOZqpU                   ";
        asciiArt[7]  = " |#JZZZZmwwwZZZZd~                   ";
        asciiArt[8]  = "  oOOmYn);ttrbmZZc                   ";
        asciiArt[9]  = "  zZw0<mwwww}rUQCC          I i+^    ";
        asciiArt[10] = "  ;bw)        +adj\\)f{>\"-c{\\tC|uf{>[ ";
        asciiArt[11] = "   ;mw`        Qd,     |{1}_}-{+\\<xt1";
        asciiArt[12] = " >Cv[{}       U0Ox          <+I i+^  ";
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

    public String getAsciiArt(int i) {
        return asciiArt[i];
    }
}

class Slime extends Boss {

    public Slime(Player player, int floor) {
        this.name = "Slime";
        asciiArt[0]  = "             ..::..                  ";
        asciiArt[1]  = "         .:========:::.              ";
        asciiArt[2]  = "        ::.:======::::=.             ";
        asciiArt[3]  = "      :==========:=======.           ";
        asciiArt[4]  = "     .====================:          ";
        asciiArt[5]  = "   .:=======================:        ";
        asciiArt[6]  = "  .:::======:==:::::========..       ";
        asciiArt[7]  = ".::..===========:::::===::::====:    ";
        asciiArt[8]  = "==:::=======:====::==:::::========+  ";
        asciiArt[9]  = "===:=======:===:::====:::===:==++++:.";
        asciiArt[10] = " ==========:.:=:::========:::.:::++=.";
        asciiArt[11] = "  .=++=======++++=:..::========:=++: ";
        asciiArt[12] = "      ..::::::..    .=:====++===:.   ";
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

    public String getAsciiArt(int i) {
        return asciiArt[i];
    }
}