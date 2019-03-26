package com.app.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;



public class StartController{
    @FXML
    private Button startGameBtn;

    @FXML
    private ImageView snakeStart;


    private ObservableList<String> levels = FXCollections.observableArrayList("0","1","2");
    @FXML
    ChoiceBox<String> levelBox;

    @FXML
    private void initialize(){
        levelBox.setItems(levels);
        levelBox.setValue("0");
        startGameBtn.setOnAction(e->startGame());
        snakeStart.setImage(new Image(getClass().getClassLoader().getResource("snakeStart.png").toExternalForm()));
    }


    private void startGame(){
        Stage stageCur = (Stage) startGameBtn.getScene().getWindow();
        stageCur.close();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Game.fxml"));
            loader.load();
            GameController gameController = (GameController) loader.getController();
            gameController.setLevel(Integer.valueOf(levelBox.getValue()));
            gameController.startGame();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
