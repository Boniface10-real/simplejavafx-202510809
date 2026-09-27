package com.example.hellofx;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class HelloJavaFX extends Application {
    @Override
    public void start(Stage stage) {
        Label message = new Label("Welcome, BONIFACE CHIKOMO");
        Button startButton = new Button("start");
        startButton.setOnAction(event ->
                message.setText("Great! You clicked the button.")
        );
        Button resetButton = new Button("Reset");
        resetButton.setOnAction(event ->
                message.setText("Welcome to javaFX!")
        );
        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(message, startButton, resetButton);
        Scene scene = new Scene(layout, 500, 300);
        stage.setTitle("202510809");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}