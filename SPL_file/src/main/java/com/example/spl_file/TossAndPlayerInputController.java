package com.example.spl_file;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class TossAndPlayerInputController{
    @FXML
    private AnchorPane TossFrom;

    @FXML
    private Button addPlayer;

    @FXML
    private RadioButton ballRadio;

    @FXML
    private RadioButton batRadio;

    @FXML
    private Button removePlayer;

    @FXML
    private Button startMatch;

    @FXML
    private Label teamAname;

    @FXML
    private Label teamBname;

    @FXML
    private ComboBox<String> tossWinner;
    @FXML
    private ToggleGroup group;

    @FXML
    private ListView<String> teamAList;

    @FXML
    private ListView<String> teamBList;

    @FXML
    private TextField playerInput;

    private ObservableList<String> teamAPlayers = FXCollections.observableArrayList();
    private ObservableList<String> teamBPlayers = FXCollections.observableArrayList();

    private String selectedTeam = null;

    @FXML
    public void initialize() {
        loadTeamsFromFile();
        teamAList.setItems(teamAPlayers);
        teamBList.setItems(teamBPlayers);
        teamAList.setOnMouseClicked(e -> selectedTeam = "A");
        teamBList.setOnMouseClicked(e -> selectedTeam = "B");
    }

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
    public void loadTeamsFromFile(){
        List<String []> match= matchFile.getMatch();
        if(!match.isEmpty()){
            String[] last=match.get(match.size()-1);
            String teamA=last[0];
            String teamB=last[1];
            setTeamNames(teamA,teamB);
        }
    }
    public void setTeamNames(String teamAName, String teamBName) {
        teamAname.setText(teamAName);
        teamBname.setText(teamBName);
        tossWinner.setItems(FXCollections.observableArrayList(teamAName, teamBName));
    }

    public void ConfirmBtn(ActionEvent event) {
        selectedTeam=tossWinner.getValue();
        if(selectedTeam==null){
            System.out.println("Please select toss winner");
            return;
        }
        String decision;
        if(batRadio.isSelected()){
            decision="Bat";
        } else if (ballRadio.isSelected()) {
            decision="Ball";
        }
        else{
            showAlert("Select bat or ball");
            return;
        }
        startMatch.setVisible(true);
        System.out.println("Toss winner: "+selectedTeam+" choose to "+ decision);
    }

    public void addPlayer(ActionEvent event) {
        String name=playerInput.getText().trim();
        if(name.isEmpty()){
            showAlert("Enter player name");
            return;
        }
        if(teamAPlayers.contains(name)||teamBPlayers.contains(name)){
            showAlert("Player already exist");
            return;
        }
        if(selectedTeam==null){
            showAlert("Select a team");
            return;
        }
        if(selectedTeam.equals("A")){
            if(teamAPlayers.size()>=11){
                showAlertError("11 players already taken");
                return;
            }
            teamAPlayers.add(name);
            savePlayerToFile(name,teamAname.getText());
        } else if (selectedTeam.equals("B")) {
            if(teamBPlayers.size()>=11){
                showAlertError("11 players already taken");
                return;
            }
            teamBPlayers.add(name);
            savePlayerToFile(name,teamBname.getText());
        }
    }
    public void savePlayerToFile(String name,String teamName){
    TossFile.addPlayers(matchId,name,teamName);
    }

    public void removePlayer(ActionEvent event) {
        String A=teamAList.getSelectionModel().getSelectedItem();
        String B=teamBList.getSelectionModel().getSelectedItem();
        if(A!=null){
            teamAPlayers.remove(A);
            deletePlayerFromFile(A,teamAname.getText());
        }
        else {
            teamBPlayers.remove(B);
            deletePlayerFromFile(B,teamBname.getText());
        }
    }
    public void deletePlayerFromFile(String playerName,String teamName){
        TossFile.removePlayer(matchId,playerName,teamName);
    }
    private int matchId;
    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }
    public void StartBtn() throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("score.fxml"));
        Parent root = loader.load();
        ScoreController controller = loader.getController();
        controller.setMatchId(matchId);
        controller.setTeamNames(teamAname.getText(), teamBname.getText());
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setTitle("Cricket Score App");
        stage.setMinWidth(600);
        stage.setMinHeight(400);
        stage.setScene(scene);
        stage.show();
    }
}
