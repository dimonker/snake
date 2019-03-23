package com.app;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        /*Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Start.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("Start");
        primaryStage.setScene(scene);
        primaryStage.show();*/
        primaryStage.setTitle( "Canvas Example" );

        Group root = new Group();
        Scene theScene = new Scene( root );
        primaryStage.setScene( theScene );

        Canvas canvas = new Canvas( 1000, 800 );
        root.getChildren().add( canvas );

        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill( Color.RED );
        gc.setStroke( Color.BLACK );
        gc.setLineWidth(2);
        Font theFont = Font.font( "Times New Roman", FontWeight.BOLD, 48 );
        gc.setFont( theFont );
        gc.fillText( "Hello, World!", 60, 50 );
        gc.strokeText( "Hello, World!", 60, 50 );

        String path = getClass().getClassLoader().getResource("google-earth.jpg").toExternalForm();

        System.out.println(path);
        Image earth = new Image(path);
        gc.drawImage( earth, 180, 100 );

        primaryStage.show();
    }
}