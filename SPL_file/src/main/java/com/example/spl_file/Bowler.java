package com.example.spl_file;

public class Bowler {
    private String name;
    private int balls;
    private int runsGiven;
    private int wickets;
    private int wideBalls;
    private int noBalls;
    private double economyRate;
    private double overs;

    public Bowler(String name){
        this.name=name;
    }
    public void addBall(int runs){
        balls++;
        runsGiven+=runs;
    }
    public void addWicket(){
        wickets++;
        balls++;
    }
    public void addNoBall(int runs){
        noBalls++;
        runsGiven+=runs;
    }
    public void addWide(int runs){
        wideBalls++;
        runsGiven+=runs;
    }
    public String getOvers(){
        return (balls/6)+"."+(balls%6);
    }
    public double getEconomy(){
        if(balls==0)
            return 0;
        return (runsGiven*6.0)/balls;
    }
    public void undoBall(int r, boolean wasWicket) {
        this.runsGiven -= r;
        this.balls--;
        if (wasWicket) this.wickets--;
    }
    public void undoExtra(int r) {
        this.runsGiven -= r;
    }
    public String getName() {
        return name;
    }
    public int getBalls() {
        return balls;
    }
    public int getRunsGiven() {
        return runsGiven;
    }
    public int getWickets() {
        return wickets;
    }
    public int getWideBalls() {
        return wideBalls;
    }
    public int getNoBalls() {
        return noBalls;
    }

    public double getEconomyRate() {
        return economyRate;
    }

    public void setOvers(double overs) {
        this.overs=overs;
    }

    public void setRunsGiven(int runsGiven) {
        this.runsGiven=runsGiven;
    }

    public void setWickets(int wickets) {
        this.wickets=wickets;
    }

    public void setEconomyRate(double economyRate) {
        this.economyRate=economyRate;
    }
}
