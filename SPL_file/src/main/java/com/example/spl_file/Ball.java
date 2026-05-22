package com.example.spl_file;

public class Ball {
    private int runs;
    private boolean isWide;
    private boolean isNoBall;
    private boolean isWicket;
    private boolean isLegal;
    private int overNumber;
    private int ballNumber;
    private String batsmanName;
    private String bowlerName;
    public Ball(int runs,boolean isWide,boolean isNoBall,boolean isWicket,boolean isLegal,
    int overNumber,int ballNumber,String batsmanName,String bowlerName){
        this.runs = runs;
        this.isWide = isWide;
        this.isNoBall = isNoBall;
        this.isWicket = isWicket;
        this.overNumber = overNumber;
        this.ballNumber = ballNumber;
        this.batsmanName = batsmanName;
        this.bowlerName = bowlerName;
        this.isLegal=!isWide && !isNoBall;
    }
    public int getRuns() {
        return runs;
    }

    public boolean isWide() {
        return isWide;
    }

    public boolean isNoBall() {
        return isNoBall;
    }

    public boolean isWicket() {
        return isWicket;
    }

    public boolean isLegal() {
        return isLegal;
    }

    public int getOverNumber() {
        return overNumber;
    }

    public int getBallNumber() {
        return ballNumber;
    }

    public String getBatsmanName() {
        return batsmanName;
    }

    public String getBowlerName() {
        return bowlerName;
    }
}
