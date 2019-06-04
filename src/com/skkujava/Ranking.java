package com.skkujava;

import java.io.*;

public class Ranking {
    /* TODO
     * 1. 층수로 내림차순 정렬
     * 2. 현재 "닉네임 층수"로 입력되는데 "닉네임 직업 층수"로 고치기
     * 3. 2인용?
     */
    private static String filePath = "rank.txt";

    public static void showRanking() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            while(true) {
                String line = br.readLine();
                if (line == null) break;
                System.out.println(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
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
