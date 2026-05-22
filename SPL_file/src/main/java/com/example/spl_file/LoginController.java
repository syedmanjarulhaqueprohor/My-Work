package com.example.spl_file;

import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML private TextField fp_answer;
    @FXML private Button fp_back;
    @FXML private Button fp_proceedBtn;
    @FXML private ComboBox<?> fp_question;
    @FXML private AnchorPane fp_questionFrom;
    @FXML private TextField fp_username;

    @FXML private Button np_ChangePassword;
    @FXML private PasswordField np_ConfirmPassword;
    @FXML private Button np_back;
    @FXML private AnchorPane np_newPassFrom;
    @FXML private PasswordField np_newPassword;

    @FXML private Hyperlink s_forgotpassword;
    @FXML private Button s_loginbtn;
    @FXML private AnchorPane s_loginfrom;
    @FXML private PasswordField s_password;
    @FXML private TextField s_username;

    @FXML private Button side_alreadyhaveaccont;
    @FXML private Button side_createbtn;
    @FXML private AnchorPane side_from;

    @FXML private TextField su_answer;
    @FXML private PasswordField su_password;
    @FXML private ComboBox<?> su_question;
    @FXML private AnchorPane su_sideform;
    @FXML private Button su_signup;
    @FXML private TextField su_username;

    private void Error(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
    private void Info(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Message");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public void loginBtn() {
        if (s_username.getText().isEmpty() || s_password.getText().isEmpty()) {
            Error("Incorrect Username/Password");
            return;
        }
        for (String[] user : loginFile.getAllUsers()) {
            if (user[0].equals(s_username.getText()) &&
                    PasswordHashing.verifyPassword(s_password.getText(), user[1])) {
                Info("Successfully Login!");
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("Main.fxml"));
                    Stage stage = new Stage();
                    stage.setTitle("Cricket Score App");
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return;
            }
        }
        Error("Incorrect Username/Password");
    }

    public void regBtn() {
        if (su_username.getText().isEmpty() || su_password.getText().isEmpty()
                || su_question.getSelectionModel().getSelectedItem() == null
                || su_answer.getText().isEmpty()) {

            Error("Please fill all fields");
            return;
        }
        for (String[] user : loginFile.getAllUsers()) {
            if (user[0].equals(su_username.getText())) {
                Error("Username already taken");
                return;
            }
        }
        if (su_password.getText().length() < 8) {
            Error("Password must be at least 8 characters");
            return;
        }
        String hashedPassword = PasswordHashing.hashPassword(su_password.getText());
        loginFile.addUser(
                su_username.getText(),
                hashedPassword,
                (String) su_question.getValue(),
                su_answer.getText()
        );
        Info("Successfully registered!");
        su_username.clear();
        su_password.clear();
        su_question.getSelectionModel().clearSelection();
        su_answer.clear();
        switchToLogin();
    }

    public void switchToLogin(){
        TranslateTransition slider=new TranslateTransition();
        slider.setNode(side_from);
        slider.setToX(0);
        slider.setDuration(Duration.seconds(.5));
        slider.setOnFinished(  e->{
            side_alreadyhaveaccont.setVisible(false);
            side_createbtn.setVisible(true);
        });
        slider.play();
    }

    public void proceedBtn() {
        if (fp_username.getText().isEmpty()
                || fp_question.getSelectionModel().getSelectedItem() == null
                || fp_answer.getText().isEmpty()) {

            Error("Please fill all fields");
            return;
        }
        for (String[] user : loginFile.getAllUsers()) {
            if (user[0].equals(fp_username.getText()) &&
                    user[2].equals(fp_question.getValue()) &&
                    user[3].equals(fp_answer.getText())) {
                np_newPassFrom.setVisible(true);
                fp_questionFrom.setVisible(false);
                return;
            }
        }
        Error("Incorrect information");
    }

    public void changePassword() {
        if (np_newPassword.getText().isEmpty() || np_ConfirmPassword.getText().isEmpty()) {
            Error("Please fill all fields");
            return;
        }
        if (!np_newPassword.getText().equals(np_ConfirmPassword.getText())) {
            Error("Passwords do not match");
            return;
        }
        String hashed = PasswordHashing.hashPassword(np_newPassword.getText());
        boolean success = loginFile.updatePassword(
                fp_username.getText(),
                hashed,
                (String) fp_question.getValue(),
                fp_answer.getText()
        );
        if (success) {
            Info("Password changed successfully");
            s_loginfrom.setVisible(true);
            np_newPassFrom.setVisible(false);
            np_newPassword.clear();
            np_ConfirmPassword.clear();
            fp_answer.clear();
            fp_username.clear();
            fp_question.setValue(null);
        } else {
            Error("User not found");
        }
    }

    private String[] questionList = {
            "What is favorite color?",
            "What is your favorite food?",
            "What is your favorite game?"
    };
    public void question() {
        List<String> list = new ArrayList<>();
        for (String q : questionList) list.add(q);
        ObservableList listData = FXCollections.observableArrayList(list);
        su_question.setItems(listData);
    }
    public void forgotPassQusList() {
        List<String> list = new ArrayList<>();
        for (String q : questionList) list.add(q);
        ObservableList observableList =FXCollections.observableArrayList(list);
        fp_question.setItems(observableList);
    }

    public void switchForgotPass() {
        fp_questionFrom.setVisible(true);
        s_loginfrom.setVisible(false);
        forgotPassQusList();
    }
    public void backToLoginFrom() {
        s_loginfrom.setVisible(true);
        fp_questionFrom.setVisible(false);
    }
    public void backToQusFrom() {
        fp_questionFrom.setVisible(true);
        np_newPassFrom.setVisible(false);
    }

    public void switchFrom(ActionEvent event) {
        TranslateTransition slider = new TranslateTransition();
        if (event.getSource() == side_createbtn) {
            slider.setNode(side_from);
            slider.setToX(300);
            slider.setDuration(Duration.seconds(.5));

            slider.setOnFinished(e -> {
                side_alreadyhaveaccont.setVisible(true);
                side_createbtn.setVisible(false);
                fp_questionFrom.setVisible(false);
                s_loginfrom.setVisible(true);
                np_newPassFrom.setVisible(false);
                question();
            });
        } else {
            slider.setNode(side_from);
            slider.setToX(0);
            slider.setDuration(Duration.seconds(.5));
            slider.setOnFinished(e -> {
                side_alreadyhaveaccont.setVisible(false);
                side_createbtn.setVisible(true);
                fp_questionFrom.setVisible(false);
                s_loginfrom.setVisible(true);
                np_newPassFrom.setVisible(false);
            });
        }
        slider.play();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}