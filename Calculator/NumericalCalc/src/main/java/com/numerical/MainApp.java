package com.numerical;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainApp extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/numerical/view/MainView.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("/com/numerical/css/style.css").toExternalForm());
        primaryStage.setTitle("NumeriCalc — Normal & Numerical Methods Calculator");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(520);
        primaryStage.setMinHeight(680);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
