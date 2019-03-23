package com.app.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartController{
    @FXML
    private Button startGameBtn;

    @FXML
    private void initialize(){
        startGameBtn.setOnAction(e->startGame());
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
