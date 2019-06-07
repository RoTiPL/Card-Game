package com.skkujava;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class Ranking {
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
        // 가로 49(51)
        System.out.printf("%19s%s\n", "", "┌─────────────────────────────────────────────────┐");
        System.out.printf("%19s%c%21s%7s%21s%c\n", "", '│', "", "RANKING", "", '│');
        System.out.printf("%19s%c%3s%5s%4s%5s%7s%8s%17s%c\n", "", '│', "", "floor", "", "class", "", "nickname", "", '│');
        for(RankInfo r : rankinfo) {
            System.out.printf("%19s%c%4s%2d%6s%-12s%-25s%c\n", "", '│', "", r.getFloor(), "", r.getJob(), r.getName(), '│');
        }
        System.out.printf("%19s%s\n", "", "└─────────────────────────────────────────────────┘");
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
