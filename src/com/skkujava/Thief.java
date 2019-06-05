package com.skkujava;

public class Thief extends Player {

    public Thief() {
        this.name = "Thief";
        setMaxHp(70);
        setHp(getMaxHp());
        setDrawCount(7);
        // 카드 풀 처리
    }
}
