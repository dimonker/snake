package com.app.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;



public class StartController{
    @FXML
    private Button startGameBtn;

    @FXML
    private ImageView snakeStart;

    @FXML
    private void initialize(){
        startGameBtn.setOnAction(e->startGame());
        snakeStart.setImage(new Image(getClass().getClassLoader().getResource("snakeStart.png").toExternalForm()));
    }


    private void startGame(){
        Stage stageCur = (Stage) startGameBtn.getScene().getWindow();
        stageCur.close();

        try {
            FXMLLoader.load(getClass().getClassLoader().getResource("Game.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
