package com.skkujava;

public class Thief extends Player {

    public Thief() {
        this.name = "Thief";
        asciiArt[0]  = "          =:===:.           ";
        asciiArt[1]  = "             .:+#+:         ";
        asciiArt[2]  = "       :=++==:. .+==:.      ";
        asciiArt[3]  = " :===+::.  .=++:::::=::.    ";
        asciiArt[4]  = "             =#++:::.=.::   ";
        asciiArt[5]  = "            +++*===#%=.::   ";
        asciiArt[6]  = "          =++:**##*++===::  ";
        asciiArt[7]  = "       =*+++*##++*###*:=:=. ";
        asciiArt[8]  = "      :+*********#%##%+ :+# ";
        asciiArt[9]  = ".     :+==+++***###*##*+    ";
        asciiArt[10] = ":=.   :++++++*+*#******#+   ";
        asciiArt[11] = "  =*  ++++++**+****++++**   ";
        asciiArt[12] = "   .=*=++++********+++++**  ";
        setMaxHp(70);
        setHp(getMaxHp());
        setDrawCount(7);
        // 카드 풀 처리
    }

    @Override
    public String getAsciiArt(int i) {
        return asciiArt[i];
    }
}
