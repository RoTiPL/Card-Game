package com.skkujava;

public class Thief extends Player {

    public Thief() {
        setName("Thief");
        setAsciiArt(0, "          =:===:.           ");
        setAsciiArt(1, "             .:+#+:         ");
        setAsciiArt(2, "       :=++==:. .+==:.      ");
        setAsciiArt(3, " :===+::.  .=++:::::=::.    ");
        setAsciiArt(4, "             =#++:::.=.::   ");
        setAsciiArt(5, "            +++*===#%=.::   ");
        setAsciiArt(6, "          =++:**##*++===::  ");
        setAsciiArt(7, "       =*+++*##++*###*:=:=. ");
        setAsciiArt(8, "      :+*********#%##%+ :+# ");
        setAsciiArt(9, ".     :+==+++***###*##*+    ");
        setAsciiArt(10, ":=.   :++++++*+*#******#+   ");
        setAsciiArt(11, "  =*  ++++++**+****++++**   ");
        setAsciiArt(12, "   .=*=++++********+++++**  ");
        setMaxHp(70);
        setHp(getMaxHp());
        setDrawCount(7);
        // 카드 풀 처리
    }
}
