package com.example.spl_file;

import java.io.*;
import java.util.*;

public class LiveStatsFile {
    private static final String FILE_NAME = "LiveStats.txt";

    // Player stats update korar method
    public static void updatePlayerStats(int matchId, int inningsNo, String team, Player p) {
        List<String> updatedLines = new ArrayList<>();
        boolean isFound = false;

        // data format: matchId,inningsNo,team,name,runs,balls,4s,6s
        String newRecord = String.format("%d,%d,%s,%s,%d,%d,%d,%d",
                matchId, inningsNo, team, p.getName(),
                p.getRuns(), p.getBalls(), p.getFours(), p.getSixes());

        File file = new File(FILE_NAME);
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(",");
                    // MatchID, InningsNo ebong Player Name mile gele sheti update hobe
                    if (data[0].equals(String.valueOf(matchId)) &&
                            data[1].equals(String.valueOf(inningsNo)) &&
                            data[3].equalsIgnoreCase(p.getName())) {
                        updatedLines.add(newRecord);
                        isFound = true;
                    } else {
                        updatedLines.add(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (!isFound) {
            updatedLines.add(newRecord);
        }

        // File-e rewrite kora
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (String row : updatedLines) {
                pw.println(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Bowler stats update korar method
    public static void updateBowlerStats(int matchId, int inningsNo, String team, Bowler b) {
        List<String> updatedLines = new ArrayList<>();
        boolean isFound = false;

        // format: matchId,inningsNo,team,name,overs,runsGiven,wickets
        String newRecord = String.format("%d,%d,%s,%s,%s,%d,%d",
                matchId, inningsNo, team, b.getName(),
                b.getOvers(), b.getRunsGiven(), b.getWickets());

        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                // Bowler er jonno check (type bowling chinho rakhte paren jodi chan)
                if (data[0].equals(String.valueOf(matchId)) &&
                        data[1].equals(String.valueOf(inningsNo)) &&
                        data[3].equalsIgnoreCase(b.getName())) {
                    updatedLines.add(newRecord);
                    isFound = true;
                } else {
                    updatedLines.add(line);
                }
            }
        } catch (IOException e) {}

        if (!isFound) updatedLines.add(newRecord);

        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (String row : updatedLines) pw.println(row);
        } catch (IOException e) {}
    }
}