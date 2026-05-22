package com.example.spl_file;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SPLApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SPLApplication.class.getResource("login.fxml"));
        Parent root= fxmlLoader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Cricket Score App");
        stage.setMinHeight(400);
        stage.setMinWidth(600);
        stage.setScene(scene);
        stage.show();
    }
}
