package com.example.spl_file;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class ScoreCard2Controller {
    @FXML private TableView<Player> batsmanTable;
    @FXML private TableView<Bowler> bowlerTable;
    @FXML private TableColumn<Player, String> colBatName;
    @FXML private TableColumn<Player, Integer> colRuns;
    @FXML private TableColumn<Player, Integer> colBalls;
    @FXML private TableColumn<Player, Integer> colFours;
    @FXML private TableColumn<Player, Integer> colSixes;
    @FXML private TableColumn<Player, Double> colSR;
    @FXML private TableColumn<Bowler, String> colBowlName;
    @FXML private TableColumn<Bowler, String> colOvers;
    @FXML private TableColumn<Bowler, Integer> colRunsGiven;
    @FXML private TableColumn<Bowler, Integer> colWickets;
    @FXML private TableColumn<Bowler, Double> colER;

    private List<Player> i1Batsman, i2Batsman;
    private List<Bowler> i1Bowler, i2Bowler;
    private int matchId;
    private String teamAName;
    private String teamBName;
    @FXML
    private Label teamALable;
    @FXML
    private Label teamBLable;
    public void setTeamNames(String teamAName,String teamBName){
        this.teamAName=teamAName;
        this.teamBName=teamBName;
        teamALable.setText(teamAName);
        teamBLable.setText(teamBName);
    }
    public void setMatchId(int matchId) {
        this.matchId=matchId;
    }
    @FXML
    public void initialize(){
        colBatName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colRuns.setCellValueFactory(new PropertyValueFactory<>("runs"));
        colBalls.setCellValueFactory(new PropertyValueFactory<>("balls"));
        colFours.setCellValueFactory(new PropertyValueFactory<>("fours"));
        colSixes.setCellValueFactory(new PropertyValueFactory<>("sixes"));
        colSR.setCellValueFactory(new PropertyValueFactory<>("strikeRate"));

        colBowlName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colOvers.setCellValueFactory(new PropertyValueFactory<>("Overs"));
        colRunsGiven.setCellValueFactory(new PropertyValueFactory<>("runsGiven"));
        colWickets.setCellValueFactory(new PropertyValueFactory<>("wickets"));
        colER.setCellValueFactory(new PropertyValueFactory<>("economyRate"));
    }
    public void setScoreCardData(List<Player> innings1Batsman, List<Bowler> innings1Bowler, List<Player> innings2Batsman, List<Bowler> innings2Bowler) {
        this.i1Batsman = innings1Batsman;
        this.i1Bowler = innings1Bowler;
        this.i2Batsman = innings2Batsman;
        this.i2Bowler = innings2Bowler;
        batsmanTable.setItems(FXCollections.observableArrayList(innings2Batsman));
        bowlerTable.setItems(FXCollections.observableArrayList(innings2Bowler));
    }
    @FXML
    public void handleBackButton() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ScoreCard.fxml"));
            Parent root = loader.load();
            ScoreCardController controller1 = loader.getController();
            controller1.setScoreCardData(i1Batsman, i1Bowler, i2Batsman, i2Bowler);
            Stage stage = (Stage) batsmanTable.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) { e.printStackTrace(); }
    }
}
