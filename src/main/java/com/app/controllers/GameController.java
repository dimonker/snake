package com.app.controllers;

import com.app.*;
import com.sun.xml.internal.ws.commons.xmlutil.Converter;
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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class GameController{
    Game game = new Game();


    @FXML
    Stage theStage;

    @FXML
    Scene theScene;

    @FXML
    Group root;

    public void initialize(){
        theStage.setTitle("SnakeGame");
        Canvas canvas = new Canvas(1000, 800);
        root.getChildren().add(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();


        Snake snake = new Snake();
        theScene.setOnKeyPressed(e -> {
            switch (e.getCode().toString()){
                case "UP":
                    snake.setNextDirection(Direction.UP);
                    break;
                case "DOWN":
                    snake.setNextDirection(Direction.DOWN);
                    break;
                case "LEFT":
                    snake.setNextDirection(Direction.LEFT);
                    break;
                case "RIGHT":
                    snake.setNextDirection(Direction.RIGHT);
                    break;
            }
        });

        snake.setX(5);
        snake.setY(5);

        LongValue lastNanoTime = new LongValue(System.nanoTime());
        new AnimationTimer(){
            @Override
            public void handle(long now) {
                //1000000000
                if (now - lastNanoTime.value < 100000000){
                    return;
                }
                lastNanoTime.value = now;

                int size = Game.CELL_SIZE;
                Predicate<Integer> predicate1 = (x) -> x % 2 == 0;
                Predicate<Integer> predicate2 = (x) -> x % 2 == 1;

                Predicate<Integer> currPredicate = predicate1;
                for (int i = 0; i < 20; i++){
                    for (int j = 0; j < 16; j++){
                        if (currPredicate.test(j))
                            gc.setFill(new Color(0.6,1,0,1));
                        else
                            gc.setFill(new Color(0,1,0,1));

                        gc.fillRect(i * size, j * size, size, size);
                    }

                    if (i % 2 == 1)
                        currPredicate = predicate1;
                    else
                        currPredicate = predicate2;

                }

                snake.update();

                gc.setFill(new Color(0,0,0,1));
                gc.fillRect(snake.getX() * size, snake.getY() * size, size, size);


            }
        }.start();

        theStage.show();
        /*theStage.setTitle( "Collect the Money Bags!" );
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

        theStage.show();*/
    }


}
