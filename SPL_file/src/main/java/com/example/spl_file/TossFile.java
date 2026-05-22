package com.example.spl_file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TossFile {
    private static final String fileName = "Name.txt";
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
    public static void addPlayers(int matchId,String teamName,String playerName){
        FileExists();
        try{
        BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(fileName,true));
        String data = matchId + "," + teamName + "," + playerName;
        bufferedWriter.write(data);
        bufferedWriter.newLine();
        bufferedWriter.close();
    } catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void removePlayer(int matchId,String teamName,String playerName){
        FileExists();
        List<String[]> players=getAllPlayer();
        try{
            BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(fileName));
            for(String [] p:players){
                if(!p[0].equals(String.valueOf(matchId))&&
                p[1].equals(teamName)&&
                p[2].equals(playerName)){
                    bufferedWriter.write(String.join(",",p));
                    bufferedWriter.newLine();
                    bufferedWriter.close();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public static List<String[]> getAllPlayer(){
        List<String[]> list=new ArrayList<>();
        try{
            BufferedReader bufferedReader=new BufferedReader(new FileReader(fileName));
            String line;
            while ((line=bufferedReader.readLine())!=null){
                list.add(line.split(","));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
    public static List<String []> getPlayerByMatchId(int matchId){
        List<String []>list=new ArrayList<>();
        try{
            BufferedReader bufferedReader=new BufferedReader(new FileReader(fileName));
            String line;
            while ((line=bufferedReader.readLine())!=null){
              String[] data=line.split(",");
              if(data.length >= 3 && data[0].equals(String.valueOf(matchId))){
                  list.add(data);
              }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
