package com.example.spl_file;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class MatchSetupController {
    @FXML
    private ComboBox<String> matchTypeBox;
    @FXML
    private TextField teamAField;
    @FXML
    private TextField overInput;
    @FXML
    private Label overLabel;
    @FXML
    private TextField teamBField;
    @FXML
    private AnchorPane welcomeFrom;
    @FXML
    private AnchorPane previousFrom;
    @FXML
    private TextField prevTeamAField;
    @FXML
    private TextField prevTeamBField;
    @FXML
    private TextField prevMatchIdField;

    List<String> MatchTypes;
    private Object TossFrom;

    @FXML
    public void initialize() {
        MatchTypes = new ArrayList<>();
        MatchTypes.add("T-20");
        MatchTypes.add("T-50");
        MatchTypes.add("T-90");
        MatchTypes.add("Customs");
        matchTypeBox.setItems(FXCollections.observableArrayList(MatchTypes));
        overInput.setVisible(false);
        overLabel.setVisible(false);
        matchTypeBox.setOnAction(event -> handleMatchType());
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void handleMatchType() {
        String selected = matchTypeBox.getValue();
        if (selected.equals("Customs")) {
            overLabel.setVisible(true);
            overInput.setVisible(true);
            overLabel.setText("Enter over:");
        } else {
            overInput.setVisible(false);
            overLabel.setVisible(false);
        }
    }
    public void SubmitBtn() throws IOException {
        String teamAName = teamAField.getText();
        String teamBName = teamBField.getText();
        String matchType = matchTypeBox.getValue();
        if (teamAName.isEmpty() || teamBName.isEmpty() || matchType == null) {
            showError("Please fill all the fields");
            return;
        }
        int overs = 0;
        if (matchType.equals("Customs")) {
            if (overInput.getText().isEmpty()) {
                showError("Enter Over");
                return;
            }
            try {
                overs = Integer.parseInt(overInput.getText());
            } catch (Exception e) {
                showError("Invalid over number");
                return;
            }
        } else {
            overs = Integer.parseInt(matchType.split("-")[1]);
        }
        System.out.println("Match will be " + overs + " overs");
        int matchId = saveMatch(teamAName, teamBName, matchType, overs);
        loadTossScreen(matchId, teamAName, teamBName);
    }
    private int saveMatch(String teamA, String teamB, String matchType, int overs) {
        int matchId = matchFile.addMatch(teamA, teamB, matchType, overs);
        System.out.println("Match data saved successfully!");
        return matchId;
    }
    private void loadTossScreen(int matchId, String teamAName, String teamBName) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Toss.fxml"));
            AnchorPane root = loader.load();
            TossAndPlayerInputController controller = loader.getController();
            controller.setMatchId(matchId);
            controller.setTeamNames(teamAName, teamBName);
            welcomeFrom.getChildren().setAll(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void Search(){
        String searchTeamA = prevTeamAField.getText().trim();
        String searchTeamB = prevTeamBField.getText().trim();
        String searchIdStr = prevMatchIdField.getText().trim();

        if (searchTeamA.isEmpty() || searchTeamB.isEmpty() || searchIdStr.isEmpty()) {
            showError("All fields (Team A, Team B, and Match ID) must be filled up!");
            return;
        }
        try {
            int searchMatchId = Integer.parseInt(searchIdStr);
            loadPreviousMatchData(searchTeamA, searchTeamB, searchMatchId);
        } catch (NumberFormatException e) {
            showError("Match ID must be a valid number!");
        }
    }
    private void loadPreviousMatchData(String teamA, String teamB, int matchID) {
        List<Player> innings1Batsmen = new ArrayList<>();
        List<Bowler> innings1Bowlers = new ArrayList<>();
        List<Player> innings2Batsmen = new ArrayList<>();
        List<Bowler> innings2Bowlers = new ArrayList<>();
        boolean matchFound = false;
        try {
            BufferedReader reader = new BufferedReader(new FileReader("MatchHistory.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 5) continue;
                int fileMatchId = Integer.parseInt(data[0].trim());
                String teamNameFromFile = data[1].trim();
                String role = data[2].trim();
                if (fileMatchId == matchID) {
                    if (teamNameFromFile.equalsIgnoreCase(teamA)) {
                        matchFound = true;
                        if (role.equals("BAT")) {
                            Player p = new Player(data[3]);
                            p.setRuns(Integer.parseInt(data[4]));
                            p.setBalls(Integer.parseInt(data[5]));
                            p.setFours(Integer.parseInt(data[6]));
                            p.setSixes(Integer.parseInt(data[7]));
                            p.setStrikeRate(Double.parseDouble(data[8]));
                            innings1Batsmen.add(p);
                        } else if (role.equals("BOWL")) {
                            Bowler b = new Bowler(data[3]);
                            b.setOvers(Double.parseDouble(data[4]));
                            b.setRunsGiven(Integer.parseInt(data[5]));
                            b.setWickets(Integer.parseInt(data[6]));
                            b.setEconomyRate(Double.parseDouble(data[7]));
                            innings1Bowlers.add(b);
                        }
                    } else if (teamNameFromFile.equalsIgnoreCase(teamB)) {
                        matchFound = true;
                        if (role.equals("BAT")) {
                            Player p = new Player(data[3]);
                            p.setRuns(Integer.parseInt(data[4]));
                            p.setBalls(Integer.parseInt(data[5]));
                            p.setFours(Integer.parseInt(data[6]));
                            p.setSixes(Integer.parseInt(data[7]));
                            p.setStrikeRate(Double.parseDouble(data[8]));
                            innings2Batsmen.add(p);
                        } else if (role.equals("BOWL")) {
                            Bowler b = new Bowler(data[3]);
                            b.setOvers(Double.parseDouble(data[4]));
                            b.setRunsGiven(Integer.parseInt(data[5]));
                            b.setWickets(Integer.parseInt(data[6]));
                            b.setEconomyRate(Double.parseDouble(data[7]));
                            innings2Bowlers.add(b);
                        }
                    }
                }
            }
                if (matchFound && (!innings1Batsmen.isEmpty() || !innings2Batsmen.isEmpty())) {
                    showHistoryScoreCard(matchID, teamA, teamB, innings1Batsmen, innings1Bowlers, innings2Batsmen, innings2Bowlers);
                } else {
                    showError("No previous match found with these team names!");
                }
            } catch(Exception e){
                e.printStackTrace();
                showError("Error reading match history file!");
            }
        }
    private void showHistoryScoreCard(int mId, String tA, String tB, List<Player> i1Bat, List<Bowler> i1Bowl, List<Player> i2Bat, List<Bowler> i2Bowl) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ScoreCard.fxml"));
            Parent root = loader.load();
            ScoreCardController controller = loader.getController();
            controller.setMatchId(mId);
            controller.setTeamNames(tA, tB);
            controller.setScoreCardData(i1Bat, i1Bowl, i2Bat, i2Bowl);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Match ID: " + mId + " | " + tA + " vs " + tB);
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
            showError("Error opening scorecard UI!");
        }
    }
    }

