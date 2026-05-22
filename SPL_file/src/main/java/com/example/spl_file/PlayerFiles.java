package com.example.spl_file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerFiles {
    private static final String fileName="players.txt";
    private static void fileExist(){
        try{
            File file=new File(fileName);
            if(!file.exists()){
                file.createNewFile();
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void savePlayer(int matchId,String teamName,String playerName){
         fileExist();
        try{
            BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(fileName,true));
            String data=matchId+","+teamName+","+playerName;
            bufferedWriter.write(data);
            bufferedWriter.newLine();
            bufferedWriter.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static List<String[]> getPlayersByMatch(int matchId){
        fileExist();
        List<String[]> players=new ArrayList<>();
        try{
            BufferedReader bufferedReader=new BufferedReader(new FileReader(fileName));
            String line;
            while ((line=bufferedReader.readLine())!=null){
                String[] data=line.split(",");
                if(Integer.parseInt(data[0])==matchId){
                    players.add(data);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return players;
    }
}
