package com.app.controllers;

import com.app.IntValue;
import com.app.LongValue;
import com.app.Sprite;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

public class GameController{
    @FXML
    Stage theStage;

    @FXML
    Scene theScene;

    @FXML
    Group root;

    public void initialize(){
        theStage.setTitle( "Collect the Money Bags!" );
        Canvas canvas = new Canvas( 512, 512 );
        root.getChildren().add( canvas );

        ArrayList<String> input = new ArrayList<String>();

        theScene.setOnKeyPressed(
                e -> {
                    String code = e.getCode().toString();
                    if ( !input.contains(code) )
                        input.add( code );
                });

        theScene.setOnKeyReleased(
                e -> {
                    String code = e.getCode().toString();
                    input.remove( code );
                });

        GraphicsContext gc = canvas.getGraphicsContext2D();

        Font theFont = Font.font( "Helvetica", FontWeight.BOLD, 24 );
        gc.setFont( theFont );
        gc.setFill( Color.GREEN );
        gc.setStroke( Color.BLACK );
        gc.setLineWidth(1);

        Sprite briefcase = new Sprite();
        briefcase.setImage(getClass().getClassLoader().getResource("sun.png").toExternalForm());
        briefcase.setPosition(200, 0);

        ArrayList<Sprite> moneybagList = new ArrayList<Sprite>();

        for (int i = 0; i < 15; i++)
        {
            Sprite moneybag = new Sprite();
            moneybag.setImage(getClass().getClassLoader().getResource("earth.png").toExternalForm());
            double px = 350 * Math.random() + 50;
            double py = 350 * Math.random() + 50;
            moneybag.setPosition(px,py);
            moneybagList.add( moneybag );
        }

        LongValue lastNanoTime = new LongValue( System.nanoTime() );

        IntValue score = new IntValue(0);

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                // calculate time since last update.
                double elapsedTime = (currentNanoTime - lastNanoTime.value) / 1000000000.0;
                lastNanoTime.value = currentNanoTime;

                // game logic

                briefcase.setVelocity(0,0);
                if (input.contains("LEFT"))
                    briefcase.addVelocity(-50,0);
                if (input.contains("RIGHT"))
                    briefcase.addVelocity(50,0);
                if (input.contains("UP"))
                    briefcase.addVelocity(0,-50);
                if (input.contains("DOWN"))
                    briefcase.addVelocity(0,50);

                briefcase.update(elapsedTime);

                // collision detection

                Iterator<Sprite> moneybagIter = moneybagList.iterator();
                while ( moneybagIter.hasNext() )
                {
                    Sprite moneybag = moneybagIter.next();
                    if ( briefcase.intersects(moneybag) )
                    {
                        moneybagIter.remove();
                        score.value++;
                    }
                }

                // render

                gc.clearRect(0, 0, 512,512);
                briefcase.render( gc );

                for (Sprite moneybag : moneybagList )
                    moneybag.render( gc );

                String pointsText = "Cash: $" + (100 * score.value);
                gc.fillText( pointsText, 360, 36 );
                gc.strokeText( pointsText, 360, 36 );
            }
        }.start();

        theStage.show();
    }


}
