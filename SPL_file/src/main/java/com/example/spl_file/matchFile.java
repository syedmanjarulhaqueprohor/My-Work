package com.example.spl_file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class matchFile {
    private static final String fileName = "match.txt";
    private static void FileExists() {
        try {
            File file = new File(fileName);
            File parent = file.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
  public static int getMatchId(){
        FileExists();
        int lastId=0;
      try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
          String line;
          while ((line = br.readLine()) != null) {
              if (!line.trim().isEmpty()) {
                  String[] data = line.split(",");
                  lastId = Integer.parseInt(data[0]); // first column = ID
              }
          }
      }
      catch (Exception e){
          e.printStackTrace();
      }
      return lastId+1;
  }
    public static int addMatch(String teamAName, String teamBName, String matchType, int customOver) {
        FileExists();
        int matchId=getMatchId();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            String data =matchId + "," + teamAName + "," + teamBName + "," + matchType + "," + customOver;
            bw.write(data);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return matchId;
    }
    public static List<String[]> getMatch() {
        FileExists();
        List<String[]> match = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] data = line.split(",");
                if (data.length >= 4) {
                    match.add(data);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return match;
    }
}
