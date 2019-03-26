package com.app.controllers;

import com.app.Game;
import com.app.RecordsStorage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.ArrayList;

public class GameController{
    private Game game;

    @FXML
    Stage theStage;

    @FXML
    Scene theScene;

    @FXML
    Group root;

    @FXML
    Canvas canvas;

    @FXML
    ImageView trophy;

    @FXML
    ImageView appleImg;

    @FXML
    Text bestScope;

    @FXML
    Text currentScope;

    private int level;
    private GraphicsContext gc;

    public void initialize(){
        theStage.setTitle("SnakeGame");
        ArrayList<String> records = new RecordsStorage().getAllRecords();
        setBestScope(Integer.valueOf(records.get(0).split(",")[0]));

        trophy.setImage(new Image(getClass().getClassLoader().getResource("trophy.png").toExternalForm()));
        trophy.setOnMouseClicked(e -> {
            try {
                FXMLLoader.load(getClass().getClassLoader().getResource("tableOfRecords.fxml"));
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        theStage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, e->{
            try {
                if (!game.isOver())
                    game.saveResult();
                Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("StartMenu.fxml"));
                Scene scene = new Scene(root);
                Stage newStage = new Stage();
                newStage.setTitle("Start");
                newStage.setScene(scene);
                newStage.show();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        appleImg.setImage(new Image(getClass().getClassLoader().getResource("apple.png").toExternalForm()));

        gc = canvas.getGraphicsContext2D();

        theScene.setOnKeyPressed(e -> {
            game.keyPressed(e.getCode().toString());
        });


    }


    public void setCurrentScope(int scope){
        currentScope.setText(String.valueOf(scope));
    }

    public void setBestScope(int scope){
        bestScope.setText(String.valueOf(scope));
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void startGame(){
        game = new Game(this, gc, level);
        game.start();
        theStage.show();
    }
}
