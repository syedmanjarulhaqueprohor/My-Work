package com.example.spl_file;

public class Player {
    private String name;
    private int runs;
    private int balls;
    private int fours;
    private int sixes;
    private boolean isOut;
    private String howOut;
    private double strikeRate;
    public Player(String name){
        this.name=name;
    }

    public void addRuns(int run,boolean isLegalBall){
        this.runs+=run;
       if(isLegalBall){
           this.balls++;
       }
        if(run==4){
            fours++;
        }
        if(run==6){
            sixes++;
        }
    }

    public void dotBall(){
        balls++;
    }

    public void wicket(String type){
        isOut=true;
        howOut=type;
    }

    public double strikeRate(){
        if(balls==0)
            return 0;
        return (runs*100.0)/balls;
    }
    public void undoRuns(int r) {
        this.runs -= r;
        this.balls--;
        if (r == 4) this.fours--;
        if (r == 6) this.sixes--;
    }
    public String getName(){
        return name;
    }

    public int getRuns(){
        return runs;
    }

    public int getFours(){
        return fours;
    }

    public int getSixes() {
        return sixes;
    }

    public int getBalls() {
        return balls;
    }

    public boolean isOut() {
        return isOut;
    }

    public String getHowOut() {
        return howOut;
    }

    public double getStrikeRate() {
        return strikeRate;
    }

    public void setStrikeRate(double strikeRate) {
        this.strikeRate=strikeRate;
    }

    public void setSixes(int sixes) {
        this.sixes=sixes;
    }

    public void setFours(int fours) {
        this.fours=fours;
    }

    public void setBalls(int balls) {
        this.balls=balls;
    }

    public void setRuns(int runs) {
        this.runs=runs;
    }
}
