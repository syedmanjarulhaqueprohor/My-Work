package com.example.spl_file;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BallFile {
    public static final String fileName="Ball.txt";
    public static void fileExist(){
        try{
            File file=new File(fileName);
            if(!file.exists())
                file.createNewFile();
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void saveBall(int matchId, Ball ball){
        fileExist();
        try{
            BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(fileName,true));
            String type="Normal";
            if(ball.isWide()){
                type="Wide";
            } else if (ball.isNoBall()) {
                type="No_Ball";
            } else if (ball.isWicket()) {
                type="Wicket";
            }
            String data=matchId + "," + ball.getOverNumber() + "," + ball.getBallNumber() + "," + ball.getRuns() + "," +
                    type + "," + ball.getBatsmanName() + "," + ball.getBowlerName();
            bufferedWriter.write(data);
            bufferedWriter.newLine();
            bufferedWriter.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public static List<String[]> getBallByMatch(int matchId){
        fileExist();
        List<String[]> balls=new ArrayList<>();
        try{
            BufferedReader bufferedReader=new BufferedReader(new FileReader(fileName));
            String line;
            while((line=bufferedReader.readLine())!=null){
                if(line.trim().isEmpty()){
                    continue;
                }
                String[] data=line.split(",");
                if(Integer.parseInt(data[0])==matchId){
                    balls.add(data);
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return balls;
    }
    public static String[] getLastBall(int matchId){
        List<String[]>balls= getBallByMatch(matchId);
        if(balls.isEmpty()){
            return null;
        }
        return balls.get(balls.size()-1);
    }
    public static void removeLastBall(int matchId) {
        fileExist();
        List<String[]> allBalls = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    allBalls.add(line.split(","));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        boolean removed = false;
        for (int i = allBalls.size() - 1; i >= 0; i--) {
            if (Integer.parseInt(allBalls.get(i)[0]) == matchId) {
                allBalls.remove(i);
                removed = true;
                break;
            }
        }
        if (removed) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
                for (String[] ballData : allBalls) {
                    bw.write(String.join(",", ballData));
                    bw.newLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
