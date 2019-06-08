package com.skkujava;

public class Warrior extends Player {

    public Warrior() {
        setName("Warrior");
        setAsciiArt(0, "                ..:=:       ");
        setAsciiArt(1, "               .=+++++:     ");
        setAsciiArt(2, "               :=+++++*.    ");
        setAsciiArt(3, "               =+=*+==      ");
        setAsciiArt(4, "            =+**@%@#@%+==:  ");
        setAsciiArt(5, "        .  ++***@@%%%%*+++*.");
        setAsciiArt(6, "+++++++=+#+#*#*####****###* ");
        setAsciiArt(7, "         . :%%%@%%%%%@@##%@ ");
        setAsciiArt(8, "              #%%@@@@@=.%## ");
        setAsciiArt(9, "            .#%%%%%%%%%  :  ");
        setAsciiArt(10, "           :%%%#%#####%#    ");
        setAsciiArt(11, "          :%%####%%%%###%=  ");
        setAsciiArt(12, "         *%###%%%@@%%%###%+ ");
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
}
