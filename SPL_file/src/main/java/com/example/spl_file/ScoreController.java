package com.example.spl_file;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class ScoreController {

    @FXML
    private Label b1Balls;

    @FXML
    private Label b1Fours;

    @FXML
    private Label b1Runs;

    @FXML
    private Label b1SR;

    @FXML
    private Label b1Sixes;

    @FXML
    private Label batsman1Name;

    @FXML
    private Label b2Balls;

    @FXML
    private Label b2Fours;

    @FXML
    private Label b2Runs;

    @FXML
    private Label b2SR;

    @FXML
    private Label b2Sixes;

    @FXML
    private Label batsman2Name;

    @FXML
    private Label ball1,ball2,ball3,ball4,ball5,ball6;
    private List<Label> overLabels;

    @FXML
    private Label inningsLabel;

    @FXML
    private TextField partnershipLabel;

    @FXML
    private Label bowlerER;

    @FXML
    private Label bowlerName;

    @FXML
    private Label bowlerOver;

    @FXML
    private Label bowlerRuns;

    @FXML
    private Label bowlerWicket;

    @FXML
    private Label runRateLabel;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label teamALable;

    @FXML
    private Label teamBLable;

    @FXML private TextField extraRunField;

    @FXML
    private TextField newBowlerName;

    @FXML
    private Button newBowlerBtn;

    @FXML
    private Button newBatsmanBtn;

    @FXML
    private TextField newBatsmanField;

    @FXML
    private Button partnershipBtn;

    @FXML
    private AnchorPane entryPane;

    @FXML
    private AnchorPane mainScore1;

    @FXML
    private AnchorPane mainScore2;

    @FXML private TextField strikerInput, nonStrikerInput, bowlerInput;

    private int totalRuns=0;
    private int wickets=0;
    private int currentOver=0;
    private int legalBalls=0;
    private int partnershipRun = 0;
    private int currentOverNum = 0;
    private int inningsCounter = 1;
    private int maxInnings = 2;
    private Player striker;
    private Player nonStriker;
    private Bowler currentBowler;
    private Over currentOverObj;
    private List<Player> retiredPlayers=new ArrayList<>();
    private int totalExtraRuns = 0;
    private String selectedExtraType = "Normal";
    private List<Player> innings1Batsmen = new ArrayList<>();
    private List<Bowler> innings1Bowlers = new ArrayList<>();
    private List<Player> innings2Batsmen = new ArrayList<>();
    private List<Bowler> innings2Bowlers = new ArrayList<>();
   // private Label teamAname;
   // private Label teamBname;

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Toss Result");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private void showAlertError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private int matchId;
    private String teamAName;
    private String teamBName;
    private String currentBattingTeam;
    private String currentBowlingTeam;
    private PauseTransition timer;

    @FXML
    public void handleEntry() {
        String sName = strikerInput.getText().trim();
        String nsName = nonStrikerInput.getText().trim();
        String bName = bowlerInput.getText().trim();
        if (sName.isEmpty() || nsName.isEmpty() || bName.isEmpty()) {
            showAlert("Sab field fill-up korun!");
            return;
        }
        if (sName.equalsIgnoreCase(nsName)) {
            showAlert("Striker and Non-Striker cannot be the same person!");
            return;
        }
        if (isValidPlayerFromToss(sName) && isValidPlayerFromToss(nsName)) {
            striker = new Player(sName);
            nonStriker = new Player(nsName);
            currentBowler = new Bowler(bName);
            if (inningsCounter == 1) {
                currentOverNum = 0;
                innings1Batsmen.add(striker);
                innings1Batsmen.add(nonStriker);
                innings1Bowlers.add(currentBowler);
            } else {
                currentOverNum = 0;
                innings2Batsmen.add(striker);
                innings2Batsmen.add(nonStriker);
                innings2Bowlers.add(currentBowler);
            }
            currentOverObj = new Over(currentOverNum);
            entryPane.setVisible(false);
            mainScore1.setVisible(true);
            mainScore2.setVisible(true);
            updateUI();
        } else {
            showAlert("Player name does not match");
        }
    }
    private boolean isValidPlayerFromToss(String name) {
        try{
            BufferedReader bufferedReader =new BufferedReader(new FileReader("Name.txt"));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",");
                if (parts.length >= 2) {
                    String playerNameFromFile = parts[1].trim();
                    if (playerNameFromFile.equalsIgnoreCase(name.trim())) {
                        return true;
                    }
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
    public void setTeamNames(String teamAName,String teamBName){
        this.teamAName=teamAName;
        this.teamBName=teamBName;
        teamALable.setText(teamAName);
        teamBLable.setText(teamBName);
    }
    public void setMatchId(int matchId) {
        this.matchId=matchId;
    }
    public void addRuns(int runs) {
        hidePartnership();
        hideExtra();
        Ball ball;
        if (selectedExtraType.equals("Normal")) {
            striker.addRuns(runs,true);
            totalRuns += runs;
            partnershipRun += runs;
            currentBowler.addBall(runs);
            ball = new Ball(runs, false, false, false, true,
                    currentOverNum, currentOverObj.getLegalBalls() + 1,
                    striker.getName(), currentBowler.getName());
            if (runs % 2 != 0) rotateStrike();
            updateOverUI(String.valueOf(runs));
        } else {
            int extra = 0;
            boolean isWide = selectedExtraType.equals("Wide");
            boolean isNoBall = selectedExtraType.equals("No Ball");

            if (isWide) {
                extra = runs + 1;
                totalRuns += extra;
                totalExtraRuns += extra;
                currentBowler.addWide(extra);
                currentBowler.addBall(extra);
            } else if (isNoBall) {
                striker.dotBall();
                extra = runs + 1;
                totalRuns += extra;
                totalExtraRuns += extra;
                currentBowler.addWide(extra);
                currentBowler.addBall(extra);
            } else {
                extra = runs;
                totalRuns += extra;
                totalExtraRuns += extra;
            }
            ball = new Ball(runs, isWide, isNoBall, false, false,
                    currentOverNum, currentOverObj.getLegalBalls(),
                    striker.getName(), currentBowler.getName());

            selectedExtraType = "Normal";
            if (runs % 2 != 0) rotateStrike();
        }
        currentOverObj.addBall(ball);
        BallFile.saveBall(matchId, ball);
        LiveStatsFile.updatePlayerStats(matchId, inningsCounter, currentBattingTeam, striker);
        LiveStatsFile.updateBowlerStats(matchId, inningsCounter, currentBowlingTeam, currentBowler);
        overComplete();
        updateUI();
    }
    private List<String> currentOverBalls = new ArrayList<>();
    private void updateOverUI(String ballResult) {
        if (currentOverBalls.size() < 6) {
            currentOverBalls.add(ballResult);
        }
        for (int i = 0; i < currentOverBalls.size(); i++) {
            overLabels.get(i).setText(currentOverBalls.get(i));
        }
    }
    private void resetOverUI() {
        currentOverBalls.clear();
        for (Label label : overLabels) {
            label.setText("0");
        }
    }
    private double calculateCRR() {
        int totalLegalBalls = (currentOverNum * 6) + currentOverObj.getLegalBalls();
        if (totalLegalBalls == 0) return 0.00;
        return (double) totalRuns / totalLegalBalls * 6;
    }
    private void overComplete() {
        if (currentOverObj.getLegalBalls() >= 6) {
            rotateStrike();
            newBowlerName.setVisible(true);
            newBowlerBtn.setVisible(true);
            showAlert("Over Complete! Please enter the next bowler's name.");
            resetOverUI();
        }
    }
    @FXML
    public void newBowler() {
        String name = newBowlerName.getText().trim();
        if (name.isEmpty()) {
            showAlert("Please enter a Bowler Name!");
            return;
        }
        if (isValidPlayerFromToss(name)) {
            currentOverNum++;
            List<Bowler> currentInningsBowlers = (inningsCounter == 1) ? innings1Bowlers : innings2Bowlers;
            Bowler existingBowler = null;
            for (Bowler b : currentInningsBowlers) {
                if (b.getName().equalsIgnoreCase(name)) {
                    existingBowler = b;
                    break;
                }
            }
            if (existingBowler != null) {
                currentBowler = existingBowler;
                System.out.println("DEBUG: Old Bowler Loaded. Total Runs Given Before: " + currentBowler.getRunsGiven());
            } else {
                currentBowler = new Bowler(name);
                currentInningsBowlers.add(currentBowler);
                System.out.println("DEBUG: New Bowler added to Innings " + inningsCounter + " List.");
            }
            currentOverObj = new Over(currentOverNum);
            BowlerFile.saveBowler(matchId, name);
            newBowlerName.clear();
            newBowlerName.setVisible(false);
            newBowlerBtn.setVisible(false);
            updateUI();
        } else {
            showAlert("Please enter a valid Bowler Name");
        }
    }
    private void rotateStrike() {
        Player temp = striker;
        striker = nonStriker;
        nonStriker = temp;
        updateUI();
    }
    @FXML
    public void undoLastBall() {
        String[] lastBallData = BallFile.getLastBall(matchId);
        if (lastBallData == null) {
            showAlert("No more balls to undo!");
            return;
        }
        int lastRuns = Integer.parseInt(lastBallData[3]);
        String type = lastBallData[4];
        if (type.equals("Normal")) {
            totalRuns -= lastRuns;
            partnershipRun -= lastRuns;
            striker.undoRuns(lastRuns);
            currentBowler.undoBall(lastRuns, false);
            if (lastRuns % 2 != 0) rotateStrike();
        } else if (type.equals("Wide") || type.equals("NoBall")) {
            int penalty = lastRuns + 1;
            totalRuns -= penalty;
            totalExtraRuns -= penalty;
            currentBowler.undoExtra(penalty);
            if (lastRuns % 2 != 0) rotateStrike();
        }
        else if (type.equals("Wicket")) {
            showAlert("Wicket cannot be undone in this version.");
            return;
        }
        BallFile.removeLastBall(matchId);
        currentOverObj.removeLastBall();
        LiveStatsFile.updatePlayerStats(matchId, inningsCounter, currentBattingTeam, striker);
        LiveStatsFile.updateBowlerStats(matchId, inningsCounter, currentBowlingTeam, currentBowler);
        updateUI();
    }
    @FXML
    public void partnership(){
        partnershipLabel.setVisible(true);
        partnershipLabel.setText("Partnership:"+ partnershipRun);
        if (timer != null) {
            timer.stop();
        }
        timer = new PauseTransition(Duration.seconds(2));
        timer.setOnFinished(event -> partnershipLabel.setVisible(false));
        timer.play();
    }
    private void hidePartnership() {
        if (timer != null) {
            timer.stop();
        }
        partnershipLabel.setVisible(false);
    }
    @FXML
    public void setExtraWide() { selectedExtraType = "Wide"; }
    @FXML
    public void setExtraNoBall() { selectedExtraType = "NoBall"; }
    @FXML
    public void setExtraByes() { selectedExtraType = "Byes"; }
    @FXML
    public void showExtra(){
        extraRunField.setVisible(true);
        extraRunField.setText("Extra"+totalExtraRuns);
        if (timer != null) {
            timer.stop();
        }
        timer = new PauseTransition(Duration.seconds(2));
        timer.setOnFinished(event -> extraRunField.setVisible(false));
        timer.play();
    }
    private void hideExtra() {
        if (timer != null) {
            timer.stop();
        }
        extraRunField.setVisible(false);
    }
    @FXML
    public void handleRetire() {
        partnershipRun=0;
        retiredPlayers.add(striker);
        showAlert(striker.getName() + " retired hurt. He can return after 9 wickets.");
        showNewBatsmanUI(true);
    }
    @FXML
    public void handleWicket() {
        wickets++;
        currentBowler.addWicket();
        partnershipRun = 0;
        Ball wicketBall = new Ball(0, false, false, true, true,
                currentOverNum, currentOverObj.getLegalBalls() + 1,
                striker.getName(), currentBowler.getName());
        currentOverObj.addBall(wicketBall);
        BallFile.saveBall(matchId, wicketBall);
        showNewBatsmanUI(true);
        overComplete();
        updateOverUI("W");
        updateUI();
    }
    private void showNewBatsmanUI(boolean visible) {
        newBatsmanField.setVisible(visible);
        newBatsmanBtn.setVisible(visible);
    }
@FXML
public void enterNewBatsman(){
    String nextName = newBatsmanField.getText().trim();

    if (!nextName.isEmpty()) {
        striker = new Player(nextName);
        if (inningsCounter == 1) {
            innings1Batsmen.add(striker);
        } else {
            innings2Batsmen.add(striker);
        }
        newBatsmanField.clear();
        showNewBatsmanUI(false);
        LiveStatsFile.updatePlayerStats(matchId, inningsCounter, currentBattingTeam, striker);

        System.out.println("DEBUG: Batsman [" + nextName + "] added to activeBatsmenList.");
    } else {
        checkAndBringBackRetiredPlayer();
    }
    updateUI();
}
    private void checkAndBringBackRetiredPlayer() {
        if (wickets >= 9 && !retiredPlayers.isEmpty()) {
            striker = retiredPlayers.remove(0);
            showAlert(striker.getName() + " is back on strike!");
        } else if (wickets >= 10) {
            showAlert("All Out!");
            finishInnings();
        }
    }
    @FXML
    public void finishInnings() {
        if (inningsCounter < maxInnings) {
            showAlert(inningsCounter + getSuffix(inningsCounter) + " Innings Over!");
            inningsCounter++;
            inningsLabel.setText(inningsCounter + getSuffix(inningsCounter) + " Innings");
            totalRuns = 0; wickets = 0;
            currentOverNum = 0; partnershipRun = 0;
            currentOverObj = new Over(0);
            retiredPlayers.clear();
            mainScore1.setVisible(false);
            mainScore2.setVisible(false);
            entryPane.setVisible(true);
            strikerInput.clear();
            nonStrikerInput.clear();
            bowlerInput.clear();
            updateUI();
        } else {
            showAlert("Match Finished! Check ScoreCard.");
        }
    }
    private String getSuffix(int n) {
        if (n == 1) return "st";
        if (n == 2) return "nd";
        if (n == 3) return "rd";
        return "th";
    }
    public void updateUI() {
        int totalLegalBalls = (currentOverNum * 6) + currentOverObj.getLegalBalls();
        int displayOvers = totalLegalBalls / 6;
        int displayBalls = totalLegalBalls % 6;
        scoreLabel.setText(totalRuns + "-" + wickets + " (" + displayOvers + "." + displayBalls + ")");
        double crr = calculateCRR();
        runRateLabel.setText(String.format("%.2f", crr));
        batsman1Name.setText(striker.getName());
        b1Runs.setText(String.valueOf(striker.getRuns()));
        b1Balls.setText(String.valueOf(striker.getBalls()));
        b1Fours.setText(String.valueOf(striker.getFours()));
        b1Sixes.setText(String.valueOf(striker.getSixes()));
        b1SR.setText(String.format("%.2f", striker.strikeRate()));

        batsman2Name.setText(nonStriker.getName());
        b2Runs.setText(String.valueOf(nonStriker.getRuns()));
        b2Balls.setText(String.valueOf(nonStriker.getBalls()));
        b2Fours.setText(String.valueOf(nonStriker.getFours()));
        b2Sixes.setText(String.valueOf(nonStriker.getSixes()));
        b2SR.setText(String.format("%.2f", nonStriker.strikeRate()));

        bowlerName.setText(currentBowler.getName());
        bowlerOver.setText(currentBowler.getOvers());
        bowlerRuns.setText(String.valueOf(currentBowler.getRunsGiven()));
        bowlerWicket.setText(String.valueOf(currentBowler.getWickets()));
        bowlerER.setText(String.format("%.2f", currentBowler.getEconomy()));
    }
    @FXML public void run1(){
        addRuns(1);
    }
    @FXML public void run2(){
        addRuns(2);
    }
    @FXML public void run3(){
        addRuns(3);
    }
    @FXML public void run4(){
        addRuns(4);
    }
    @FXML public void run5(){
        addRuns(5);
    }
    @FXML public void run6(){
        addRuns(6);
    }
    @FXML public void run0() { addRuns(0); }
    public void saveMatchDataToFile(int matchId, String teamAName, String teamBName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("MatchHistory.txt", true))) {
            for (Player p : innings1Batsmen) {
                writer.write(matchId + "," + teamAName + ",BAT," + p.getName() + "," + p.getRuns() + "," + p.getBalls() + "," + p.getFours() + "," + p.getSixes() + "," + p.getStrikeRate());
                writer.newLine();
            }
            for (Bowler b : innings1Bowlers) {
                writer.write(matchId + "," + teamAName + ",BOWL," + b.getName() + "," + b.getOvers() + "," + b.getRunsGiven() + "," + b.getWickets() + "," + b.getEconomyRate());
                writer.newLine();
            }
            for (Player p : innings2Batsmen) {
                writer.write(matchId + "," + teamBName + ",BAT," + p.getName() + "," + p.getRuns() + "," + p.getBalls() + "," + p.getFours() + "," + p.getSixes() + "," + p.getStrikeRate());
                writer.newLine();
            }
            for (Bowler b : innings2Bowlers) {
                writer.write(matchId + "," + teamBName + ",BOWL," + b.getName() + "," + b.getOvers() + "," + b.getRunsGiven() + "," + b.getWickets() + "," + b.getEconomyRate());
                writer.newLine();
            }

            System.out.println("DEBUG: Entire match data permanently saved to MatchHistory.txt!");

        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Error saving match history!");
        }
    }
    @FXML
    public void showData() {
        saveMatchDataToFile(this.matchId, teamAName, teamBName);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ScoreCard.fxml"));
            Parent root=loader.load();
            ScoreCardController controller=loader.getController();
            controller.setMatchId(matchId);
            controller.setTeamNames(teamALable.getText(), teamBLable.getText());
            controller.setScoreCardData(innings1Batsmen,innings1Bowlers,innings2Batsmen,innings2Bowlers);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Innings Scorecard");
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void initialize(){
        entryPane.setVisible(true);
        mainScore1.setVisible(false);
        mainScore2.setVisible(false);
        striker=new Player("");
        nonStriker=new Player("");
        currentBowler=new Bowler("");
        currentOverObj =new Over(0);
        overLabels = Arrays.asList(ball1, ball2, ball3, ball4, ball5, ball6);
        resetOverUI();
        updateUI();
    }
}

