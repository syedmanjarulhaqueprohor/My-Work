package com.example.spl_file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BowlerFile {
    private static final String fileName="Bowlers.txt";
    private static void fileExist(){
        try{
            File file=new File(fileName);
            if(!file.exists()){
                file.createNewFile();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void saveBowler(int matchId,String name){
        fileExist();
        try{
            BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(fileName,true));
            String data=matchId+","+name;
            bufferedWriter.write(data);
            bufferedWriter.newLine();
            bufferedWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static List<String[]> getBowlerByMatch(int matchId){
        fileExist();
        List<String[]> bowlers=new ArrayList<>();
        try{
            BufferedReader bufferedReader=new BufferedReader(new FileReader(fileName));
            String line;
            while ((line=bufferedReader.readLine())!=null){
                String[] data=line.split(",");
                if(Integer.parseInt(data[0])==matchId){
                    bowlers.add(data);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return bowlers;
    }
}
