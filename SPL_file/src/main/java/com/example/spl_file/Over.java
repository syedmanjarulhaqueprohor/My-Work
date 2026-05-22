package com.example.spl_file;

import java.util.ArrayList;
import java.util.List;

public class Over {
    private int overNumber;
    private List<Ball> balls;
    private int legalBalls;
    private int runsInOver;
    private int wicketsInOver;

    public Over(int overNumber){
        this.overNumber=overNumber;
        balls=new ArrayList<>();
    }
    public void addBall(Ball ball){
        balls.add(ball);
        runsInOver+=ball.getRuns();
        if(ball.isWicket()){
            wicketsInOver++;
        }
        if(ball.isLegal()){
            legalBalls++;
        }
    }
    public String getCurrentBall(){
        return overNumber+"."+legalBalls;
    }
    public String summary(){
        StringBuilder stringBuilder=new StringBuilder();
        for(Ball ball:balls){
            if(ball.isWide()){
                stringBuilder.append("Wd ");
            }
            else if(ball.isNoBall()){
                stringBuilder.append("NB ");
            }
           else if(ball.isWicket()){
                stringBuilder.append("W ");
            }
           else {
               stringBuilder.append(ball.getRuns()).append(" ");
            }
        }
        return stringBuilder.toString();
    }
    public void removeLastBall() {
        if (!balls.isEmpty()) {
            balls.remove(balls.size() - 1);
        }
    }
    public int getOverNumber() {
        return overNumber;
    }

    public List<Ball> getBalls() {
        return balls;
    }

    public int getLegalBalls() {
        int legalCount = 0;
        for (Ball ball : balls) {
            if (!ball.isWide() && !ball.isNoBall()) {
                legalCount++;
            }
        }
        return legalCount;
    }
    public boolean isOverComplete(){
        return legalBalls>=6;
    }
    public int getRunsInOver() {
        return runsInOver;
    }

    public int getWicketsInOver() {
        return wicketsInOver;
    }
}
