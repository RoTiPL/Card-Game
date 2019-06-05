package com.skkujava;

public class Warrior extends Player {

    public Warrior() {
        this.name = "Warrior";
        asciiArt[0]  = "                ..:=:       ";
        asciiArt[1]  = "               .=+++++:     ";
        asciiArt[2]  = "               :=+++++*.    ";
        asciiArt[3]  = "               =+=*+==      ";
        asciiArt[4]  = "            =+**@%@#@%+==:  ";
        asciiArt[5]  = "        .  ++***@@%%%%*+++*.";
        asciiArt[6]  = "+++++++=+#+#*#*####****###* ";
        asciiArt[7]  = "         . :%%%@%%%%%@@##%@ ";
        asciiArt[8]  = "              #%%@@@@@=.%## ";
        asciiArt[9]  = "            .#%%%%%%%%%  :  ";
        asciiArt[10] = "           :%%%#%#####%#    ";
        asciiArt[11] = "          :%%####%%%%###%=  ";
        asciiArt[12] = "         *%###%%%@@%%%###%+ ";
        setMaxHp(80);
        setHp(getMaxHp());
        setDrawCount(5);
        // 카드 풀 처리
    }

    @Override
    public void completeFloor() {
        setHp(getHp() + 7);
        if(getHp() > getMaxHp())
            setHp(getMaxHp());
    }

    @Override
    public String getAsciiArt(int i) {
        return asciiArt[i];
    }
}
