package com.skkujava;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Ranking {
    /* TODO
     * 1. 층수로 내림차순 정렬
     */
    private static String filePath = "rank.txt";
    private static ArrayList<RankInfo> rankinfo = new ArrayList<>();

    public static void loadRanking() {
        try {
            rankinfo.clear();
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            while(true) {
                String line = br.readLine();
                if (line == null) break;
                String[] info = line.split(" ");
                rankinfo.add(new RankInfo(Integer.parseInt(info[0]), info[1], info[2]));
            }
            Collections.sort(rankinfo);
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showRanking() {
        if(rankinfo.isEmpty()) {
            System.out.println("There is no ranking information. Please play the game first!");
            return;
        }
        System.out.println(rankinfo.size());
        System.out.println("========== RANKING ==========");
        System.out.printf("%-10s%-15s%s\n", "floor", "nickname", "class");
        for(RankInfo r : rankinfo) {
            System.out.printf("%-10d%-15s%s\n", r.getFloor(), r.getName(), r.getJob());
        }
    }

    public static void uploadRanking(String name, int floor, String job) {
        try {
            FileWriter fw = new FileWriter(filePath, true);
            String str = floor + " " + name + " " + job + "\r\n";
            fw.write(str);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
