package com.skkujava;

public class Warrior extends Player {

    public Warrior() {
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
