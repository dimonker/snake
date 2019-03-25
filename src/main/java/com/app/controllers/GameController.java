package com.app.controllers;

import com.app.Game;
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
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

public class GameController{
    private Game game = new Game(this);

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

    public void initialize(){
        theStage.setTitle("SnakeGame");

        ArrayList<String> records = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(getClass().getClassLoader().getResource("results.txt").toURI()))){
            stream.forEach(x -> {
                records.add(x);
                return;
            });
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        setBestScope(Integer.valueOf(records.get(0).split(",")[0]));
        trophy.setImage(new Image(getClass().getClassLoader().getResource("trophy.png").toExternalForm()));
        trophy.setOnMouseClicked(e -> {
            game.stop();
        });

        theStage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, e->{
            try {
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

        GraphicsContext gc = canvas.getGraphicsContext2D();

        theScene.setOnKeyPressed(e -> {
            game.keyPressed(e.getCode().toString());
        });

        game.initAnimationTimer(gc);
        game.start();

        theStage.show();
    }


    public void setCurrentScope(int scope){
        currentScope.setText(String.valueOf(scope));
    }

    public void setBestScope(int scope){
        bestScope.setText(String.valueOf(scope));
    }

}
