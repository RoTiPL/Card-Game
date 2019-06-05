package com.skkujava;

public class RankInfo implements Comparable<RankInfo> {
    private int floor;
    private String name;
    private String job;

    public RankInfo(int floor, String name, String job) {
        this.floor = floor;
        this.name = name;
        this.job = job;
    }

    public int getFloor() { return floor; }
    public String getName() { return name; }
    public String getJob() { return job; }

    @Override
    public int compareTo(RankInfo r) {
        if(this.floor > r.getFloor()) return -1;
        else if(this.floor < r.getFloor()) return 1;
        else return 0;
    }
}
